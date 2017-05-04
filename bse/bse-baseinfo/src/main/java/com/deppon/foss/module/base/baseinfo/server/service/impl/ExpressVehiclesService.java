package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDetailDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressVehicleToOMSService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressVehiclesException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressVehiclesService implements IExpressVehiclesService{
	/**
	 * 日志打印对象声明
	 */
//	private static final Logger log = Logger
//			.getLogger(ExpressVehiclesService.class);
	
	/**
	 * 快递车辆dao
	 */
	IExpressVehiclesDao expressVehiclesDao;
	IExpressVehiclesDetailDao expressVehiclesDetailDao;
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	IAdministrativeRegionsService administrativeRegionsService;
	IEmployeeService employeeService;
	ILeasedVehicleTypeService leasedVehicleTypeService;
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 将快递车辆信息同步到OMS的service
	 */
	ISendExpressVehicleToOMSService sendExpressVehicleToOMSService;

	public void setSendExpressVehicleToOMSService(
			ISendExpressVehicleToOMSService sendExpressVehicleToOMSService) {
		this.sendExpressVehicleToOMSService = sendExpressVehicleToOMSService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	@Override
	public int addExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity,
			String createUser, boolean ignoreNull)
			throws ExpressVehiclesException {
		boolean flag = checkAddVehicles(expressVehiclesEntity);
		if(flag){
			throw new BusinessException("该车牌号已经存在于系统中，不能重复添加，请重新输入！");
		}
		
		/**
		 * 检查车牌号是否已经存在
		 */
		//-------------
		checkAddUpdateDataRights(expressVehiclesEntity);
	    //1、产生一条最新启用记录
		Date date  = new Date();
		Long versionNo = date.getTime();
		expressVehiclesEntity.setVersionNo(versionNo);
		expressVehiclesEntity.setCreateUser(createUser);
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		expressVehiclesEntity.setId(UUIDUtils.getUUID());
		expressVehiclesDao.addExpressVehicles(expressVehiclesEntity);
		List<String> areaList = expressVehiclesEntity.getAreaCodeList();
		
		List<ExpressVehiclesDetailEntity> detailEntitys = new ArrayList<ExpressVehiclesDetailEntity>();
		for (String areaCode : areaList){
			ExpressVehiclesDetailEntity expressVehiclesDetailEntity = new ExpressVehiclesDetailEntity();
			expressVehiclesDetailEntity.setId(UUIDUtils.getUUID());
			expressVehiclesDetailEntity.setActive(FossConstants.ACTIVE);
			expressVehiclesDetailEntity.setCreateUser(createUser);
			expressVehiclesDetailEntity.setModifyUser(createUser);
			expressVehiclesDetailEntity.setModifyDate(date);
			expressVehiclesDetailEntity.setCreateDate(date);
			expressVehiclesDetailEntity.setEmpCode(expressVehiclesEntity.getEmpCode());
			expressVehiclesDetailEntity.setNotes(expressVehiclesEntity.getNotes());
			expressVehiclesDetailEntity.setVersionNo(expressVehiclesEntity.getVersionNo());
			expressVehiclesDetailEntity.setAreaCode(areaCode);
			int result = expressVehiclesDetailDao.addExpressVehiclesDetail(expressVehiclesDetailEntity);
			if(result > 0){
				detailEntitys.add(expressVehiclesDetailEntity);
			}
		} 
		
		//同步快递车辆信息到OMS
		this.syncToOMSWebsite(expressVehiclesEntity, detailEntitys);
		
		return FossConstants.SUCCESS;
	}

	/**
	 * 更新时检测
	 * @param expressVehiclesEntity
	 * @param createUser
	 */
	/*@SuppressWarnings("unused")
	private void checkUpdateExpressVehicles(
			ExpressVehiclesEntity expressVehiclesEntity, String createUser) {
		checkExpressVehicles(expressVehiclesEntity,createUser);
		List<ExpressVehiclesEntity> list = expressVehiclesDao.getExpressVehiclesEntitysByEmpcode(expressVehiclesEntity.getEmpCode());
		if(list.size()>1){
			throw new BusinessException("已经存在该快递员的快递车辆！");
		}else if(list.size()==1){
			ExpressVehiclesEntity entity=list.get(0);
			if(!entity.getId().equals(expressVehiclesEntity.getId())){
				throw new BusinessException("已经存在该快递员的快递车辆！");
			}
		}
	}*/

	/**
	 * 修改内容：判断车牌号是否已经存在于系统中
	 * 
	 * @updator  WangPeng
	 * @Date    2013-8-23 上午8:43:55
	 * @param   expressVehiclesEntity
	 *
	 */
	private boolean checkAddVehicles(ExpressVehiclesEntity expressVehiclesEntity) {
		if(null == expressVehiclesEntity){
			throw new BusinessException("新增的对象为空！");
		}
		if(StringUtils.isEmpty(expressVehiclesEntity.getVehicleNo())){
			throw new BusinessException("车牌号不能为空！");
		}
		Long totalCount = queryRecordCount(expressVehiclesEntity);
		return totalCount > 0 ? true : false;    	
   
	}

	/**
	 * 保存前校验
	 * @param expressVehiclesEntity
	 * @param createUser
	 */
	/*private void checkExpressVehicles(
			ExpressVehiclesEntity expressVehiclesEntity, String createUser) {
		if (null == expressVehiclesEntity) {
		    throw new BusinessException("快递车辆为空！");
		}
		if (StringUtils.isBlank(expressVehiclesEntity.getVehicleNo())) {
		    throw new BusinessException("外请车车牌号为空！");
		}else{
			expressVehiclesEntity.setVehicleNo(StringUtils.trim(expressVehiclesEntity.getVehicleNo()));
		}
		if (StringUtils.isBlank(createUser)) {
		    throw new BusinessException("创建人为空！");
		}		
	}*/

	/**
	 * 作废快递车辆以及对应的相关行政区域信息
	 * 
	 * @author  WangPeng
	 * @Date   2013-8-23 下午5:24:24
	 * @param  ids
	 * @param  modifyUser
	 * @return
	 * @throws ExpressVehiclesException
	 *
	 */
	@Override
	public int deleteExpressVehicles(List<String> ids, String modifyUser)
			throws ExpressVehiclesException {
		if (CollectionUtils.isEmpty(ids)) {
		    throw new ExpressVehiclesException("ID 为空",null);
		}

			ExpressVehiclesDetailEntity expressVehiclesDetailEntity = new ExpressVehiclesDetailEntity();
			expressVehiclesDetailEntity.setModifyDate(new Date());
			expressVehiclesDetailEntity.setModifyUser(modifyUser);
			expressVehiclesDetailEntity.setActive(FossConstants.INACTIVE);
			
			expressVehiclesDetailDao.updateExpressVehiclesDetailBySelective(expressVehiclesDetailEntity,ids);
			
			ExpressVehiclesEntity expressVehEntity = new ExpressVehiclesEntity();
			expressVehEntity.setModifyDate(new Date());
			expressVehEntity.setModifyUser(modifyUser);
			expressVehEntity.setActive(FossConstants.INACTIVE);
			expressVehiclesDao.deleteExpressVehicles(expressVehEntity, ids);
			
			//同步快递车辆信息到OMS
			List<ExpressVehiclesEntity> syncEntitys = new ArrayList<ExpressVehiclesEntity>();
			List<ExpressVehiclesDetailEntity> detailEntitys = new ArrayList<ExpressVehiclesDetailEntity>();
			for(String id : ids){
				ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
				entity.setModifyDate(new Date());
				entity.setCreateDate(new Date());
				entity.setModifyUser(modifyUser);
				entity.setActive(FossConstants.INACTIVE);
				entity.setId(id);
				syncEntitys.add(entity);
			}
			syncToOMSWebsite(syncEntitys, detailEntitys);
			
		return FossConstants.SUCCESS;
	}

	/**
	 * 作废快递车辆以及对应的相关行政区域信息
	 * 
	 * @author  qiupeng
	 * @Date   2016-3-30 下午3:24:24
	 * @param  ids
	 * @param  modifyUser
	 * @param  是否需要同步到OMS
	 * @return
	 * @throws ExpressVehiclesException
	 *
	 */
	public int deleteExpressVehicles(List<String> ids, String modifyUser,
			boolean isNeedSync) throws ExpressVehiclesException {
		if (CollectionUtils.isEmpty(ids)) {
			throw new ExpressVehiclesException("ID 为空", null);
		}

		ExpressVehiclesDetailEntity expressVehiclesDetailEntity = new ExpressVehiclesDetailEntity();
		expressVehiclesDetailEntity.setModifyDate(new Date());
		expressVehiclesDetailEntity.setModifyUser(modifyUser);
		expressVehiclesDetailEntity.setActive(FossConstants.INACTIVE);

		expressVehiclesDetailDao.updateExpressVehiclesDetailBySelective(
				expressVehiclesDetailEntity, ids);

		ExpressVehiclesEntity expressVehEntity = new ExpressVehiclesEntity();
		expressVehEntity.setModifyDate(new Date());
		expressVehEntity.setModifyUser(modifyUser);
		expressVehEntity.setActive(FossConstants.INACTIVE);
		expressVehiclesDao.deleteExpressVehicles(expressVehEntity, ids);

		// 同步快递车辆信息到OMS
		if (isNeedSync) {
			List<ExpressVehiclesEntity> syncEntitys = new ArrayList<ExpressVehiclesEntity>();
			List<ExpressVehiclesDetailEntity> detailEntitys = new ArrayList<ExpressVehiclesDetailEntity>();
			for (String id : ids) {
				ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
				entity.setModifyDate(new Date());
				entity.setModifyUser(modifyUser);
				entity.setActive(FossConstants.INACTIVE);
				entity.setId(id);
				syncEntitys.add(entity);
			}
			syncToOMSWebsite(syncEntitys, detailEntitys);
		}
		return FossConstants.SUCCESS;
	}

	@Override
	@Transactional
	public int updateExpressVehicles(
			ExpressVehiclesEntity expressVehiclesEntity, String modifyUser,
			boolean ignoreNull) throws ExpressVehiclesException {
		/**
		 * 检查车牌号是否已经存在
		 * 修改时车牌号不允许修改，所以不用在校验该车牌是否存在
		 */
//		checkUpdateExpressVehicles(expressVehiclesEntity,modifyUser);
//		String empCode = expressVehiclesEntity.getEmpCode();
//		expressVehiclesDetailDao.deleteExpressVehiclesByEmpCode(empCode);

		checkAddUpdateDataRights(expressVehiclesEntity);
		//-------------
		List<String> ids=new ArrayList();
		ids.add(expressVehiclesEntity.getId());
		//现根据ID作废 在新增
	    deleteExpressVehicles(ids,  modifyUser, false);
	    //1、产生一条最新启用记录
		Date date  = new Date();
		Long versionNo = date.getTime();
		expressVehiclesEntity.setVersionNo(versionNo);
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		//expressVehiclesDao.updateExpressVehicles(expressVehiclesEntity);
		
		//2016/06/03 修改的时候发送删除的entity
		ExpressVehiclesEntity deleteEntity = new ExpressVehiclesEntity();
		deleteEntity.setActive(FossConstants.INACTIVE);
		deleteEntity.setId(expressVehiclesEntity.getId());
		deleteEntity.setModifyDate(new Date());
	    
	    expressVehiclesEntity.setCreateUser(modifyUser);
	    expressVehiclesEntity.setId(UUIDUtils.getUUID());
	    
		/**
		 * 修改的新增前验证车牌号是否已存在
		 * @author 313353
		 * @date:2016-05-17 13:40
		 */
		boolean flag = checkAddVehicles(expressVehiclesEntity);
		if(flag){
			throw new BusinessException("其他用户已修改该数据，请刷新后重新修改。");
		}
	    
		expressVehiclesDao.addExpressVehicles(expressVehiclesEntity);
		
		List<String> areaList = expressVehiclesEntity.getAreaCodeList();
		
		List<ExpressVehiclesDetailEntity> detailEntitys = new ArrayList<ExpressVehiclesDetailEntity>();
		for (String areaCode : areaList){
			ExpressVehiclesDetailEntity expressVehiclesDetailEntity = new ExpressVehiclesDetailEntity();
			expressVehiclesDetailEntity.setId(UUIDUtils.getUUID());
			expressVehiclesDetailEntity.setActive(FossConstants.ACTIVE);
			expressVehiclesDetailEntity.setCreateUser(modifyUser);
			expressVehiclesDetailEntity.setModifyUser(modifyUser);
			expressVehiclesDetailEntity.setModifyDate(date);
			expressVehiclesDetailEntity.setCreateDate(date);
			expressVehiclesDetailEntity.setEmpCode(expressVehiclesEntity.getEmpCode());
			expressVehiclesDetailEntity.setNotes(expressVehiclesEntity.getNotes());
			expressVehiclesDetailEntity.setVersionNo(expressVehiclesEntity.getVersionNo());
			expressVehiclesDetailEntity.setAreaCode(areaCode);
			int result = expressVehiclesDetailDao.addExpressVehiclesDetail(expressVehiclesDetailEntity);
			if(result > 0){
				detailEntitys.add(expressVehiclesDetailEntity);
			}
		} 

		//同步快递车辆信息到OMS
		List<ExpressVehiclesEntity> sendList = new ArrayList<ExpressVehiclesEntity>();
		sendList.add(deleteEntity);
		sendList.add(expressVehiclesEntity);
		syncToOMSWebsite(sendList, detailEntitys);
		
		return FossConstants.SUCCESS;
	}

	public void setExpressVehiclesDao(IExpressVehiclesDao expressVehiclesDao) {
		this.expressVehiclesDao = expressVehiclesDao;
	}

	public void setExpressVehiclesDetailDao(
			IExpressVehiclesDetailDao expressVehiclesDetailDao) {
		this.expressVehiclesDetailDao = expressVehiclesDetailDao;
	}
	/***
	 * 
	 * <p>提供给外部的接口（pda，中转···）</p> 
	 * @author 189284 
	 * @date 2015-6-26 上午8:11:25
	 * @param expressVehiclesEntity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService#queryExpressVehiclesList(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity, int, int)
	 */
	@Override
	public List<ExpressVehiclesEntity> queryExpressVehiclesList(
			ExpressVehiclesEntity expressVehiclesEntity, int start, int limit) {
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		/*if(StringUtils.isNotEmpty(expressVehiclesEntity.getProvCode())
				&& StringUtils.isNotEmpty(expressVehiclesEntity.getCityCode())
				&& StringUtils.isNotEmpty(expressVehiclesEntity.getAreaCode())){

			//拼接省市区code
			StringBuffer bufferCode = new StringBuffer();
			bufferCode.append(expressVehiclesEntity.getProvCode()).append("-").append(expressVehiclesEntity.getCityCode()).append("-")
			.append(expressVehiclesEntity.getAreaCode());
			
			expressVehiclesEntity.setAreaCode(bufferCode.toString());
		}*/
		List<ExpressVehiclesEntity> expressVehiclesEntityList = expressVehiclesDao.queryInfos(expressVehiclesEntity, limit, start);
		transFormDistinct(expressVehiclesEntityList);
//		return convertInfoList(expressVehiclesEntityList);
		return expressVehiclesEntityList;
	}
	/**
	 * 
	 * <p>快递车辆页面用查询方法</p> 
	 * @author 189284 
	 * @date 2015-6-26 上午8:22:05
	 * @param expressVehiclesEntity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService#queryExpressVehiclesListToView(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity, int, int)
	 */
	public List<ExpressVehiclesEntity> queryExpressVehiclesListToView(
			ExpressVehiclesEntity expressVehiclesEntity, int start, int limit) {
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		/*if(StringUtils.isNotEmpty(expressVehiclesEntity.getProvCode())
				&& StringUtils.isNotEmpty(expressVehiclesEntity.getCityCode())
				&& StringUtils.isNotEmpty(expressVehiclesEntity.getAreaCode())){

			//拼接省市区code
			StringBuffer bufferCode = new StringBuffer();
			bufferCode.append(expressVehiclesEntity.getProvCode()).append("-").append(expressVehiclesEntity.getCityCode()).append("-")
			.append(expressVehiclesEntity.getAreaCode());
			
			expressVehiclesEntity.setAreaCode(bufferCode.toString());
		}*/
	    checkDataRights( expressVehiclesEntity);
		List<ExpressVehiclesEntity> expressVehiclesEntityList = expressVehiclesDao.queryInfos(expressVehiclesEntity, limit, start);
		//对快递员所管理的区域进行封装
		transFormDistinct(expressVehiclesEntityList);
		return expressVehiclesEntityList;
	}
	
	/**
	 * 封装开单营业部名、快递员名、修改人名、快递员所属行政区域名及编码
	 * @param ExpressVehicles
	 */
	@SuppressWarnings("unused")
	private void transFormDistinct(List<ExpressVehiclesEntity> expressVehicles){
		for(ExpressVehiclesEntity entity:expressVehicles){
			//封装开单营业部名称
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getOrgCode());
			if(orgAdministrativeInfoEntity!=null){
				entity.setOrgName(orgAdministrativeInfoEntity.getName());
			}
			//封装快递员姓名
			EmployeeEntity employeeEntity1=employeeService.querySimpleEmployeeByEmpCode(entity.getEmpCode());
			if(employeeEntity1!=null){
				entity.setEmpName(employeeEntity1.getEmpName());
			}
			//封装修改人姓名
			EmployeeEntity employeeEntity2=employeeService.querySimpleEmployeeByEmpCode(entity.getModifyUser());
			if(employeeEntity2!=null){
				entity.setModifyUserName(employeeEntity2.getEmpName());
			}
			String regionNameDesc=null;
			String regionCodeDesc=null;
			List<String> regionNameConetcts=null;
			List<String> regionCodeConetcts=null;
			//查询快递员所属行政区域，通过工号
			List<ExpressVehiclesDetailEntity> expressVehiclesDetails=expressVehiclesDetailDao.queryExpressVehiclesByEmpCode(entity.getEmpCode());
			//如果查不到则跳过此次循环
			if(CollectionUtils.isEmpty(expressVehiclesDetails)){
				continue;
			}
			for(ExpressVehiclesDetailEntity detailEntity:expressVehiclesDetails){
				AdministrativeRegionsEntity area=
						administrativeRegionsService.queryAdministrativeRegionsByCode(detailEntity.getAreaCode());
				if(area==null){
					continue;
				}
				AdministrativeRegionsEntity city=
						administrativeRegionsService.queryAdministrativeRegionsByCode(area.getParentDistrictCode());
				if(city==null){
					continue;
				}
				AdministrativeRegionsEntity prove=
						administrativeRegionsService.queryAdministrativeRegionsByCode(city.getParentDistrictCode());
				if(prove==null){
					continue;
				}
				if(!StringUtils.isEmpty(prove.getName())&&
						!StringUtils.isEmpty(city.getName())&&
						!StringUtils.isEmpty(area.getName())){
					regionNameDesc=prove.getName()+"-"+city.getName()+"-"+area.getName();
					regionNameConetcts=new ArrayList<String>();
					regionNameConetcts.add(regionNameDesc);
				}
				if(!StringUtils.isEmpty(prove.getCode())&&
						!StringUtils.isEmpty(city.getCode())&&
						!StringUtils.isEmpty(area.getCode())){
					regionCodeDesc=prove.getCode()+"-"+city.getCode()+"-"+area.getCode();
					regionCodeConetcts=new ArrayList<String>();
					//313353 sonar优化
//					regionCodeConetcts.add(regionNameDesc);
					regionCodeConetcts.add(regionCodeDesc);
				}
			}
			
			//313353 sonar
			this.sonarSplitOne(regionCodeConetcts, regionNameConetcts, entity);
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(List<String> regionCodeConetcts, List<String> regionNameConetcts,
			ExpressVehiclesEntity entity) {
		if(null != regionNameConetcts && !CollectionUtils.isEmpty(regionNameConetcts)){
			String regionNameResult=regionNameConetcts.toString();
			if(!StringUtils.isEmpty(regionNameResult)){
				entity.setAreaName(regionNameResult.substring(1, regionNameResult.length()-1));
			}
		}
		if(null != regionCodeConetcts && !CollectionUtils.isEmpty(regionCodeConetcts)){
			String regionCodeResult=regionCodeConetcts.toString();
			if(!StringUtils.isEmpty(regionCodeResult)){
				entity.setAreaCode(regionCodeResult.substring(1, regionCodeResult.length()-1));
			}
		}
	}
	
	
	/*2.	本事业部的人员查询时仅仅可以查询到本事业部的数据
	 * 3.	保留快递收派标准管理组的现有权限，可以新增、修改
	 */
    public void checkDataRights(ExpressVehiclesEntity expressVehiclesEntity){
    	String orgCode=FossUserContext.getCurrentInfo().getDept().getCode();
    //	List<String> orgCodeList=new ArrayList<String>();
    	//快递收派标准管理组 编码 W0000000042
    	List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys=
    			orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode("W0000000042");
    	if(CollectionUtils.isEmpty(orgAdministrativeInfoEntitys)){
    		throw new ExpressVehiclesException("", "快递收派标准管理组,组织不存在");
    	}
    	boolean flage=true;
    	
//    	if(!OrgAdministrativeInfoEntitys.contains(orgCode)){
    	for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoEntitys) {
			if(StringUtils.equals(orgAdministrativeInfoEntity.getCode(),orgCode)){
				flage=false;
			}
		}
    	if(flage){
    		List<String> bizTypes =new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_DIVISION);
    		OrgAdministrativeInfoEntity	orgAdministrativeInfo=orgAdministrativeInfoComplexService.
    		queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,bizTypes);
    		if(orgAdministrativeInfo!=null){
    			expressVehiclesEntity.setPrentOrgCode(orgAdministrativeInfo.getCode());
    		}
//    		List<OrgAdministrativeInfoEntity> OrgAdministrativeInfoLists=orgAdministrativeInfoComplexService.
//    				queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfo.getCode());
//    		if(CollectionUtils.isEmpty(OrgAdministrativeInfoLists)){
//    			throw new ExpressVehiclesException("", "没有找到自己所在的事业部");
//    		}
//    		for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : OrgAdministrativeInfoLists) {
//				if(StringUtils.isNotBlank(orgAdministrativeInfoEntity.getCode())){
//					orgCodeList.add(orgAdministrativeInfoEntity.getCode());
//				}
//			}
    	}
//    	if(!"W0000000042".equals(orgCode)){
//    		expressVehiclesEntity.setOrgCode(orgCode);
//    	}
    }
    /**
     * 
     *1.	将快递车辆基础资料维护权限变更为各事业部快递业务管理组，并且各事业部只有对自己事业部的数据有新增、修改权限。
     *3.	保留快递收派标准管理组的现有权限，可以新增、修改
     */
    public void checkAddUpdateDataRights(ExpressVehiclesEntity expressVehiclesEntity){
    	String orgCode=FossUserContext.getCurrentInfo().getDept().getCode();
    	//快递收派标准管理组 编码 W0000000042
    	List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys=
    			orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode("W0000000042");
    	if(CollectionUtils.isEmpty(orgAdministrativeInfoEntitys)){
    		throw new ExpressVehiclesException("", "快递收派标准管理组,组织不存在");
    	}
    	/**
    	 * 是否做数据权限标志（保留快递收派标准管理组的现有权限，可以新增、修改（即快递收派标准管理组，不用判断部门 有全国的））
    	 * 默认为Y（要做数据权限控制）
    	 */
    	String yOrNoFlage=FossConstants.YES;
    	
//    	if(!OrgAdministrativeInfoEntitys.contains(orgCode)){
    	for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoEntitys) {
			if(StringUtils.equals(orgAdministrativeInfoEntity.getCode(),orgCode)){
				yOrNoFlage=FossConstants.NO;
			}
		}
    	if(StringUtils.equals(yOrNoFlage,FossConstants.YES)){
    		List<String> bizTypes =new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_DIVISION);
    		/**
    		 * 找到登陆人所属的 事业部
    		 */
    		OrgAdministrativeInfoEntity	orgAdministrativeInfo=orgAdministrativeInfoComplexService.
    		queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,bizTypes);
    		if(orgAdministrativeInfo==null){
    			throw new ExpressVehiclesException("", "没有找到自己所在的事业部");
    		}
    		/**
    		 * 根据事业部编码，找都事业部  及其下属组织
    		 */
    		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoLists=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgAdministrativeInfo.getCode());
    		if(CollectionUtils.isEmpty(orgAdministrativeInfoLists)){
    			throw new ExpressVehiclesException("", "没有找到自己所在的事业部");
    		}
//    		if(!OrgAdministrativeInfoLists.contains(expressVehiclesEntity.getOrgCode())){
//    			throw new ExpressVehiclesException("", "开单部门只能为自己所在事业部");
//    		}
    		/**
    		 * 开单部门 是否在登陆人所以在的事业部 组织内（默认不在）
    		 */
    		Boolean orgFlage=true;
    		/**
    		 * 循环判断开单部门 是否在登陆人所以在的事业部 组织内
    		 */
    		for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoLists) {
				if(StringUtils.equals(orgAdministrativeInfoEntity.getCode(), expressVehiclesEntity.getOrgCode())){
					orgFlage=false;
				}
			}
    		if(orgFlage){
    			throw new ExpressVehiclesException("", "开单部门只能为自己所在事业部");
    		}
    	}
    	
//    	if(!"W0000000042".equals(orgCode)){
//    		if(StringUtils.equals(orgCode, )){
//        		throw new ExpressVehiclesException("", "开单部门只能为自己所在事业部");
//        	}
//    	}
    	
    }
//	private List<ExpressVehiclesEntity> convertInfoList(
//			List<ExpressVehiclesEntity> expressVehiclesEntityList) {
//		List<ExpressVehiclesEntity> list = new ArrayList<ExpressVehiclesEntity>();
//		if(null==expressVehiclesEntityList||expressVehiclesEntityList.size()<1){
//			return null;
//		}
//		for(ExpressVehiclesEntity expressVehiclesEntity:expressVehiclesEntityList){
//			//封装获取关联数据
//			list.add(convertInfo(expressVehiclesEntity));
//		}
//		return list;
//	}

	/**
	 * 封装
	 * @param expressVehiclesEntity
	 * @return
	 */
	private ExpressVehiclesEntity convertInfo(
			ExpressVehiclesEntity expressVehiclesEntity) {
		 if(null == expressVehiclesEntity){
		     return null;
		 }
		 //1根据编码查询所属营业部
		 OrgAdministrativeInfoEntity orgentity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(expressVehiclesEntity.getOrgCode());
		 if(orgentity != null){
		     //设置 组织名称.
			 expressVehiclesEntity.setOrgName(orgentity.getName());
		 }
		 //2根据编码查询所属区域
		 AdministrativeRegionsEntity distrEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(expressVehiclesEntity.getAreaCode());
		 if(distrEntity != null){
		     //判断是否区县
		     if(StringUtils.equals(DictionaryValueConstants.DISTRICT_COUNTY, distrEntity.getDegree())){
		    	 expressVehiclesEntity.setAreaCode(expressVehiclesEntity.getAreaCode());
			 expressVehiclesEntity.setAreaName(distrEntity.getName());
			 expressVehiclesEntity.setCityCode(distrEntity.getParentDistrictCode());
			 expressVehiclesEntity.setCityName(distrEntity.getParentDistrictName());
			 //根据城市编码查询省份编码
			 AdministrativeRegionsEntity entity1 = administrativeRegionsService.queryAdministrativeRegionsByCode(distrEntity.getParentDistrictCode());
			 if(entity1 != null){
			     expressVehiclesEntity.setProvCode(entity1.getParentDistrictCode());
			     expressVehiclesEntity.setProvName(entity1.getParentDistrictName());
			     expressVehiclesEntity.setAreaName(expressVehiclesEntity.getProvName()+ "-" + expressVehiclesEntity.getCityName()+ "-" + expressVehiclesEntity.getAreaName());
			 }else{
				 expressVehiclesEntity.setAreaName(expressVehiclesEntity.getCityName()+ "-" + expressVehiclesEntity.getAreaName());
			 }
		     }else {
				 expressVehiclesEntity.setCityCode(expressVehiclesEntity.getAreaCode());
				 expressVehiclesEntity.setCityName(distrEntity.getName());
				 expressVehiclesEntity.setProvCode(distrEntity.getParentDistrictCode());
				 expressVehiclesEntity.setProvName(distrEntity.getParentDistrictName());
				 expressVehiclesEntity.setAreaName(expressVehiclesEntity.getCityName());
		    }
		 }
		 
		 if(null!=expressVehiclesEntity.getExpressVehiclesDetailList()&&expressVehiclesEntity.getExpressVehiclesDetailList().size()>0){
			 AdministrativeRegionsEntity regionsEntity ;
			 for(ExpressVehiclesDetailEntity entityDetial :expressVehiclesEntity.getExpressVehiclesDetailList()){
				 //2根据编码查询所属区域
				 regionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(entityDetial.getAreaCode());
				 if(regionsEntity != null){
				     //判断是否区县
				     if(StringUtils.equals(DictionaryValueConstants.DISTRICT_COUNTY, regionsEntity.getDegree())){
						 String areaName = regionsEntity.getName();
						 String cityName = regionsEntity.getParentDistrictName();
//						 String areaCode = regionsEntity.getCode();
//						 String cityCode = regionsEntity.getParentDistrictCode();
						 //根据城市编码查询省份编码
						 AdministrativeRegionsEntity entity1 = administrativeRegionsService.queryAdministrativeRegionsByCode(regionsEntity.getParentDistrictCode());
						 if(entity1 != null){
						     String provName = entity1.getParentDistrictName();
//						     String provCode = entity1.getParentDistrictCode();
						     entityDetial.setAreaName(provName+ "-" +cityName+ "-" +areaName);
//						     entityDetial.setAreaCode(provCode+","+cityCode+","+areaCode);
						 }else{
							 entityDetial.setAreaName(cityName+ "-" +areaName);
//							 entityDetial.setAreaCode(cityCode+","+areaCode);
						 }
				     }
				 }
			 }
		 }
		 
		    //3根据编码查询所属车型
		 VehicleTypeEntity vehicleTypeEntity =leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(expressVehiclesEntity.getVehicleLengthCode());
		 if(null!=vehicleTypeEntity){
			 expressVehiclesEntity.setVehicleLengthName(vehicleTypeEntity.getVehicleLengthName());
		 }
		//4根据编码查询所属所属快递员name
		 EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(expressVehiclesEntity.getEmpCode()); 
		 if(null!=emp){
			 expressVehiclesEntity.setEmpName(emp.getEmpName());
		 }
		return expressVehiclesEntity;
	}
	/**
	 * 根据ID 获取数据
	 */
	@Override
	public ExpressVehiclesEntity queryExpressVehiclesById(String id) {
		
		ExpressVehiclesEntity expressVehiclesEntity = expressVehiclesDao.queryExpressVehiclesById(id);
		List<ExpressVehiclesDetailEntity> expressVehiclesDetailList = new ArrayList<ExpressVehiclesDetailEntity>();
		String empCode = null;
		if(expressVehiclesEntity!=null){
			empCode = expressVehiclesEntity.getEmpCode();
			expressVehiclesDetailList = expressVehiclesDetailDao.queryExpressVehiclesByEmpCode(empCode);
			
			expressVehiclesEntity.setExpressVehiclesDetailList(expressVehiclesDetailList);
			expressVehiclesEntity = convertInfo(expressVehiclesEntity);
		}
		
		return expressVehiclesEntity;
	}

	@Override
	public Long queryRecordCount(ExpressVehiclesEntity expressVehiclesEntity) {
		return expressVehiclesDao.queryRecordCount(expressVehiclesEntity);
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setLeasedVehicleTypeService(
			ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}
	
	/**
	   * 统计按条件查询返回的记录数
	   * (提供给外部的接口（pda，中转···）)
	   * @author  WangPeng
	   * @Date    2013-8-23 下午1:31:17
	   * @param   enity
	   * @return  Long
	   * 
	   *
	   */
	  public Long recordCountByQueryParams(ExpressVehiclesEntity expressVehiclesEntity) {
		  expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		return expressVehiclesDao.recordCountByQueryParams(expressVehiclesEntity);
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 189284 
	 * @date 2015-6-26 上午8:21:24
	 * @param expressVehiclesEntity
	 * @return
	 * @see
	 */
	  public Long recordCountByQueryParamsToView(ExpressVehiclesEntity expressVehiclesEntity) {
		  expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		 checkDataRights( expressVehiclesEntity);
		return expressVehiclesDao.recordCountByQueryParams(expressVehiclesEntity);
	}
	
  /**
   * 根据条件查询快递车辆信息
   * 
   * @author  FOSS-ShenWeiHua-132599
   * @Date    2014-9-24 下午1:31:17
   * @param   enity
   * @return  entity
   *
   */
	@Override
	public List<ExpressVehiclesEntity> queryExpressVehiclesByEntity(
			ExpressVehiclesEntity entity) {
		if(null==entity){
			return null;
		}
		return expressVehiclesDao.queryExpressVehiclesByEntity(entity);
	}
	  
	/**
	 * 
	 *<p>Title: queryOrgCodeByEmpCode</p>
	 *<p>根据车牌号 查询开单营业部</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-8-11下午4:43:59
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService#queryOrgCodeByEmpCode(java.lang.String)
	 * @param vehicleNo
	 * @return
	*/
	@Override
	public String queryOrgCodeByVehicleNo(String vehicleNo) {
		if(StringUtils.isBlank(vehicleNo)){
			throw new ExpressVehiclesException(null,"车牌号为空");
		}
		ExpressVehiclesEntity entity =new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setVehicleNo(vehicleNo);
		List<ExpressVehiclesEntity> resultList =expressVehiclesDao.queryInfos(entity, 1, 0);
		//非空校验
		if(CollectionUtils.isEmpty(resultList)||null ==resultList.get(0)){
			return null;
		}
		return resultList.get(0).getOrgCode();
	}

	/**
	 * 根据快递员code查询出所服务的开单营业部
	 * 232608
	 */
	@Override
	public String queryExpressVehiclesByCode(String code) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(code)){
			return null;
		}
		ExpressVehiclesEntity entity =new ExpressVehiclesEntity();
		entity.setEmpCode(code);
		List<ExpressVehiclesEntity> resultList =expressVehiclesDao.queryInfos(entity, 1, 0);
		//非空校验
		if(CollectionUtils.isEmpty(resultList)||null ==resultList.get(0)){
			return null;
		}
		return resultList.get(0).getOrgCode();
	}
	
	/**
	 * 
	 * <p>
	 * 同步到OMS
	 * </p>
	 * 
	 * @author foss-qiupeng
	 * @date 2016-03-21
	 * @param entity
	 * @see
	 */
	private void syncToOMSWebsite(ExpressVehiclesEntity expressVehEntity,
			List<ExpressVehiclesDetailEntity> detailEntitys) {
		if (expressVehEntity == null) {
			return;
		}
		List<ExpressVehiclesEntity> entitys = new ArrayList<ExpressVehiclesEntity>();
		entitys.add(expressVehEntity);
		syncToOMSWebsite(entitys, detailEntitys);
	}
	

	/**
	 * 
	 * <p>
	 * 同步到OMS
	 * </p>
	 * 
	 * @author foss-qiupeng
	 * @date 2016-03-21
	 * @param entitys
	 * @see
	 */
	@Override
	public void syncToOMSWebsite(List<ExpressVehiclesEntity> entitys, 
			List<ExpressVehiclesDetailEntity> detailEntitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
			sendExpressVehicleToOMSService.syncExpressVehicleToOMS(entitys, detailEntitys);
		
	}
	
}
