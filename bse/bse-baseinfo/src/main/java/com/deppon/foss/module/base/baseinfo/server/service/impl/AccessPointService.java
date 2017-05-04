package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAccessPointDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AccessPointVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;
import com.kp.persistance.types.Date;
import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 接驳点Service
 * @author 198771
 *
 */
public class AccessPointService implements IAccessPointService{
	
	private Logger logger = Logger.getLogger(AccessPointService.class);
	
	private IAccessPointDao accessPointDao;
	
	// 用于注入行政区域业务服务实现类
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 声明车队service
	 */
	private IMotorcadeService motorcadeService;
	
	/**
     * 接驳点对应营业部映射的service
     */
    private IAcceptPointSalesDeptService acceptPointSalesDeptService;
	
	public void setAccessPointDao(IAccessPointDao accessPointDao) {
		this.accessPointDao = accessPointDao;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}



	public void setAcceptPointSalesDeptService(
			IAcceptPointSalesDeptService acceptPointSalesDeptService) {
		this.acceptPointSalesDeptService = acceptPointSalesDeptService;
	}


	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 增加接驳点
	 */
	@Override
	public void addAccessPoint(AccessPointEntity entity) {
		if(entity==null){
			logger.info("新增接驳点失败....参数为null");
			return;
		}
		//设置状态
		if(entity.getStatu()==null||"".equals(entity.getStatu())){
			entity.setStatu(FossConstants.INACTIVE);
		}
		//设置启用
		if(entity.getActive()==null||"".equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		//设置名称
		entity.setName(entity.getName().trim()+"接驳点");
		if(entity.getCreateDate()==null){
			//设置创建时间
			entity.setCreateDate(new Date());
			//设置修改时间
			entity.setModifyDate(entity.getCreateDate());
		}
		//设置id
		entity.setId(UUID.randomUUID().toString());
		//设置code
		entity.setCode(entity.getId().toUpperCase());
		//去掉前后空白
		entity.setName(entity.getName().trim());
		entity.setStreet(entity.getStreet().trim());
		accessPointDao.addAccessPoint(entity);
	}

	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	public List<AccessPointEntity> queryAccessPoints(AccessPointEntity entity,int start,int limit){
		if(!checkAccessPoint(entity)){
			return null;
		}
		return accessPointDao.queryAccessPoints(entity, start, limit);
	}

	/**
	 * 根据条件查询总条数
	 * @param entity
	 * @return
	 */
	public long getCount(AccessPointEntity entity){
		if(!checkAccessPoint(entity)){
			return 0;
		}
		return accessPointDao.getCount(entity);
	}
	
	private boolean checkAccessPoint(AccessPointEntity entity){
		if(entity==null){
			logger.info("接驳点查询失败..........");
			return false;
		}
		if(entity.getName()!=null&&!"".equals(entity.getName())){
			entity.setName("%"+entity.getName().trim()+"%");
		}
		if(entity.getActive()==null){
			entity.setActive(FossConstants.ACTIVE);
		}
		return true;
	}

	@Override
	public List<AccessPointEntity> queryAccessPointsByName(
			AccessPointEntity entity) {
		if(entity==null){
			logger.info("接驳点查询失败..........");
			return null;
		}
		if(entity.getActive()==null||"".equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		if(entity.getName()!=null&&!"".equals(entity.getName())){
			//去掉前后空白
			entity.setName(entity.getName().trim()+"接驳点");
		}
		return accessPointDao.queryAccessPointsByName(entity);
	}

	@Override
	public void updateAccessPointStatu(List<String> accessPointCodes) {
		CurrentInfo emp =FossUserContext.getCurrentInfo();
		Map<String,Object> maps = new HashMap<String,Object>();
		if(emp!=null){
			maps.put("modifyUser", emp.getEmpCode());
		}else{
			maps.put("modifyUser", null);
		}
		maps.put("accessPointCodes", accessPointCodes);
		accessPointDao.updateAccessPointStatu(maps);
	}
	/**
	 * 查询省市区信息
	 */
	@Override
	public List<AdministrativeRegionsEntity> queryRegions(AdministrativeRegionsEntity entity){
		//如果行政区域级别为空
		if(entity==null||entity.getDegree()==null){
			return null;
		}
		return administrativeRegionsService.queryAdministrativeRegionsByEntity(entity,0,Integer.MAX_VALUE);
	}

	@Override
	public void deleteAccessPoint(AccessPointEntity entity) {
		if(entity!=null&&entity.getCode()!=null){
			accessPointDao.deleteAccessPoint(entity);
		}
	}

	@Override
	public List<AdministrativeRegionsEntity> queryRegionsByCodes(
			List<String> codes) {
		return administrativeRegionsService.queryAdministrativeRegionsBatchByCode(codes);
	}

	@Override
	public AccessPointVo queryDegreeRegionsByCodes(AccessPointVo vo) {
		if(vo==null){
			return null;
		}
		if(vo.getRegionCodes()==null||vo.getRegionCodes().size()!=NumberConstants.NUMBER_3){
			return null;
		}
		AdministrativeRegionsEntity condition = new AdministrativeRegionsEntity();
		condition.setDegree("DISTRICT_PROVINCE");
		vo.setProvinceList(this.queryRegions(condition));
		condition.setDegree("CITY");
		condition.setParentDistrictCode(vo.getRegionCodes().get(0));
		vo.setCityList(this.queryRegions(condition));
		condition.setDegree("DISTRICT_COUNTY");
		condition.setParentDistrictCode(vo.getRegionCodes().get(1));
		vo.setCountyList(this.queryRegions(condition));
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService#updateAccessPoint(com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity)
	 */
	@Override
	public void updateAccessPoint(AccessPointEntity entity) {
		//启用
		entity.setActive(FossConstants.ACTIVE);
		AccessPointEntity oriAccessPointEntity = accessPointDao.queryAccessPointsByCode(entity);
		if(oriAccessPointEntity==null){
			return;
		}
		this.deleteAccessPoint(oriAccessPointEntity);
		//经营大区
		entity.setBigRegionCode(oriAccessPointEntity.getBigRegionCode());
		//中转场
		entity.setTransferCode(oriAccessPointEntity.getTransferCode());
		//创建时间
		entity.setCreateDate(oriAccessPointEntity.getCreateDate());
		//创建人
		entity.setCreateUser(oriAccessPointEntity.getCreateUser());
		//修改人
		entity.setModifyUser(oriAccessPointEntity.getModifyUser());
		//修改时间
		entity.setModifyDate(new Date());
		//省
		if(entity.getProvince()==null||"".equals(entity.getProvince())){
			entity.setProvince(oriAccessPointEntity.getProvince());
		}
		//市
		if(entity.getCity()==null||"".equals(entity.getCity())){
			entity.setCity(oriAccessPointEntity.getCity());
		}
		//区
		if(entity.getCounty()==null||"".equals(entity.getCounty())){
			entity.setCounty(oriAccessPointEntity.getCounty());
		}
		//状态
		entity.setStatu(oriAccessPointEntity.getStatu());
		this.addAccessPoint(entity);
	}

	/**
	 * 根据接驳点编码查询
	 */
	@Override
	public AccessPointEntity queryAccessPointsByCode(AccessPointEntity entity) {
		if(entity==null||entity.getCode()==null){
			return null;
		}
		if(entity.getActive()==null||"".equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		return accessPointDao.queryAccessPointsByCode(entity);
	}
	
	/**
	 * 根据中转场编码查找
	 */
	@Override
	public List<AccessPointEntity> queryAccessPointsByTransferCode(
			AccessPointEntity entity) {
		if(entity==null||entity.getTransferCode()==null){
			return null;
		}
		if(entity.getActive()==null||"".equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		return accessPointDao.queryAccessPointsByTransferCode(entity);
	}

	/**
	 * 根据参数动态查询
	 * @param entity
	 */
	@Override
	public List<AccessPointEntity> queryAccessPointByCommon(
			AccessPointEntity entity) {
		if(entity==null){
			return null;
		}
		if(entity.getName()!=null){
			entity.setName("%"+entity.getName()+"%");
		}
		if(entity.getActive()==null||"".equals(entity.getActive())){
			entity.setActive(FossConstants.ACTIVE);
		}
		return accessPointDao.queryAccessPointByCommon(entity);
	}

	/**
	 *
	 * 根据接驳点编码查询接驳点与营业部关系
	 */
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointSalesByAcceptPointCode(
			List<String> acceptPointCodes) {
		return acceptPointSalesDeptService.queryAcceptPointSalesByAcceptPointCode(acceptPointCodes);
	}

	/**
	 *根据司机工号查询所属部门 对应的外场编码
	 */
	@Override
	public String queryTransCenterByEmpCode(
			String empCode) {
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys =
				accessPointDao.queryOrgAdministrativeInfoEntitysByEmpCode(empCode);
		if(orgAdministrativeInfoEntitys==null||orgAdministrativeInfoEntitys.size()==0){
			logger.info("工号为:"+empCode+"司机查不到所在的部门");
			return null;
		}
		if(orgAdministrativeInfoEntitys.get(0)==null){
			logger.info("工号为:"+empCode+"司机查不到所在的部门");
			return null;
		}
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoEntitys.get(0);
		if(FossConstants.NO.equals(orgAdministrativeInfoEntity.getTransDepartment())
				&&FossConstants.NO.equals(orgAdministrativeInfoEntity.getTransTeam())){
			logger.info("工号为:"+empCode+"司机所属部门不为车队或者车队组");
			return null;
		}
		//如果司机所在的部门有外场属性，直接返回部门编码
		if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getTransferCenter())){
			logger.info("工号为:"+empCode+"司机所属部门 对应的外场编码:"+orgAdministrativeInfoEntity.getCode());
			return orgAdministrativeInfoEntity.getCode();
		}
		//根据部门编码查询车队信息
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(orgAdministrativeInfoEntity.getCode());
		if(motorcadeEntity==null){
			logger.info("工号为:"+empCode+"司机所属部门 无对应的外场");
			return null;
		}
		logger.info("工号为:"+empCode+"司机所属部门 对应的外场编码:"+motorcadeEntity.getTransferCenter());
		//返回车队所服务的外场编码
		return motorcadeEntity.getTransferCenter();
	}
}
