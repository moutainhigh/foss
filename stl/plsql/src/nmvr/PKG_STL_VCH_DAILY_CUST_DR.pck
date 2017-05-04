CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CUST_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : ÿ��ƾ֤Ԥ�տͻ�
  -- ==============================================================

  -----------------------------------------------------------------
  -- Ԥ�տͻ���������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_CUST_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CUST_DR IS

  -----------------------------------------------------------------
  -- Ԥ�տͻ���������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    --1)  Ԥ�տͻ�01 Ԥ���ֽ�
    --2)  Ԥ�տͻ�01 Ԥ�����п�
    --3)  Ԥ�տͻ�02 Ԥ���ֽ�
    --4)  Ԥ�տͻ�02 Ԥ�����п�
    --5)  ���� Ԥ�տͻ�01 Ԥ���ֽ�
    --6)  ���� Ԥ�տͻ�01 Ԥ�����п�
    --7)  01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --8)  02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --9)  01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --10) 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --11) 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --12) 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --13) 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
    --14) 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
    --15) 01Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
    --16) 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
    --17) 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
    --18) 02Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
    --19) 01Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
    --20) 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
    --21) 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
    --22) 02Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
    --23) 01ʼ����Ԥ�ո�������
    --24) 02ʼ����Ԥ�ո�������
    --25) ���� 01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --26) ���� 02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --27) ���� 01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --28) ���� 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --29) ���� 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
    --30) ���� 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
    --31) ���� 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
    --32) ���� 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
    --33) ���� 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --34) ���� 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --35) ���� 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
    --36) ���� 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
    --37) ���� 01ʼ����Ԥ�ո�������
    
    --38) 01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --39) 02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
    --40) 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --41) 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
    --42) 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
    --43) 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
    --44) 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
    --45) 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
    --46) 01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --47) 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
    --48) 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
    --49) 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
  
    --1)Ԥ�տͻ�01 Ԥ���ֽ�
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
             PKG_STL_VCH_COMMON.CUST_DR_OCH, --ҵ�񳡾�
             DEP.CUSTOMER_CODE, --�ͻ�����
             DEP.CUSTOMER_NAME, --�ͻ�����
             DEP.CUSTOMER_TYPE, --�ͻ�����
             DEP.GENERATING_ORG_CODE, --ʼ�����ű���
             DEP.GENERATING_ORG_NAME, --ʼ����������
             DEP.GENERATING_ORG_CODE, --���ﲿ�ű���
             DEP.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
             DEP.ACCOUNT_DATE, --�������
             DEP.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DEP.BILL_TYPE, --����������
             DEP.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2)Ԥ�տͻ�01 Ԥ�����п�
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
             PKG_STL_VCH_COMMON.CUST_DR_OCD, --ҵ�񳡾�
             DEP.CUSTOMER_CODE, --�ͻ�����
             DEP.CUSTOMER_NAME, --�ͻ�����
             DEP.CUSTOMER_TYPE, --�ͻ�����
             DEP.GENERATING_ORG_CODE, --ʼ�����ű���
             DEP.GENERATING_ORG_NAME, --ʼ����������
             DEP.GENERATING_ORG_CODE, --���ﲿ�ű���
             DEP.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
             DEP.ACCOUNT_DATE, --�������
             DEP.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DEP.BILL_TYPE, --����������
             DEP.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --3)Ԥ�տͻ�02 Ԥ���ֽ�
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
             PKG_STL_VCH_COMMON.CUST_DR_TCH, --ҵ�񳡾�
             DEP.CUSTOMER_CODE, --�ͻ�����
             DEP.CUSTOMER_NAME, --�ͻ�����
             DEP.CUSTOMER_TYPE, --�ͻ�����
             DEP.GENERATING_ORG_CODE, --ʼ�����ű���
             DEP.GENERATING_ORG_NAME, --ʼ����������
             DEP.GENERATING_ORG_CODE, --���ﲿ�ű���
             DEP.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
             DEP.ACCOUNT_DATE, --�������
             DEP.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DEP.BILL_TYPE, --����������
             DEP.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --4)Ԥ�տͻ�02 Ԥ�����п�
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
             PKG_STL_VCH_COMMON.CUST_DR_TCD, --ҵ�񳡾�
             DEP.CUSTOMER_CODE, --�ͻ�����
             DEP.CUSTOMER_NAME, --�ͻ�����
             DEP.CUSTOMER_TYPE, --�ͻ�����
             DEP.GENERATING_ORG_CODE, --ʼ�����ű���
             DEP.GENERATING_ORG_NAME, --ʼ����������
             DEP.GENERATING_ORG_CODE, --���ﲿ�ű���
             DEP.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
             DEP.ACCOUNT_DATE, --�������
             DEP.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DEP.BILL_TYPE, --����������
             DEP.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --5) ���� Ԥ�տͻ�01 Ԥ���ֽ�
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
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.CUST_DR_OCH,
             DEP.CUSTOMER_CODE,
             DEP.CUSTOMER_NAME,
             DEP.CUSTOMER_TYPE,
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO,
             DEP.ACCOUNT_DATE,
             DEP.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US,
             DEP.BILL_TYPE,
             DEP.AMOUNT,
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --6) ���� Ԥ�տͻ�01 Ԥ�����п�
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
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.CUST_DR_OCD,
             DEP.CUSTOMER_CODE,
             DEP.CUSTOMER_NAME,
             DEP.CUSTOMER_TYPE,
             '', --�˵���
             DEP.DEPOSIT_RECEIVED_NO,
             DEP.ACCOUNT_DATE,
             DEP.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US,
             DEP.BILL_TYPE,
             DEP.AMOUNT,
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE --Ԥ��01
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --7) 01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --8) 02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --9) 01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --11) 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�  ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --13) 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --14) 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�� ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --15) 01Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --17) 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --18) 02Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --19) 01Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --20) 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --21) 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --22) 02Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --���ﲿ������
             RE.WAYBILL_NO, --�˵���
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
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --23) 01ʼ����Ԥ�ո�������
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
             PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --ʼ�����ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ԥ�յ�Ϊ�Ǻ쵥
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --01Ԥ��
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --24) 02ʼ����Ԥ�ո�������
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
             PKG_STL_VCH_COMMON.CUST_DRT_PAY_APPLY, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --ʼ�����ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             PAY.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ԥ�յ�Ϊ�Ǻ쵥
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --02Ԥ��
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --25) ���� 01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --26) ���� 02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --27) ���� 01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --28) ���� 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --29) ���� 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --30) ���� 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --31) ���� 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --32) ���� 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --33) ���� 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --34) ���� 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --35) ���� 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --36) ���� 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD, --ҵ�񳡾�
             RE.CUSTOMER_CODE, --�ͻ�����
             RE.CUSTOMER_NAME, --�ͻ�����
             RE.CUSTOMER_TYPE, --�ͻ�����
             RE.WAYBILL_NO, --�˵���
             RE.RECEIVABLE_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             RE.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
             RE.BILL_TYPE, --����������
             WO.AMOUNT, --���
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --��Ʒ����
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --�������ű���
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --ʼ����������
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --37) ���� 01ʼ����Ԥ�ո�������
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
             PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY, --ҵ�񳡾�
             DR.CUSTOMER_CODE,
             DR.CUSTOMER_NAME,
             DR.CUSTOMER_TYPE,
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO,
             DR.ACCOUNT_DATE,
             DR.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --���
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ԥ�յ�Ϊ�Ǻ쵥
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --01Ԥ��
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --38) 01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --ʼ�����ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --39) 02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --40) 01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --41) 02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�  ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --42) 01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --43) 02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
   
    --44) 01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;     
    
    --45) 02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --46) 01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --47) 02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --48) 01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --Ԥ��01 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --49) 02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_PODD, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.GENERATING_ORG_CODE, --�������ű���
             DR.GENERATING_ORG_NAME, --ʼ����������
             DR.GENERATING_ORG_CODE, --���ﲿ�ű���
             DR.GENERATING_ORG_NAME, --���ﲿ������
             '', --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             WO.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             WO.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
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
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�� ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --Ԥ��02 Ӧ��02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
        
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'Ԥ�տͻ���������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_CUST_DR;
/
