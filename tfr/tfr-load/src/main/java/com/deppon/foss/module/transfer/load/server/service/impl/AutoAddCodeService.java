package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoComplementManageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IServerConfigService;
import com.deppon.foss.module.transfer.common.api.shared.define.ServerConfigCodeConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.ServerConfigModuleConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.ServerConfigEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddGisDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeLogService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddGisService;
import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.GisRequestDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.GisResponseDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.GisWaybillResponseDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @title: IAutoAddCodeService 
 * @description: 自动补码 业务接口实现.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public class AutoAddCodeService extends TheadPool implements IAutoAddCodeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoAddCodeService.class);
	
	/**
	 * dao层接口
	 */
	private IAutoAddCodeDao autoAddCodeDao;
	
	private IAutoAddGisDao autoAddGisDao;
	
	/**
	 * Service 层接口
	 */

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
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	
	/**
	 * 综合数据字典Service
	* @fields configurationParamsService
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:48:37
	* @version V1.0
	*/
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 快递运单Service
	* @fields waybillExpressService
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午2:09:48
	* @version V1.0
	*/
	private IWaybillExpressService waybillExpressService;
	
	private IAutoAddGisService autoAddGisService;
	
	public void setAutoAddGisService(IAutoAddGisService autoAddGisService) {
		this.autoAddGisService = autoAddGisService;
	}


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
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
	
	/**
	 * 检测GIS所用运单号
	 */
	private static final String TEST_GIS_WAYBILL_NO = "000000000";
	
	/**
	 * 检测GIS所用运单ID
	 */
	private static final String TEST_GIS_WAYBILL_ID = "111111111";
	
	/**
	 * 检测GIS所用省份code
	 */
	private static final String TEST_GIS_PRO_CODE = "310000";
	
	/**
	 * 检测GIS所用城市code
	 */
	private static final String TEST_GIS_CITY_CODE = "310000-1";
	
	/**
	 * 检测GIS所用区县code
	 */
	private static final String TEST_GIS_COUNTY_CODE = "310118";
	
	/**
	 * 检测GIS所用镇code
	 */
	private static final String TEST_GIS_TOWN_CODE = "310118-3";
	
	/**
	 * 检测GIS所用地址
	 */
	private static final String TEST_GIS_ADDRESS = "明珠路1018号";
	
	/**
	 * 一次取出的待补码运单个数
	 */
	private static final int LIMIT = 200;
	
	/**
	 * gis一次取出的待补码运单个数
	 */
	private static final int GIS_LIMIT = 200;
	
	/**
	 * GIS最大线程数
	* @fields MaxActiveThreads
	* @author 14022-foss-songjie
	* @update 2015年7月16日 下午4:45:24
	* @version V1.0
	*/
	private static final int GISMaxActiveThreads=20;
	
	/**
	 * 最大线程数
	* @fields MaxActiveThreads
	* @author 14022-foss-songjie
	* @update 2015年7月16日 下午4:45:24
	* @version V1.0
	*/
	private static final int MaxActiveThreads=10;
	
	
	/**
	 * 访问时间间隔6S
	* @fields MaxActiveTime
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午5:00:53
	* @version V1.0
	*/
	private static final int MaxActiveTime = 6;
	
	
	/**
	 * 城市开关
	* @fields expressAutoComplementManageService
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午1:58:03
	* @version V1.0
	*/
	private IExpressAutoComplementManageService expressAutoComplementManageService; 

	
	/**
	 * 虚拟营业部快递代理网点映射的service
	* @fields expressSalesAgentMapService
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:02:20
	* @version V1.0
	*/
	private IExpressSalesAgentMapService expressSalesAgentMapService;
	
	/**
	 * 根据省市code获取name
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**  
	 * administrativeRegionsService.  
	 *  
	 * @param   administrativeRegionsService    the administrativeRegionsService to set  
	 * @since   JDK 1.6  
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 补码service
	 */
	private IComplementService complementService;
	
	/**
	 * 后台配置service
	 */
	private IServerConfigService serverConfigService;
	
	/**
	 * 待人工补码service
	 */
	private IAutoAddCodeByHandService autoAddCodeByHandService;
	
	/**  
	 * autoAddCodeByHandService.  
	 *  
	 * @param   autoAddCodeByHandService    the autoAddCodeByHandService to set  
	 * @since   JDK 1.6  
	 */
	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}


	/**  
	 * serverConfigService.  
	 *  
	 * @param   serverConfigService    the serverConfigService to set  
	 * @since   JDK 1.6  
	 */
	public void setServerConfigService(IServerConfigService serverConfigService) {
		this.serverConfigService = serverConfigService;
	}


	/**  
	 * complementService.  
	 *  
	 * @param   complementService    the complementService to set  
	 * @since   JDK 1.6  
	 */
	public void setComplementService(IComplementService complementService) {
		this.complementService = complementService;
	}

	/**
	* @description 判断到达城市的开关
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月14日 下午2:46:52
	*/
	private int cityBeOpenAutoAddCode(AutoAddCodeEntity entity){
		int backIntValue = 0;
		if(entity!=null){
			//根据提货网点code 获取开启自动补码的城市
			String cityStatusValue;
			try {
				cityStatusValue = expressAutoComplementManageService.queryListCityCodeByCodeStatus(entity.getReceiveCustomerCityCode());
				backIntValue = Integer.parseInt(cityStatusValue);
			} catch (Exception e) {
				return backIntValue;
			}
		}
		return backIntValue;
	}
	
	/**
	 * autoAddCode:调用补码服务. <br/>  
	 *  Date:2015年6月16日下午6:20:04  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @return  
	 * @since JDK 1.6
	 */
	private String autoAddCode(String waybillNo,String waybillID,String pkporgCode, String matchType){
		LOGGER.info("自动补码foss,运单号：" + waybillNo+"场景autoAddCode_");
		//获取网点名称 
		String pkpOrgName = "";
		try{
			OuterBranchExpressEntity obee = ldpAgencyDeptService.queryLdpAgencyDeptByCode(pkporgCode, FossConstants.YES);
			LOGGER.info("自动补码foss,运单号：" + waybillNo+"场景autoAddCode_queryLdpAgencyDeptByCode 根据网点编码查询是快递代理网点信息");
			if(obee!=null){
				pkpOrgName = obee.getAgentDeptName();
			}
			//没有补过码的进行自动补码
			WaybillExpressEntity waybillDto = waybillExpressService.queryWaybillExpressByNo(waybillNo);
			LOGGER.info("自动补码foss,运单号：" + waybillNo+"场景autoAddCode_waybillExpressService.queryWaybillExpressByNo(waybillNo)");
			if(waybillDto!=null && StringUtils.endsWith(FossConstants.NO, waybillDto.getIsAddCode())){
				complementService.complementPkpOrg(waybillNo, pkporgCode, pkpOrgName, FossConstants.YES, matchType);
				LOGGER.info("自动补码foss,运单号：" + waybillNo+"场景autoAddCode_complementPkpOrg 实际的补码方法");
			}
			
		} catch (BusinessException e) {
			return e.getErrorCode();
		}catch(Exception e){
			StringWriter sw=new StringWriter();  
	        PrintWriter pw=new PrintWriter(sw);  
	        e.printStackTrace(pw);
	        String msgError = "autoAddCode自动补码失败"+sw.toString();
	        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
	        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
	        }
			LOGGER.error(msgError);
			return msgError;
		}
		return FossConstants.YES;
	}
	
	/**
	 * 
	 * getProvinceCityNameByCode:通过行政区划（省市区镇）code获取name. <br/>  
	 *  
	 *  Date:2015年6月10日下午10:52:05  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param proCode
	 * @param cityCode
	 * @param contyCode
	 * @param townCode
	 * @return  
	 * @since JDK 1.6
	 */
	private String[] getProvinceCityNameByCode(String proCode,String cityCode,String distCode,String townCode){
		String[] codes = new String[]{proCode,cityCode,distCode,townCode};
		String[] names = new String[LoadConstants.SONAR_NUMBER_4];
		for(int i = 0;i < LoadConstants.SONAR_NUMBER_4;i++){
			//按省市区镇的顺序依次获取name
			String name = administrativeRegionsService.queryAdministrativeRegionsNameByCode(codes[i]);
			names[i] = name;
		}
		return names;
	}
	
	
	/**
	* @description 通过esb请求gis服务获取运单对应的提货网点
	* @param entityList
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午9:23:21
	*/
	@Override
	public Map<String,Object> requestGisByPostList(
			List<AutoAddCodeEntity> entityList)
			throws Exception {
		Map<String,Object> backMap = new HashMap<String, Object>();
		Date requestGisStartTime= new Date();
		backMap.put("requestGisStartTime", requestGisStartTime);
		//转换为GIS请求所需的dto(城市静默或开启的数据)
		List<GisRequestDto> gisDtoList = this.convertEntity2GisRequestDto(entityList);
		if(gisDtoList!=null && gisDtoList.size()>0){
			//esb服务编码
			//sonar修改 没有使用 311396
			/*String gis_code="ESB_FOSS2ESB_AUTO_FILLCODE_ADDRESS_MATCH_INTF";*/
			 //String url = "192.168.67.12:8580/esb2/rs/ESB_FOSS2ESB_AUTO_FILLCODE_ADDRESS_MATCH_INTF"+gis_code;
//		    String url = "http://192.168.67.12:8180/esb2/rs"+ "/" + gis_code;
		    //项目环境http://192.168.67.70:8180/esb2/rs/ESB_FOSS2ESB_AUTO_FILLCODE_ADDRESS_MATCH_INTF
		    // 修改地址 TODO
//		    String url = "http://192.168.67.70:8180/esb2/rs/"+ gis_code;
		    //String url ="http://10.224.70.67:8090/gis-ws/com/expressMatch/expressDeptMatch";
			//日常一
			//String url ="http://192.168.10.130:8084/gis-ws/com/expressMatch/expressDeptMatch";
			//日常二
			//String url ="http://10.230.13.80:8084/gis-ws/com/expressMatch/expressDeptMatch";
			//生产环境直连url
			String url = "http://gis.deppon.com/gis-ws/com/expressMatch/expressDeptMatch";
		    //String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + gis_code;
			//将list转换为json字符串
			ObjectMapper om = AutoAddCodeService.obtainJSONObjectMapper();
			try {
				String requestStr = om.writeValueAsString(gisDtoList);
				//Date a = new Date();
				//System.out.println("requestStr:===>"+requestStr);
				RequestEntity requestEntity = new StringRequestEntity(requestStr);
				String responseStr = HttpClientComm.postRequest(url, requestEntity);
				//String responseStr = "{\"exceptionMSG\":\"\",\"responses\":[{\"matchType\":\"10\",\"waybillID\":\"87c6e4ad-4dfe-4b94-8ec0-4e2f9613d584\",\"deptCode\":\"W04061130\",\"waybillNum\":\"5250000124\",\"exceptionMSG\":\"\",\"is_success\":\"true\"}]}";
				//Date b = new Date();
				//System.out.println("gis响应时间："+(b.getTime()-a.getTime()));
				//System.out.println("responseStr:===>"+responseStr);
//				LOGGER.info("responseStr:===>"+responseStr);
				GisResponseDto gisResponseDto = om.readValue(responseStr, GisResponseDto.class);
//				LOGGER.info("自动补码是否有匹配类型：" + gisResponseDto.getResponses().get(0).getMatchType());
				if(gisResponseDto!=null && gisResponseDto.getExceptionCode()!=null){
					if(gisResponseDto.getExceptionCode().equals("S000099")){
						throw new TfrBusinessException("自动补码请求gis时Esb报错exceptionCode:S000099","自动补码请求gis时Esb报错exceptionCode:S000099");
					}
				}
				Date requestGisEndTime = new Date();
				backMap.put("requestGisEndTime", requestGisEndTime);
				//System.out.println("============requestGisStartTime:"+DateUtils.convert(requestGisStartTime,DateUtils.TIMESTAMP_FORMAT)+"requestGisEndTime:"+DateUtils.convert(requestGisEndTime,DateUtils.TIMESTAMP_FORMAT)+"diff:"+(requestGisEndTime.getTime()-requestGisStartTime.getTime()));
				if(gisResponseDto!=null){
					List<GisWaybillResponseDto> resList = gisResponseDto.getResponses();
					backMap.put("resList", resList);
				}
			} catch (Exception e) {
				throw e;
			}
		}
		return backMap;
	}


	/**
	* @description 自动补码
	* @param entity
	* @param requestGisStartTime
	* @param requestGisEndTime
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午9:30:04
	*/
	@Override
	public void autoComplement_TheadComm(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity){
		LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景autoComplement_TheadComm_");
			AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
			logEntity.setWaybillNo(entity.getWaybillNO());
			if(gisEntity!=null){
				if(gisEntity.getGisStartTime()!=null){
					//开始请求gis时间
					logEntity.setGisStartTime(gisEntity.getGisStartTime());
				}
				if(gisEntity.getGisEndTime()!=null){
					//结束请求gis时间
					logEntity.setGisEndTime(gisEntity.getGisEndTime());
				}
				if(gisEntity.getNewTargetOrgCode()!=null){
					//补码部门
					logEntity.setNewTargetOrgCode(gisEntity.getNewTargetOrgCode());
				}
			}
			//记录jobid
			logEntity.setJobid(entity.getJobId());
			logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
			logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
			logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
			logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
			logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
			Date autoAddStartTime = new Date();
			//记录补码开始时间
			logEntity.setAutoStartTime(autoAddStartTime);
			//默认正常返回
			logEntity.setResult(AutoAddCodeConstants.ADD_CODE_RESULT_GIS_SUCCESS);
			//gis有返回值
			if(gisEntity!=null && StringUtils.isNotBlank(gisEntity.getNewTargetOrgCode())){
					String result = null;
					//补码
					result = this.autoAddCode(entity.getWaybillNO(),entity.getWaybillID(), gisEntity.getNewTargetOrgCode(), gisEntity.getMatchType());
					LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景autoComplement_TheadComm_autoAddCode补码操作");
					Date autoAddEndTime = new Date();
					//记录部门结束时间
					logEntity.setAutoEndTime(autoAddEndTime);
					//System.out.println(tmpWaybillNo+"单个运单执行时间"+DateUtils.convert(autoAddStartTime,DateUtils.TIMESTAMP_FORMAT)+"|"+DateUtils.convert(autoAddEndTime,DateUtils.TIMESTAMP_FORMAT)+"||:"+(autoAddEndTime.getTime()-autoAddStartTime.getTime()));
					//如果补码时有异常
					if(result!=null && !StringUtils.equals(result, FossConstants.YES)){
						//标记为补码业务异常，并记录异常信息
						gisEntity.setExceptionInfo(result);
						addHand(entity,gisEntity,AutoAddCodeConstants.ADD_CODE_RESULT_BIZ_EXCEPTION);
						LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景autoComplement_TheadComm_addHand转人工");
					}else{
						//记录补码日志，删除待补码记录
						try {
							doActionAfterComplement(logEntity,gisEntity);
						} catch (Exception e) {
							StringWriter sw=new StringWriter();  
					        PrintWriter pw=new PrintWriter(sw);  
					        e.printStackTrace(pw);
					        String msgError = ""+sw.toString();
					        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
					        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
					        }
							LOGGER.error("自动补码成功,但是删除缓存记录和记录日志时报错", msgError);
							if (StringUtils.isNotBlank(entity.getTbid())) {
								// 线程jobid重置
								autoAddCodeDao.resetDataById(entity.getTbid());
							}
							if(gisEntity.getTbid()==null){

								gisEntity = autoAddGisDao.queryByAutoAddId(entity.getTbid());
							}
							autoAddGisDao.resetDataById(gisEntity.getTbid());
						}
					}
			}else{//gis返回为空
				if(gisEntity!=null && gisEntity.getExceptionInfo()!=null){
					//保留原来的错误信息
				}else{
					throw new TfrBusinessException("补码部门为空");
				}
				addHand(entity,gisEntity,AutoAddCodeConstants.ADD_CODE_RESULT_GIS_FAILURE);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景autoComplement_TheadComm_addHand+gis返回为空");
			}
			
			
	}
	
	
	/**
	* @description 人工补码
	* @param entity
	* @param requestGisStartTime
	* @param requestGisEndTime
	* @param errorMsg
	* @param resutType
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午11:16:28
	*/
	@Override
	public void addHand(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity,String resutType) {
		LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景addHand_");
		AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
		logEntity.setWaybillNo(entity.getWaybillNO());
		logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
		logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
		logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
		logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
		logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
		logEntity.setNewTargetOrgCode(entity.getNewTargetOrgCode());
		// 开始请求gis时间
		if(gisEntity!=null && gisEntity.getGisStartTime()!=null){
			logEntity.setGisStartTime(gisEntity.getGisStartTime());
		}
		// 结束请求gis时间
		if(gisEntity!=null && gisEntity.getGisEndTime()!=null){
			logEntity.setGisEndTime(gisEntity.getGisEndTime());
		}
		//218427 sonar优化
		if(gisEntity==null){
			throw new TfrBusinessException("gisEntity为空");
		}
		// 记录jobid
		logEntity.setJobid(entity.getJobId());
		logEntity.setResult(resutType);
		logEntity.setExceptionInfo(gisEntity.getExceptionInfo());
		// 转人工补码流程
		AutoAddCodeByHandEntity hEntity = new AutoAddCodeByHandEntity();
		hEntity.setWaybillNo(entity.getWaybillNO());
		hEntity.setReason(resutType);
		hEntity.setCreateTime(new Date());
		hEntity.setId(UUIDUtils.getUUID());
		try {
			//不做人工处理,记录日志和重置待补码池
			if(resutType.equals(AutoAddCodeConstants.ADD_CODE_BY_JOB_FAILURE)){
				//1,保存补码日志
				autoAddCodeLogService.insert(logEntity);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景addHand_保存补码日志");
				if (entity!=null && StringUtils.isNotBlank(entity.getTbid())) {
					//2,线程jobid重置
					autoAddCodeDao.resetDataById(entity.getTbid());
					if(gisEntity==null || gisEntity.getTbid()==null){

						gisEntity = autoAddGisDao.queryByAutoAddId(entity.getTbid());
					}
					autoAddGisDao.resetDataById(gisEntity.getTbid());
				}
			}else{//转人工
				//转人工补码流程
				autoAddCodeByHandService.insertAddCodeByHand(hEntity);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景addHand_转人工保存insertAddCodeByHand");
				Date autoAddEndTime = new Date();
				// 记录部门结束时间
				logEntity.setAutoEndTime(autoAddEndTime);
				// 记录补码日志，删除待补码记录
				if(gisEntity==null || gisEntity.getTbid()==null){

					gisEntity = autoAddGisDao.queryByAutoAddId(entity.getTbid());
				}
				doActionAfterComplement(logEntity, gisEntity);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景addHand_转人工记录日志doActionAfterComplement");
			}
		} catch (Exception eg3) {
			StringWriter sw=new StringWriter();  
	        PrintWriter pw=new PrintWriter(sw);  
	        eg3.printStackTrace(pw);
	        String msgError = "人工补码异常"+sw.toString();
	        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
	        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
	        }
			LOGGER.error("人工补码异常", msgError);
			if (entity!=null && StringUtils.isNotBlank(entity.getTbid())) {
				// 线程jobid重置
				autoAddCodeDao.resetDataById(entity.getTbid());
				if(gisEntity==null || gisEntity.getTbid()==null){

					gisEntity = autoAddGisDao.queryByAutoAddId(entity.getTbid());
				}
				autoAddGisDao.resetDataById(gisEntity.getTbid());
			}
		}
	}


	/**
	 * 
	 * convertEntity2GisRequestDto:进行实体转换，转换为GIS请求所需的对象(城市静默或开启的数据). <br/>  
	 *  
	 *  Date:2015年6月14日下午4:41:30  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param entityList
	 * @return  
	 * @since JDK 1.6
	 */
	private List<GisRequestDto> convertEntity2GisRequestDto(List<AutoAddCodeEntity> entityList){
		//定义待返回的list
		List<GisRequestDto> dtoList = new ArrayList<GisRequestDto>();
		for(AutoAddCodeEntity entity : entityList){
			String[] names = this.getProvinceCityNameByCode(entity.getReceiveCustomerProvCode(), entity.getReceiveCustomerCityCode(), entity.getReceiveCustomerDistCode(), entity.getReceiveCustomerTownCode());
			//省
			entity.setReceiveCustomerProvName(names[0]);
			//市
			entity.setReceiveCustomerCityName(names[1]);
			//区
			entity.setReceiveCustomerDistName(names[2]);
			//镇
			entity.setReceiveCustomerTownName(names[LoadConstants.SONAR_NUMBER_3]);
			
			GisRequestDto dto = new GisRequestDto();
			dto.setAddress(entity.getReceiveCustomerAddress());
			dto.setProvinceCode(entity.getReceiveCustomerProvCode());
			dto.setProvinceName(entity.getReceiveCustomerProvName());
			dto.setCityCode(entity.getReceiveCustomerCityCode());
			dto.setCityName(entity.getReceiveCustomerCityName());
			dto.setCountyCode(entity.getReceiveCustomerDistCode());
			dto.setCountyName(entity.getReceiveCustomerDistName());
			dto.setTownCode(entity.getReceiveCustomerTownCode());
			dto.setTownName(entity.getReceiveCustomerTownName());
			dto.setWaybillID(entity.getWaybillID());
			dto.setWaybillNum(entity.getWaybillNO());
			
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	
	/**
	* @description 将AutoAddCodeEntity对象转换成AutoAddGisEntity
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 下午2:31:13
	*/
	private AutoAddGisEntity covertDtoToGisEntity(AutoAddCodeEntity dto){
		if(dto!=null){
			AutoAddGisEntity backEntity = new AutoAddGisEntity();
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
			backEntity.setAutoAddCodeId(dto.getTbid());
			backEntity.setAutoAddCodeJobId(dto.getJobId());
			return backEntity;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * doActionAfterComplement:补码完成后（包括GIS无法匹配转人工） <br/>  
	 * 1、保存status表的操作记录
	 * 2、删除add_code表中的未补码记录 
	 *  Date:2015年6月11日下午10:32:08  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param entity
	 * @param waybillNo
	 * @return  
	 * @since JDK 1.6
	 */
	private int doActionAfterComplement(AutoAddCodeLogEntity entity,AutoAddGisEntity gisEntity) throws Exception{
		//1,保存补码日志
		autoAddCodeLogService.insert(entity);
		//2,删除add_code表
		AutoAddCodeEntity codeEntity = new AutoAddCodeEntity();
		codeEntity.setTbid(gisEntity.getAutoAddCodeId());
		remove(codeEntity);
		autoAddGisDao.deleteEntityByPrimaryKey(gisEntity);
		return FossConstants.SUCCESS;
	}
	
	
	/**
	* @description 判断是否外发;如果是外发直接进行补码
	* @param autoAddCodePojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月14日 下午2:45:46
	*/
	@Override
	public int outerAutoAddCode(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity){
		LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景outerAutoAddCode_");
			if(gisEntity!=null && StringUtils.isNotBlank(gisEntity.getNewTargetOrgCode())){
				//自动补码
				String result = this.autoAddCode(entity.getWaybillNO(),entity.getWaybillID(), gisEntity.getNewTargetOrgCode(), null);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景outerAutoAddCode_autoAddCode补码动作");
				//记录自动补码
				AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
				logEntity.setWaybillNo(entity.getWaybillNO());
				logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
				logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
				logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
				logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
				logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
				logEntity.setNewTargetOrgCode(gisEntity.getNewTargetOrgCode());
				if(StringUtils.equals(result, FossConstants.YES)){
					//如果补码成功，则记录外发补码成功
					logEntity.setResult(AutoAddCodeConstants.ADD_CODE_RESULT_OUTER_SUCCESS);
					//删除待补码记录，记录自动补码记录
					try {
						this.doActionAfterComplement(logEntity, gisEntity);
						LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景outerAutoAddCode_doActionAfterComplement");
					} catch (Exception e) {
						StringWriter sw=new StringWriter();  
				        PrintWriter pw=new PrintWriter(sw);  
				        e.printStackTrace(pw);
				        String msgError = "自动补码成功,但是删除缓存记录和记录日志时报错"+sw.toString();
				        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
				        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
				        }
				        LOGGER.error("自动补码成功,但是删除缓存记录和记录日志时报错", msgError);
				        gisEntity.setExceptionInfo(msgError);
				        addHand(entity,gisEntity, AutoAddCodeConstants.ADD_CODE_RESULT_OUTER_FAILURE);
					}
					return FossConstants.SUCCESS;
				}else{
					 gisEntity.setExceptionInfo(result);
					addHand(entity,gisEntity, AutoAddCodeConstants.ADD_CODE_RESULT_OUTER_FAILURE);
					LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景outerAutoAddCode_addHand+1");
					return FossConstants.FAILURE;
				}
			}else{
				addHand(entity,gisEntity, AutoAddCodeConstants.ADD_CODE_RESULT_OUTER_FAILURE);
				LOGGER.info("自动补码foss,运单号：" + entity.getWaybillNO()+"场景outerAutoAddCode_addHand+2");
				return FossConstants.FAILURE;
			}
			
	}
	
	/**
	* @description 判断是否外发单
	* @param entity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 下午5:48:26
	*/
	@Override
	public String outerAutoAddCodeFlag(AutoAddCodeEntity entity) {
		//判断是否空值
		if(entity != null && entity.getTargetOrgCode() != null){
			//虚拟营业部 即 目的站
			List<String> targetOrgCodes = new ArrayList<String>();
			//entity.getTargetOrgCode()存的是中文
			//targetOrgCodes.add(entity.getTargetOrgCode());
			targetOrgCodes.add(entity.getCustomerPickupOrgCode());
			Map<String,String> outerMap = expressSalesAgentMapService.queryExpressSalesAgentMapBySalesCodes(targetOrgCodes);
			//对应递代理网点
				if(outerMap != null && outerMap.get(entity.getCustomerPickupOrgCode())!=null){
					return outerMap.get(entity.getCustomerPickupOrgCode());
				}else{
					return null;
				}
			}else{
				return null;
			}
	}


	/**
	* @description 城市开关
	* @param expressAutoComplementManageService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:04:06
	*/
	public void setExpressAutoComplementManageService(
			IExpressAutoComplementManageService expressAutoComplementManageService) {
		this.expressAutoComplementManageService = expressAutoComplementManageService;
	}


	
	/**
	* @description 虚拟营业部快递代理网点映射的service
	* @param expressSalesAgentMapService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月8日 下午2:03:59
	*/
	public void setExpressSalesAgentMapService(
			IExpressSalesAgentMapService expressSalesAgentMapService) {
		this.expressSalesAgentMapService = expressSalesAgentMapService;
	}
	
	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * @param entity AutoAddCodeEntiy
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Override
	public void save(AutoAddCodeEntity entity) throws IllegalArgumentException, IllegalAccessException {
		autoAddCodeDao.insert(entity);
	}
	
	/**
	 * <pre>
	 * 	   更新操作.
	 * </pre>
	 * @param entity AutoAddCodeEntiy
	 * @throws SQLException
	 */
	@Override
	public void update(AutoAddCodeEntity entity) throws SQLException {
		autoAddCodeDao.updateByPrimaryKey(entity);
	}
	
	/**
	 * <pre>
	 * 	   查询操作.
	 * </pre>
	 * @param entity AutoAddCodeEntiy
	 * @return AutoAddCodeEntiy
	 * @throws SQLException
	 */
	@Override
	public AutoAddCodeEntity find(AutoAddCodeEntity entity) throws SQLException {
		return autoAddCodeDao.expandByPrimaryKey(entity);
	}

	/**
	 * <pre>
	 * 	  移除操作.
	 * </pre>
	 * @param entity AutoAddCodeEntiy
	 * @throws SQLException
	 */
	@Override
	public void remove(AutoAddCodeEntity entity) {
		autoAddCodeDao.deleteEntityByPrimaryKey(entity);
	}

	/**
	 * @param autoAddCodeDao the autoAddCodeDao to set
	 */
	public void setAutoAddCodeDao(IAutoAddCodeDao autoAddCodeDao) {
		this.autoAddCodeDao = autoAddCodeDao;
	}


	/**  
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService#testGisBeNormal()  
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public int testGisBeNormal() throws JsonGenerationException, JsonMappingException, IOException {
		/**
		 * 获取检测GIS所需的参数
		 */
		// 1,省份code
		ServerConfigEntity proEntity = serverConfigService.queryServerConfig(
				ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
				ServerConfigCodeConstants.TEST_GIS_PROVINCE_CODE);
		String proCode = proEntity == null ? this.TEST_GIS_PRO_CODE : proEntity
				.getValue();
		// 2,城市code
		ServerConfigEntity cityEntity = serverConfigService.queryServerConfig(
				ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
				ServerConfigCodeConstants.TEST_GIS_CITY_CODE);
		String cityCode = cityEntity == null ? this.TEST_GIS_CITY_CODE
				: cityEntity.getValue();
		// 3,区县code
		ServerConfigEntity countyEntity = serverConfigService
				.queryServerConfig(
						ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
						ServerConfigCodeConstants.TEST_GIS_COUNTY_CODE);
		String countyCode = countyEntity == null ? this.TEST_GIS_COUNTY_CODE
				: countyEntity.getValue();
		// 4,镇code
		ServerConfigEntity townEntity = serverConfigService.queryServerConfig(
				ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
				ServerConfigCodeConstants.TEST_GIS_TOWN_CODE);
		String townCode = townEntity == null ? this.TEST_GIS_TOWN_CODE
				: townEntity.getValue();
		// 5,地址
		ServerConfigEntity addrEntity = serverConfigService.queryServerConfig(
				ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
				ServerConfigCodeConstants.TEST_GIS_ADDRESS);
		String address = addrEntity == null ? this.TEST_GIS_ADDRESS
				: addrEntity.getValue();
		// 构造gis请求所需的对象
		List<AutoAddCodeEntity> entityList = new ArrayList<AutoAddCodeEntity>();
		AutoAddCodeEntity entity = new AutoAddCodeEntity();
		entity.setWaybillNO(TEST_GIS_WAYBILL_NO);
		entity.setWaybillID(TEST_GIS_WAYBILL_ID);
		entity.setReceiveCustomerProvCode(proCode);
		entity.setReceiveCustomerCityCode(cityCode);
		entity.setReceiveCustomerDistCode(countyCode);
		entity.setReceiveCustomerTownCode(townCode);
		entity.setReceiveCustomerAddress(address);
		entityList.add(entity);

		// 修复30分钟之前jobid不为空的运单进程 begin
		// restNaAJobId();
		// 修复30分钟之前jobid不为空的运单进程 end
		try {
			Map<String, Object> map = this.requestGisByPostList(entityList);
			// gis服务器检验分支
			List<GisWaybillResponseDto> resList = null;
			if (map.get("resList") != null) {
				resList = (List<GisWaybillResponseDto>) map.get("resList");
			}
			if (resList != null && resList.size() > 0) {
				for (GisWaybillResponseDto wResDto : resList) {
					ServerConfigEntity sConfigEntity = serverConfigService
							.queryServerConfig(
									ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
									ServerConfigCodeConstants.GIS_BE_NORMAL);
					if (wResDto.getIs_success()) {
						// 后台配置参数的值为N时需要更新成Y
						if (sConfigEntity != null
								&& FossConstants.NO.equals(sConfigEntity
										.getValue())) {
							// 检测GIS系统是否正常，如果此处正常，则更改后台配置参数
							serverConfigService
									.updateServerConfig(
											ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
											ServerConfigCodeConstants.GIS_BE_NORMAL,
											FossConstants.YES);
						}
					} else {// Gis返回失败
						StringBuffer msg = new StringBuffer("test gis返回失败");
						if (wResDto != null
								&& org.apache.commons.lang.StringUtils
										.isNotBlank(wResDto.getExceptionMSG())) {
							msg.append(wResDto.getExceptionMSG());
						}
						LOGGER.error("test gis返回失败", msg.toString());
						// 后台配置参数的值更改为N
						serverConfigService
								.updateServerConfig(
										ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
										ServerConfigCodeConstants.GIS_BE_NORMAL,
										FossConstants.NO);
						//插入一条异常日志
						serverConfigService
								.insertServerConfig(
										ServerConfigModuleConstants.SCMODULE_AUTO_ADD_CODE,
										ServerConfigCodeConstants.GIS_BE_NORMAL_LOG
												+ UUIDUtils.getUUID(), msg.toString());
					}

				}
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String msgError = "" + sw.toString();
			LOGGER.error("testGisBeNormal请求gis异常", msgError);
			return FossConstants.FAILURE;
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	* @description 重置
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年9月24日 上午09:55:05
	*/
	@Override
	public void restNaAJobId() {
		//修复30分钟之前jobid不为空的运单进程 begin
		List<AutoAddCodeEntity> queryListJobIdForReset = autoAddCodeDao.queryListJobIdForReset();
		if(queryListJobIdForReset!=null && queryListJobIdForReset.size()>0){
			for (AutoAddCodeEntity autoAddCodeEntity : queryListJobIdForReset) {
				if(autoAddCodeEntity!=null && StringUtils.isNotBlank(autoAddCodeEntity.getJobId())){
					autoAddCodeDao.resetDataByJobId(autoAddCodeEntity.getJobId());
				} 
			}
		}
		//修复30分钟之前jobid不为空的运单进程 end
	}
	
	/**
	* @description 根据jobId重置补码
	* @param jobId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月29日 上午8:39:57
	*/
	@Override
	public void restNaAJobId(String jobId) throws Exception {
		autoAddCodeDao.resetDataByJobId(jobId);
	}


	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)  
	 */
	@Override
	public void businessExecutor(Object obj) {
		this.businessComm(obj);
	}
	
	private void businessComm(Object obj){
		AutoAddCodeEntity pojo = (AutoAddCodeEntity) obj;
		if(pojo!=null){
			try {
				String outerCode = this.outerAutoAddCodeFlag(pojo);
				//城市关闭
				String type=AutoAddCodeConstants.CITY_CLOSE_INT+"";
				//TODO
				//外发单子 ====不需要向gis请求
				if(StringUtils.isNotBlank(outerCode)){
					type = AutoAddCodeConstants.OUT_FLAG;
					//对象转换
					AutoAddGisEntity entity = covertDtoToGisEntity(pojo);
					if(entity!=null){
						entity.setType(type);
						entity.setNewTargetOrgCode(outerCode);
					}
					//gis有返回后插入autogis表
					autoAddGisService.insertAutoAddGisEntity(entity);
				}else{
					int checkCityStatus = cityBeOpenAutoAddCode(pojo);
					type = checkCityStatus+"";
					//城市开关开启  ====需要向gis请求
					if(checkCityStatus == AutoAddCodeConstants.CITY_OPEN_INT || checkCityStatus == AutoAddCodeConstants.CITY_CLOSE_JM_INT){
						gisServiceComm(pojo,type);
					/*}else if(checkCityStatus == AutoAddCodeConstants.CITY_CLOSE_JM_INT){
					//城市静默开启  ====需要向gis请求
						gisServiceComm(pojo,type);*/
					}else{
					//城市关闭 ====不需要向gis请求
						//对象转换
						AutoAddGisEntity entity = covertDtoToGisEntity(pojo);
						if(entity!=null){
							entity.setType(type);
						}
						//gis有返回后插入autogis表
						autoAddGisService.insertAutoAddGisEntity(entity);
					}
				}
			} catch (Exception e) {
				StringWriter sw=new StringWriter();  
		        PrintWriter pw=new PrintWriter(sw);  
		        e.printStackTrace(pw);
		        String msgError = "和gis交互中异常"+sw.toString();
		        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
		        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
		        }
					AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
					//异常插入线程日志中
					logEntity.setWaybillNo(pojo.getWaybillNO());
					logEntity.setJobid(pojo.getJobId());
					logEntity.setResult(AutoAddCodeConstants.ADD_CODE_GIS_ERROR);
					logEntity.setExceptionInfo("运单："+pojo.getWaybillNO()+msgError);
					logEntity.setReceiveCustomerAddress(pojo.getReceiveCustomerAddress());
					logEntity.setReceiveCustomerCityCode(pojo.getReceiveCustomerCityCode());
					logEntity.setReceiveCustomerDistCode(pojo.getReceiveCustomerDistCode());
					logEntity.setReceiveCustomerProvCode(pojo.getReceiveCustomerProvCode());
					logEntity.setReceiveCustomerTownCode(pojo.getReceiveCustomerTownCode());
					autoAddCodeLogService.insert(logEntity);
					//批量更新没有处理完成数据的jobid为N/A
					autoAddCodeDao.resetDataById(pojo.getTbid());
			}
		}
	}
	
	
	/**
	* @description gis请求后的数据处理
	* @param pojo
	* @param type
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 下午3:33:44
	*/
	private void gisServiceComm(AutoAddCodeEntity pojo,String type){
		List<AutoAddCodeEntity> gisList = new ArrayList<AutoAddCodeEntity>();
		gisList.add(pojo);
		//对象转换
		AutoAddGisEntity entity = covertDtoToGisEntity(pojo);
		
		Date gisStart = new Date();
		Date gisEnd = null;
		if(entity==null){
			throw new TfrBusinessException("entity参数为空");
		}
		if(entity!=null){
			entity.setType(type);
			entity.setGisStartTime(gisStart);
		}
		try {
			//通过esb请求gis服务获取运单对应的提货网点
			Map<String,Object> gisMapBack = this.requestGisByPostList(gisList);
			gisMapBack.get("requestGisStartTime");
			gisStart = (Date) gisMapBack.get("requestGisStartTime");
			gisEnd = (Date) gisMapBack.get("requestGisEndTime");
			@SuppressWarnings("unchecked")
			List<GisWaybillResponseDto> resList = (List<GisWaybillResponseDto>) gisMapBack.get("resList");
			entity.setGisStartTime(gisStart);
			entity.setGisEndTime(gisEnd);
			if(resList!=null && resList.size()>0){
				GisWaybillResponseDto wResDto = resList.get(0);
				entity.setNewTargetOrgCode(wResDto.getDeptCode());
				entity.setMatchType(wResDto.getMatchType());
			}
			//gis有返回后插入autogis表
			autoAddGisService.insertAutoAddGisEntity(entity);
		} catch (Exception e) {
			entity.setGisEndTime(new Date());
			StringWriter sw=new StringWriter();  
	        PrintWriter pw=new PrintWriter(sw);  
	        e.printStackTrace(pw);
	        String msgError = "和gis交互中异常"+sw.toString();
	        if(msgError.length()>LoadConstants.SONAR_NUMBER_3500){
	        	msgError= msgError.substring(0,LoadConstants.SONAR_NUMBER_3499);
	        }
	        entity.setExceptionInfo(msgError);
	        autoAddGisService.insertAutoAddGisEntity(entity);
		}
		
	}


	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#outOfUnloadPool(java.lang.Object)  
	 */
	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【gis交互的线程池满异常处理开始。。。。。。】");
		AutoAddCodeEntity entity = (AutoAddCodeEntity) obj;
		if(entity!=null){
			AutoAddCodeLogEntity logEntity = new AutoAddCodeLogEntity();
			//异常插入线程日志中
			logEntity.setWaybillNo(entity.getWaybillNO());
			logEntity.setJobid(entity.getJobId());
			logEntity.setResult(AutoAddCodeConstants.ADD_CODE_GIS_ERROR);
			logEntity.setExceptionInfo("运单："+entity.getWaybillNO()+"被调用时！线程池已满");
			logEntity.setReceiveCustomerAddress(entity.getReceiveCustomerAddress());
			logEntity.setReceiveCustomerCityCode(entity.getReceiveCustomerCityCode());
			logEntity.setReceiveCustomerDistCode(entity.getReceiveCustomerDistCode());
			logEntity.setReceiveCustomerProvCode(entity.getReceiveCustomerProvCode());
			logEntity.setReceiveCustomerTownCode(entity.getReceiveCustomerTownCode());
			autoAddCodeLogService.insert(logEntity);
			//批量更新没有处理完成数据的jobid为N/A
			autoAddCodeDao.resetDataById(entity.getTbid());
		}
		LOGGER.info("【gis交互的线程池满异常处理结束。。。。。。】");
		
	}


	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#getActiveThreads()  
	 */
	@Override
	public int getActiveThreads() {
		return readAutoAddCodeThreadNum();
	}


	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService#updateAndGetJobId()  
	 */
	@Override
	public String updateAndGetJobId() {
		String jobId = UUIDUtils.getUUID();
		//autoAddCodeDao.updateAndGetJobId(jobId, readAutoAddCodeExeNum());
		autoAddCodeDao.updateAndGetJobId(jobId, LoadConstants.SONAR_NUMBER_2000);
		return jobId;
	}


	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService#queryAutoAddCodeEntityByJodId(java.lang.String)  
	 */
	@Override
	public List<AutoAddCodeEntity> queryAutoAddCodeEntityByJodId(String jobId) {
		return autoAddCodeDao.queryAutoAddCodeEntityByJodId(jobId);
	}
	
	
	/**
	* @description 更新jobid并返回对应的jobid
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午9:51:19
	*/
	@Override
	public String GisUpdateGetJobId() {
		String jobId = UUIDUtils.getUUID();
		autoAddGisDao.updateAndGetJobId(jobId, readAutoAddCodeExeNum());
		return jobId;
	}

	/**
	* @description 据生成的jobId获取 List<AutoAddGisEntity> 
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:25:19
	*/
	@Override
	public List<AutoAddGisEntity> queryGisAutoAddCodeEntityByJodId(String jobId) {
		return autoAddGisDao.queryAutoAddCodeEntityByJodId(jobId);
	}
	
	
	/**
	* @description gis交互返回或未处理掉的运单保存
	* @param entity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:32:32
	*/
	@Override
	public int insertAutoAddGisEntity(AutoAddGisEntity entity) {
		return autoAddGisDao.insert(entity);
	}


	@Override
	public void ThreadsPool(Object obj) {
		pushThreadsPool(obj);
	}


	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService#resetData()  
	 */
	@Override
	public void resetData() {
		autoAddCodeDao.resetData();
	}
	
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
	* @description 自动补码总开关
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:55:05
	*/
	@Override
	public String readAutoAddCodePower() {
		String msg ="从数据字典获取自动补码总开关异常！";
		String value= ""+FossConstants.SUCCESS;
		try {
			value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_ADDCODE_POWER,FossConstants.ROOT_ORG_CODE,AutoAddCodeConstants.AUTO_ADD_JOB_OPEN,msg);
		} catch (Exception e) {
			return value;
		}
		return value;
	}
	

	/**
	* @description 自动补码job的线程数量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:23:45
	*/
	@Override
	public int readAutoAddCodeThreadNum() {
		String msg ="从数据字典获取自动补码job的线程数量异常！";
		String value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_ADDCODE_THREAD_NUM,FossConstants.ROOT_ORG_CODE,MaxActiveThreads,msg);
		//默认值
		int valueInt=MaxActiveThreads;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return valueInt;
		}
		return valueInt;
	}

	/**
	* @description 自动补码job的线程执行间隔时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:23:58
	*/
	@Override
	public int readAutoAddCodeThreadExeTime() {
		String msg ="从数据字典获取自动补码job的线程执行间隔时间异常！";
		String value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_ADDCODE_THREAD_EXE_TIME,FossConstants.ROOT_ORG_CODE,MaxActiveTime,msg);
		//默认值
		int valueInt=MaxActiveTime;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return valueInt;
		}
		return valueInt;
	}

	/**
	* @description 自动补码job的一次处理总量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	@Override
	public int readAutoAddCodeExeNum() {
		String msg ="从数据字典获取自动补码job的一次处理总量异常！";
		String value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_ADDCODE_EXE_NUM,FossConstants.ROOT_ORG_CODE,LIMIT,msg);
		//默认值
		int valueInt=LIMIT;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return valueInt;
		}
		return valueInt;
	}
	
	
	
	/**
	* @description 自动补码gis的线程数量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	@Override
	public int readAutoGisThreadNum() {
		String msg ="从数据字典获取自动补码gis的线程数量异常！";
		String value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_GIS_THREAD_NUM,FossConstants.ROOT_ORG_CODE,GISMaxActiveThreads,msg);
		//默认值
		int valueInt=GISMaxActiveThreads;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return valueInt;
		}
		return valueInt;
	}

	/**
	* @description 自动补码gis的一次处理总量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	@Override
	public int readAutoGisExeNum() {
		String msg ="从数据字典获取自动补码gis的一次处理总量异常！";
		String value = readPramComm(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM_AUTO_GIS_EXE_NUM,FossConstants.ROOT_ORG_CODE,GIS_LIMIT,msg);
		//默认值
		int valueInt=GIS_LIMIT;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return valueInt;
		}
		return valueInt;
	}


	/**
	* @description 读取数据字典
	* @param parmMouudle
	* @param parmCode
	* @param orgCode
	* @param defaultValue
	* @param logMessage
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:30:38
	*/
	private String readPramComm(String parmMouudle,String parmCode,String orgCode,int defaultValue,String logMessage){
		String value = null;
		ConfigurationParamsEntity paramEntity;
		try {
			paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(parmMouudle,parmCode,orgCode);
			if(paramEntity!= null){
				value = paramEntity.getConfValue();
			}
		} catch (Exception e) {
			LOGGER.info(logMessage+e.getMessage());
			//数据字典读取失败时 ,读取默认值
			return defaultValue+"";
		}
		return value;
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


	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}


	
	/**
	* @description 综合数据字典Service
	* @param configurationParamsService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:49:26
	*/
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	
	
	
}