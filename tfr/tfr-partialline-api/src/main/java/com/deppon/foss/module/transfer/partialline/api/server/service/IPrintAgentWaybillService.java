package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;

public interface IPrintAgentWaybillService extends IService {
	/**
	 * @author nly
	 * @param limit 
	 * @param start 
	 * @date 2014年9月5日 下午2:56:25
	 * @function 查询代理面单的运单
	 * @return 
	 */
	List<PrintAgentWaybillEntity> queryWaybills(PrintAgentWaybillDto dto, int start, int limit);

	/**
	 * @author nly
	 * @date 2014年9月9日 下午2:37:14
	 * @function 查询运单数量
	 * @param dto
	 * @return
	 */
	Long queryWaybillCount(PrintAgentWaybillDto dto);
	/**
	 * @author nly
	 * @date 2014年9月13日 下午12:38:27
	 * @function 根据运单号查询运单信息
	 * @param waybillNo
	 * @return
	 */
	PrintAgentWaybillEntity queryWaybillByNo(String waybillNo);

	/**
	 * @author nly
	 * @date 2014年9月15日 下午2:06:30
	 * @function 新增打印代理面单记录
	 * @param printRecord
	 * @return
	 */
	int addPrintrecord(PrintAgentWaybillRecordEntity printRecord);
	
	/**
	 * @author chigo
	 * @date 2014-10-15上午8:39:24
	 * @function  综合查询代理信息
	 * @param agentWaybillRecordEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<PrintAgentWaybillRecordEntity> queryWaybillsRecord(PrintAgentWaybillRecordEntity agentWaybillRecordEntity, int start, int limit);
	/**
	 * @author nly
	 * @date 2015年2月2日 下午5:14:46
	 * @function
	 * @param agentWaybillNo
	 * @return
	 */
	List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(String agentWaybillNo);
	/**
	 * @author nly
	 * @date 2015年2月4日 下午2:30:36
	 * @function 根据运单号和绑定类型查询绑定记录
	 * @param waybillNo
	 * @return
	 */
	List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(String waybillNo,String type);

	/**
	 * @author chigo
	 * @date 2015年2月3日 上午10:23:56
	 * @function 查询营业部外发绑定运单信息
	 * @return 
	 */
	List<PrintAgentWaybillRecordEntity> queryBundleWaybills(
			PrintAgentWaybillDto dto, int start, int limit);

	/**
	 * @author chigo
	 * @date 2015年2月3日 下午4:06:37
	 * @function 查询营业部外发绑定运单信息
	 * @param dto
	 * @return
	 */
	Long queryBundleWaybillsCount(PrintAgentWaybillDto dto);
	/**
	 * @author chigo
	 * @date 2015年2月4日 下午8:52:37
	 * @function 绑定营业部外发运单信息
	 * @param dto
	 * @return
	 */
	int bundleSdExternalBill(PrintAgentWaybillDto dto);

	/**
	 * @author chigo
	 * @date 2015年2月5日 上午10:40:36
	 * @function 作废绑定营业部外发运单信息
	 * @param dto
	 * @return
	 */
	int unBundleSdExternalBill(PrintAgentWaybillDto dto);

	int addLdpBindRecord(PrintAgentWaybillRecordEntity entity);

	int invalidLdpBindRecord(PrintAgentWaybillRecordEntity entity);

	String queryLdpExternalBill(List<String> list);

	void checkAgentWaybillRecord(PrintAgentWaybillRecordEntity entity);

	void checkWaybillRecord(PrintAgentWaybillRecordEntity entity);

	int batchImportPrintAgentWaybillList(List<PrintAgentWaybillRecordEntity> excelDtos);

	void checkStockAndHandOverbill(PrintAgentWaybillRecordEntity entity);
	
	void setAgentCompanybyPickupNetWork(PrintAgentWaybillRecordEntity entity);

	/**
	 * @param excelDto
	 * @author 257220
	 * @date 2015-6-2下午2:46:23
	 */
	void checkWaybillIsOuter(PrintAgentWaybillRecordEntity excelDto);

	/**
	 * 构建与快递一百交互dto
	 * @param recordList
	 * @return
	 * @author 257220
	 * @date 2015-7-29上午8:52:20
	 */
	List<AgentWaybillNoRequestDto> buildAgentWaybillNoRequestDto(
			List<PrintAgentWaybillRecordEntity> recordList);

	/**
	 * @param entity
	 * @author 257220
	 * @date 2015-8-3下午4:00:24
	 */
	void checkAgentWaybillCanDoOrder(PrintAgentWaybillRecordEntity entity);

	/**
	 * 停止向快递100订阅轨迹
	 * @param printAgentWaybillRecordEntity
	 * @author 257220
	 * @date 2015-8-5下午2:29:18
	 */
	int stopTrackOrder(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity);
	/**
	 * 根据代理单号与代理公司查询绑定记录
	 * @param agentWaybillNo
	 * @param agentCompanyCode
	 * @return
	 * @author 257220
	 * @date 2015-8-12下午2:35:38
	 */
	public List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNoAndCompany(String agentWaybillNo,String 
			agentCompanyCode);
	
	/**
	 * @author alfred
	 * @function 插入轨迹信息
	 * @param entity
	 * @param user
	 */
	public void addTrackSyn(PrintAgentWaybillRecordEntity entity,CurrentInfo user);

}
