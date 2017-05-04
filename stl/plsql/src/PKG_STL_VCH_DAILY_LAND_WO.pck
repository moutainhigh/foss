CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_LAND_WO IS

  -- ==============================================================
  -- Author  : DDW
  -- Created : 2013-09-23
  -- Purpose : Ӫҵ�������±�����ϸ
  -- ==============================================================

  -----------------------------------------------------------------
  -- Ӫҵ�������±���������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_LAND_WO;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_LAND_WO IS

  -----------------------------------------------------------------
  -- Ӫҵ�������±���������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
    --Ӫҵ��Ԥ�տͻ��������Ӧ��
  
    --1)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ��ʼ���˷�δǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD, --ҵ�񳡾�,Ԥ�տͻ�����Ӧ��ʼ���˷�δǩ��
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DR.CREATE_ORG_CODE, --Ӫҵ���ű���
             DR.CREATE_ORG_NAME, --Ӫҵ��������
             RE.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RE.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---Ӧ�յ��ĵ���������Ϊʼ��Ӧ��
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --2)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ��ʼ���˷���ǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD, --ҵ�񳡾�,Ԥ�տͻ�����Ӧ��ʼ���˷���ǩ��
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DR.CREATE_ORG_CODE, --Ӫҵ���ű���
             DR.CREATE_ORG_NAME, --Ӫҵ��������
             RE.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RE.EXPRESS_ORIG_ORG_NAME, --��ݲ�������,
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---Ӧ�յ��ĵ���������Ϊʼ��Ӧ��
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --Ӫҵ������������Ӧ��
  
    --3) ����-������������-�����ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD, --ҵ�񳡾�,�������ʼ��Ӧ����ǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             PAY.PAYABLE_ORG_CODE, --Ӫҵ���ű���
             PAY.PAYABLE_ORG_NAME, --Ӫҵ��������
             RCV.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RCV.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --4) ����-������������-�����ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD, --ҵ�񳡾�,�������ʼ��Ӧ��δǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             PAY.PAYABLE_ORG_CODE, --Ӫҵ���ű���
             PAY.PAYABLE_ORG_NAME, --Ӫҵ��������
             RCV.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RCV.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --Ӫҵ���������СƱӦ��
  
    --5)СƱӦ�պ���  Ӧ�������СƱӦ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY, --ҵ�񳡾�,Ӧ���������СƱӦ��
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             PA.PAYABLE_ORG_CODE, --Ӫҵ���ű���
             PA.PAYABLE_ORG_NAME, --Ӫҵ��������
             RE.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RE.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             WO.WRITEOFF_TIME, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- Ӧ�ճ�Ӧ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---Ӧ�յ�ΪСƱ
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- Ӧ������
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --6)СƱӦ�պ���  Ԥ�տͻ���СƱӦ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR, --ҵ�񳡾�,Ԥ�տͻ�����СƱӦ��
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DR.CREATE_ORG_CODE, --Ӫҵ���ű���
             DR.CREATE_ORG_NAME, --Ӫҵ��������
             RE.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RE.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             WO.WRITEOFF_TIME, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --��Դ��СƱ
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --Ӫҵ�����˷ѳ������Ӧ��
  
    --7) ���˷�-������������-���˷ѳ�ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD, --ҵ�񳡾�,���˷ѳ���ʼ��Ӧ����ǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             PAY.PAYABLE_ORG_CODE, --Ӫҵ���ű���
             PAY.PAYABLE_ORG_NAME, --Ӫҵ��������
             RCV.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RCV.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --8) ���˷�-������������-���˷ѳ�ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD, --ҵ�񳡾�,���˷ѳ���ʼ��Ӧ��δǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             PAY.PAYABLE_ORG_CODE, --Ӫҵ���ű���
             PAY.PAYABLE_ORG_NAME, --Ӫҵ��������
             RCV.EXPRESS_ORIG_ORG_CODE, --��ݲ��ű���
             RCV.EXPRESS_ORIG_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --����������Ӫҵ��Ӧ��
  
    --9) ����-������������-�����ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD, --ҵ�񳡾�,��������ʼ��Ӧ����ǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             RCV.ORIG_ORG_CODE, --Ӫҵ���ű���
             RCV.ORIG_ORG_NAME, --Ӫҵ��������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --��ݲ��ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME��PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --10) ����-������������-�����ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD, --ҵ�񳡾�,��������ʼ��Ӧ��δǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             RCV.ORIG_ORG_CODE, --Ӫҵ���ű���
             RCV.ORIG_ORG_NAME, --Ӫҵ��������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --��ݲ��ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME��PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --��ݳ���Ӫҵ��Ӧ��СƱ
  
    --11)СƱӦ�պ���  Ӧ�������СƱӦ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY, --ҵ�񳡾�,���Ӧ�������СƱӦ��
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.ORIG_ORG_CODE, --Ӫҵ���ű���
             RE.ORIG_ORG_NAME, --Ӫҵ��������
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PA.PAYABLE_ORG_CODE,
                           PA.ORIG_ORG_CODE,
                           PA.EXPRESS_ORIG_ORG_CODE,
                           PA.DEST_ORG_CODE,
                           PA.EXPRESS_DEST_ORG_CODE��PA.PAYABLE_ORG_CODE),
                    PA.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --��ݲ��ű���
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PA.PAYABLE_ORG_NAME,
                           PA.ORIG_ORG_NAME,
                           PA.EXPRESS_ORIG_ORG_NAME,
                           PA.DEST_ORG_NAME,
                           PA.EXPRESS_DEST_ORG_NAME��PA.PAYABLE_ORG_NAME),
                    PA.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --��ݲ�������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             WO.WRITEOFF_TIME, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- Ӧ�ճ�Ӧ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---Ӧ�յ�ΪСƱ
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- Ӧ������
         AND PA.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --������˷ѳ���Ӫҵ��Ӧ��
  
    --12) ���˷�-������������-���˷ѳ�ʼ��Ӧ����ǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD, --ҵ�񳡾�,������˷ѳ�ʼ��Ӧ����ǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             RCV.ORIG_ORG_CODE, --Ӫҵ���ű���
             RCV.ORIG_ORG_NAME, --Ӫҵ��������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --��ݲ��ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME��PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --13) ���˷�-������������-���˷ѳ�ʼ��Ӧ��δǩ��
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD, --ҵ�񳡾�,������˷ѳ�ʼ��Ӧ��δǩ��
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             RCV.ORIG_ORG_CODE, --Ӫҵ���ű���
             RCV.ORIG_ORG_NAME, --Ӫҵ��������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --��ݲ��ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME��PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --��ݲ�������
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'Ӫҵ�������±���������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_LAND_WO;
/
