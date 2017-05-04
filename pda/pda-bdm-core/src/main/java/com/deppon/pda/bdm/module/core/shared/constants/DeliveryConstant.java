package com.deppon.pda.bdm.module.core.shared.constants;

public interface DeliveryConstant {
	interface INSERT_TABLE_ROWS{
		//【反馈任务已接收 】扫描表
		String SAVE_FEDBCKDERYSTUS_SCANMSG = ".saveFedbckDeryStusScanMsg";
		
		//【派送异常】扫描表
		String SAVE_DERYEXCEP_SCANMSG = ".saveDeryExcepScanMsg";
		//【派送异常】 签收信息表
		String SAVE_DERYEXCEP_SIGN = ".saveDeryExcepSign";
		
		//【正常按件签收】扫描表
		String SAVE_NORMPCSIGNSCAN_SCANMSG = ".saveNormPcSignScanScanMsg";
		//【正常按件签收】签收信息表
		String SAVE_NORMPCSIGNSCAN_SIGN = ".saveNormPcSignScanSign";
		//【正常按件签收】流水号列表
		String SAVE_NORMPCSIGNSCAN_SERILNUMBER = ".saveNormPcSignScanSerilnumber";
		
		//【正常按票签收】扫描表
		String SAVE_NORMSIGNBYVOTE_SCANMSG = ".saveNormSignByVoteScanMsg";
		//【正常按票签收】签收信息表
		String SAVE_NORMSIGNBYVOTE_SIGN = ".saveNormSignByVoteSign";
		//【正常按票签收】流水号列表
		String SAVE_NORMSIGNBYVOTE_SERILNUMBER = ".saveNormSignByVoteSerilnumber";
		
		//【客户自提】扫描表
		String SAVE_SELFDERYSCAN_SCANMSG = ".saveSelfDeryScanScanMsg";
		//【客户自提】签收信息表
		String SAVE_SELFDERYSCAN_SIGN = ".saveSelfDeryScanSign";
		//【客户自提】流水号列表
		String SAVE_SELFDERYSCAN_SERILNUMBER = ".saveSelfDeryScanSerilnumber";
		
		//【异常按件签收】扫描表 
		String SAVE_EXCPSIGNBYPCSCAN_SCANMSG = ".saveExcpSignByPcScanScanMsg";
		//【异常按件签收】签收信息表
		String SAVE_EXCPSIGNBYPCSCAN_SIGN = ".saveExcpSignByPcScanSign";
		//【异常按件签收】流水号列表
		String SAVE_EXCPSIGNBYPCSCAN_SERILNUMBER = ".saveExcpSignByPcScanSerilnumber";
		//【异常按件签收】图片信息表
		String SAVE_EXCPSIGNBYPCSCAN_PICTURE = ".saveExcpSignByPcScanPicture";
		
		//【正常按件签收】扫描表
        String SAVE_EXPNORMSIGNSCAN_SCANMSG = ".saveExpNormSignScanScanMsg";
        //【正常按件签收】签收信息表
        String SAVE_EXPNORMSIGNSCAN_SIGN = ".saveExpNormSignScanSign";
      //【正常按件签收】签收信息表分录
        String SAVE_EXPNORMSIGNSCAN_DETAIL_SIGN = ".saveExpNormSignScanSignDetail";
        //【正常按件签收】流水号列表
        String SAVE_EXPNORMSIGNSCAN_SERILNUMBER = ".saveExpNormSignScanSerilnumber";
		
        //【异常按件签收】扫描表 
        String SAVE_EXPEXCPSIGNSCAN_SCANMSG = ".saveExpExcpSignScanScanMsg";
        //【异常按件签收】签收信息表
        String SAVE_EXPEXCPSIGNSCAN_SIGN = ".saveExpExcpSignScanSign";
        //【异常按件签收】签收信息表分录
        String SAVE_EXPEXCPSIGNSCAN_DETAIL_SIGN = ".saveExpExcpSignScanSignDetail";
        //【异常按件签收】流水号列表
        String SAVE_EXPEXCPSIGNSCAN_SERILNUMBER = ".saveExpExcpSignScanSerilnumber";
        //【异常按件签收】图片信息表
        String SAVE_EXPEXCPSIGNSCAN_PICTURE = ".saveExpExcpSignScanPicture";
		
        /**保存子母件签收信息*/
        //【子母件正常签收】扫描表
        String SAVE_EXPPARENTSIGN_SCANMSG=".saveExpParentSignScanmsg";
       //【子母件正常签收】签收信息
        String SAVE_EXPPARENTSIGN_SCANSIGN=".saveExpParentSignScanSign";
       //【子母件正常签收】签收分录
        String SAVE_EXPPARENTSIGN_DETAIL=".saveExpParentSignDetail";
	}
}
