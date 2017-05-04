CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_APWR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : ÿ��ƾ֤����Ӧ����Ӧ��
  -- ==============================================================

  -----------------------------------------------------------------
  -- ����Ӧ����Ӧ�մ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_APWR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_APWR IS

  -----------------------------------------------------------------
  -- ����Ӧ����Ӧ�մ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    --01) Ӧ���������ɱ���01Ӧ�յ����˷���ǩ��
    --02) Ӧ���������ɱ���01Ӧ�յ����˷�δǩ��
    --03) Ӧ���������ɱ���02Ӧ�յ����˷���ǩ��
    --04) Ӧ���������ɱ���02Ӧ�յ����˷�δǩ��
    --05) ����Ӧ����01Ӧ�յ����˷���ǩ��
    --06) ����Ӧ����01Ӧ�յ����˷�δǩ��
    --07) ����Ӧ����02Ӧ�յ����˷���ǩ��
    --08) ����Ӧ����02Ӧ�յ����˷�δǩ��
    --09) ����Ӧ��������Ӧ��
    --10) ���� Ӧ���������ɱ���01Ӧ�յ����˷���ǩ��
    --11) ���� Ӧ���������ɱ���01Ӧ�յ����˷�δǩ��
    --12) ���� Ӧ���������ɱ���02Ӧ�յ����˷���ǩ��
    --13) ���� Ӧ���������ɱ���02Ӧ�յ����˷�δǩ��
    --14) ���� ����Ӧ����01Ӧ�յ����˷���ǩ��
    --15) ���� ����Ӧ����01Ӧ�յ����˷�δǩ��
    --16) ���� ����Ӧ����02Ӧ�յ����˷���ǩ��
    --17) ���� ����Ӧ����02Ӧ�յ����˷�δǩ��
  
    --01) Ӧ���������ɱ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --02) Ӧ���������ɱ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ�� 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --03) Ӧ���������ɱ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ�� 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --04) Ӧ���������ɱ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --05) ����Ӧ����01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --06) ����Ӧ����01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ�� 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --07) ����Ӧ����02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ�� 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --08) ����Ӧ����02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --09) ����Ӧ��������Ӧ��
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_OTH_RCV,
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             PA.ORIG_ORG_CODE, --ʼ�����ű���
             PA.ORIG_ORG_NAME, --ʼ����������
             PA.DEST_ORG_CODE, --���ﲿ�ű���
             PA.DEST_ORG_NAME, --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ���Ϳ���
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AR --��������Ӧ��
         AND PA.BILL_TYPE IN
             (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --��������Ӧ��
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, --���չ�˾�˷�
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, --���˳�������Ӧ��
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY) --���˵������Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ӧ��02 Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) ���� Ӧ���������ɱ���01Ӧ�յ����˷���ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT, --���
             RCV.PRODUCT_CODE, --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL ����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --���˵������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --��������Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --11) ���� Ӧ���������ɱ���01Ӧ�յ����˷�δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT, --���
             RCV.PRODUCT_CODE, --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL ����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --���˵������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --��������Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) ���� Ӧ���������ɱ���02Ӧ�յ����˷���ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL ����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --���˵������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --��������Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --13) ���� Ӧ���������ɱ���02Ӧ�յ����˷�δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL ����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --���˵������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --��������Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --14) ���� ����Ӧ����01Ӧ�յ����˷���ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT, --���
             RCV.PRODUCT_CODE, --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --��������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --���������Ӧ�� 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --15) ���� ����Ӧ����01Ӧ�յ����˷�δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RCV.CUSTOMER_CODE, --�ͻ�����
             RCV.CUSTOMER_NAME, --�ͻ�����
             RCV.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --�˵���
             RCV.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RCV.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RCV.BILL_TYPE, --����������
             WO.AMOUNT, --���
             RCV.PRODUCT_CODE, --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --��������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --���������Ӧ�� 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) ���� ����Ӧ����02Ӧ�յ����˷���ǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --��������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --���������Ӧ�� 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --17) ���� ����Ӧ����02Ӧ�յ����˷�δǩ��
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --�ڼ�
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             WO.AMOUNT, --���
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --��Ʒ����
             PA.DEST_ORG_CODE, --Ӧ�����ű���
             PA.DEST_ORG_NAME, --Ӧ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --ʼ�����ű���
             RCV.ORIG_ORG_NAME, --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --����䵽�����Ӧ��  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --��������Ӧ��
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --���������Ӧ�� 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '����Ӧ����Ӧ�մ�������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_APWR;
/
