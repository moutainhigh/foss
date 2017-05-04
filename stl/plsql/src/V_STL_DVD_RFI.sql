CREATE OR REPLACE VIEW STV.V_STL_DVD_RFI AS
SELECT BUSINESS_CASE,
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
         PRODUCT_CODE,
         ID,
         PERIOD
    FROM STV.T_STL_DVD T
   WHERE T.BUSINESS_CASE IN
         ('COD_ORIG_RCV_POD', 'COD_ORIG_RCV_NPOD', 'COD_UR_CH_NPOD',
          'COD_UR_CD_NPOD', 'COD_WO_OR_RCV', 'RD_DEST_WO_INCOME',
          'UR_DEST_CH_NPOD', 'UR_DEST_CD_NPOD', 'UR_DEST_CH_POD',
          'UR_DEST_CD_POD', 'CLAIM_DEST_WO_INCOME', 'CLAIM_WO_DEST_RCV_POD',
          'CLAIM_WO_DEST_RCV_NPOD', 'COD_DEST_RCV_POD', 'COD_DEST_RCV_NPOD',
          'CUST_DR_DEST_RCV_NPOD','CUST_DR_DEST_RCV_POD','RD_WO_DEST_RCV_POD','RD_WO_DEST_RCV_NPOD')
         AND T.PRODUCT_CODE IN ('FLF', --精准卡航
          'FSF', --精准城运
          'LRF', --精准汽运(长途)
          'SRF', --精准汽运(短途)
          'WVH', --整车
          'PACKAGE'--经济快递
         )
WITH READ ONLY;
