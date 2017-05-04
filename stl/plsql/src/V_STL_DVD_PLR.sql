CREATE OR REPLACE VIEW V_STL_DVD_PLR AS
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
         ('UR_DEST_CH_NPOD', 'UR_DEST_CD_NPOD', 'UR_DEST_CH_POD',
          'UR_DEST_CD_POD', 'PL_COST_WO_DEST_RCV_POD',
          'PL_COST_WO_DEST_RCV_NPOD', 'PL_COST_VECH', 'PL_COST_CONFIRM',
          'PL_COST_NOT_CONFIRM', 'PL_COST_PAY_APPLY', 'PL_DR_WO_DEST_RCV_POD',
          'PL_DR_WO_DEST_RCV_NPOD', 'PL_DR_CH', 'PL_DR_CD', 'PL_DR_PAY_APPLY')
         AND T.PRODUCT_CODE = 'PLF'
WITH READ ONLY;