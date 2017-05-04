CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_RD IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : ÿ��ƾ֤���˷�
  -- ==============================================================

  -----------------------------------------------------------------
  -- ���˷Ѵ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_RD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_RD IS

  -----------------------------------------------------------------
  -- ���˷Ѵ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd,����20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ�,false:ʧ��
   IS
  BEGIN
     --1�����˷�--������������--01���˷ѳ�����
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
             PKG_STL_VCH_COMMON.RD_ORIGO_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ������
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             ;

    --2�����˷�--������������--01���˷���ɱ�
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
             PKG_STL_VCH_COMMON.RD_ORIGO_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ������
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --3�����˷�--������������--01���˷Ѹ�������
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
             PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PMT.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
             --���ȡ��������,2013-8-5 ��С���޶�
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
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
             AND DP.Source_Bill_No = PAY.Payable_No
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
     --4�����˷�--������������--02���˷ѳ�����
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
             PKG_STL_VCH_COMMON.RD_ORIGT_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ������
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
             ;

    --5�����˷�--������������--02���˷���ɱ�
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
             PKG_STL_VCH_COMMON.RD_ORIGT_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ������
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

    --6�����˷�--������������--02���˷Ѹ�������
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
             PKG_STL_VCH_COMMON.RD_ORIGT_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PMT.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
               --���ȡ��������,2013-8-5 ��С���޶�
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
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
             AND DP.Source_Bill_No = PAY.Payable_No
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
      --7�����˷�--������������--01���˷ѳ�01ʼ��Ӧ����ǩ��
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_POD,--01���˷ѳ�01ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;

       --8�����˷�--������������--01���˷ѳ�02ʼ��Ӧ����ǩ�� /***********
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_PODP,--01���˷ѳ�02ʼ��Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --��ǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
      --8--1�����˷�--������������--01���˷ѳ�02ʼ��Ӧ����ǩ�� /***********
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,--01���˷ѳ�02ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --��ǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
      --9�����˷�--������������--02���˷ѳ�01ʼ��Ӧ����ǩ�� /***************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_PODP,--02���˷ѳ�01ʼ��Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ�յ�
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
       --9--1�����˷�--������������--02���˷ѳ�01ʼ��Ӧ����ǩ�� /***************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,--02���˷ѳ�01ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
       ;
      
      --10�����˷�--������������--02���˷ѳ�02ʼ��Ӧ����ǩ��
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_POD,--02���˷ѳ�02ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE--ʼ������
        ;
      --11�����˷�--������������--01���˷ѳ�01ʼ��Ӧ��δǩ��
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_NPOD,--01���˷ѳ�01ʼ��Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
       --12�����˷�--������������--01���˷ѳ�02ʼ��Ӧ��δǩ�� /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPODP,--01���˷ѳ�02ʼ��Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE =PAY.ORIG_ORG_CODE --ʼ������
        ;
        
       --12--1�����˷�--������������--01���˷ѳ�02ʼ��Ӧ��δǩ�� /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,--01���˷ѳ�02ʼ��Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE =PAY.ORIG_ORG_CODE --ʼ������
        ;
      --13�����˷�--������������--02���˷ѳ�01ʼ��Ӧ��δǩ�� /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPODP,--02���˷ѳ�01ʼ��Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��       
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
      
      --13--1�����˷�--������������--02���˷ѳ�01ʼ��Ӧ��δǩ�� /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,--02���˷ѳ�01ʼ��Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��       
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
      --14�����˷�--������������--02���˷ѳ�02ʼ��Ӧ����ǩ��
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_NPOD,--02���˷ѳ�02ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;

     --15�����˷�--���ﲿ������--01���˷ѳ�����
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
             PKG_STL_VCH_COMMON.RD_DESTO_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE ;

    --16�����˷�--���ﲿ������--01���˷���ɱ�
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
             PKG_STL_VCH_COMMON.RD_DESTO_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --17�����˷�--����������--01���˷Ѹ�������
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
             PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PMT.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
               --���ȡ��������,2013-8-5 ��С���޶�
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
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
              AND DP.Source_Bill_No = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
     --18�����˷�--���ﲿ������--02���˷ѳ�����
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
             PKG_STL_VCH_COMMON.RD_DESTT_INCOME, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
             ;

    --19�����˷�--���ﲿ������--02���˷���ɱ�
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
             PKG_STL_VCH_COMMON.RD_DESTT_COST, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

    --20�����˷�--���ﲿ������--02���˷Ѹ�������
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
             PKG_STL_VCH_COMMON.RD_DESTT_PAY_APPLY, --ҵ�񳡾�
             PAY.CUSTOMER_CODE, --�ͻ�����
             PAY.CUSTOMER_NAME, --�ͻ�����
             PAY.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
             PAY.PAYABLE_NO, --���ݱ��
             PMT.ACCOUNT_DATE, --��������
             PAY.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PAY.BILL_TYPE, --����������
               --���ȡ��������,2013-8-5 ��С���޶�
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
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
             AND DP.SOURCE_BILL_NO = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

      --21�����˷�--���ﲿ������--01���˷ѳ�01ʼ��Ӧ����ǩ�� /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD ,--01���˷ѳ�01ʼ��Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
       ;
       
      --21--1�����˷�--���ﲿ������--01���˷ѳ�01ʼ��Ӧ����ǩ�� /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_PODP,--01���˷ѳ�01ʼ��Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
       ;

       --22�����˷�--������������--01���˷ѳ�02����Ӧ����ǩ��/*********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_PODP,--01���˷ѳ�02����Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      
       --22--1�����˷�--������������--01���˷ѳ�02����Ӧ����ǩ��/*********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,--01���˷ѳ�02����Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --23�����˷�--���ﲿ������--02���˷ѳ�01����Ӧ����ǩ�� /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_PODP,--02���˷ѳ�01����Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
     INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      
      --23--1�����˷�--���ﲿ������--02���˷ѳ�01����Ӧ����ǩ�� /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,--02���˷ѳ�01����Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
     INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --24�����˷�--���ﲿ������--02���˷ѳ�02����Ӧ����ǩ�� /****************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,--02���˷ѳ�02����Ӧ����ǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --24--1�����˷�--���ﲿ������--02���˷ѳ�02����Ӧ����ǩ�� /****************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_PODP,--02���˷ѳ�02����Ӧ����ǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --25�����˷�--���ﲿ������--01���˷ѳ�01����Ӧ��δǩ�� /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPODP,--01���˷ѳ�01����Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
        
      --25--1�����˷�--���ﲿ������--01���˷ѳ�01����Ӧ��δǩ�� /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,--01���˷ѳ�01����Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
       --26�����˷�--���ﲿ������--01���˷ѳ�02����Ӧ��δǩ�� /******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPODP,--01���˷ѳ�02����Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
       --26--1�����˷�--���ﲿ������--01���˷ѳ�02����Ӧ��δǩ�� /******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,--01���˷ѳ�02����Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --27�����˷�--���ﲿ������--02���˷ѳ�01����Ӧ��δǩ�� /*************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPODP,--02���˷ѳ�01����Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE =PAY.DEST_ORG_CODE --���ﲿ��
        ;
        
        --27--1�����˷�--���ﲿ������--02���˷ѳ�01����Ӧ��δǩ�� /*************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,--02���˷ѳ�01����Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ����
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE =PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --28�����˷�--������������--02���˷ѳ�02����Ӧ��δǩ�� /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPODP,--02���˷ѳ�02����Ӧ��δǩ��
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      
      --28--1�����˷�--������������--02���˷ѳ�02����Ӧ��δǩ�� /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,--02���˷ѳ�02����Ӧ��δǩ��
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--�˵���
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--Ӧ�յ�
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --��Ʒ
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
        
      --29�����˷� ������������ 01���˷Ѹ�������--����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY, --01���˷Ѹ�������
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PMT.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       DP.PAY_AMOUNT*DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
              AND DP.Source_Bill_No = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

      --30�����˷� ������������ 01���˷ѳ�02ʼ��Ӧ����ǩ��--���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
       ;


      --31�����˷� ������������ 02���˷ѳ�01ʼ��Ӧ����ǩ��--���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,--02��01
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
        ;
     --32�����˷� ������������ 01���˷ѳ�02ʼ��Ӧ��δǩ��--���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ������
       ;

       --33�����˷� ������������ 02���˷ѳ�01ʼ��Ӧ��δǩ��--���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,--02��01
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--�˵���
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE
                          ) = 0 --δǩ��      
       AND��RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --��������
        ;
     --34�����˷� ���ﲿ������ 01���˷ѳ����� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_INCOME, --01���˷ѳ�����
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PAY.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
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
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
       FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
             AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --���ﲿ��
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --35�����˷� ���ﲿ������ 01���˷Ѹ������� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY, --01���˷�����
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PMT.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       DP.PAY_AMOUNT*DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
      FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
             AND DP.SOURCE_BILL_NO = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

      --36�����˷� ���ﲿ������ 02���˷ѳ����� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_INCOME, --01���˷ѳ�����
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PAY.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
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
             END,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
       FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
             AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --���˷�����Ӧ����
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --���ﲿ��
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

     --37�����˷� ���ﲿ������ 01���˷ѳ�01����Ӧ����ǩ�� --���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD, --01���˷ѳ�01����Ӧ����ǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
     --38�����˷� ���ﲿ������ 02���˷ѳ�01����Ӧ����ǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD, --02���˷ѳ�01����Ӧ����ǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��       
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;

     --39�����˷� ���ﲿ������ 01���˷ѳ�01����Ӧ��δǩ�� --���� 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD, --01���˷ѳ�01����Ӧ��δǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;

     --40�����˷� ���ﲿ������ 02���˷ѳ�01����Ӧ��δǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD, --02���˷ѳ�01����Ӧ��δǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --Ӧ��02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
      --41�����˷� ���ﲿ������ 01���˷ѳ�02����Ӧ����ǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD, --01���˷ѳ�02����Ӧ����ǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE  IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;

     --42�����˷� ���ﲿ������ 02���˷ѳ�02����Ӧ����ǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD, --02���˷ѳ�02����Ӧ����ǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��      
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;

     --43�����˷� ���ﲿ������ 01���˷ѳ�02����Ӧ��δǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD, --01���˷ѳ�02����Ӧ��δǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --ʼ�����ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��      
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;

     --44�����˷� ���ﲿ������ 02���˷ѳ�02����Ӧ��δǩ�� --����
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD, --02���˷ѳ�02����Ӧ��δǩ��
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--Ӧ����,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --��ǩ��      
       AND��RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--����Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--����ƫ�ߴ���Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --������������Ӧ��
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --Ӧ��01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --Ӧ�����˷�
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --���ﲿ��
        ;
    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '���˷Ѵ�������ϸ' || '�쳣');

      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_RD;
/
