package com.deppon.pda.bdm.module.foss.delivery.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.constants.DeliveryConstant;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
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

/**
 * 
  * @ClassName DeliveryDao 
  * @Description TODO 
  * @author xujun cometzb@126.com 
  * @date 2012-12-28
 */
public class DeliveryDao extends iBatis3DaoImpl implements IDeliveryDao{
	/**
	 * 
	* @Title: saveFedbckDeryStusScanMsg
	* @Description: 【反馈任务已接收 】扫描表
	* @param scanMsg
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveFedbckDeryStusScanMsg(FedBackDeryStatusEntity scanMsg) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_FEDBCKDERYSTUS_SCANMSG, scanMsg);
	}

	/**
	 * 
	* @Title: saveDeliveryExceptionMsg
	* @Description: 【派送异常】扫描表
	* @param deryExcpScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveDeryExcepScanMsg(DeryExcpScanEntity deryExcpScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_DERYEXCEP_SCANMSG, deryExcpScan);
	}

	/**
	 * @Title: saveDeliveryExceptionMsg
	* @Description: 【派送异常】 签收信息表
	* @param deryExcpScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveDeryExcepSign(DeryExcpScanEntity deryExcpScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_DERYEXCEP_SIGN, deryExcpScan);
	}

	/**
	 * @Title: saveNormPcSignScanScanMsg
	* @Description: 【正常按件签收】扫描表 
	* @param normPcSignScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormPcSignScanScanMsg(NormPcSignScanEntity normPcSignScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMPCSIGNSCAN_SCANMSG, normPcSignScan);
	}

	/**
	 * @Title: saveNormPcSignScanSign
	* @Description: 【正常按件签收】签收信息表
	* @param normPcSignScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormPcSignScanSign(NormPcSignScanEntity normPcSignScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMPCSIGNSCAN_SIGN, normPcSignScan);
	}

	/**
	 * @Title: saveNormPcSignScanSerilnumber
	* @Description: 【正常按件签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormPcSignScanSerilnumber(
			PdaSignDetailEntity pdaSignDetail) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMPCSIGNSCAN_SERILNUMBER, pdaSignDetail);
	}
	/**
	 * @Title: saveNormSignByVoteScanMsg
	* @Description: 【正常按票签收】扫描表
	* @param normVoteSignScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormSignByVoteScanMsg(
			NormVoteSignScanEntity normVoteSignScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMSIGNBYVOTE_SCANMSG, normVoteSignScan);
	}

	/**
	 * @Title: saveNormSignByVoteSign
	* @Description: 【正常按票签收】签收信息表
	* @param normVoteSignScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormSignByVoteSign(NormVoteSignScanEntity normVoteSignScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMSIGNBYVOTE_SIGN, normVoteSignScan);
	}

	/**
	 * @Title: saveNormSignByVoteSerilnumber
	* @Description: 【正常按票签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveNormSignByVoteSerilnumber(
			PdaSignDetailEntity pdaSignDetail) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_NORMSIGNBYVOTE_SERILNUMBER, pdaSignDetail);
	}

	/**
	 * @Title: saveSelfDeryScanScanMsg
	* @Description: 【客户自提】扫描表
	* @param selfDeryScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveSelfDeryScanScanMsg(SelfDeryScanEntity selfDeryScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_SELFDERYSCAN_SCANMSG, selfDeryScan);
	}

	/**
	 * @Title: saveSelfDeryScanSign
	* @Description: 【客户自提】签收信息表
	* @param selfDeryScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveSelfDeryScanSign(SelfDeryScanEntity selfDeryScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_SELFDERYSCAN_SIGN, selfDeryScan);
	}

	/**
	 * @Title: saveSelfDeryScanSerilnumber
	* @Description: 【客户自提】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveSelfDeryScanSerilnumber(PdaSignDetailEntity pdaSignDetail) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_SELFDERYSCAN_SERILNUMBER, pdaSignDetail);
	}

	/**
	 * @Title: saveExcpSignByPcScanScanMsg
	* @Description: 【异常按件签收】扫描表 
	* @param excpSignByPcScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveExcpSignByPcScanScanMsg(
			ExcpSignByPcScanEntity excpSignByPcScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXCPSIGNBYPCSCAN_SCANMSG, excpSignByPcScan);
	}

	/**
	 * @Title: saveExcpSignByPcScanSign
	* @Description: 【异常按件签收】签收信息表
	* @param excpSignByPcScan
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveExcpSignByPcScanSign(ExcpSignByPcScanEntity excpSignByPcScan) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXCPSIGNBYPCSCAN_SIGN, excpSignByPcScan);
	}

	/**
	 * @Title: saveExcpSignByPcScanSerilnumber
	* @Description: 【异常按件签收】流水号列表
	* @param pdaSignDetail
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveExcpSignByPcScanSerilnumber(
			PdaSignDetailEntity pdaSignDetail) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXCPSIGNBYPCSCAN_SERILNUMBER, pdaSignDetail);
	}

	/**
	 * @Title: saveExcpSignByPcScanPicture
	* @Description: 【异常按件签收】图片信息表
	* @param picture
	* @return void  返回类型 
	* @throws
	 */
	@Override
	public void saveExcpSignByPcScanPicture(PictureEntity picture) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXCPSIGNBYPCSCAN_PICTURE, picture);
	}

	 /**
     * @Title: saveExpNormSignScanScanMsg
    * @Description: 【快递正常按件签收】签收信息表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
	@Override
    public void saveExpNormSignScanScanMsg(ExpNormSignEntity expNormSign){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPNORMSIGNSCAN_SCANMSG, expNormSign);   
    }
    /**
     * @Title: saveExpNormSignScanSign
    * @Description: 【快递正常按件签收】签收信息表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
	@Override
    public void saveExpNormSignScanSign(ExpNormSignEntity expNormSign){
        getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPNORMSIGNSCAN_SIGN, expNormSign); 
    }
	
    /**
     * @Title: saveExpNormSignScanSignDetail
    * @Description: 【快递正常按件签收】签收信息表分录
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
    @Override
    public void saveExpNormSignScanSignDetail(ExpNormSignEntity expNormSign) {      
        getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPNORMSIGNSCAN_DETAIL_SIGN, expNormSign);
    }
    /**
     * @Title: saveExpNormSignScanSerilnumber
    * @Description: 【快递正常按件签收】流水号列表
    * @param pdaSignDetail
    * @return void  返回类型 
    * @throws
     */
	@Override
    public void saveExpNormSignScanSerilnumber(PdaSignDetailEntity pdaSignDetail){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPNORMSIGNSCAN_SERILNUMBER, pdaSignDetail);
    }
	

    /**
     * @Title: saveExpExcpSignScanScanMsg
    * @Description: 【快递异常按件签收】扫描表 
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
	@Override
    public  void saveExpExcpSignScanScanMsg(ExpExcpSignEntity expExcpSign){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPEXCPSIGNSCAN_SCANMSG, expExcpSign);
    }
    /**
     * @Title: saveExpExcpSignScanSign
    * @Description: 【快递异常按件签收】签收信息表
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
	@Override
    public  void saveExpExcpSignScanSign(ExpExcpSignEntity expExcpSign){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPEXCPSIGNSCAN_SIGN, expExcpSign);
    }
	

    /**
     * @Title: saveExpExcpSignScanSignDetail
    * @Description: 【快递异常按件签收】签收信息表分录
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
    @Override
    public void saveExpExcpSignScanSignDetail(ExpExcpSignEntity expExcpSign) {
        getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPEXCPSIGNSCAN_DETAIL_SIGN, expExcpSign);
    }
	
    /**
     * @Title: saveExpExcpSignScanSerilnumber
    * @Description: 【快递异常按件签收】流水号列表
    * @param pdaSignDetail
    * @return void  返回类型 
    * @throws
     */
	@Override
    public  void saveExpExcpSignScanSerilnumber(PdaSignDetailEntity pdaSignDetail){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPEXCPSIGNSCAN_SERILNUMBER, pdaSignDetail);
    }
    /**
     * @Title: saveExpExcpSignScanPicture
    * @Description: 【快递异常按件签收】图片信息表
    * @param picture
    * @return void  返回类型 
    * @throws
     */
	@Override
    public  void saveExpExcpSignScanPicture(PictureEntity picture){
	    getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPEXCPSIGNSCAN_PICTURE, picture);
	}

	
	/**
     * @Title: saveExpParentSignScanmsg
    * @Description: 【子母件正常签收】扫描表
    * @param 
    * @return void  返回类型 
    * @throws
    * @author 245955
     */
	@Override
	public void saveExpParentSignScanmsg(MotherSonSignList motherSonSignList) {
    	getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPPARENTSIGN_SCANMSG, motherSonSignList);
	}
	 /**
     * @Title: saveExpParentSignScanSign
    * @Description: 【子母件正常签收】签收信息
    * @param 
    * @return void  返回类型 
    * @throws
    *  @author 245955
     */
	@Override
	public void saveExpParentSignScanSign(MotherSonSignList motherSonSignList) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPPARENTSIGN_SCANSIGN, motherSonSignList);
	}
	 /**
     * @Title: saveExpParentSignDetail 
    * @Description: 【子母件正常签收】 签收分录
    * @param 
    * @return void  返回类型 
    * @throws
    *  @author 245955
     */
	@Override
	public void saveExpParentSignDetail(ExpSignParentEntity expSignParentEntity) {
		getSqlSession().insert(DeliveryDao.class.getName()+DeliveryConstant.INSERT_TABLE_ROWS.SAVE_EXPPARENTSIGN_DETAIL, expSignParentEntity);
	}
}
