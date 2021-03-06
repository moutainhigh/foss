CREATE OR REPLACE VIEW V_STL_DVD_NRFONO AS
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
	FROM STV.T_STL_NDVD T
 WHERE T.BUSINESS_CASE IN ('CUST_DRT_WO_ORIG_RCVO_NPOD',
													 'OTH_PAY_WO_DEST_RCVO_NPOD',
													 'URO_ORIG_CD_NPOD',
													 'URO_ORIG_CD_POD',
													 'ALPWR_WO_DEST_RCVO_POD',
													 'AL_DR_WO_DEST_RCVO_NPOD',
													 'PL_COST_WO_DEST_RCVO_POD',
													 'UPDO_ORIG_RCV_NWO',
													 'AL_DR_WO_DEST_RCVO_POD',
													 'COD_PAY_WO_ORIG_RCVO_NPOD',
													 'CUST_DRO_WO_ORIG_RCVO_NPOD',
													 'CUST_DRO_WO_ORIG_RCVO_POD',
													 'CUST_DRO_WO_ORIG_RCVT_PODD',
													 'CUST_DRT_WO_DEST_RCVO_NPOD',
													 'PODO_DEST_RCV_NWO',
													 'UPDO_ORIG_RCV_WO',
													 'COD_PAY_WO_DEST_RCVO_NPOD',
													 'CUST_DRO_WO_DEST_RCVO_POD',
													 'CUST_DRT_WO_DEST_RCVO_POD',
													 'PODO_DEST_RCV_WO',
													 'PODO_ORIG_RCV_NWO',
													 'UPDO_CASH_COLLECTED',
													 'URO_DEST_CD_POD',
													 'ALPWR_WO_DEST_RCVO_NPOD',
													 'COD_PAY_WO_DEST_RCVO_POD',
													 'COD_PAY_WO_ORIG_RCVO_POD',
													 'CUST_DRO_WO_DEST_RCVO_NPOD',
													 'PL_DR_WO_DEST_RCVO_POD',
													 'UPDO_DEST_RCV_WO',
													 'CUST_DRT_WO_ORIG_RCVO_POD',
													 'CUST_DR_OCD',
													 'PL_DR_WO_DEST_RCVO_NPOD',
													 'CUST_DRO_PAY_APPLY',
													 'CUST_DR_OCH',
													 'DEO_CH',
													 'PODO_CASH_COLLECTED',
													 'URO_DEST_CH_NPOD',
													 'URO_DEST_CH_POD',
													 'CUST_DRO_WO_ORIG_RCVT_NPODD',
													 'DEO_CD',
													 'OTH_PAY_WO_DEST_RCVO_POD',
													 'PL_COST_WO_DEST_RCVO_NPOD',
													 'PODO_ORIG_RCV_WO',
													 'UPDO_DEST_RCV_NWO',
													 'URO_DEST_CD_NPOD',
													 'URO_ORIG_CH_NPOD',
													 'URO_ORIG_CH_POD',
                           'POP_WO_DRO_POD',
                           'POP_WO_DRO_NPOD')
WITH READ ONLY;
