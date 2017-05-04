CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_MONTHLY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : ÿ��ƾ֤���
  -- ==============================================================

  -----------------------------------------------------------------
  -- ÿ��ƾ֤�������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ʼ���±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- �����±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƫ���±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- �����±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ʼ�����������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ʼ��ƫ�������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ʼ�����������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- �˴��ջ�������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
                                   P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                   P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                   ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ������±���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ����������±���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- Ӫҵ�������±���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_MONTHLY;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_MONTHLY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : ÿ��ƾ֤���
  -- ==============================================================

  -----------------------------------------------------------------
  -- ÿ��ƾ֤���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21(����)
                                P_END_DATE   DATE -- ����ʱ�� 2012-12-22(������)
                                ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS

    V_START_PERIOD VARCHAR2(50); --��ʼ�ڼ�
    V_END_PERIOD   VARCHAR2(50); --�����ڼ�

  BEGIN

    --��ʼ�ڼ�ͽ����ڼ�
    V_START_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    -- ɾ����Ҫ����ͳ�Ƶ��»��ܱ���
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVS T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
      USING V_START_PERIOD, V_END_PERIOD;

    --ͳ���»�������
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_START_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY',
                                        'ƾ֤�����»��ܳ����쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_MONTHLY;

  -----------------------------------------------------------------
  -- ʼ���±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) UPD_DEST_RCV_WO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_WO_INCOME, --���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_COST, --������ɱ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_RCV_POD, --�����ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_RCV_NPOD, --�����ʼ��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_PAY_APPLY, --���⸶������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --����嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --����嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.SF_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) SF_PAY_APPLY, --װж�Ѹ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --Ӧ�����ջ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --Ӧ�����ջ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_CH, --Ԥ�տͻ��ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_CD, --Ԥ�տͻ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --Ԥ�տͻ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD, --Ԥ�տͻ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_RCV_NPOD, --Ԥ�տͻ���Ӧ��ʼ���˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_RCV_POD, --Ԥ�տͻ���Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_PAY_APPLY, --ʼ����Ԥ�ո�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.EX_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) EX_ORIG_RCV_POD, --Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.EX_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) EX_DEST_RCV_POD, --Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BD_ORIG_RCV_POD, --���˳�Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BD_DEST_RCV_POD, --���˳�Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_AC,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_AC, --СƱ�ֽ�֮�¹����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_SI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_SI, --СƱ�ֽ�֮������Ʒ����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_OPAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_OPAY, --СƱ�ֽ�֮�ͻ��ึ�˷ѻ��̵㳤����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_OTHER,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_OTHER, --СƱ�ֽ�֮����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_MBI, --СƱ�ֽ���Ӫҵ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_AC,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_AC, --СƱ����֮�¹����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_BANK_IT,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_BANK_IT, --СƱ����֮����Ա����Ϣ
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_OPAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_OPAY, --СƱ����֮�ͻ��ึ�˷ѻ��̵㳤����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_OTHER,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_OTHER, --СƱ����֮����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_MBI, --СƱ������Ӫҵ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_MBI, --СƱӦ����Ӫҵ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_UR_CH, --�����ֽ��СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_UR_CD, --�������г�СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_COD_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_COD_PAY, --Ӧ�����ջ����СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_CLAIM_PAY, --Ӧ�������СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_CUST_DR,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_CUST_DR, --Ԥ�տͻ���СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_BD_DEBT,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_BD_DEBT, --����֮���������СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_BD_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_BD_INCOME, --����֮������ʧ��СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CTDTOL_NWO,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CTDTOL_NWO, --����Ϊ�½���ʱǷ������֧��δ����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CTDTOL_WO,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CTDTOL_WO, --����Ϊ�½���ʱǷ������֧���Ѻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CHCD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CHCD, --����Ϊ�ֽ����п�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_WO_INCOME, --���˷ѳ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_COST, --���˷���ɱ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_PAY_APPLY, --���˷Ѹ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --���˷ѳ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CN_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CN_ORIG_PAY_APPLY, --ʼ�����񲹾ȸ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --Ԥ�տ��˴����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --����С��ҵ��
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.DE_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) DE_CH, --�����ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.DE_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) DE_CD, --�������п�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_CASH_COLLECTED_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_CASH_COLLECTED_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_CASH_COLLECTED_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_CASH_COLLECTED_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_CASH_COLLECTED_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_CASH_COLLECTED_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_CASH_COLLECTED_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_DEST_RCV_WO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_DEST_RCV_WO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_DEST_RCV_WO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_DEST_RCV_WO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_DEST_RCV_WO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_DEST_RCV_WO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_DEST_RCV_WO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_FRT, --�������˷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_PUP, --�ӻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_DEL, --�ͻ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_PKG, --��װ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_DV, --���۷�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_COD, --���ջ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_OT, --��������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_ORIG_RCV_POD, --���˷�_������������_���˷ѳ�ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_ORIG_RCV_NPOD, --���˷�_������������_���˷ѳ�ʼ��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ��δǩ��
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
              --��������䳡��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFO',
                                        'ƾ֤����ʼ����������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_RFO;

  -----------------------------------------------------------------
  -- �����±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_COST, --������ɱ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_PAY_APPLY, --���⸶������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --����嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --����嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --������ջ�������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --���˷ѳ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_COST, --���˷���ɱ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_PAY_APPLY, --���˷Ѹ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CN_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CN_DEST_PAY_APPLY, --������񲹾ȸ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --Ԥ�տͻ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --Ԥ�տͻ���Ӧ�յ����˷���ǩ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --����
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --���ÿ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFD',
                                        'ƾ֤���ɵ����±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_RFD;

  -----------------------------------------------------------------
  -- ƫ���±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_VECH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_VECH, --�ⷢ��¼��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_CONFIRM, --ƫ�ߴ���ɱ�ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_NOT_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_NOT_CONFIRM, --ƫ�ߴ���ɱ���ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_PAY_APPLY, --ƫ�ߴ���ɱ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_CH, --Ԥ��ƫ�ߴ����ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_CD, --Ԥ��ƫ�ߴ�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_PAY_APPLY --ƫ����Ԥ�ո�������
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- ƫ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLR',
                                        'ƾ֤����ƫ���±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_PLR;

  -----------------------------------------------------------------
  -- �����±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) AIR_COD_CD_NPOD, --���˻�����ջ�������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD, --��������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_NPOD, --���˵������Ӧ����Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_NPOD, --��������Ӧ����Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_COM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_COM, --Ԥ�����չ�˾
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_WO_COM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_WO_COM_PAY, --Ԥ����Ӧ�����չ�˾
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_WO_OTH_PAY, --Ԥ��������Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BDR_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BDR_WO_OTH_RCV, --���˳�����Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_COM_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_COM_CONFIRM, --���˺��չ�˾�˷�ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_ORIG_AGENCY_CFM, --���˳�������Ӧ��ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_GEN,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_GEN, --���˵������Ӧ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_CFM, --���˵������Ӧ���ɱ�ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_NCFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_NCFM, --���˵������Ӧ���ɱ���ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_PAY_APPLY, --���˳ɱ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_ENTRY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_ENTRY, --��������Ӧ��¼��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_RCV_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_RCV_CH, --�����������Ӧ���ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_RCV_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_RCV_CD, --�����������Ӧ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --Ԥ�տ��˴����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_CH, --Ԥ�տ��˴����ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_CD, --Ԥ�տ��˴�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_OTHER_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_OTHER_RCV, --Ԥ�տ��˴��������Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_NPOD, --Ԥ�տ��˴����Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_PAY_APPLY, --������Ԥ�ո�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_OTH_RCV, --����Ӧ��������Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --���˻�����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --���˻�����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --���˷�ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_NPOD, --���˻�����ջ����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_OTHER_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_OTHER_CONFIRM
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --����
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
                                        'ƾ֤���ɿ����±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_AFR;

  -----------------------------------------------------------------
  -- ʼ�����������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_RFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    --ʼ��
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
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG, --ʼ��
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --����嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --����嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --Ӧ�����ջ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --Ӧ�����ջ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --������ջ�������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_WO_OR_RCV, --Ӧ�����ջ����СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME , --���˷ѳ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --Ԥ�տͻ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --Ԥ�տͻ���Ӧ�յ����˷���ǩ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND ((T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --����
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --���ÿ��
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

    --����
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
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST, --����
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --����嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --����嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --Ӧ�����ջ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --Ӧ�����ջ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --������ջ�������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_WO_OR_RCV, --Ӧ�����ջ����СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --���˷ѳ�����
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --���˷�_���ﲿ������_���˷ѳ嵽��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --Ԥ�տͻ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --Ԥ�տͻ���Ӧ�յ����˷���ǩ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND ((T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --��׼����
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --��׼����(��;)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --����
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --�����
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFI',
                                        'ƾ֤����ʼ��ר�������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_RFI;

  -----------------------------------------------------------------
  -- ʼ��ƫ�������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD --Ԥ��ƫ�ߴ����Ӧ�յ����˷�δǩ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- ƫ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --Ԥ��ƫ�ߴ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD --Ԥ��ƫ�ߴ����Ӧ�յ����˷�δǩ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- ƫ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLI',
                                        'ƾ֤����ʼ��ƫ�������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_PLI;

  -----------------------------------------------------------------
  -- ʼ�����������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --Ԥ�տ��˴����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --���˻�����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --���˻�����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --���˷�ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_NWO_COD, --����ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_NWO_COD, --���˷�ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD --��������Ӧ����Ӧ�մ��ջ�����ǩ��
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
                 0) UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --Ԥ�տ��˴����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --���˻�����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --���˻�����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --���˷�ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_NWO_COD, --����ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_NWO_COD, --���˷�ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD --��������Ӧ����Ӧ�մ��ջ�����ǩ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
                                        'ƾ֤����ʼ�����������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_AFI;

  -----------------------------------------------------------------
  -- �˴��ջ�������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
                                   P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                   P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                   ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD',
                                        'ƾ֤���ɴ��ջ��������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_RETURN_COD;

  -----------------------------------------------------------------
  -- ������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) LAND_COST_AGENCY_GEN, --��������Ӧ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_AGENCY_CFM, --��������Ӧ���ɱ�ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_AGENCY_NCFM, --��������Ӧ���ɱ���ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_OTHER_CONFIRM, --����Ӧ���ɱ�ȷ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_PAY_APPLY, --�����ɱ���������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_ENTRY, --���������Ӧ��¼��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_RCV_CH, --�������������Ӧ���ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_RCV_CD, --�������������Ӧ������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_NPOD, --����仹����ջ����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_NPOD, --����仹����ջ�������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --�����Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_NPOD, --����䵽�����Ӧ����Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_NPOD, --���������Ӧ����Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_BDR_WO_OTH_RCV, --���˳�����Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_OTH_RCV, --����Ӧ��������Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_CH, --Ԥ�����������ֽ�
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_CD, --Ԥ��������������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_OTHER_RCV, --Ԥ���������������Ӧ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD, --Ԥ�����������Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_NPOD, --Ԥ�����������Ӧ�մ��ջ���δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_PAY_APPLY, --�������Ԥ�ո�������
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_COM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_COM, --Ԥ������乫˾
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_WO_COM_PAY, --Ԥ����Ӧ������乫˾
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_WO_OTH_PAY --Ԥ��������Ӧ��
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG --�����
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
                                        'ƾ֤����������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_LDD;

  -----------------------------------------------------------------
  -- ����������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_NWO_COD, --�����ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_NWO_COD, --����䷴ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD --Ԥ�����������Ӧ�մ��ջ�����ǩ��
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
                 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_NWO_COD, --�����ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_NWO_COD, --����䷴ǩ��ʱδ�������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --����������ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD --Ԥ�����������Ӧ�մ��ջ�����ǩ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
                                        'ƾ֤��������������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_LDI;

  -----------------------------------------------------------------
  -- Ӫҵ�������±������
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                            P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                            ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
                 0) CUST_DR_ORIG_LAND_RCV_NPOD, --Ԥ�տͻ�����Ӧ��ʼ���˷�δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_LAND_RCV_POD, --Ԥ�տͻ�����Ӧ��ʼ���˷���ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_LAND_RCV_POD, --�������ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_LAND_RCV_NPOD, --�������ʼ��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_LAND_RCV_WO_CLAIM_PAY, --Ӧ���������СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_LAND_RCV_WO_CUST_DR, --Ԥ�տͻ�����СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) REFUND_WO_ORIG_LAND_RCV_POD, --���˷ѳ���ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) REFUND_WO_ORIG_LAND_RCV_NPOD, --���˷ѳ���ʼ��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_CLAIM_WO_ORIG_RCV_POD, --��������ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_CLAIM_WO_ORIG_RCV_NPOD, --��������ʼ��Ӧ��δǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_LAND_CLAIM_PAY, --���Ӧ�������СƱӦ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_REFUND_WO_ORIG_RCV_POD, --������˷ѳ�ʼ��Ӧ����ǩ��
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_REFUND_WO_ORIG_RCV_NPOD --������˷ѳ�ʼ��Ӧ��δǩ��
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
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO',
                                        'ƾ֤����Ӫҵ�������±�������쳣:' || SQLERRM);

      --����ʧ�ܱ�־
      RETURN FALSE;

  END FUNC_VOUCHER_LWO;

END PKG_STL_VCH_MONTHLY;
/
