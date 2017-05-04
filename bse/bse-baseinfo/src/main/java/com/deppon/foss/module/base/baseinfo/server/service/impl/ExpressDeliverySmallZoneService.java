package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressSmallZoneToOMSService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressDeliveryDistanceDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryZoneException;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressDeliverySmallZoneService implements
		IExpressDeliverySmallZoneService {


	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDeliverySmallZoneService.class);
    /**
     * 快递收派小区小区Dao接口.
     */
    private IExpressDeliverySmallZoneDao expressDeliverySmallZoneDao;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 快递收派大区service接口.
     */
    private IExpressDeliveryBigZoneService expressDeliveryBigZoneService;
    /**
     * 快递员排班Service接口
     */
    private ICourierScheduleService courierScheduleService;
    
    /**
     * 人员Service接口
     */
    private IEmployeeService employeeService;
  
    
    private String url;
    
   public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 快递车辆
	 * 
	 * @param courierScheduleService
	 */
	private IExpressVehiclesService expressVehiclesService;

	/**
	 * 同步快递收派小区
	 */
	private ISendExpressSmallZoneToOMSService sendExpressSmallZoneToOMSService;

	public void setCourierScheduleService(
			ICourierScheduleService courierScheduleService) {
		this.courierScheduleService = courierScheduleService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setExpressDeliverySmallZoneDao(
			IExpressDeliverySmallZoneDao expressDeliverySmallZoneDao) {
		this.expressDeliverySmallZoneDao = expressDeliverySmallZoneDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
/*public void setSaleDepartmentService(
		ISaleDepartmentService saleDepartmentService) {
	this.saleDepartmentService = saleDepartmentService;
}*/

	public void setExpressDeliveryBigZoneService(
			IExpressDeliveryBigZoneService expressDeliveryBigZoneService) {
		this.expressDeliveryBigZoneService = expressDeliveryBigZoneService;
	}

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	
	public void setSendExpressSmallZoneToOMSService(
			ISendExpressSmallZoneToOMSService sendExpressSmallZoneToOMSService) {
		this.sendExpressSmallZoneToOMSService = sendExpressSmallZoneToOMSService;
	}

	private boolean change(String jsonResponse) {
		try {
			// Double num=Double.parseDouble(jsonResponse);//把字符串强制转换为数字
			return true;// 如果是数字，返回True
		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}

	/**
	 * 接口
	 * 
	 * @param salesCode
	 * @param GisId
	 * @return
	 */
	private String acceptDistance(String polygonId, String pointId) {
		ExpressDeliveryDistanceDto request = new ExpressDeliveryDistanceDto();
		request.setPolygonId(polygonId);
		request.setPointId(pointId);
		String jsonString = JSON.toJSONString(request);
		System.out.println(jsonString);
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
	    method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.TIME_OUT_NUMBER);
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.TIME_OUT_NUMBER);
	    method.getParams().setContentCharset("UTF-8");
	    method.getParams().setHttpElementCharset("UTF-8");
	    String jsonResponse="-3";
	    try {
	    	StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
	    	method.setRequestEntity(requestEntity);
	    	LOGGER.info("开始同步"+url);
	    	int statuCode = client.executeMethod(method);
	    	LOGGER.info("同步结束"+statuCode);
			if(statuCode==HttpStatus.SC_OK){
				jsonResponse = method.getResponseBodyAsString();
				if(jsonResponse.length()>NumberConstants.NUMBER_19){
					jsonResponse=jsonResponse.substring(0,NumberConstants.NUMBER_18);
				}
				if (!change(jsonResponse)) {
					// throw new
					// ExpressDeliveryZoneException("返回的字符串非数字类型，可能是esb出了问题"+jsonResponse);
					jsonResponse = "链接esb错误,返回值不是数字";
				}
			} else {
				LOGGER.info("链接异常" + statuCode);
				jsonResponse = "链接异常" + statuCode;
				// throw new ExpressDeliveryZoneException("连接服务器异常"+statuCode);
			}
		} catch (Exception e) {
			LOGGER.info("算营业部与小区距离异常" + e.getMessage());
			// throw new ExpressDeliveryZoneException(e.getMessage());

		}
		return jsonResponse;
	}

	/**
	 * 新增快递收派小区小区--------
	 * 
	 * @param entity
	 * @return
	 * @throws ExpressDeliveryZoneException
	 * @param @param entity
	 * @param @return
	 * @param @throws ExpressDeliveryZoneException
	 * @author 130134
	 * @date 2014-4-16 下午2:52:31
	 */
	@Transactional
	@Override
	public int addExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity)
			throws ExpressDeliveryZoneException {
		if (null == entity) {
			throw new ExpressDeliveryZoneException(
					ExpressDeliveryZoneException.SMALLZONE_NULL_ERROR_CODE);
		}
		if (StringUtil.isEmpty(entity.getCourierCode())) {
			throw new ExpressDeliveryZoneException("主责快递员不能为空！");
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setVirtualCode(entity.getId());
		entity.setActive(FossConstants.ACTIVE);
		// 验证小区名称是否唯一
		ExpressDeliverySmallZoneEntity smallZone = querySmallZoneByName(entity
				.getRegionName());
		if (null != smallZone) {
			throw new ExpressDeliveryZoneException("快递派送小区名称不允许重复！",
					"快递派送小区名称不允许重复！");
		}
		// 验证小区编码是否唯一-187862-dujunhui
		ExpressDeliverySmallZoneEntity myEntity = expressDeliverySmallZoneDao
				.querySmallZoneByCode(entity.getRegionCode());
		if (null != myEntity) {
			throw new ExpressDeliveryZoneException("快递派送小区编码不允许重复！");
		}
		ExpressDeliverySmallZoneEntity regionCodeEntity = querySmallZoneByCode(entity
				.getRegionCode());
		if (null != regionCodeEntity) {
			throw new ExpressDeliveryZoneException("快递派送小区编码重复,请重置后再添加！",
					"快递派送小区编码重复,请重置后再添加！");
		}
		conver(entity);
		int result = expressDeliverySmallZoneDao.addExpressDeliverySmallZone(entity);
		//同步快递收发信息到OMS
		this.syncToOMSWebsite(entity);
		
		return result;
	}

	/**
	 * 根据code作废快递收派小区小区信息---------
	 * 
	 * @param codeStr
	 * @param modifyUser
	 * @return
	 * @param @param codeStr
	 * @param @param modifyUser
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:52:40
	 */
	@Transactional
	@Override
	public int deleteExpressDeliverySmallZoneByCode(String codeStr,
			String modifyUser) {
		if (StringUtil.isBlank(codeStr)) {
			throw new ExpressDeliveryZoneException("快递派送小区虚拟编码不允许为空！");
		} else {
			LOGGER.debug("codeStr: " + codeStr);
			String[] codes = StringUtil
					.split(codeStr, SymbolConstants.EN_COMMA);

			List<ExpressDeliverySmallZoneEntity> smallZones = expressDeliverySmallZoneDao
					.queryExpressDeliverySmallZoneByCode(codes);
			List<String> regionsCodeList = new ArrayList<String>();
			for (ExpressDeliverySmallZoneEntity deliverySmallZoneEntity : smallZones) {
				// 小区编码集合
				regionsCodeList.add(deliverySmallZoneEntity.getRegionCode());

				if (!StringUtil.isEmpty(deliverySmallZoneEntity
						.getBigzonecode())) {

					throw new ExpressDeliveryZoneException(
							deliverySmallZoneEntity.getRegionName()
									+ "快递派送小区已被绑定！");
				}
			}

			// 作废操作
			int result = expressDeliverySmallZoneDao
					.deleteExpressDeliverySmallZoneByCode(codes, modifyUser);
			
			//同步信息到OMS
			List<ExpressDeliverySmallZoneEntity> entitys = new ArrayList<ExpressDeliverySmallZoneEntity>();
			for (ExpressDeliverySmallZoneEntity smallZoneEntity : smallZones) {
				boolean isExit = false;
				for (ExpressDeliverySmallZoneEntity entity : entitys) {
					if (smallZoneEntity.getVirtualCode().equals(
							entity.getVirtualCode())) {
						isExit = true;
					}
					if(isExit && smallZoneEntity.getCreateDate().after(entity.getCreateDate())
							&& smallZoneEntity.getActive().equals(FossConstants.ACTIVE)){
						smallZoneEntity.setCreateDate(new Date());
						smallZoneEntity.setActive(FossConstants.INACTIVE);
						smallZoneEntity.setModifyUser(modifyUser);
						smallZoneEntity.setModifyDate(new Date(
								NumberConstants.ENDTIME));
						entitys.remove(entity);
						entitys.add(smallZoneEntity);
					}
				}
				if(!isExit && smallZoneEntity.getActive().equals(FossConstants.ACTIVE)){
					smallZoneEntity.setCreateDate(new Date());
					smallZoneEntity.setActive(FossConstants.INACTIVE);
					smallZoneEntity.setModifyUser(modifyUser);
					smallZoneEntity.setModifyDate(new Date(
							NumberConstants.ENDTIME));
					entitys.add(smallZoneEntity);
				}
			}
			this.syncToOMSWebsite(entitys);
			
			if (result > 0) {
				// 集合转换成数组
				String[] regionCodes = regionsCodeList
						.toArray(new String[regionsCodeList.size()]);
				// 作废这些小区相关的排班信息
				courierScheduleService
						.deleteCourierScheduleByExpressSmallZoneCode(regionCodes);
			}
			return result;
		}
	}

	/**
	 * 修改快递收派小区小区信息---------
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:52:49
	 */
	@Transactional
	@Override
	public int updateExpressDeliverySmallZone(
			ExpressDeliverySmallZoneEntity entity) {
		// 参数验证
		if (null == entity) {
			// 返回失败信息
			return FossConstants.FAILURE;
			// 虚拟编码验证
		} else if (StringUtil.isBlank(entity.getVirtualCode())) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("快递收派小区虚拟编码不允许为空！");
		}
		//313353 方法性能优化
		if (StringUtil.isEmpty(entity.getCourierCode())) {
			throw new ExpressDeliveryZoneException("主责快递员不能为空！");
		}
		if (!StringUtils.isEmpty(entity.getBigzonecode())) {
			throw new ExpressDeliveryZoneException("该小区已被"
					+ entity.getBigzoneName() + "大区绑定，请到快递收派大区界面解除绑定在修改！");
		}
		ExpressDeliverySmallZoneEntity virtualCodeEntity = expressDeliverySmallZoneDao
				.queryExpressDeliverySmallZoneByVirtualCode(entity
						.getVirtualCode());
		//313353 sonar
		this.sonarSplitCheckException(entity, virtualCodeEntity);
		
		// 第一种情况
		if (StringUtil.isBlank(entity.getSalesToSmallZone())
				&& !"DELETE".equals(entity.getMapState())) {
			conver(entity);
		}
		// 第2种情况
		if ("UPDATE".equals(entity.getMapState())
				|| "ADD".equals(entity.getMapState())) {
			conver(entity);
		}
		// 第3种情况
		if (!StringUtils.equals(entity.getCourierCode(),
				virtualCodeEntity.getCourierCode())) {
			conver(entity);
		}
		// 定义一个虚拟编码code
		String[] codes = { entity.getVirtualCode() };
		// 作废历史记录
		int flag = expressDeliverySmallZoneDao
				.deleteExpressDeliverySmallZoneByCode(codes,
						entity.getModifyUser());
		
		// 处理结果验证
		if (flag > 0) {
			entity.setActive(FossConstants.ACTIVE);
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateDate(new Date());
			entity.setCreateUser(entity.getModifyUser());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			// 2016/06/14 修改删除地图后无法保存的问题
			entity.setVirtualCode(virtualCodeEntity.getVirtualCode());
			// 验证小区名称是否唯一
			// ExpressDeliverySmallZoneEntity smallZone =
			// querySmallZoneByName(entity.getRegionName());

			int resultNum = expressDeliverySmallZoneDao
					.addExpressDeliverySmallZone(entity);
			
			//同步信息到PMS
			List<ExpressDeliverySmallZoneEntity> sendMessage = new ArrayList<ExpressDeliverySmallZoneEntity>();
			sendMessage.add(entity);
			this.syncToOMSWebsite(sendMessage);
			
			// 修改成功之后
			if (resultNum > 0) {
				// 若改过名称，或者改过编码
				if ((!StringUtils.equals(virtualCodeEntity.getRegionCode(),
						entity.getRegionCode()))
						|| (!StringUtils.equals(
								virtualCodeEntity.getRegionName(),
								entity.getRegionName()))) {
					// 修改排班中的名称或者编码
					courierScheduleService.updateCourierScheduleByCondition(
							entity.getRegionCode(), entity.getRegionName(),
							virtualCodeEntity.getRegionCode(),
							virtualCodeEntity.getRegionName());
				}
			}
			return resultNum;
		}

		return FossConstants.FAILURE;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitCheckException(ExpressDeliverySmallZoneEntity entity,
			ExpressDeliverySmallZoneEntity virtualCodeEntity) {
		if (!StringUtils.equals(entity.getRegionName(),
				virtualCodeEntity.getRegionName())) {
			ExpressDeliverySmallZoneEntity regionNameEntity = expressDeliverySmallZoneDao
					.querySmallZoneByName(entity.getRegionName());
			if (null != regionNameEntity) {
				throw new ExpressDeliveryZoneException("快递收派小区名称不允许重复！");
			}
		}
		// 验证小区编码是否唯一-187862-dujunhui
		if (!StringUtils.equals(entity.getRegionCode(),
				virtualCodeEntity.getRegionCode())) {
			ExpressDeliverySmallZoneEntity regionCodeEntity = expressDeliverySmallZoneDao
					.querySmallZoneByCode(entity.getRegionCode());
			if (null != regionCodeEntity) {
				throw new ExpressDeliveryZoneException("快递收派小区编码不允许重复！");
			}
		}
	}

	/**
	 * 根据传入对象查询符合条件所有快递收派小区信息------
	 * 
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:11
	 */
	@Transactional
	@Override
	public List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZones(
			ExpressDeliverySmallZoneEntity entity, int limit, int start)
			throws ExpressDeliveryZoneException {
		// 设置状态
		entity.setActive(FossConstants.ACTIVE);
		// 批量填充行政区域名称、管理部门名称
		return convertInfoList(expressDeliverySmallZoneDao
				.queryExpressDeliverySmallZones(entity, limit, start));
	}

	/**
	 * 根据传入对象查询符合条件所有快递收派小区小区信息 （供导出用） --------
	 * 
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @throws ExpressDeliveryZoneException
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @param @throws ExpressDeliveryZoneException
	 * @author 130134
	 * @date 2014-4-16 下午2:54:52
	 */
	@Transactional
	@Override
	public List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZonesExport(
			ExpressDeliverySmallZoneEntity entity, int limit, int start)
			throws ExpressDeliveryZoneException {
		// 设置状态
		entity.setActive(FossConstants.ACTIVE);
		// 批量填充行政区域名称、管理部门名称
		return convertInfoListExport(expressDeliverySmallZoneDao
				.queryExpressDeliverySmallZones(entity, limit, start));
	}

	/**
	 * 根据条件查询快递收派小区信息
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:18
	 */
	@Override
	public List<ExpressDeliverySmallZoneEntity> querySmallZonesByNameOrCode(
			ExpressDeliverySmallZoneEntity entity)
			throws ExpressDeliveryZoneException {
		// 设置状态
		entity.setActive(FossConstants.ACTIVE);
		// 批量填充行政区域名称、管理部门名称
		return convertInfoList(expressDeliverySmallZoneDao
				.querySmallZonesByNameOrCode(entity));
	}

	/**
	 * 统计总记录数
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:48
	 */
	@Transactional
	@Override
	public Long queryRecordCount(ExpressDeliverySmallZoneEntity entity) {
		// 设置状态
		entity.setActive(FossConstants.ACTIVE);
		// 统计总记录数
		return expressDeliverySmallZoneDao.queryRecordCount(entity);
	}

	/**
	 * 根据传入的管理部门Code、快递收派小区类型和大区虚拟编码
	 * 
	 * @param deptCode
	 * @param type
	 * @param bigZoneVirtualCode
	 * @return
	 * @param @param deptCode
	 * @param @param type
	 * @param @param bigZoneVirtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:55
	 */
	@Transactional
	@Override
	public List<ExpressDeliverySmallZoneEntity> querySmallZonesByDeptCode(
			String deptCode, String type, String bigZoneVirtualCode)
			throws ExpressDeliveryZoneException {
		// 参数验证
		if (StringUtil.isBlank(deptCode)) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("管理部门编码不允许为空!");
			// 参数验证
		} else {
			// 日志记录
			LOGGER.debug("deptCode: " + deptCode);
			// 批量填充行政区域名称、管理部门名称
			return convertInfoList(expressDeliverySmallZoneDao
					.querySmallZonesByDeptCode(deptCode, type,
							bigZoneVirtualCode));
		}
	}

	/**
	 * 根据区域编码查询快递收派小区小区详细信息
	 * 
	 * @param regionCode
	 * @return
	 * @param @param regionCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:25
	 */
	@Override
	public ExpressDeliverySmallZoneEntity querySmallZoneByCode(String regionCode)
			throws ExpressDeliveryZoneException {
		// 参数验证
		if (StringUtil.isBlank(regionCode)) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("快递收派小区区域编码不允许为空！");
		}
		// 日志记录
		LOGGER.debug("regionCode: " + regionCode);
		// 填充行政区域名称、管理部门名称、所属大区名称
		return convertInfo(expressDeliverySmallZoneDao
				.querySmallZoneByCode(regionCode));
	}

	/**
	 * 更具省市区编码完善数据
	 * 
	 * @param regionCode
	 * @return
	 * @param @param regionCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:25
	 */
	private ExpressDeliverySmallZoneEntity convertInfo(
			ExpressDeliverySmallZoneEntity entity) {
		// 参数验证
		if (null == entity) {
			// 返回空值
			return null;
		} else {
			// 通过部门编码获得该部门的实体
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(entity.getManagement());
			// 参数验证
			if (StringUtil.isNotBlank(entity.getBigzonecode())) {
				// 根据大区虚拟编码查询快递收派小区大区详细信息
				ExpressDeliveryBigZoneEntity bigZone = expressDeliveryBigZoneService
						.queryBigzoneByVirtualCode(entity.getBigzonecode());
				// 结果验证
				if (null != bigZone) {
					// 设置所属大区名称
					entity.setBigzoneName(bigZone.getRegionName());
				} else {
					// 设置空值
					entity.setBigzoneName(null);
				}
			}
			if (null != org) {
				// 设置管理部门名称
				entity.setManagementName(org.getName());
			} else {
				// 设置空值
				entity.setManagementName(null);
			}
			// 设置省份名称
			entity.setProvName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(entity.getProvCode()));
			// 设置城市名称
			entity.setCityName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(entity.getCityCode()));
			// 设置区县名称
			entity.setCountyName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(entity
							.getCountyCode()));
			// 设置操作人名称
			if (StringUtils.isNotBlank(entity.getOperatorCode())) {
				entity.setOperatorName(employeeService
						.queryEmpNameByEmpCode(entity.getOperatorCode()));
			}
			return entity;
		}
	}

	/**
	 * 更具部门的编码完善信息
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:25
	 */
	private ExpressDeliverySmallZoneEntity convertInfoExport(
			ExpressDeliverySmallZoneEntity entity) {
		// 参数验证
		if (null == entity) {
			// 返回空值
			return null;
		} else {
			// 通过部门编码获得该部门的实体
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(entity
							.getManagement());
			// 参数验证
			if (StringUtil.isNotBlank(entity.getBigzonecode())) {
				// 根据大区虚拟编码查询快递收派小区大区详细信息
				ExpressDeliveryBigZoneEntity bigZone = expressDeliveryBigZoneService
						.queryBigzoneByVirtualCode(entity.getBigzonecode());
				// 结果验证
				if (null != bigZone) {
					// 设置所属大区名称
					entity.setBigzoneName(bigZone.getRegionName());
				} else {
					// 设置空值
					entity.setBigzoneName(null);
				}
			}
			if (null != org) {
				// 设置管理部门名称
				entity.setManagementName(org.getName());
			} else {
				// 设置空值
				entity.setManagementName(null);
			}
			return entity;
		}
	}

	/**
	 * 
	 * 
	 * @see
	 */
	private List<ExpressDeliverySmallZoneEntity> convertInfoList(
			List<ExpressDeliverySmallZoneEntity> list) {
		// 定义集合
		List<ExpressDeliverySmallZoneEntity> entities = new ArrayList<ExpressDeliverySmallZoneEntity>();
		// 集合验证
		if (CollectionUtils.isNotEmpty(list)) {
			// 迭代循环
			for (ExpressDeliverySmallZoneEntity entity : list) {
				// 填充行政区域名称、管理部门名称、所属大区名称
				entity = convertInfo(entity);
				// 存放到集合
				entities.add(entity);
			}
			// 返回信息
			return entities;
		} else {
			return null;
		}
	}

	/**
	 * 批量填充行政区域名称、管理部门名称（供导出用）
	 * 
	 * @author 130134
	 * @param list
	 * @return
	 */
	private List<ExpressDeliverySmallZoneEntity> convertInfoListExport(
			List<ExpressDeliverySmallZoneEntity> list) {
		// 定义集合
		List<ExpressDeliverySmallZoneEntity> entities = new ArrayList<ExpressDeliverySmallZoneEntity>();
		// 集合验证
		if (CollectionUtils.isNotEmpty(list)) {
			// 迭代循环
			for (ExpressDeliverySmallZoneEntity entity : list) {
				// 填充行政区域名称、管理部门名称、所属大区名称
				entity = convertInfoExport(entity);
				// 存放到集合
				entities.add(entity);
			}
			// 返回信息
			return entities;
		} else {
			return null;
		}
	}

	/**
	 * 根据传入条件 导出查询结果------
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public ExportResource exportSmallZoneList(
			ExpressDeliverySmallZoneEntity entity)
			throws ExpressDeliveryZoneException {
		// 参数验证
		if (null == entity) {
			// 返回空值
			return null;
		}
		// 设置有效
		entity.setActive(FossConstants.ACTIVE);
		// 根据传入对象查询符合条件所有快递收派小区小区信息.
		List<ExpressDeliverySmallZoneEntity> list = queryExpressDeliverySmallZonesExport(
				entity, Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
		// 集合验证
		if (null == list) {
			// 定义一个集合
			list = new ArrayList<ExpressDeliverySmallZoneEntity>();
		}
		// 定义集合
		List<List<String>> resultList = new ArrayList<List<String>>();
		// 迭代循环
		for (ExpressDeliverySmallZoneEntity smallZone : list) {
			// 实体信息封装到集合中
			List<String> result = exportInfoList(smallZone);
			// 存放到集合
			resultList.add(result);
		}
		// 导出对象创建
		ExportResource sheet = new ExportResource();
		// 设置Excel表头
		sheet.setHeads(ComnConst.EXPRESS_SMALLZONE_TITLE);
		// 设置导出数据
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 根据传入条件 填充导出数据----
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	private List<String> exportInfoList(ExpressDeliverySmallZoneEntity entity) {
		// 定义一个机会
		List<String> result = new ArrayList<String>();
		// 添加小区编码.
		result.add(entity.getRegionCode());
		// 小区名称.
		result.add(entity.getRegionName());
		// 管理部门名称.
		result.add(entity.getManagementName());
		// 所属大区名称(扩展).
		result.add(entity.getBigzoneName());
		// String area = entity.getGisArea();
		// if(area!=null&&!area.equals("")){
		// String areas[] = area.split("\\.");
		// String decimal =
		// areas[1].length()<=3?areas[1]:areas[1].substring(0,3);
		// String gisarea = areas[0]+"."+decimal;
		// // 小区面积（单位平方千米）
		// result.add(gisarea);
		// }else{
		result.add(entity.getGisArea());
		result.add(DictUtil.rendererSubmitToDisplay(entity.getRegionType(),
				"EXPRESS_SMALL_TYPE"));
		result.add(entity.getCourierName());
		// }
		// 返回集合
		return result;
	}

	/**
	 * 根据小区名称验证改名称是否重复-------
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public ExpressDeliverySmallZoneEntity querySmallZoneByName(String regionName) {
		// 参数验证
		if (StringUtil.isBlank(regionName)) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("快递收派小区小区名称不能为空！",
					"快递收派小区小区名称不能为空！");
		} else {
			// 去掉两端空格
			regionName = regionName.trim();
			// 验证小区名称是否唯一
			return expressDeliverySmallZoneDao.querySmallZoneByName(regionName);
		}
	}

	/**
	 * 根据虚拟编码验证-----
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public int updateSmallZoneByVirtualCode(
			ExpressDeliverySmallZoneEntity entity) {
		// 参数严重
		if (null == entity) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("传入的参数不允许为空！");
		} else {
			// 参数严重
			if (StringUtil.isBlank(entity.getVirtualCode())) {
				// 异常信息处理
				throw new ExpressDeliveryZoneException("快递派送小区虚拟编码不允许为空！");
			} else {
				// 根据小区虚拟编码修改小区的区域编码、所属大区
				return expressDeliverySmallZoneDao
						.updateSmallZoneByVirtualCode(entity);
			}
		}
	}

	/**
	 * 根据所属大区编码修改小区编码、所属大区编码为空-----
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
    @Override
    public int updateSmallZoneByBigCode(ExpressDeliverySmallZoneEntity entity) {
		if(null == entity||StringUtils.isBlank(entity.getBigzonecode())){
		    return FossConstants.FAILURE;
		}
		return expressDeliverySmallZoneDao.updateSmallZoneByBigCode(entity);
	}

	/**
	 * 根据虚拟编码查询 小区信息
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public ExpressDeliverySmallZoneEntity querySmallZoneByVirtualCode(
			String virtualCode) {
		if (StringUtil.isBlank(virtualCode)) {
			// 异常信息处理
			throw new ExpressDeliveryZoneException("虚拟编码不能为空！");
		} else {
			return expressDeliverySmallZoneDao
					.querySmallZoneByVirtualCode(virtualCode);
		}
	}

	/**
	 * 根据管理部门编码生成小区编码----------
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public String queryRegionCodeByManagement(
			ExpressDeliverySmallZoneEntity entity) {
		String regionCode = null;
		String tuncatStr = "X" + entity.getManagement();
		entity.setActive("Y");
		String maxCode = expressDeliverySmallZoneDao
				.queryRegionCodeByManagement(entity);
		if (!StringUtil.isEmpty(maxCode)) {
			String truncatRegionCode = maxCode.substring(tuncatStr.length(),
					maxCode.length());
			int number = Integer.parseInt(truncatRegionCode) + 1;
			String strCode = String.valueOf(number);
			if (strCode.length() == NumberConstants.NUMERAL_ONE) {
				strCode = "00" + strCode;
			} else if (strCode.length() == NumberConstants.NUMERAL_TWO) {

				strCode = "0" + strCode;
			}
			regionCode = tuncatStr + strCode;
		} else {
			regionCode = tuncatStr + "001";
		}
		return regionCode;
	}

	/**
	 * 
	 * 根据GISID查询小区信息
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public ExpressDeliverySmallZoneEntity querySmallZoneByGisId(
			ExpressDeliverySmallZoneEntity entity) {

		return expressDeliverySmallZoneDao.querySmallZoneByGisId(entity);
	}

	/**
	 * 根据用户编码和部门编码查询快递收派点部-------
	 * 
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	@Override
	public Long queryDataPermissions(String empCode, String deptCode) {

		return expressDeliverySmallZoneDao.queryDataPermissions(empCode,
				deptCode);
	}

	/**
	 * 封装的方法用于获取小区与营业部的距离
	 * 
	 * @param entity
	 */
	public void conver(ExpressDeliverySmallZoneEntity entity) {
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
		expressVehiclesEntity.setActive("Y");
		expressVehiclesEntity.setEmpCode(entity.getCourierCode());
		// 去快递车辆表中获取快递员与营业部的关系
		List<ExpressVehiclesEntity> list = expressVehiclesService
				.queryExpressVehiclesByEntity(expressVehiclesEntity);
		if (list.size() <= 0) {
			entity.setSalesToSmallZone("主责快递员在快递车辆页面不存在");
			return;
		}
		String salesCode = list.get(0).getOrgCode();

		// 组织的查询出营业部
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeNoCache(salesCode);
		if (orgAdministrativeInfoEntity == null) {
			entity.setSalesToSmallZone("主责快递员对应的营业部不存在");
			return;
		}
		String pointId = orgAdministrativeInfoEntity.getDepCoordinate();
		if (StringUtil.isBlank(pointId)) {
			entity.setSalesToSmallZone("主责快递员营业部没有地图点");
			return;
		}
		String polygonId = entity.getGisid();
		if (StringUtil.isBlank(polygonId)) {
			entity.setSalesToSmallZone("小区的GISID不存在");
			return;
		}
		// 返回值类型 进行判断！
		String salesToSmallZone = acceptDistance(polygonId, pointId);
		entity.setSalesToSmallZone(salesToSmallZone);
	}

	/**
	 * 同步信息到OMS
	 * 
	 * @author 313353
	 * @date 2016-03-22
	 * @param 快递收派小区信息
	 */
	private void syncToOMSWebsite(ExpressDeliverySmallZoneEntity entity) {
		if (entity == null) {
			return;
		}
		List<ExpressDeliverySmallZoneEntity> entitys = new ArrayList<ExpressDeliverySmallZoneEntity>();
		entitys.add(entity);
		syncToOMSWebsite(entitys);
	}

	/**
	 * 同步信息到OMS
	 * 
	 * @author 313353
	 * @date 2016-03-22
	 * @param 快递收派小区信息集合
	 */
	@Override
	public void syncToOMSWebsite(List<ExpressDeliverySmallZoneEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
			sendExpressSmallZoneToOMSService.syncExpressSmallZoneToOMS(entitys);
		
	}

}
