CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_LAND_COST IS

  -- ==============================================================
  -- Author  : MJQ
  -- Created : 2013-07-25
  -- Purpose : ÿ��ƾ֤����
  -- ==============================================================

  -----------------------------------------------------------------
  -- ƾ֤������������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_LAND_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_LAND_COST IS

  -----------------------------------------------------------------
  -- ƾ֤������������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
    --1)��������Ӧ������
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             PA.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             PA.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PA
       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE --������ⷢ�ɱ�
             AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PA.ACCOUNT_DATE < P_END_DATE;

    --2)��������Ӧ���ɱ�ȷ�ϣ���ǩ�ձ��е�ǩ��ʱ����ͳ�ƣ�ȡ�����˵����ɵĿ��˵������Ӧ�������
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             PA.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             PA.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PA
        JOIN STL.T_STL_POD POD
          ON POD.WAYBILL_NO = PA.WAYBILL_NO
             AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
       WHERE POD.POD_DATE >= P_BEGIN_DATE
             AND POD.POD_DATE < P_END_DATE
             AND PA.ACCOUNT_DATE < POD.POD_DATE
             AND
             PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE; --������ⷢ�ɱ�

    --3)��������Ӧ���ɱ���ȷ�ϣ���ǩ�ձ��еķ�ǩ��ʱ����ͳ�ƣ�ȡ�����˵����ɵĿ��˵������Ӧ�������
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             PA.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             PA.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PA
        JOIN STL.T_STL_POD POD
          ON POD.WAYBILL_NO = PA.WAYBILL_NO
             AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
       WHERE POD.POD_DATE >= P_BEGIN_DATE
             AND POD.POD_DATE < P_END_DATE
             AND PA.ACCOUNT_DATE < POD.POD_DATE
             AND
             PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE; --������ⷢ�ɱ�

    --4)����Ӧ���ɱ�ȷ�ϣ�����������Ӧ�����ļ���������ͳ�ƣ�ȡ��������Ӧ�����Ľ��
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
             PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', PA.source_bill_no, PA.waybill_no), --�˵���
             PA.Payable_No, --���ݱ��
             PA.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             PA.AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --��Ʒ����
        FROM STL.T_STL_BILL_PAYABLE PA
       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER --���������Ӧ��
             AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PA.ACCOUNT_DATE < P_END_DATE;

    --5�������ɱ���������
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
             PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY, --ҵ�񳡾�
             PA.CUSTOMER_CODE, --�ͻ�����
             PA.CUSTOMER_NAME, --�ͻ�����
             PA.CUSTOMER_TYPE, --�ͻ�����
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', PA.source_bill_no, PA.waybill_no), --�˵���
             PA.PAYABLE_NO, --���ݱ��
             PA.ACCOUNT_DATE, --�������
             PA.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
             PA.BILL_TYPE, --����������
             DECODE(PMT.IS_RED_BACK,
                    PKG_STL_COMMON.IS_RED_BACK_YES,
                    -1,
                    PKG_STL_COMMON.IS_RED_BACK_NO,
                    1) * PMTD.PAY_AMOUNT, --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT
        JOIN STL.T_STL_BILL_PAYMENT_D PMTD
          ON PMTD.PAYMENT_NO = PMT.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PMTD.SOURCE_BILL_NO = PA.PAYABLE_NO
       WHERE PA.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --������ⷢ�ɱ�
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER --���������Ӧ��
                               )
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PMTD.PAY_AMOUNT <> 0;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'ƾ֤������������ϸLAND_COST' || '�쳣');

      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_LAND_COST;
/
