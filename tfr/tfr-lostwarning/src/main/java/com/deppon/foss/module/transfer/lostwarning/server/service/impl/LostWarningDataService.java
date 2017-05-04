package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.LostWarningDataEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.WayBillSerialInfoEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.ILostWarningDataService;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 丢货预警数据操作
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：LostWarningDataService
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-10 上午9:56:06
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningDataService implements ILostWarningDataService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LostWarningDataService.class);

	private ILostWarningDataDao lostWarningDataDao;
	
	
	public void setLostWarningDataDao(ILostWarningDataDao lostWarningDataDao) {
		this.lostWarningDataDao = lostWarningDataDao;
	}
	
//	private IConfigurationParamsService configurationParamsService;
	/*public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}*/
	
	/**
	 * 查询丢货数据
	 * @Description: 
	 * @date 2015-6-10 下午5:35:37   
	 * @author 263072
	 */
	@Override
	public List<LostWarningTempDto> searchLostWarnningData(String jobID) {
		return lostWarningDataDao.searchLostData(jobID);
			
	}

	/**
	 * 出发库存丢货数据同步到临时表
	 */
	@Override
	public void synStartDptLostWarningData() {
		try{
			lostWarningDataDao.synStartDptLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
		
	}

	/**
	 * @Description: 同步集中接货丢货数据
	 * @date 2015-6-16 下午6:22:59   
	 * @author 263072
	 */
	@Override
	public void synJZReceiveLostData() {
		try{
			lostWarningDataDao.synJZReceiveLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步传输丢货数据
	 * @date 2015-6-19 下午1:56:20   
	 * @author 263072
	 */
	@Override
	public void synTransferLostData() {
		try{
			lostWarningDataDao.synTransferLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步已到达丢货数据
	 * @date 2015-6-24 上午10:09:34   
	 * @author 263072
	 */
	@Override
	public void synAlreadyArriveLostData() {
		try{
			lostWarningDataDao.synAlreadyArriveLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步中转库存丢货数据
	 * @date 2015-6-25 上午11:09:02   
	 * @author 263072
	 */
	@Override
	public void synTransferStoreLostData() {
		try{
			lostWarningDataDao.synTransferStoreLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步已交接丢货数据
	 * @date 2015-6-26 下午6:51:13   
	 * @author 263072
	 */
	@Override
	public void synHandoverLostData() {
		try{
			lostWarningDataDao.synHandoverLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description:同步三次以上异常库存数据 
	 * @date 2015-6-30 上午11:06:22   
	 * @author 263072
	 */
	@Override
	public void synDifferStockLostData() {
		try{
			lostWarningDataDao.synDifferStockLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步派送丢货数据
	 * @date 2015-7-3 下午3:25:28   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synDeliverLostData() {
		try{
			lostWarningDataDao.synDeliverLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步空运外发丢货数据
	 * @date 2015-7-6 下午4:35:23   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synAirTransferLostData() {
		try{
			lostWarningDataDao.synAirTransferLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	/**
	 * @Description: 同步快递外发丢货数据
	 * @date 2015-7-6 下午7:04:38   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synExpressExternalLostData() {
		try{
			lostWarningDataDao.synExpressExternalLostData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}		
	}

	
	/**
	 * @Description: 更改JOBID
	 * @date 2015-7-21 上午8:56:18   
	 * @author 263072 
	 */
	@Override
	public String upateWarningDataForJob() {
		String jobId = UUIDUtils.getUUID();
		lostWarningDataDao.upateWarningDataForJob(jobId, LostWarningConstant.dataLimitCount);
		return jobId;
	}
	
	
	/**
	 * @Description: 保存上报信息成功日志
	 * @date 2015-7-18 下午6:52:26   
	 * @author 263072 
	 * @param bean
	 * @param lostRepCode 丢货编码
	 * @param infoType 临时表字段：上报信息类型
	 * @param uploadMsg 上报字符串
	 */
	public void saveUploadSuccInfo(LostWarningDataEntity bean, String lostRepCode,String infoType,String uploadMsg){
		try{
			List<LostWarningLogDto> list = new ArrayList<LostWarningLogDto>();
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				LostWarningLogDto logDto = new LostWarningLogDto();
				logDto.setIsFind("0");// 是否找到： 1已找到 0未找到
				logDto.setLostRepCode(lostRepCode);
				logDto.setRepCode("1");//上报结果: 1成功 0失败
				logDto.setRepMsg("");
				logDto.setRepScene(bean.getRepScene());
				logDto.setRepType(bean.getRepType());
				logDto.setSerialNo(serialEntity.getFlowCode());
				logDto.setWayBillNo(bean.getWaybillNum());
				logDto.setRespDeptCode(serialEntity.getRespDeptCode());
				logDto.setUploadMsg(uploadMsg);
				list.add(logDto);
			}
			lostWarningDataDao.insertAndDelWarningData(list, infoType);	
			
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * @Description: 保存上报信息失败日志
	 * @date 2015-7-18 下午6:54:46   
	 * @author 263072 
	 * @param bean
	 * @param errorMsg 失败日志信息
	 * @param uploadMsg 
	 */
	public void saveUploadFalseInfo(LostWarningDataEntity bean,String errorMsg,String infoType,String uploadMsg){
		try{
			List<LostWarningLogDto> list = new ArrayList<LostWarningLogDto>();
			//防止字符过长，导致插入失败
			if(!Utils.isStrNull(errorMsg)){
				if(errorMsg.length()>LostWarningConstant.SONAR_NUMBER_500){
					errorMsg=errorMsg.substring(0, LostWarningConstant.SONAR_NUMBER_500);
				}
			}
			for(WayBillSerialInfoEntity serialEntity:bean.getFlowCodeList()){
				LostWarningLogDto logDto = new LostWarningLogDto();
				logDto.setIsFind("0");// 是否找到： 1已找到 0未找到
				logDto.setLostRepCode("");
				logDto.setRepCode("0");//上报结果: 1成功 0失败
				logDto.setRepMsg(errorMsg);
				logDto.setRepScene(bean.getRepScene());
				logDto.setRepType(bean.getRepType());
				logDto.setSerialNo(serialEntity.getFlowCode());
				logDto.setWayBillNo(bean.getWaybillNum());
				logDto.setRespDeptCode(serialEntity.getRespDeptCode());
				logDto.setUploadMsg(uploadMsg);
				list.add(logDto);
			}
			lostWarningDataDao.insertAndDelWarningData(list, infoType);	
			
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	

}
