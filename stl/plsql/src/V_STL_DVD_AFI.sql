CREATE OR REPLACE VIEW V_STL_DVD_AFI AS
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
					'UR_DEST_CD_POD', 'AIR_DR_DEST_RCV_POD', 'AIR_DR_DEST_RCV_NPOD',
					'AIR_DR_WO_COD_RCV_POD', 'AIR_PR_AGENCY_WO_DEST_RCV_POD',
					'AIR_PR_AGENCY_WO_DEST_RCV_NPOD', 'AIR_PR_OT_WO_DEST_RCV_POD',
					'AIR_PR_OTH_WO_DEST_RCV_NPOD', 'AIR_COD_CH_POD', 'AIR_COD_CD_POD',
					'AIR_COD_POD_WO_COD', 'AIR_COD_NPOD_WO_COD', 'AIR_COD_POD_NWO_COD',
					'AIR_COD_NPOD_NWO_COD', 'AIR_COD_WO_AGENCY_PAY_POD',
					'AIR_COD_WO_OTH_PAY_COD')
				 AND T.PRODUCT_CODE = 'AF'

WITH READ ONLY;
