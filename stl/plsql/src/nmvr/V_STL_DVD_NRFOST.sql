CREATE OR REPLACE VIEW V_STL_DVD_NRFOST AS

--02特殊始发月报表

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
 WHERE T.BUSINESS_CASE IN ('CLAIM_ORIGT_COST',
													 'CLAIM_ORIGT_WO_ORIG_RCVT_NPOD',
													 'EX_ORIG_RCVT_POD',
													 'OR_CD_OPAY',
													 'OR_CD_OTHER',
													 'OR_CLAIM_PAYO_WO_RCVT',
													 'RD_DESTO_WO_DEST_RCVT_NPOD',
													 'RD_DESTT_WO_DEST_RCVT_POD',
													 'AC_CASHT',
													 'CLAIM_DESTT_INCOME',
													 'CLAIM_DESTT_WO_DEST_RCVT_POD',
													 'CPO_ORIG_PAY_APPLY',
													 'OR_CD_UR_RCVT',
													 'OR_CH_AC',
													 'OR_CH_PBIT',
													 'OR_CH_SI',
													 'OR_CLAIM_PAYT_WO_RCVOP',
													 'RD_ORIGT_WO_ORIG_RCVT_POD',
													 'SFO_PAY_APPLY',
													 'AC_ORIG_RCV_NWOT',
													 'BD_WO_ORIG_RCVT_POD',
													 'OR_CD_PBIT',
													 'OR_CH_UR_RCVO',
													 'OR_CUST_DRT_WO_RCVOD',
													 'RD_ORIGT_WO_ORIG_RCVO_NPODP',
													 'RD_ORIGT_WO_ORIG_RCVT_NPOD',
													 'CLAIM_ORIGT_PAY_APPLY',
													 'OR_CH_PBIO',
													 'OR_EX_WO_RCVT',
													 'OR_RCVT_PBI',
													 'RD_DESTO_DEST_RCVT_POD',
													 'RD_ORIGO_PAY_APPLY',
													 'CLAIM_ORIGO_PAY_APPLY',
													 'CLAIM_ORIGO_WO_ORIG_RCVT_POD',
													 'OR_BAD_WO_RCVT',
													 'OR_CH_UR_RCVT',
													 'RD_DESTT_WO_DEST_RCVT_NPOD',
													 'RD_ORIGT_COST',
													 'SFT_PAY_APPLY',
													 'AC_ORIG_RCV_WOT',
													 'CLAIM_DESTO_WO_DEST_RCVT_NPOD',
													 'CLAIM_DESTT_WO_DEST_RCVT_NPOD',
													 'CLAIM_ORIGT_INCOME',
													 'CLAIM_ORIGT_WO_ORIG_RCVT_POD',
													 'EX_DEST_RCVT_POD',
													 'OR_CD_UR_RCVO',
													 'OR_CLAIM_PAYT_WO_RCVT',
													 'OR_COD_PAY_WO_RCVT',
													 'OR_CUST_DRO_WO_RCVT',
													 'RD_ORIGO_WO_ORIG_RCVT_POD',
													 'RD_ORIGT_PAY_APPLY',
													 'BD_WO_DEST_RCVT_POD',
													 'CLAIM_ORIGO_WO_ORIG_RCVT_NPOD',
													 'CLAIM_ORIGT_WO_ORIG_RCVO_NPODP',
													 'OR_CD_BANK_INT',
													 'OR_CD_PBIO',
													 'OR_CH_OPAY',
													 'OR_CH_OTHER',
													 'RD_ORIGT_WO_ORIG_RCVO_PODP',
													 'CLAIM_DESTO_WO_DEST_RCVT_POD',
													 'CLAIM_ORIGT_ORIG_RCVO_PODP',
													 'CPT_ORIG_PAY_APPLY',
													 'OR_CD_AC',
													 'OR_CUST_DRT_WO_RCVT',
													 'RD_DESTT_INCOME',
													 'RD_ORIGO_WO_ORIG_RCVT_NPOD',
													 'RD_ORIGT_INCOME',
                           'OR_CH_RENT_INCOME',
                           'OR_CH_BANK_INT',
                           'OR_CD_RENT_INCOME')
                           
WITH READ ONLY;
