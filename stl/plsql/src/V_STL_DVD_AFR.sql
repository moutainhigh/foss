CREATE OR REPLACE VIEW V_STL_DVD_AFR AS
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
          'UR_DEST_CD_POD', 'AIR_COST_COM_CONFIRM', 'AIR_COST_ORIG_AGENCY_CFM',
          'AIR_COST_DEST_AGENCY_GEN', 'AIR_COST_DEST_AGENCY_CFM',
          'AIR_COST_DEST_AGENCY_NCFM', 'AIR_COST_OTHER_CONFIRM',
          'AIR_COST_PAY_APPLY', 'OTH_ENTRY', 'OTH_RCV_CH', 'OTH_RCV_CD',
          'AIR_DR_DEST_RCV_POD', 'AIR_DR_DEST_RCV_NPOD', 'AIR_DR_CH',
          'AIR_DR_CD', 'AIR_DR_WO_OTHER_RCV', 'AIR_DR_WO_COD_RCV_POD',
          'AIR_DR_WO_COD_RCV_NPOD', 'AIR_DR_PAY_APPLY',
          'AIR_PR_AGENCY_WO_DEST_RCV_POD', 'AIR_PR_AGENCY_WO_DEST_RCV_NPOD',
          'AIR_PR_OT_WO_DEST_RCV_POD', 'AIR_PR_OTH_WO_DEST_RCV_NPOD',
          'AIR_PR_OTH_WO_OTH_RCV', 'AIR_COD_CH_POD', 'AIR_COD_CD_POD',
          'AIR_COD_POD_WO_COD', 'AIR_COD_NPOD_WO_COD', 'AIR_COD_CH_NPOD',
          'AIR_COD_CD_NPOD', 'AIR_COD_WO_AGENCY_PAY_POD',
          'AIR_COD_WO_OTH_PAY_COD', 'AIR_COD_WO_AGENCY_PAY_NPOD',
          'AIR_COD_WO_OTH_NPOD', 'APT_COM', 'APT_WO_COM_PAY', 'APT_WO_OTH_PAY',
          'BDR_WO_OTH_RCV')
         AND T.PRODUCT_CODE = 'AF'
WITH READ ONLY;
