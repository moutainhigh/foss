package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddGisDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeLogService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddGisService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * @title: IAutoAddCodeService 
 * @description: 自动补码 业务接口实现.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public class AutoAddGisService extends TheadPool implements IAutoAddGisService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoAddGisService.class);
	
	/**
	 * dao层接口
	 */
	
	private IAutoAddGisDao autoAddGisDao;
	
	/**
	 * Service 层接口
	 */
	
	private IAutoAddCodeService autoAddCodeService;
	
	
	/**
	 * 补码service
	 */
	public void setAutoAddCodeService(IAutoAddCodeService autoAddCodeService) {
		this.autoAddCodeService = autoAddCodeService;
	}


	/**
	 * 补码日志service
	 */
	private IAutoAddCodeLogService autoAddCodeLogService;
	
	
	/**
	 * 综合Service
	* @fields ldpAgencyDeptService
	* @author 14022-foss-songjie
	* @update 2015年7月2日 下午5:46:13
	* @version V1.0
	*/
//	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	
	/**
	 * 综合数据字典Service
	* @fields configurationParamsService
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:48:37
	* @version V1.0
	*/
//	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 快递运单Service
	* @fields waybillExpressService
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午2:09:48
	* @version V1.0
	*/
//	private IWaybillExpressService waybillExpressService;
	
	
	
	public void setAutoAddGisDao(IAutoAddGisDao autoAddGisDao) {
		this.autoAddGisDao = autoAddGisDao;
	}


	/**
	* @description 快递运单Service
	* @param waybillExpressService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午2:10:00
	*/
	/*public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}*/
	
	/**
	 * 访问时间间隔6S
	* @fields MaxActiveTime
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午5:00:53
	* @version V1.0
	*/
//	private static final int MaxActiveTime = 6;
	
	
	/**
	 * 城市开关
	* @fields expressAutoComplementManageService
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午1:58:03
	* @version V1.0
	*/
//	private IExpressAutoComplementManageService expressAutoComplementManageService; 

	
	/**
	 * 虚拟营业部快递代理网点映射的service
	* @fields expressSalesAgentMapService
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:02:20
	* @version V1.0
	*/
//	private IExpressSalesAgentMapService expressSalesAgentMapService;
	
	/**
	 * 根据省市code获取name
	 */
//	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**  
	 * administrativeRegionsService.  
	 *  
	 * @param   administrativeRegionsService    the administrativeRegionsService to set  
	 * @since   JDK 1.6  
	 */
	/*public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}*/

	/**
	 * 补码service
	 */
//	private IComplementService complementService;
	
	/**
	 * 后台配置service
	 */
//	private IServerConfigService serverConfigService;
	
	/**
	 * 待人工补码service
	 */
//	private IAutoAddCodeByHandService autoAddCodeByHandService;
	
	/**  
	 * autoAddCodeByHandService.  
	 *  
	 * @param   autoAddCodeByHandService    the autoAddCodeByHandService to set  
	 * @since   JDK 1.6  
	 */
	/*public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
*/

	/**  
	 * serverConfigService.  
	 *  
	 * @param   serverConfigService    the serverConfigService to set  
	 * @since   JDK 1.6  
	 */
/*	public void setServerConfigService(IServerConfigService serverConfigService) {
		this.serverConfigService = serverConfigService;
	}*/


	/**  
	 * complementService.  
	 *  
	 * @param   complementService    the complementService to set  
	 * @since   JDK 1.6  
	 */
	/*public void setComplementService(IComplementService complementService) {
		this.complementService = complementService;
	}*/


	/**
	* @description 城市开关
	* @param expressAutoComplementManageService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:04:06
	*/
/*	public void setExpressAutoComplementManageService(
			IExpressAutoComplementManageService expressAutoComplementManageService) {
		this.expressAutoComplementManageService = expressAutoComplementManageService;
	}
*/

	
	/**
	* @description 虚拟营业部快递代理网点映射的service
	* @param expressSalesAgentMapService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:03:59
	*/
/*	public void setExpressSalesAgentMapService(
			IExpressSalesAgentMapService expressSalesAgentMapService) {
		this.expressSalesAgentMapService = expressSalesAgentMapService;
	}*/
	


	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#getActiveThreads()  
	 */
	
	
	/**
	 * 设置 json 数据格式
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01 上午9:21:30
	 */
	public static ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		return objectMapper;
	}
	

	/**
	* @description 日志添加
	* @param autoAddCodeLogService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月2日 上午8:50:21
	*/
	public void setAutoAddCodeLogService(
			IAutoAddCodeLogService autoAddCodeLogService) {
		this.autoAddCodeLogService = autoAddCodeLogService;
	}


/*	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

*/
	
	/**
	* @description 综合数据字典Service
	* @param configurationParamsService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:49:26
	*/
	/*public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}*/


	@Override
	public int getActiveThreads() {
		return autoAddCodeService.readAutoGisThreadNum();
	}


	@Override
	public String GisUpdateGetJobId() {
		String jobId = UUIDUtils.getUUID();
		autoAddGisDao.updateAndGetJobId(jobId, autoAddCodeService.readAutoGisExeNum());
		return jobId;
	}


	@Override
	public List<AutoAddGisEntity> queryGisAutoAddCodeEntityByJodId(String jobId) {
		return autoAddGisDao.queryAutoAddCodeEntityByJodId(jobId);
	}


	@Override
	public int insertAutoAddGisEntity(AutoAddGisEntity entity) {
		return autoAddGisDao.insert(entity);
	}
	
	
	/**
	* @description AutoAddGisEntity 转换为AutoAddCodeEntity
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月6日 上午10:01:08
	*/
	private AutoAddCodeEntity covertDtoToGisEntity(AutoAddGisEntity dto){
		if(dto!=null){
			AutoAddCodeEntity backEntity = new AutoAddCodeEntity();
			backEntity.setBillTime(dto.getBillTime());
			backEntity.setCreateOrgCode(dto.getCreateOrgCode());
			backEntity.setCreateTime(dto.getCreateTime());
			backEntity.setCustomerPickupOrgCode(dto.getCustomerPickupOrgCode());
			backEntity.setReceiveCustomerAddress(dto.getReceiveCustomerAddress());
			backEntity.setReceiveCustomerCityCode(dto.getReceiveCustomerCityCode());
			backEntity.setReceiveCustomerCityName(dto.getReceiveCustomerCityName());
			backEntity.setReceiveCustomerDistCode(dto.getReceiveCustomerDistCode());
			backEntity.setReceiveCustomerDistName(dto.getReceiveCustomerDistName());
			backEntity.setReceiveCustomerProvCode(dto.getReceiveCustomerProvCode());
			backEntity.setReceiveCustomerProvName(dto.getReceiveCustomerProvName());
			backEntity.setReceiveCustomerTownCode(dto.getReceiveCustomerTownCode());
			backEntity.setReceiveCustomerTownName(dto.getReceiveCustomerTownName());
			backEntity.setTargetOrgCode(dto.getTargetOrgCode());
			backEntity.setWaybillID(dto.getWaybillID());
			backEntity.setWaybillNO(dto.getWaybillNO());
			backEntity.setTbid(dto.getAutoAddCodeId());
			backEntity.setJobId(dto.getAutoAddCodeJobId());
			return backEntity;
		}else{
			return null;
		}
	}


	@Override
	public void ThreadsPool(Object obj) {
		pushThreadsPool(obj);
	}


	@Override
	public void resetData() {
		autoAddGisDao.resetData();
	}
	
	@Override
	public void restNaAJobId(String jobId) throws Exception {
		autoAddGisDao.resetDataById(jobId);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)  
	 */
	@Override
	public void businessExecutor(Object obj) {
		AutoAddGisEntity entity = (AutoAddGisEntity) obj;
		businessComm(entity);
	}
	
	/**
	 * @param entity
	 */
	private void businessComm(AutoAddGisEntity entity){
		if(entity!=null && entity.getType()!=null){
			AutoAddCodeEntity addEntity = covertDtoToGisEntity(entity);
			try{
				LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm");
				//外发单子
				if(entity.getType().equals(AutoAddCodeConstants.OUT_FLAG)){
					autoAddCodeService.outerAutoAddCode(addEntity, entity);
					LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm:外发单子autoAddCodeService.outerAutoAddCode");
				}else if(entity.getType().equals(AutoAddCodeConstants.CITY_OPEN_INT+"")){
					//gis有补码地址
					autoAddCodeService.autoComplement_TheadComm(addEntity, entity);
					LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm:gis有补码地址autoComplement_TheadComm");
				}else if(entity.getType().equals(AutoAddCodeConstants.CITY_CLOSE_JM_INT+"")){
					//静默  人工补码
					entity.setExceptionInfo("城市静默:转人工补码流程");
					autoAddCodeService.addHand(addEntity,entity,AutoAddCodeConstants.ADD_CODE_RESULT_CITY_CLOSE_JM);
					LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm:城市静默:转人工补码流程addHand");
				}else{
					//城市关闭或其他类型 人工补码
					entity.setExceptionInfo("城市关闭或其他类型:人工补码流程");
					autoAddCodeService.addHand(addEntity,entity,AutoAddCodeConstants.ADD_CODE_RESULT_CITY_CLOSE);
					LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm:城市关闭或其他类型:人工补码流程addHand");
				}
			}catch(Exception e){
				StringWriter sw=new StringWriter();  
		        PrintWriter pw=new PrintWriter(sw);  
		        e.printStackTrace(pw);
		        String msgError = "自动补码失败,"+sw.toString();
		        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
		        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
		        }
				AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
				logEntity.setWaybillNo(entity.getWaybillNO());
				logEntity.setJobid(entity.getJobId());
				logEntity.setResult(AutoAddCodeConstants.ADD_CODE_RESULT_BIZ_EXCEPTION);
				logEntity.setExceptionInfo("运单："+entity.getWaybillNO()+msgError);
				logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
				logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
				logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
				logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
				logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
				if(entity!=null && entity.getNewTargetOrgCode()!=null){
					logEntity.setNewTargetOrgCode(entity.getNewTargetOrgCode());
				}
				autoAddCodeLogService.insert(logEntity);
				//转人工补码
				autoAddCodeService.addHand(addEntity,entity,AutoAddCodeConstants.ADD_CODE_RESULT_BIZ_EXCEPTION);
				LOGGER.info("自动补码foss,运单号：" + addEntity.getWaybillNO()+"场景businessComm:人工补码流程addHand"+msgError);
			}
		}
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#outOfUnloadPool(java.lang.Object)  
	 */
	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【 线程池满异常处理开始。。。。。。】");
		AutoAddGisEntity entity = (AutoAddGisEntity) obj;
		if(entity!=null){
			AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
			//异常插入线程日志中
			logEntity.setWaybillNo(entity.getWaybillNO());
			logEntity.setJobid(entity.getJobId());
			logEntity.setResult(AutoAddCodeConstants.ADD_CODE_RESULT_OTHER_EXP);
			logEntity.setExceptionInfo("运单："+entity.getWaybillNO()+"被调用时！线程池已满");
			logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
			logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
			logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
			logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
			logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
			autoAddCodeLogService.insert(logEntity);
			//批量更新没有处理完成数据的jobid为N/A
			autoAddGisDao.resetDataById(entity.getTbid());
		}
		LOGGER.info("【线程池满异常处理结束。。。。。。】");
		
	}
}