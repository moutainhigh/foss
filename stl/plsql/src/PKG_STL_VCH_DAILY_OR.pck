CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_OR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-05-04
  -- Purpose : ÿ��ƾ֤����
  -- ==============================================================

  -----------------------------------------------------------------
  -- ƾ֤������������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_OR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_OR IS

  -----------------------------------------------------------------
  -- ƾ֤СƱ��������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
    --1)СƱ¼���ֽ�  СƱ�ֽ�֮�¹����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ𣬹���СƱ�տ����Ϊ���¹�˽����� */
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
             PKG_STL_VCH_COMMON.OR_CH_AC, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'A' --�¹�˽����� 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --2)СƱ¼���ֽ�  СƱ�ֽ�֮������Ʒ����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�ȡ�ֽ��տ�Ľ�
    �ֽ��տ����ԴΪСƱ���ֽ��տ�ĸ��ʽΪ�ֽ�
    ����СƱ�տ����Ϊ��������Ʒ���� */
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
             PKG_STL_VCH_COMMON.OR_CH_SI, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'R' --������Ʒ���� 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --3)СƱ¼���ֽ�  СƱ�ֽ�֮�ͻ��ึ�˷ѻ��̵㳤����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ𣬹���СƱ�տ����Ϊ��
    �ͻ��ึ�˷ѣ���Ҫ������̵㳤����  */
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
             PKG_STL_VCH_COMMON.OR_CH_OPAY, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES IN ('B', 'G') --�ͻ��ึ�˷ѣ���Ҫ������̵㳤����  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --4)СƱ¼���ֽ�  СƱ�ֽ�֮����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ�
    ����СƱ�տ����Ϊ�������ֵ��г��ͻ��ึ�˷ѣ���Ҫ�����
    �̵㳤���� ������Ա����Ϣ ��������Ʒ���롢 �¹�˽�����֮��ķ���Ӫ��������    */
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
             PKG_STL_VCH_COMMON.OR_CH_OTHER, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'O' --��������Ӫ��������  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --5)СƱ¼���ֽ�  СƱ�ֽ���Ӫҵ������  
    /*           ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ𣬹���СƱ�տ����Ϊ��
    �����ֵ��е���Ӫ��������    */
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
             PKG_STL_VCH_COMMON.OR_CH_MBI, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --��Ӫ��������  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
            --- and ORE.ACTIVE=PKG_STL_COMMON.YES
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --1)СƱ¼������  СƱ����֮�¹����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ���п�����㡢֧Ʊ������֧��������СƱ�տ����Ϊ���¹�˽����� */
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
             PKG_STL_VCH_COMMON.OR_CD_AC, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'A' --�¹�˽����� 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --7)СƱ¼������  СƱ����֮����Ա����Ϣ  
    /*           ���ֽ��տ�ļ���������ͳ�ƣ�ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ���п�����㡢֧Ʊ������֧��������СƱ�տ����Ϊ������Ա����Ϣ  */
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
             PKG_STL_VCH_COMMON.OR_CD_BANK_IT, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'H' --����Ա����Ϣ
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --8)СƱ¼������  СƱ����֮�ͻ��ึ�˷ѻ��̵㳤����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ𣬹���СƱ�տ����Ϊ��
    �ͻ��ึ�˷ѣ���Ҫ������̵㳤����  */
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
             PKG_STL_VCH_COMMON.OR_CD_OPAY, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES IN ('B', 'G') --�ͻ��ึ�˷ѣ���Ҫ������̵㳤����  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --9)СƱ¼������  СƱ����֮����  
    /*            ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ�
    ����СƱ�տ����Ϊ�������ֵ��г��ͻ��ึ�˷ѣ���Ҫ�����
    �̵㳤���� ������Ա����Ϣ ��������Ʒ���롢 �¹�˽�����֮��ķ���Ӫ��������    */
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
             PKG_STL_VCH_COMMON.OR_CD_OTHER, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES = 'O' --��������Ӫ��������  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --10)СƱ¼������  СƱ������Ӫҵ������  
    /*           ���ֽ��տ�ļ���������ͳ�ƣ�
    ȡ�ֽ��տ�Ľ��ֽ��տ����ԴΪСƱ��
    �ֽ��տ�ĸ��ʽΪ�ֽ𣬹���СƱ�տ����Ϊ��
    �����ֵ��е���Ӫ��������    */
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
             PKG_STL_VCH_COMMON.OR_CD_MBI, --ҵ�񳡾�
             CASH.CUSTOMER_CODE, --�ͻ�����
             CASH.CUSTOMER_NAME, --�ͻ�����
             CASH.CUSTOMER_TYPE, --�ͻ�����
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --�˵���
             CASH.CASH_COLLECTION_NO, --���ݱ��
             CASH.ACCOUNT_DATE, --�������
             CASH.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
             CASH.BILL_TYPE, --����������
             CASH.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --��Ӫ��������  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --11)СƱ¼��Ӧ��  СƱӦ����Ӫҵ������  
    /*           ��Ӧ�յ��ļ���������ͳ�ƣ�ȡӦ�յ��Ľ�
    Ӧ�յ��ĵ���������ΪСƱӦ�գ�����СƱ�տ����Ϊ�������ֵ��е���Ӫ��������   */
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
             PKG_STL_VCH_COMMON.OR_RCV_MBI, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             RE.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             RE.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_RECEIVABLE RE
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON RE.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
            --AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --��Ӫ��������  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = re.business_date
         AND RE.ACCOUNT_DATE >= P_BEGIN_DATE
         AND RE.ACCOUNT_DATE < P_END_DATE;
  
    --12)СƱӦ�պ���  �����ֽ��СƱӦ��  
    /*           СƱ��Ӧ���ֽ�ʽ�Ļ����
    ������ļ���������ͳ�ƣ�
    �����Ӧ��Ӧ�յ���ϸΪСƱӦ�����ͣ�
    ���ϻ�����ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CH, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
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
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --13)СƱӦ�պ���  �������г�СƱӦ�� 
    /*           СƱ��Ӧ�����п�����㡢֧Ʊ������֧����ʽ�Ļ����
    ������ļ���������ͳ�ƣ�
    �����Ӧ��Ӧ�յ���ϸΪСƱӦ�����ͣ�
    ���ϻ�����ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
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
             RE.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --14)СƱӦ�պ���  Ӧ�����ջ����СƱӦ�� 
    /*           ���������ļ���������ͳ�ƣ�
    Ӧ�յ��ĵ���������ΪСƱӦ�գ�
    ȡ������ϸ�Ľ�
    Ӧ�����ĵ���������Ϊ��Ӧ�����ջ��
    ������ʱ���ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_COD_PAY, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
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
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- Ӧ�ճ�Ӧ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---Ӧ�յ�ΪСƱ
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC ----- Ӧ�����ջ���
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --15)СƱӦ�պ���  Ӧ�������СƱӦ�� 
    /*           ���������ļ���������ͳ�ƣ�
    ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������ΪСƱӦ�գ�
    Ӧ�����ĵ���������Ϊ������Ӧ����������ʱ���ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_CLAIM_PAY, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
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
            --  AND RE.SOURCE_BILL_TYPE =
            ---   PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --��Դ��СƱ
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- Ӧ������
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --16)СƱӦ�պ���  Ԥ�տͻ���СƱӦ�� 
    /*           ���������ļ���������ͳ�ƣ�
    ��������ΪԤ�ճ�Ӧ�գ�Ӧ�յ��ĵ���������ΪСƱӦ�գ�
    ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��  */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_CUST_DR, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
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
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --��Դ��СƱ
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --17)СƱӦ�պ���  ����֮���������СƱӦ�� 
    /*           ���������ļ���������ͳ�ƣ�
    ��������Ϊ���˳�Ӧ�գ��һ��˵��ĳ��˷�ʽΪ���������⣬
    Ӧ�յ��ĵ���������ΪСƱӦ�գ�ȡ������ϸ�Ľ�
    ������ʱ���ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_BD_DEBT, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             WO.WRITEOFF_TIME, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             BA.BILL_TYPE, --����������
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
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
      
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- ���˳�Ӧ��
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_INCOME ----����Ϊ��������
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- Ӧ�յ�ΪСƱ
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --18)СƱӦ�պ���  ����֮������ʧ��СƱӦ�� 
    /*          ���������ļ���������ͳ�ƣ�
    ��������Ϊ���˳�Ӧ�գ��һ��˵��ĳ��˷�ʽΪ��������ʧ��
    Ӧ�յ��ĵ���������ΪСƱӦ�գ�ȡ������ϸ�Ľ�
    ������ʱ���ɵĸ���Ҳͳ��   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_BD_INCOME, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --ʼ�����ű��� 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --�˵���
             WO.WRITEOFF_BILL_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             WO.WRITEOFF_TIME, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             BA.BILL_TYPE, --����������
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
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
      
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- ���˳�Ӧ��
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_BADDEDTS ----����Ϊ������ʧ
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- Ӧ�յ�ΪСƱ
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'ƾ֤СƱ��������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_OR;
/
