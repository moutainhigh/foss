CREATE OR REPLACE VIEW V_STL_DVD_NAFR AS

--空运月报表

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
 WHERE T.BUSINESS_CASE IN ('AIR_COST_DEST_AGENCY_PAY_CFRM',
													 'AIR_COST_DEST_AGENCY_PAY_NCFRM',
													 'AIR_COST_PAY_APPLY',
													 'AOR_CH_UR',
													 'APWR_COST_WO_DEST_RCVT_NPOD',
													 'APWR_OTH_PAY_WO_DEST_RCVT_POD',
													 'URTA_DEST_CH_POD',
													 'AIR_COD_CH_UR_POD',
													 'AIR_COD_OPAY_WO_COD_RCV_NPOD',
													 'AIR_COD_POD_WO',
													 'AIR_COD_UPD_WO',
													 'AIR_COST_FEE_CONFIRM',
													 'AIR_DR_WO_DEST_RCVT_NPOD',
													 'AOR_ENTRY',
													 'APWR_OTH_PAY_WO_DEST_RCVO_POD',
													 'URTA_DEST_CD_NPOD',
													 'URTA_DEST_CD_POD',
													 'AIR_COD_DPAY_WO_COD_RCV_POD',
													 'AIR_DR_WO_DEST_RCVO_POD',
													 'APT_WO_AIR_PAY',
													 'APWR_COST_WO_DEST_RCVO_POD',
													 'APWR_OTH_PAY_WO_OTH_RCV',
													 'BWOR',
													 'URTA_DEST_CH_NPOD',
													 'AIR_COST_DEST_AGENCY_PAY_GEN',
													 'AIR_COST_OTH_PAY_COST_CFRM',
													 'AIR_DR_CD',
													 'APT_AIR_COMPANY',
													 'APWR_COST_WO_DEST_RCVT_POD',
													 'UROA_DEST_CH_NPOD',
													 'AIR_COD_CD_UR_POD',
													 'AIR_COST_ORIG_AGENCY_PAY_CFRM',
													 'APWR_OTH_PAY_WO_DEST_RCVO_NPOD',
													 'AIR_COD_CD_UR_NPOD',
													 'AIR_COD_CH_UR_NPOD',
													 'AIR_COD_COST_WO_COD_RCV_NPOD',
													 'AIR_DR_PAY_APPLY',
													 'AIR_DR_WO_COD_RCV_NPOD',
													 'AIR_DR_WO_DEST_RCVT_POD',
													 'APT_WO_OTH_PAY',
													 'AIR_DR_CH',
													 'AIR_DR_WO_OTH_RCV',
													 'AOR_CD_UR',
													 'APWR_OTH_PAY_WO_DEST_RCVT_NPOD',
													 'UROA_DEST_CD_POD',
													 'UROA_DEST_CH_POD',
													 'AIR_COD_OTH_PAY_WO_COD_RCV_POD',
													 'AIR_DR_WO_COD_RCV_POD',
													 'AIR_DR_WO_DEST_RCVO_NPOD',
													 'APWR_COST_WO_DEST_RCVO_NPOD',
													 'UROA_DEST_CD_NPOD')
			 AND T.PRODUCT_CODE = 'AF' --精准空运                              
                                      
WITH READ ONLY;
