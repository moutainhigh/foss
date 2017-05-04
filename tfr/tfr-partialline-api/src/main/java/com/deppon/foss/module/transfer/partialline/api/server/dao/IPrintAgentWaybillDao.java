package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;

public interface IPrintAgentWaybillDao {

	List<PrintAgentWaybillEntity> queryWaybills(PrintAgentWaybillDto dto, int start, int limit) ;
	List<PrintAgentWaybillRecordEntity> queryWaybillsRecord(PrintAgentWaybillRecordEntity agentWaybillRecordEntity, int start, int limit) ;

	Long queryWaybillCount(PrintAgentWaybillDto dto);
	Integer addPrintrecord(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity);
	Integer updateActive(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity);
	List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(String agentWaybillNo);
	List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(String waybillNo,String type);
	
	List<PrintAgentWaybillRecordEntity> queryBundleWaybills(
			PrintAgentWaybillDto dto, int start, int limit);
	
	Long queryBundleWaybillsCount(PrintAgentWaybillDto dto);
	
	Integer bundleSdExternalBill(PrintAgentWaybillRecordEntity entity);
	
	Integer bundleFrightFee(PrintAgentWaybillRecordEntity entity);
	
	Integer unBundleSdExternalBill(PrintAgentWaybillRecordEntity entity);
	
	Integer unBundleFrightFee(PrintAgentWaybillRecordEntity entity);
	
	Long validateBundle(PrintAgentWaybillRecordEntity entity);
	int invalidLdpBindRecord(PrintAgentWaybillRecordEntity entity);
	List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNoAndSerialNo(String waybillNo, String serialNo);
	int queryStockAndHandOverBillCount(String waybillNo, String serialNo,String deptCode);
	/**
	 * @param waybillNo
	 * @return
	 * @author 257220
	 * @date 2015-6-2上午11:30:05
	 */
	int queryOuterNetWorkNumByWaybillNo(String waybillNo);

	
	List<PrintAgentWaybillRecordEntity> queryOrderedRecord(PrintAgentWaybillRecordEntity entity);
	/**
	 * 更新推送代理绑定状态
	 * @param printAgentWaybillRecordEntity
	 * @return
	 * @author 257220
	 * @date 2015-8-5下午2:34:03
	 */
	int updatePrintAgentPushState(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity);
	/**
	 * @param agentWaybillNo
	 * @param agentCompanyCode
	 * @return
	 * @author 257220
	 * @date 2015-8-10下午6:58:04
	 */
	List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(String agentWaybillNo, String agentCompanyCode);
	
}
