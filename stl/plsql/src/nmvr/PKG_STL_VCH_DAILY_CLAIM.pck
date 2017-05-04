CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CLAIM IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : ÿ��ƾ֤����
  -- ==============================================================

  -----------------------------------------------------------------
  -- ���⴦������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_CLAIM;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CLAIM IS

  -----------------------------------------------------------------
  -- ���⴦������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    --1) ����-������������-01���������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2) ����-������������-01������ɱ�
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --�������ȥ���˷�,������С��0,ֵΪ0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --3) ����-������������-02���������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --4) ����-������������-02������ɱ�
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --�������ȥ���˷�,������С��0,ֵΪ0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --5) ����-������������-01�����01ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVO_POD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --6) ����-������������-01�����02ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --6I) ����-������������-01�����02ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7) ����-������������-02�����01ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7I) ����-������������-02�����01ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --8) ����-������������-01�����01ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_ORIG_RCVO_NPOD,
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.Payable_No, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9) ����-������������-01�����02ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9I) ����-������������-01�����02ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) ����-������������-02�����01ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10I) ����-������������-02�����01ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --11) ����-������������-01���⸶������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --11I) ����-������������-01���⸶������
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --12) ����-������������-02���⸶������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --13) ����-������������-02�����02ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_POD,
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --14) ����-������������-02�����02ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_NPOD,
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.Payable_No, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --15) ����-���ﲿ������-01���������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --15I) ����-���ﲿ������-01���������
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --16) ����-���ﲿ������-01������ɱ�
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --�������ȥ���˷�,������С��0,ֵΪ0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND WAYBILL.RECEIVE_ORG_CODE <> WAYBILL.LAST_LOAD_ORG_CODE
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --17) ����-���ﲿ������-02���������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --17I) ����-���ﲿ������-02���������
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --���˷ѡ�����������ȡС
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --18) ����-���ﲿ������-01������ɱ�
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --�������ȥ���˷�,������С��0,ֵΪ0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND WAYBILL.RECEIVE_ORG_CODE <> WAYBILL.LAST_LOAD_ORG_CODE
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --19) ����-���ﲿ������-01�����01����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --19I) ����-���ﲿ������-01�����01����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20) ����-���ﲿ������-02�����01����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20I) ����-���ﲿ������-02�����01����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21) ����-���ﲿ������-01�����02����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21I) ����-���ﲿ������-01�����02����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22) ����-���ﲿ������-02�����02����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22I) ����-���ﲿ������-02�����02����Ӧ����ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23) ����-���ﲿ������-01�����01����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23I) ����-���ﲿ������-01�����01����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         -- AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24) ����-���ﲿ������-02�����01����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24I) ����-���ﲿ������-02�����01����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25) ����-���ﲿ������-01�����02����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25I) ����-���ﲿ������-01�����02����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26) ����-���ﲿ������-02�����02����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --ʼ����������
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --���ﲿ������
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26I) ����-���ﲿ������-02�����02����Ӧ��δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             WO.AMOUNT,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --27) ����-���ﲿ������-01���⸶������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --27I) ����-���ﲿ������-01���⸶������
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�跽���ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --�跽��������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --�������ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --������������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --28) ����-���ﲿ������-02���⸶������
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.CLAIM_DESTT_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --6) ����-������������-01�����02ʼ��Ӧ����ǩ�� --01ʼ������                                                                                             
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_PODP, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7) ����-������������-02�����01ʼ��Ӧ����ǩ�� --02����ʼ��                                                                                             
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_PODP,
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9) ����-������������-01�����02ʼ��Ӧ��δǩ�� --01����ʼ��                                                                                             
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPODP,
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) ����-������������-02�����01ʼ��Ӧ��δǩ�� --02����ʼ��                                                                                            
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPODP,
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --19) ����-���ﲿ������-01�����01����Ӧ����ǩ�� --01�ﵽ                                                                                                
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_PODP, --ҵ�񳡾�                                                                                    
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20) ����-���ﲿ������-02�����01����Ӧ����ǩ�� --02����                                                                                                
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_PODP, --ҵ�񳡾�                                                                                    
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23) ����-���ﲿ������-01�����01����Ӧ��δǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPODP, --ҵ�񳡾�                                                                                   
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24) ����-���ﲿ������-02�����01����Ӧ��δǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPODP, --ҵ�񳡾�                                                                                   
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21) ����-���ﲿ������-01�����02����Ӧ����ǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_PODP, --ҵ�񳡾�                                                                                    
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22) ����-���ﲿ������-02�����02����Ӧ����ǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_PODP, --ҵ�񳡾�                                                                                    
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25) ����-���ﲿ������-01�����02����Ӧ��δǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPODP, --ҵ�񳡾�                                                                                   
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26) ����-���ﲿ������-02�����02����Ӧ��δǩ��                                                                                                         
    INSERT INTO STV.T_STL_NDVD
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
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID                                                                                                                                
             P_PERIOD, --�ڼ�                                                                                                                                
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPODP, --ҵ�񳡾�                                                                                   
             PAY.CUSTOMER_CODE, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_NAME, --�ͻ�����                                                                                                                   
             PAY.CUSTOMER_TYPE, --�ͻ�����                                                                                                                   
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --ʼ�����ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --ʼ����������                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --���ﲿ�ű���                                                                                                       
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --���ﲿ������                                                                                                       
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���                                                                                               
             PAY.PAYABLE_NO, --���ݱ��                                                                                                                      
             WO.ACCOUNT_DATE, --��������                                                                                                                     
             PAY.BUSINESS_DATE, --ҵ������                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������                                                                                                 
             PAY.BILL_TYPE, --����������                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --������                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '���⴦������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_CLAIM;
/
