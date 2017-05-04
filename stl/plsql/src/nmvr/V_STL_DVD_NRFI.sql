CREATE OR REPLACE VIEW V_STL_DVD_NRFI AS
SELECT BUSINESS_CASE,
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
			 ID,
			 PERIOD,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE
	FROM STV.T_STL_NDVDI T
 WHERE T.BUSINESS_CASE IN ('CLAIM_DESTT_WO_DEST_RCVO_POD',
													 'COD_PAY_WO_ORIG_RCVT_NPOD',
													 'CUST_DRO_WO_DEST_RCVT_POD',
													 'CUST_DRT_WO_ORIG_RCVO_NPOD',
													 'OR_CLAIM_PAYO_WO_RCVT',
													 'RD_DESTO_DEST_RCVO_POD',
													 'RD_DESTO_WO_DEST_RCVT_NPOD',
													 'RD_DESTT_WO_DEST_RCVT_POD',
													 'URO_ORIG_CD_NPOD',
													 'URO_ORIG_CD_POD',
													 'URT_DEST_CD_POD',
													 'CLAIM_DESTT_INCOME',
													 'CLAIM_DESTT_WO_DEST_RCVT_POD',
													 'COD_PAY_WO_DEST_RCVT_POD',
													 'CPO_ORIG_PAY_APPLY',
													 'CUST_DRO_WO_DEST_RCVT_NPOD',
													 'CUST_DRT_WO_DEST_RCVT_POD',
													 'OR_CLAIM_PAYT_WO_RCVO',
													 'SFO_PAY_APPLY',
													 'CLAIM_DESTO_WO_DEST_RCVO_POD',
													 'CLAIM_DESTT_WO_DEST_RCVO_NPOD',
													 'COD_PAY_WO_ORIG_RCVO_NPOD',
													 'CUST_DRO_WO_ORIG_RCVT_POD',
													 'CUST_DRT_WO_DEST_RCVO_NPOD',
													 'OR_CH_UR_RCVO',
													 'OR_CUST_DRT_WO_RCVO',
													 'RD_DESTO_PAY_APPLY',
													 'RD_DESTO_WO_DEST_RCVO_NPOD',
													 'RD_ORIGT_WO_ORIG_RCVO_NPOD',
													 'URT_DEST_CD_NPOD',
													 'CLAIM_DESTO_WO_DEST_RCVO_NPOD',
													 'COD_PAY_WO_DEST_RCVO_NPOD',
													 'COD_PAY_WO_ORIG_RCVT_POD',
													 'CUST_DRO_WO_DEST_RCVO_POD',
													 'CUST_DRT_WO_DEST_RCVO_POD',
													 'OR_CH_PBIO',
													 'RD_DESTO_DEST_RCVT_POD',
													 'RD_DESTO_INCOME',
													 'RD_DESTT_WO_DEST_RCVO_POD',
													 'RD_ORIGO_PAY_APPLY',
													 'URO_DEST_CD_POD',
													 'URT_DEST_CH_NPOD',
													 'CLAIM_DESTO_PAY_APPLY',
													 'CLAIM_ORIGO_PAY_APPLY',
													 'CLAIM_ORIGO_WO_ORIG_RCVT_POD',
													 'COD_PAY_WO_DEST_RCVO_POD',
													 'COD_PAY_WO_ORIG_RCVO_POD',
													 'CUST_DRO_WO_DEST_RCVO_NPOD',
													 'RD_DESTT_WO_DEST_RCVT_NPOD',
													 'CLAIM_DESTO_WO_DEST_RCVT_NPOD',
													 'CLAIM_DESTT_WO_DEST_RCVT_NPOD',
													 'CUST_DRT_WO_DEST_RCVT_NPOD',
													 'CUST_DRT_WO_ORIG_RCVO_POD',
													 'CUST_DR_OCD',
													 'OR_CD_UR_RCVO',
													 'OR_CUST_DRO_WO_RCVT',
													 'RD_DESTT_WO_DEST_RCVO_NPOD',
													 'RD_ORIGO_WO_ORIG_RCVT_POD',
													 'CLAIM_ORIGO_WO_ORIG_RCVT_NPOD',
													 'CLAIM_ORIGT_WO_ORIG_RCVO_NPOD',
													 'COD_PAY_WO_OTH_RCVT',
													 'COD_UR_CD_NPOD',
													 'CPO_DEST_PAY_APPLY',
													 'CUST_DRO_PAY_APPLY',
													 'CUST_DR_OCH',
													 'DEO_CH',
													 'OR_CD_PBIO',
													 'RD_ORIGT_WO_ORIG_RCVO_POD',
													 'URO_DEST_CH_NPOD',
													 'URO_DEST_CH_POD',
													 'URT_DEST_CH_POD',
													 'CLAIM_DESTO_INCOME',
													 'CLAIM_DESTO_WO_DEST_RCVT_POD',
													 'CLAIM_ORIGT_ORIG_RCVO_POD',
													 'COD_PAY_WO_DEST_RCVT_NPOD',
													 'COD_PAY_WO_OTH_RCVO',
													 'COD_UR_CH_NPOD',
													 'CUST_DRO_WO_ORIG_RCVT_NPOD',
													 'DEO_CD',
													 'RD_DESTT_INCOME',
													 'RD_ORIGO_WO_ORIG_RCVT_NPOD',
													 'URO_DEST_CD_NPOD',
													 'URO_ORIG_CH_NPOD',
													 'URO_ORIG_CH_POD')

WITH READ ONLY
;