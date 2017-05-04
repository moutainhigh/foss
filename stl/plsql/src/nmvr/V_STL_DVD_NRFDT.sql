CREATE OR REPLACE VIEW V_STL_DVD_NRFDT AS
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
 WHERE T.BUSINESS_CASE IN ('CLAIM_DESTT_WO_DEST_RCVO_PODP',
													 'CPT_DEST_PAY_APPLY',
													 'RD_DESTT_WO_DEST_RCVT_PODP',
													 'URT_DEST_CD_POD',
													 'CLAIM_DESTT_INCOME',
													 'CLAIM_DESTT_WO_DEST_RCVT_PODP',
													 'CUST_DRT_WO_DEST_RCVT_PODD',
													 'CLAIM_DESTT_PAY_APPLY',
													 'CLAIM_DESTT_WO_DEST_RCVO_NPODP',
													 'CUST_DRT_WO_DEST_RCVO_NPODD',
													 'RD_DESTO_PAY_APPLY',
													 'RD_DESTT_COST',
													 'URT_DEST_CD_NPOD',
													 'CUST_DRT_WO_DEST_RCVO_PODD',
													 'RD_DESTT_WO_DEST_RCVO_PODP',
													 'URO_DEST_CD_POD',
													 'URT_DEST_CH_NPOD',
													 'CLAIM_DESTO_PAY_APPLY',
													 'RD_DESTT_WO_DEST_RCVT_NPODP',
													 'CLAIM_DESTT_COST',
													 'CLAIM_DESTT_WO_DEST_RCVT_NPODP',
													 'CUST_DRT_WO_DEST_RCVT_NPODD',
													 'RD_DESTT_PAY_APPLY',
													 'RD_DESTT_WO_DEST_RCVO_NPODP',
													 'COD_UR_CD_NPOD',
													 'CPO_DEST_PAY_APPLY',
													 'URO_DEST_CH_NPOD',
													 'URO_DEST_CH_POD',
													 'URT_DEST_CH_POD',
													 'COD_UR_CH_NPOD',
													 'RD_DESTT_INCOME',
													 'URO_DEST_CD_NPOD')
			 AND T.PRODUCT_CODE IN ('FLF', --精准卡航
															'FSF', --精准城运
															'LRF', --精准汽运(长途)
															'SRF', --精准汽运(短途)
															'WVH', --整车
															'PACKAGE' --快递包裹
															)

WITH READ ONLY
;
