package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.EarlyWarningException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;
/**
 * 
 * 提前预警线路Service接口实现
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:37:57
 * @since
 * @version
 */
public class EarlyWarningService implements IEarlyWarningService{
	/**
	 * 提前预警DAO接口
	 */
	private IEarlyWarningDao earlyWarningDao;
	/**
	 * 组织信息Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 配置参数SERVICE
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 
	 * 设置提前预警线路DAO接口
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:39:17
	 * @param earlyWarningDao
	 * @see
	 */
	public void setEarlyWarningDao(IEarlyWarningDao earlyWarningDao) {
		this.earlyWarningDao = earlyWarningDao;
	}
	/**
	 * 
	 * 设置组织信息Service接口
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:39:51
	 * @param orgAdministrativeInfoService
	 * @see
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 
	 * 配置参数SERVICE
	 * @author 262036 - huangwei
	 * @date 2015-10-12 下午6:39:51
	 * @param orgAdministrativeInfoService
	 * @see
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 
	 * 根据传入对象查询提前预警西信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:12:57
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService#queryEarlyWarningEntitysByCityCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity, int, int)
	 */
	@Override
	public List<EarlyWarningEntity> queryEarlyWarningEntitysByCityCode(
			EarlyWarningEntity entity, int limit, int start) {
		if(null == entity){
		    throw new EarlyWarningException("传入的参数不允许为空！");
		}else {
			entity.setActive(FossConstants.ACTIVE);
			return earlyWarningDao.queryEarlyWarningEntitysByCityCode(entity, limit, start);
		}
	}
	/**
	 * 
	 * 根据传入实体类参数统计提前预警信息总数
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:13:25
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity)
	 */
	@Override
	public Long queryRecordCount(EarlyWarningEntity entity) {
		if(null == entity){
		    throw new EarlyWarningException("传入的参数不允许为空！");
		}else {
			entity.setActive(FossConstants.ACTIVE);
			return earlyWarningDao.queryRecordCount(entity);
		}
	}
	/**
	 * 
	 * 导入提前预警信息,导入之前，先作废以前所有有效的提前预警信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:13:55
	 * @param earlyWarningEntitys 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService#importEarlyWarnings(java.util.List)
	 */
	@Override
	@Transactional
	public void importEarlyWarnings(List<EarlyWarningEntity> earlyWarningEntitys) {
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//作废原有的数据
		EarlyWarningEntity earlyWarningEntity = new EarlyWarningEntity();
		earlyWarningEntity.setActive(FossConstants.INACTIVE);
		earlyWarningEntity.setModifyDate(new Date());
		earlyWarningEntity.setModifyUser(userCode);
		earlyWarningDao.deleteEarlyWarnings(earlyWarningEntity);
		
		for(EarlyWarningEntity se : earlyWarningEntitys){
			se.setId(UUIDUtils.getUUID());
			se.setCreateUser(userCode);
			se.setCreateDate(new Date());
			se.setActive(FossConstants.ACTIVE);
		}
		//插入导入的数据
		earlyWarningDao.insertEarlyWarnings(earlyWarningEntitys);
	}
	/**
	 * 
	 * 此接口供接送货调用，根据传入出发网点编码和到达网点编码查询提前预警线路信息，如有符合的信息则返回兑现率，如查询没有记录返回null
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:15:49
	 * @param startOrgCode
	 * @param endOrgCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService#searchEarlyWarningInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public String searchEarlyWarningInfo(String startOrgCode, String endOrgCode) {
		try{
			if(StringUtils.isBlank(startOrgCode) || StringUtils.isBlank(endOrgCode)){//判断传入的参数是否为空
				return null;
			}
			
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoService.queryOrgAdministrativeInfoBatchByCodeClean(new String[]{startOrgCode, endOrgCode});
			
			String startCityCode = null;
			String endCityCode = null;
			
			if(orgList != null && orgList.size() == 2){
				OrgAdministrativeInfoEntity orgEntity = orgList.get(0);
				if(StringUtils.isNotBlank(orgEntity.getCityCode())){
					if(startOrgCode.equals(orgEntity.getCode())){
						startCityCode = orgEntity.getCityCode();
					}else if(endOrgCode.equals(orgEntity.getCode())){
						endCityCode = orgEntity.getCityCode();
					}
				}
				orgEntity = orgList.get(1);
				if(StringUtils.isNotBlank(orgEntity.getCityCode())){
					if(endOrgCode.equals(orgEntity.getCode())){
						endCityCode = orgEntity.getCityCode();
					}else if(startOrgCode.equals(orgEntity.getCode())){
						startCityCode = orgEntity.getCityCode();
					}
				}
			}
			
			if(StringUtils.isBlank(startCityCode) || StringUtils.isBlank(endCityCode)){//判断传入的参数是否为空
				return null;
			}
			
			EarlyWarningEntity entity = new EarlyWarningEntity();
			entity.setStartCityCode(startCityCode);
			entity.setEndCityCode(endCityCode);
			List<EarlyWarningEntity> list = this.queryEarlyWarningEntitysByCityCode(entity, 1, 0);//根据出发城市编码和到达城市编码查询提前预警信息
			//313353 sonar
			String result = this.sonarSplit(list);
			if(null != result){
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private String sonarSplit(List<EarlyWarningEntity> list) {
		if(list != null && list.size() > 0 && StringUtil.isNotEmpty(list.get(0).getPromiseRate())){//判断提前预警信息是否为空，如果不为空返回兑现率
			
			String high = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.EARLTWARING_INFO_HIGH);//高兑现线路提示语
			String low = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.EARLTWARING_INFO_LOW);//低兑现线路提示语
			String highValue = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.EARLTWARING_INFO_HIGH_VALUE);//高兑现率值
			String lowValue = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.EARLTWARING_INFO_LOW_VALUE);//低兑现率值
			if (StringUtils.isNotBlank(high) && high.indexOf("%") != -1) {
				if(Integer.parseInt(list.get(0).getPromiseRate()) >= Integer.parseInt(highValue)){
					return high.replace("%", list.get(0).getPromiseRate()+"%");
				}
				
			}
			
			if (StringUtils.isNotBlank(low) && low.indexOf("%") != -1) {
				if(Integer.parseInt(list.get(0).getPromiseRate()) <= Integer.parseInt(lowValue)){
					return low.replace("%", list.get(0).getPromiseRate()+"%");
				}
				
			}
	
		}
		return null;
	}

}
