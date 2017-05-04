package com.deppon.foss.module.base.baseinfo.server.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAcceptPointSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAccSalesDeptToCUBCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AcceptPointSalesDeptDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AcceptPointSalesDeptException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * 接驳点与营业部映射
 * @author 132599 ShenWeiHua
 *
 * @date 2015-4-16 下午4:14:13
 */
public class AcceptPointSalesDeptService implements IAcceptPointSalesDeptService{
	
    /**
     * 接驳点与营业部映射DAO
     */
    private IAcceptPointSalesDeptDao acceptPointSalesDeptDao;
    
    /**
     * 接驳点与营业部映射关系同步
     */  
    private ISyncAccSalesDeptToCUBCService syncAccSalesDeptToCUBCService;
    
	public void setSyncAccSalesDeptToCUBCService(
			ISyncAccSalesDeptToCUBCService syncAccSalesDeptToCUBCService) {
		this.syncAccSalesDeptToCUBCService = syncAccSalesDeptToCUBCService;
	}

	/**
     * 接驳点基础资料service
     */
    private IAccessPointService accessPointService;
      
    /**
     * <p>新增接驳点与营业部映射主干及子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
    @SuppressWarnings("null")
	@Override
    @Transactional
	public int addAcceptPointSalesDept(AcceptPointSalesDeptEntity entity) {
    	//判断添加的实体不为空并且对应的接驳点主干信息和子信息都不为空
		if(null == entity || 
			CollectionUtils.isEmpty(entity.getChildrenSalesDeptEntitys())){
			throw new AcceptPointSalesDeptException("接驳点信息为空或者接驳点对应的营业部信息为空！");
		}
		String acceptPointCode = entity.getAcceptPointCode();
		if(StringUtils.isNotEmpty(acceptPointCode)){
			
			entity.setActive(FossConstants.ACTIVE);
			// 根据实体查询一个有效的接驳点实体主干
			AcceptPointSalesDeptEntity apEntity = acceptPointSalesDeptDao.queryAcceptPointSales(entity);
			if(null !=apEntity){
				throw new AcceptPointSalesDeptException("新增接驳点映射信息已存在，请勿再新增！");
			}
		}else{
			throw new AcceptPointSalesDeptException("新增接驳点映射信息接驳点code为空");
		}
		
		String createUser = FossUserContext.getCurrentInfo().getEmpCode();
		String createUserName = FossUserContext.getCurrentInfo().getEmpName();
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setCreateUser(createUser);
		entity.setCreateUserName(createUserName);
		entity.setStatus(FossConstants.INACTIVE);
		acceptPointSalesDeptDao.addAcceptPointSales(entity);
		
		List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntityList = new ArrayList<AcceptPointSalesDeptEntity>();
		AcceptPointSalesDeptDto acceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		
		acceptPointSalesDeptEntityList.add(entity);
		acceptPointSalesDeptDto.setAcceptPointEntitys(acceptPointSalesDeptEntityList);
		//向cubc同步数据
		syncAccSalesDeptToCUBCService.syncAccSalesDept(acceptPointSalesDeptDto, NumberConstants.ONE.toString());

		// 循环插入接驳点对应的营业部信息
		List<AcceptPointSalesChildrenDeptEntity> acceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();
		AcceptPointSalesDeptDto synAcceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		
		for(AcceptPointSalesChildrenDeptEntity childEntity : entity.getChildrenSalesDeptEntitys()){
			childEntity.setId(UUIDUtils.getUUID());
			childEntity.setAcceptPointCode(entity.getAcceptPointCode());
			childEntity.setActive(FossConstants.ACTIVE);
			childEntity.setStatus(FossConstants.INACTIVE);
			acceptPointSalesDeptDao.addSalesDept(childEntity);
			
			acceptPointSalesChildrenDeptEntityList.add(childEntity);
		}
		synAcceptPointSalesDeptDto.setChildAcceptPointEntitys(acceptPointSalesChildrenDeptEntityList);
		//向cubc同步数据
		syncAccSalesDeptToCUBCService.syncAccSalesDept(synAcceptPointSalesDeptDto, NumberConstants.ONE.toString());
		
		return FossConstants.SUCCESS;
	}

	/**
     * <p>修改接驳点与营业部映射主干及子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor=AcceptPointSalesDeptException.class)
	public int updateAcceptPointSalesDept(AcceptPointSalesDeptEntity entity) {
		//判断添加的实体不为空并且对应的接驳点主干信息和子信息都不为空
		if(null == entity ||  
		 CollectionUtils.isEmpty(entity.getChildrenSalesDeptEntitys())){
			throw new AcceptPointSalesDeptException("接驳点信息为空或者接驳点对应的营业部信息为空！");
		}
		//判断接驳点code和营业区code不为空
		if(StringUtils.isNotEmpty(entity.getAcceptPointCode())){
			entity.setActive(FossConstants.ACTIVE);
			AcceptPointSalesDeptEntity apEntity = acceptPointSalesDeptDao.queryAcceptPointSales(entity);
			// 判断该实体在数据库里是否存在
			if(null !=apEntity){
				String modifyUser = FossUserContext.getCurrentInfo().getEmpCode();
				String modifyUserName = FossUserContext.getCurrentInfo().getEmpName();
				apEntity.setModifyDate(new Date());
				apEntity.setModifyUser(modifyUser);
				apEntity.setModifyUserName(modifyUserName);
				// 更新接驳点主干信息
				acceptPointSalesDeptDao.updateAcceptPointSales(apEntity);
				
				List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntityList = new ArrayList<AcceptPointSalesDeptEntity>();
				AcceptPointSalesDeptDto acceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
				
				acceptPointSalesDeptEntityList.add(apEntity);
				acceptPointSalesDeptDto.setAcceptPointEntitys(acceptPointSalesDeptEntityList);		
				//向cubc同步数据
				syncAccSalesDeptToCUBCService.syncAccSalesDept(acceptPointSalesDeptDto, NumberConstants.TWO.toString());
				
				// 要更新的实体
				List<AcceptPointSalesChildrenDeptEntity> newChildrenDeptEntitys = entity.getChildrenSalesDeptEntitys();
				// 查询数据库里还未变更的实体
				List<AcceptPointSalesChildrenDeptEntity> oldChildrenDeptEntitys = acceptPointSalesDeptDao.queryChildrenDeptByAcceptSmallCode
						(entity.getAcceptPointCode());
				// 判断要更新接驳点映射子信息在数据库里是否存在
				if(CollectionUtils.isEmpty(oldChildrenDeptEntitys)){
					throw new AcceptPointSalesDeptException("要更新的接驳点映射子信息在数据库里未查到");
				}
				//建立一个营业部code集合
				List<String> salesDepartmentCodes = new ArrayList<String>();
				
				List<AcceptPointSalesChildrenDeptEntity> acceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();
				AcceptPointSalesDeptDto synAcceptPointSalesDeptDto = new AcceptPointSalesDeptDto();

				//如果数据库里不存在要更新的信息，那么说明要新增
				for(AcceptPointSalesChildrenDeptEntity childrenEntity : newChildrenDeptEntitys){
					// 遍历要更新的数据把营业部code添加到集合里
					salesDepartmentCodes.add(childrenEntity.getSalesDepartmentCode());
					if(StringUtils.isEmpty(childrenEntity.getId())){
						childrenEntity.setId(UUIDUtils.getUUID());
						childrenEntity.setAcceptPointCode(apEntity.getAcceptPointCode());
						childrenEntity.setActive(FossConstants.ACTIVE);
						childrenEntity.setStatus(FossConstants.INACTIVE);
						acceptPointSalesDeptDao.addSalesDept(childrenEntity);
					}
					acceptPointSalesChildrenDeptEntityList.add(childrenEntity);

				}
				synAcceptPointSalesDeptDto.setChildAcceptPointEntitys(acceptPointSalesChildrenDeptEntityList);
				//向cubc同步数据
				syncAccSalesDeptToCUBCService.syncAccSalesDept(synAcceptPointSalesDeptDto, NumberConstants.ONE.toString());
				List<AcceptPointSalesChildrenDeptEntity> synAcceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();

				// 如果数据库中有，而即将更新的数据中没有，说明要删除
				for(AcceptPointSalesChildrenDeptEntity childrenEntity : oldChildrenDeptEntitys){
					if(!salesDepartmentCodes.contains(childrenEntity.getSalesDepartmentCode())){
						acceptPointSalesDeptDao.deleteChildrenSalesDeptById(childrenEntity.getId());
						
						childrenEntity = acceptPointSalesDeptDao.queryChildrenDeptById(childrenEntity.getId());
						synAcceptPointSalesChildrenDeptEntityList.add(childrenEntity);
					} 
				}
				
				synAcceptPointSalesDeptDto.setChildAcceptPointEntitys(synAcceptPointSalesChildrenDeptEntityList);
				//向cubc同步数据
				syncAccSalesDeptToCUBCService.syncAccSalesDept(synAcceptPointSalesDeptDto, NumberConstants.THREE.toString());
				
			}else{
				// 回调新增方法
				addAcceptPointSalesDept(entity);
			}
		}
		return FossConstants.SUCCESS;
	}


	/**
     * <p>作废接驳点与营业部映射主干及子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor=AcceptPointSalesDeptException.class)
	public int deleteAcceptPointSalesDeptById(String id, Date modifyDate,
			String modifyUser, String modifyUserName) {
		// 判断传入的参数是否为空
		if(StringUtils.isEmpty(id)){
			throw new AcceptPointSalesDeptException("删除时传入的参数id为空！");
		}
		// 先根据id把接驳点主干实体查出来
		AcceptPointSalesDeptEntity deptEntity = acceptPointSalesDeptDao.queryAcceptPointSalesById(id);
		if(null==deptEntity){
			throw new AcceptPointSalesDeptException("deptEntity为空！");	
		}
		// 只有在未启用的情况下才可以删除
		if(!FossConstants.INACTIVE.equals(deptEntity.getStatus())){
			throw new AcceptPointSalesDeptException("该映射信息只有在未启用状态下才能删除!");
		}
			
		AcceptPointSalesDeptDto acceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		List<AcceptPointSalesChildrenDeptEntity> acceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();
		List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntityList = new ArrayList<AcceptPointSalesDeptEntity>();

		
		if(null != deptEntity && StringUtils.isNotEmpty(deptEntity.getAcceptPointCode())){
			List<AcceptPointSalesChildrenDeptEntity> newAcceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();

			//先取出没作废之前的有效数据，后面active手工修改
			acceptPointSalesChildrenDeptEntityList = acceptPointSalesDeptDao.queryChildrenDeptInfoByAcceptSmallCode(deptEntity.getAcceptPointCode(),FossConstants.ACTIVE,null);
			for(AcceptPointSalesChildrenDeptEntity acceptPointSalesChildrenDeptEntity:acceptPointSalesChildrenDeptEntityList){
				acceptPointSalesChildrenDeptEntity.setActive(FossConstants.INACTIVE);
				newAcceptPointSalesChildrenDeptEntityList.add(acceptPointSalesChildrenDeptEntity);
			}
			acceptPointSalesDeptDao.deleteSalesDeptByAcceptSmallCode(deptEntity.getAcceptPointCode());
			acceptPointSalesDeptDto.setChildAcceptPointEntitys(newAcceptPointSalesChildrenDeptEntityList);
			
			//向cubc同步数据
			syncAccSalesDeptToCUBCService.syncAccSalesDept(acceptPointSalesDeptDto, NumberConstants.THREE.toString());
			
		}else{
			throw new AcceptPointSalesDeptException("根据id没有查到对应的接驳点映射主干信息！或者该实体的接驳点code为空！");
		}
		
		AcceptPointSalesDeptDto synAcceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		
		acceptPointSalesDeptDao.deleteAcceptPointSalesDeptById(id, modifyDate, modifyUser, modifyUserName);
		AcceptPointSalesDeptEntity acceptPointSalesDeptEntity = acceptPointSalesDeptDao.queryAcceptPointSalesById(id);
		acceptPointSalesDeptEntityList.add(acceptPointSalesDeptEntity);
		synAcceptPointSalesDeptDto.setAcceptPointEntitys(acceptPointSalesDeptEntityList);
		
		//向cubc同步数据
		syncAccSalesDeptToCUBCService.syncAccSalesDept(synAcceptPointSalesDeptDto, NumberConstants.THREE.toString());
		
		return FossConstants.SUCCESS;
	}

	/**
     * <p>修改接驳点与营业部映射主干及子状态信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
	@Override
	@Transactional(rollbackFor=AcceptPointSalesDeptException.class)
	public int updateAcceptPointSalesStatusById(List<String> idList,
			String status, Date modifyDate, String modifyUser,
			String modifyUserName) {
		// 判断传入的参数是否为空
		if(CollectionUtils.isEmpty(idList) || StringUtils.isEmpty(status)){
			throw new AcceptPointSalesDeptException("修改状态时传入的参数id为空或者状态信息为空！");
		}
		AcceptPointSalesDeptDto acceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		List<AcceptPointSalesChildrenDeptEntity> newAcceptPointSalesChildrenDeptEntityList = new ArrayList<AcceptPointSalesChildrenDeptEntity>();
		for(String id : idList){
			// 先根据id把接驳点主干实体查出来
			AcceptPointSalesDeptEntity deptEntity = acceptPointSalesDeptDao.queryAcceptPointSalesById(id);
			if(null != deptEntity && StringUtils.isNotEmpty(deptEntity.getAcceptPointCode())){
				// 判断传进来的状态是不是启用状态，如果是启用状态则要先判断这个接驳点是不是已启用
				if(FossConstants.YES.equals(status)){
					AccessPointEntity apEntity = new AccessPointEntity();
					apEntity.setCode(deptEntity.getAcceptPointCode());
					AccessPointEntity accessPointEntity = accessPointService.queryAccessPointsByCode(apEntity);
					// 判断接驳点不为空并且接驳点的状态为启用状态
					if(null == accessPointEntity || !FossConstants.YES.equals(accessPointEntity.getStatu())){
						throw new AcceptPointSalesDeptException("如果要启用接驳点映射信息，那么接驳点必须要为启用状态！");
					}
				}
				acceptPointSalesDeptDao.updateSalesDeptStatusByAcceptSmallCode(status, deptEntity.getAcceptPointCode());
				newAcceptPointSalesChildrenDeptEntityList = acceptPointSalesDeptDao.queryChildrenDeptInfoByAcceptSmallCode(deptEntity.getAcceptPointCode(),null,status);
				acceptPointSalesDeptDto.setChildAcceptPointEntitys(newAcceptPointSalesChildrenDeptEntityList);
				
				//向cubc同步数据
				syncAccSalesDeptToCUBCService.syncAccSalesDept(acceptPointSalesDeptDto, NumberConstants.TWO.toString());
				
			}else{
				throw new AcceptPointSalesDeptException("根据id没有查到对应的接驳点映射主干信息！或者该实体的接驳点code为空！");
			}
		}
		// 批量修改接驳点主干信息
		acceptPointSalesDeptDao.updateAcceptPointSalesStatusById(idList, status, modifyDate, modifyUser, modifyUserName);
		AcceptPointSalesDeptDto synAcceptPointSalesDeptDto= new AcceptPointSalesDeptDto();
		
		List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntityList= acceptPointSalesDeptDao.queryAcceptPointSalesDeptByIdList(idList);
		synAcceptPointSalesDeptDto.setAcceptPointEntitys(acceptPointSalesDeptEntityList);
		
		//向cubc同步数据
		syncAccSalesDeptToCUBCService.syncAccSalesDept(synAcceptPointSalesDeptDto, NumberConstants.TWO.toString());
		return FossConstants.SUCCESS;
	}


	/**
     * 根据传入对象查询符合条件所有接驳点与营业部映射信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesDeptEntity> queryAllAcceptPointSalesDept(
			AcceptPointSalesDeptEntity entity, int limit, int start) {
		// 判断传入参数不为空
		if(null == entity){
			throw new AcceptPointSalesDeptException("查询时传入的参数不允许为空！");
		}
		//根据实体查询接驳点主干信息
		List<AcceptPointSalesDeptEntity> deptEntitys = acceptPointSalesDeptDao.
				queryAllAcceptPointSalesDept(entity, limit, start);
		if(CollectionUtils.isNotEmpty(deptEntitys)){
			//循环查询回来的接驳点主干list
			for(AcceptPointSalesDeptEntity deptEntity : deptEntitys){
				if(StringUtils.isNotEmpty(deptEntity.getAcceptPointCode())){
					// 根据接驳点和营业区查询某个接驳点对应的多个营业部
					List<AcceptPointSalesChildrenDeptEntity> childrenEntitys = 
							acceptPointSalesDeptDao.queryAllAcceptPointSalesChildrenDept
							(deptEntity.getAcceptPointCode());
					/*if(CollectionUtils.isEmpty(childrenEntitys)){
						throw new AcceptPointSalesDeptException("接驳点"+deptEntity.getAcceptPointName()+ "对应的营业部信息为空！");
					}*/
					deptEntity.setChildrenSalesDeptEntitys(childrenEntitys);
				}
			}
		}
		return deptEntitys;
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-15 下午3:10:32
     * @param entity
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(AcceptPointSalesDeptEntity entity) {
		
		if(null == entity){
		    throw new AcceptPointSalesDeptException("传入的参数不允许为空！");
		}else {
		    entity.setActive(FossConstants.ACTIVE);
		    return acceptPointSalesDeptDao.queryRecordCount(entity);
		}
	}
	
	/**
     * 根据上级编码查询下面的营业部
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryAllOrgAdministrativeInfoByParentOrgCode
	(String acceptPointCode, String smallRegion, String smallRegionName){
		// 判断传入的参数不为空
		if(StringUtils.isEmpty(smallRegion) || StringUtils.isEmpty(smallRegionName) || StringUtils.isEmpty(acceptPointCode)){
			throw new AcceptPointSalesDeptException("根据上级编码查询下面的营业部时传入的参数不允许为空！");
		}
		List<AcceptPointSalesChildrenDeptEntity> childrenEntitys = acceptPointSalesDeptDao.queryAllOrgAdministrativeInfoByParentOrgCode(acceptPointCode,smallRegion);
		if(CollectionUtils.isNotEmpty(childrenEntitys)){
			for(AcceptPointSalesChildrenDeptEntity entity : childrenEntitys){
				entity.setSmallRegion(smallRegion);
				entity.setSmallRegionName(smallRegionName);
			}
		}else{
			throw new AcceptPointSalesDeptException("该营业区下面没有可配置营业部！");
		}
		return childrenEntitys;
	}
	
	/**
     * 根据大区编码从接驳点基础资料里面查询接驳点信息和中转场信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointTransferInfoByBigRegionCode(
			String bigRegionCode) {
		// 判断传入的参数是否为空
		if(StringUtils.isEmpty(bigRegionCode)){
			throw new AcceptPointSalesDeptException("传入的参数不允许为空！");
		}
		return acceptPointSalesDeptDao.queryAcceptPointTransferInfoByBigRegionCode(bigRegionCode);
	}
	
	/**
     * 根据大区编码从组织表总查询大区下面的营业区信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> querySmallRegionInfoByBigRegionCode(
			String bigRegionCode) {
		// 判断传入的参数是否为空
		if(StringUtils.isEmpty(bigRegionCode)){
			throw new AcceptPointSalesDeptException("传入的参数不允许为空！");
		}
		return acceptPointSalesDeptDao.querySmallRegionInfoByBigRegionCode(bigRegionCode);
	}
	
	/**
     * 根据接驳点、营业区编码查询接驳点与营业部映射子信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryChildrenDeptByAcceptSmallCode(
			String acceptPointCode) {
		// 判断传入的参数不为空
		if(StringUtils.isEmpty(acceptPointCode)){
			throw new AcceptPointSalesDeptException("根据根据接驳点查询接驳点与营业部映射子信息时传入的参数不允许为空！");
		}
		return acceptPointSalesDeptDao.queryChildrenDeptByAcceptSmallCode(acceptPointCode);
	}
	
	/**
     * 根据接驳点编码查询
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesDeptEntity> queryAcceptPointSalesByAcceptPointCode(
			List<String> acceptPointCodes) {
		if(acceptPointCodes==null||acceptPointCodes.size()==0){
			return null;
		}
		return acceptPointSalesDeptDao.queryAcceptPointSalesByAcceptPointCode(acceptPointCodes);
	}
	
	/**
	 * 根据员工号查询快递员对应的接驳点
     * @author 132599-FOSS-ShenweiHua
     * @date   2015-4-29 上午10:50:21
     * @param empCode
     * @return
     */ 
	@Override
	public List<String> queryExpressAcceptPointByEmployeeCode(String empCode) {
		// 判断传进来的参数是否为空
		if(StringUtils.isEmpty(empCode)){
			throw new AcceptPointSalesDeptException("根据员工号查询快递员对应的接驳点时传入的参数不允许为空！");
		}
		List<String> oriAccessPointCodes = acceptPointSalesDeptDao.queryExpressAcceptPointByEmployeeCode(empCode);
		List<String> accessPointCodes = new ArrayList<String>();
		if(oriAccessPointCodes!=null&&oriAccessPointCodes.size()!=0){
			for(String oriAccessPointCode:oriAccessPointCodes){
				if("".equals(oriAccessPointCode)||oriAccessPointCode==null){
					continue;
				}
				accessPointCodes.add(oriAccessPointCode);
			}
		}
		return accessPointCodes;
	}
	
	
	/**
	 * 设置接驳点与营业部映射的注入dao
	 * @param acceptPointSalesDeptDao
	 */
	public void setAcceptPointSalesDeptDao(
			IAcceptPointSalesDeptDao acceptPointSalesDeptDao) {
		this.acceptPointSalesDeptDao = acceptPointSalesDeptDao;
	}
	
	/**
	 * 设置接驳点基础资料的service
	 * @param accessPointService
	 */
	public void setAccessPointService(IAccessPointService accessPointService) {
		this.accessPointService = accessPointService;
	}
	
	/**
     * 根据接驳点编码查询接驳点与营业部关系
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<AcceptPointSalesChildrenDeptEntity> queryAcceptPointSaleDeptsByAcceptPointCode(
			List<String> acceptPointCodes) {
		if(acceptPointCodes==null||acceptPointCodes.size()==0){
			return null;
		}
		return acceptPointSalesDeptDao.queryAcceptPointSaleDeptsByAcceptPointCode(acceptPointCodes);
	}

}

