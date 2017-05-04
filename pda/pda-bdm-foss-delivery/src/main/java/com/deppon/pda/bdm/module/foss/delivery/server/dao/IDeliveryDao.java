package com.deppon.pda.bdm.module.foss.delivery.server.dao;

import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeryExcpScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExcpSignByPcScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpExcpSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpNormSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpSignParentEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.FedBackDeryStatusEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.MotherSonSignList;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.NormPcSignScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.NormVoteSignScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PictureEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SelfDeryScanEntity;

public interface IDeliveryDao {
	/**
	 * 
	* @Title: saveFedbckDeryStusScanMsg
	* @Description: 【反馈任务已接收 】扫描表
	* @param scanMsg
	* @return void  返回类型 
	* @throws
	 */
	void saveFedbckDeryStusScanMsg(FedBackDeryStatusEntity scanMsg);
	
	/**
	 * 
	* @Title: saveDeliveryExceptionMsg
	* @Description: 【派送异常】扫描表
	* @param deryExcpScan
	* @return void  返回类型 
	* @throws
	 */
	void saveDeryExcepScanMsg(DeryExcpScanEntity deryExcpScan);
	/**
	 * @Title: saveDeliveryExceptionMsg
	* @Description: 【派送异常】 签收信息表
	* @param deryExcpScan
	* @return void  返回类型 
	* @throws
	 */
	void saveDeryExcepSign(DeryExcpScanEntity deryExcpScan);
	
	/**
	 * @Title: saveNormPcSignScanScanMsg
	* @Description: 【正常按件签收】扫描表 
	* @param normPcSignScan
	* @return void  返回类型 
	* @throws
	 */
	void saveNormPcSignScanScanMsg(NormPcSignScanEntity normPcSignScan);
	/**
	 * @Title: saveNormPcSignScanSign
	* @Description: 【正常按件签收】签收信息表
	* @param normPcSignScan
	* @return void  返回类型 
	* @throws
	 */
	void saveNormPcSignScanSign(NormPcSignScanEntity normPcSignScan);
	/**
	 * @Title: saveNormPcSignScanSerilnumber
	* @Description: 【正常按件签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	void saveNormPcSignScanSerilnumber(PdaSignDetailEntity pdaSignDetail);
	
	/**
	 * @Title: saveNormSignByVoteScanMsg
	* @Description: 【正常按票签收】扫描表
	* @param normVoteSignScan
	* @return void  返回类型 
	* @throws
	 */
	void saveNormSignByVoteScanMsg(NormVoteSignScanEntity normVoteSignScan);
	/**
	 * @Title: saveNormSignByVoteSign
	* @Description: 【正常按票签收】签收信息表
	* @param normVoteSignScan
	* @return void  返回类型 
	* @throws
	 */
	void saveNormSignByVoteSign(NormVoteSignScanEntity normVoteSignScan);
	/**
	 * @Title: saveNormSignByVoteSerilnumber
	* @Description: 【正常按票签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	void saveNormSignByVoteSerilnumber(PdaSignDetailEntity pdaSignDetail);
	
	/**
	 * @Title: saveSelfDeryScanScanMsg
	* @Description: 【客户自提】扫描表
	* @param selfDeryScan
	* @return void  返回类型 
	* @throws
	 */
	void saveSelfDeryScanScanMsg(SelfDeryScanEntity selfDeryScan);
	/**
	 * @Title: saveSelfDeryScanSign
	* @Description: 【客户自提】签收信息表
	* @param selfDeryScan
	* @return void  返回类型 
	* @throws
	 */
	void saveSelfDeryScanSign(SelfDeryScanEntity selfDeryScan);
	/**
	 * @Title: saveSelfDeryScanSerilnumber
	* @Description: 【客户自提】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	void saveSelfDeryScanSerilnumber(PdaSignDetailEntity pdaSignDetail);
	
	/**
	 * @Title: saveExcpSignByPcScanScanMsg
	* @Description: 【异常按件签收】扫描表 
	* @param excpSignByPcScan
	* @return void  返回类型 
	* @throws
	 */
	void saveExcpSignByPcScanScanMsg(ExcpSignByPcScanEntity excpSignByPcScan);
	/**
	 * @Title: saveExcpSignByPcScanSign
	* @Description: 【异常按件签收】签收信息表
	* @param excpSignByPcScan
	* @return void  返回类型 
	* @throws
	 */
	void saveExcpSignByPcScanSign(ExcpSignByPcScanEntity excpSignByPcScan);
	/**
	 * @Title: saveExcpSignByPcScanSerilnumber
	* @Description: 【异常按件签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	void saveExcpSignByPcScanSerilnumber(PdaSignDetailEntity pdaSignDetail);
	/**
	 * @Title: saveExcpSignByPcScanPicture
	* @Description: 【异常按件签收】图片信息表
	* @param picture
	* @return void  返回类型 
	* @throws
	 */
	void saveExcpSignByPcScanPicture(PictureEntity picture);
    /**
     * @Title: saveExpNormSignScanScanMsg
    * @Description: 【快递正常按件签收】签收信息表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpNormSignScanScanMsg(ExpNormSignEntity expNormSign);
    /**
     * @Title: saveExpNormSignScanSign
    * @Description: 【快递正常按件签收】签收信息表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpNormSignScanSign(ExpNormSignEntity expNormSign);
    
    /**
     * @Title: saveExpNormSignScanSignDetail
    * @Description: 【快递正常按件签收】签收信息表分录
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpNormSignScanSignDetail(ExpNormSignEntity expNormSign);
    
    
    /**
     * @Title: saveExpNormSignScanSerilnumber
    * @Description: 【快递正常按件签收】流水号列表
    * @param pdaSignDetail
    * @return void  返回类型 
    * @throws
     */
    void saveExpNormSignScanSerilnumber(PdaSignDetailEntity pdaSignDetail);
    /**
     * @Title: saveExpExcpSignScanScanMsg
    * @Description: 【快递异常按件签收】扫描表 
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpExcpSignScanScanMsg(ExpExcpSignEntity expExcpSign);
    /**
     * @Title: saveExpExcpSignScanSign
    * @Description: 【快递异常按件签收】签收信息表
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpExcpSignScanSign(ExpExcpSignEntity expExcpSign);
    
    /**
     * @Title: saveExpExcpSignScanSignDetail
    * @Description: 【快递异常按件签收】签收信息表分录
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
    void saveExpExcpSignScanSignDetail(ExpExcpSignEntity expExcpSign);
    
    /**
     * @Title: saveExpExcpSignScanSerilnumber
    * @Description: 【快递异常按件签收】流水号列表
    * @param pdaSignDetail
    * @return void  返回类型 
    * @throws
     */
    void saveExpExcpSignScanSerilnumber(PdaSignDetailEntity pdaSignDetail);
    /**
     * @Title: saveExpExcpSignScanPicture
    * @Description: 【快递异常按件签收】图片信息表
    * @param picture
    * @return void  返回类型 
    * @throws
     */
    void saveExpExcpSignScanPicture(PictureEntity picture);
   
    
    /**
     * @Title: saveExpParentSignScanmsg
    * @Description: 【子母件正常签收】扫描表
    * @param 
    * @return void  返回类型 
    * @throws
    * @author 245955
     */
    void saveExpParentSignScanmsg(MotherSonSignList motherSonSignList);
    /**
     * @Title: saveExpParentSignScanSign
    * @Description: 【子母件正常签收】签收信息
    * @param 
    * @return void  返回类型 
    * @throws
    *  @author 245955
     */
    void saveExpParentSignScanSign(MotherSonSignList motherSonSignList);
    /**
     * @Title: saveExpParentSignDetail 
    * @Description: 【子母件正常签收】 签收分录
    * @param 
    * @return void  返回类型 
    * @throws
    *  @author 245955
     */
    void saveExpParentSignDetail(ExpSignParentEntity expSignParentEntity);
}
