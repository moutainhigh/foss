CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AC IS

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

END PKG_STL_VCH_DAILY_AC;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AC IS

  -----------------------------------------------------------------
  -- ƾ֤������������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN

    --1)������Υ��Ʒ��ȫƱ������������Ϊ�½���ʱǷ������֧��δ����
    DECLARE

      CURSOR CUR_DATA IS
        SELECT *
          FROM STL.T_STL_OUT_STOCK_EXCEPTION T
         WHERE T.CREATE_TIME >= P_BEGIN_DATE
               AND T.CREATE_TIME < P_END_DATE;

    BEGIN

      FOR V_DATA IN CUR_DATA LOOP

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
          SELECT SYS_GUID(),
                 P_PERIOD,
                 PKG_STL_VCH_COMMON.AC_CTDTOL_NWO,
                 RCV.CUSTOMER_CODE,
                 RCV.CUSTOMER_NAME,
                 RCV.CUSTOMER_TYPE,
                 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE), --ʼ�����ű��� 
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME), --ʼ����������
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME), --���ﲿ������
                 RCV.WAYBILL_NO,
                 RCV.RECEIVABLE_NO,
                 RCV.ACCOUNT_DATE,
                 RCV.BUSINESS_DATE,
                 PKG_STL_COMMON.BILL_PARENT_TYPE__YS,
                 RCV.BILL_TYPE,
                 RCV.AMOUNT -
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                            AND WO.ACCOUNT_DATE < V_DATA.CREATE_TIME),
                     0),
                 0,
                 0,
                 0,
                 0,
                 0,
                 0,
                 0,
                 RCV.PRODUCT_CODE
            FROM STL.T_STL_BILL_RECEIVABLE RCV
           WHERE RCV.ID =
                 (SELECT ID
                    FROM (SELECT *
                            FROM (SELECT *
                                    FROM STL.T_STL_BILL_RECEIVABLE T
                                   WHERE T.WAYBILL_NO = V_DATA.WAYBILL_NO
                                         AND
                                         T.ACCOUNT_DATE < V_DATA.CREATE_TIME
                                    AND BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
                                   ORDER BY T.ACCOUNT_DATE DESC,
                                            T.CREATE_TIME  DESC,
                                            T.IS_RED_BACK  ASC)
                           WHERE ROWNUM <= 1)
                   WHERE BILL_TYPE =
                         PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
                         AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO)
                 AND RCV.AMOUNT <>
                 NVL((SELECT SUM(WO.AMOUNT)
                           FROM STL.T_STL_BILL_WRITEOFF WO
                          WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                WO.END_NO = RCV.RECEIVABLE_NO)
                                AND WO.ACCOUNT_DATE < V_DATA.CREATE_TIME),
                         0);
      END LOOP;

    END;

    --2)������Υ��Ʒ��ȫƱ������������Ϊ�½���ʱǷ������֧���Ѻ���
    DECLARE

      CURSOR CUR_DATA IS
        SELECT *
          FROM STL.T_STL_OUT_STOCK_EXCEPTION T
         WHERE T.CREATE_TIME >= P_BEGIN_DATE
               AND T.CREATE_TIME < P_END_DATE;

    BEGIN

      FOR V_DATA IN CUR_DATA LOOP

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
          SELECT SYS_GUID(),
                 P_PERIOD,
                 PKG_STL_VCH_COMMON.AC_CTDTOL_WO,
                 RCV.CUSTOMER_CODE,
                 RCV.CUSTOMER_NAME,
                 RCV.CUSTOMER_TYPE,
                  DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE), --ʼ�����ű��� 
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME), --ʼ����������
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 		DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME), --���ﲿ������
                 RCV.WAYBILL_NO,
                 RCV.RECEIVABLE_NO,
                 RCV.ACCOUNT_DATE,
                 RCV.BUSINESS_DATE,
                 PKG_STL_COMMON.BILL_PARENT_TYPE__YS,
                 RCV.BILL_TYPE,
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                            AND WO.ACCOUNT_DATE < V_DATA.CREATE_TIME),
                     0),
                 0,
                 0,
                 0,
                 0,
                 0,
                 0,
                 0,
                 RCV.PRODUCT_CODE
            FROM STL.T_STL_BILL_RECEIVABLE RCV
           WHERE RCV.ID =
                 (SELECT ID
                    FROM (SELECT *
                            FROM (SELECT *
                                    FROM STL.T_STL_BILL_RECEIVABLE T
                                   WHERE T.WAYBILL_NO = V_DATA.WAYBILL_NO
                                         AND
                                         T.ACCOUNT_DATE < V_DATA.CREATE_TIME
                                   AND BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
                                   ORDER BY T.ACCOUNT_DATE DESC,
                                            T.CREATE_TIME  DESC,
                                            T.IS_RED_BACK  ASC)
                           WHERE ROWNUM <= 1)
                   WHERE BILL_TYPE =
                         PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
                         AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO)
                 AND NVL((SELECT SUM(WO.AMOUNT)
                           FROM STL.T_STL_BILL_WRITEOFF WO
                          WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                WO.END_NO = RCV.RECEIVABLE_NO)
                                AND WO.ACCOUNT_DATE < V_DATA.CREATE_TIME),
                         0) <> 0;
      END LOOP;

    END;

    --3)������Υ��Ʒ��ȫƱ������������Ϊ�ֽ����п�
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
      SELECT SYS_GUID(),
             P_PERIOD,
             PKG_STL_VCH_COMMON.AC_CHCD,
             CASH.CUSTOMER_CODE,
             CASH.CUSTOMER_NAME,
             CASH.CUSTOMER_TYPE,
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
             CASH.WAYBILL_NO,
             CASH.CASH_COLLECTION_NO,
             CASH.ACCOUNT_DATE,
             CASH.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS,
             CASH.BILL_TYPE,
             CASH.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE
        FROM STL.T_STL_OUT_STOCK_EXCEPTION EX
        JOIN STL.T_STL_BILL_CASH_COLLECTION CASH
          ON EX.WAYBILL_NO = CASH.WAYBILL_NO
       WHERE CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
             AND EX.CREATE_TIME >= P_BEGIN_DATE
             AND EX.CREATE_TIME < P_END_DATE;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'ƾ֤������������ϸ' || '�쳣');

      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_AC;
/
