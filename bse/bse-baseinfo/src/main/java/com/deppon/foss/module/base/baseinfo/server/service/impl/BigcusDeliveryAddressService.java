package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBigcusDeliveryAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncBigcusDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;

/**
 * 零担大客户派送地址库Service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-4-15 上午8:14:34,content:TODO </p>
 * @author 232607 
 * @date 2016-4-15 上午8:14:34
 * @since
 * @version
 */
public class BigcusDeliveryAddressService implements
		IBigcusDeliveryAddressService {
	
	private IBigcusDeliveryAddressDao bigcusDeliveryAddressDao;
	/**
	 * 组织service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
		
	/**
	 * 偏线代理网点Service
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	private ISyncBigcusDeliveryAddressService syncBigcusDeliveryAddressService;



	public void setSyncBigcusDeliveryAddressService(
			ISyncBigcusDeliveryAddressService syncBigcusDeliveryAddressService) {
		this.syncBigcusDeliveryAddressService = syncBigcusDeliveryAddressService;
	}

	public void setBigcusDeliveryAddressDao(
			IBigcusDeliveryAddressDao bigcusDeliveryAddressDao) {
		this.bigcusDeliveryAddressDao = bigcusDeliveryAddressDao;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/** 
	 * 行政区域新增时，连带新增零担派送地址库
	 * @author 232607 
	 * @date 2016-5-28 上午7:55:18
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#addBigcusDeliveryAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity addBigcusFromAdRegion(
			AdministrativeRegionsEntity adEntity) {
		// 合法性检查
		if (adEntity==null  || StringUtils.isBlank(adEntity.getCode())) {
			throw new BusinessException("传入的参数为空！");
		}
		// 新增的对象，在库中是否存在，若存在则修改，不存在则新增
		BigcusDeliveryAddressEntity bigEntity = bigcusDeliveryAddressDao.queryBigcusDeliveryAddressEntityByCode(adEntity.getCode());
		if (bigEntity == null) {
			bigEntity=new BigcusDeliveryAddressEntity();
			bigEntity.setCode(adEntity.getCode());
			bigEntity.setName(adEntity.getName());
			bigEntity.setParentDistrictCode(adEntity.getParentDistrictCode());
			bigEntity.setVirtualDistrictId(adEntity.getVirtualDistrictId());
			bigEntity.setDegree(adEntity.getDegree());
			bigEntity= bigcusDeliveryAddressDao.addBigcusDeliveryAddress(bigEntity);
		}else{
			bigEntity.setName(adEntity.getName());
			bigEntity.setParentDistrictCode(adEntity.getParentDistrictCode());
			bigEntity.setVirtualDistrictId(adEntity.getVirtualDistrictId());
			bigEntity.setDegree(adEntity.getDegree());
			bigEntity = bigcusDeliveryAddressDao.updateBigcusDeliveryAddress(bigEntity);
		}
		//推给OMS
		List<BigcusDeliveryAddressEntity> sendEntitys=new ArrayList<BigcusDeliveryAddressEntity>();
		sendEntitys.add(bigEntity);
		syncBigcusDeliveryAddressService.syncBigcusDeliveryAddressToOMS(sendEntitys);
		return bigEntity;
	}
	/** 
	 * <p>界面修改零担大客户派送地址库</p> 
	 * @author 232607 
	 * @date 2016-4-6 下午1:58:31
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#updateBigcusDeliveryAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity updateBigcusDeliveryAddress(
			BigcusDeliveryAddressEntity entity) {
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			throw new BusinessException("传入的参数为空");
		}
		//查询更新之前的信息
		BigcusDeliveryAddressEntity old = bigcusDeliveryAddressDao.queryBigcusDeliveryAddressEntityByCode(entity.getCode());
		if(old==null){
			throw new BusinessException("原区域不存在");
		}
		String oldType=old.getDeliveryType();
		old.setDeliveryAddTime(entity.getDeliveryAddTime());
		old.setDeliveryRemark(entity.getDeliveryRemark());
		old.setDeliverySalesDeptCode(entity.getDeliverySalesDeptCode());
		old.setDeliveryType(entity.getDeliveryType());
		if (entity.getDeliveryType().equals(DictionaryValueConstants.MUCHHIGHER_DELIVERY)) {
			//当选择超远派送时，设置派送费用
			old.setMuchHigherDelivery(entity.getMuchHigherDelivery());
		}else {
			//非超远派送时直接设置为0
			old.setMuchHigherDelivery(0.0);
		}
		//执行修改操作
		BigcusDeliveryAddressEntity result = bigcusDeliveryAddressDao.updateBigcusDeliveryAddress(old);
		if(result==null){
			throw new BusinessException("修改失败");
		}
		//待推送OMS集合
		List<BigcusDeliveryAddressEntity> sendEntitys=new ArrayList<BigcusDeliveryAddressEntity>();
		sendEntitys.add(result);
		//如果派送网点类型改变，则改变其上级派送网点类型
		if(!StringUtil.equals(oldType, result.getDeliveryType())){
			BigcusDeliveryAddressEntity parentEntity=optimizeParentDeliveryNature(result);
			if(parentEntity!=null){
				sendEntitys.add(parentEntity);				
			}
		}
		syncBigcusDeliveryAddressService.syncBigcusDeliveryAddressToOMS(sendEntitys);
		return result;
	}
	
	/** 
	 * <p>修改行政区域，连带改零担大客户派送地址库基本字段</p> 
	 * @author 232607 
	 * @date 2016-4-6 下午3:23:23
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#updateBigcusDeliveryAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity updateBigcusFromAdRegion(AdministrativeRegionsEntity adEntity) {
		if (adEntity==null || StringUtils.isBlank(adEntity.getCode())) {
			throw new BusinessException("传入的参数为空！");
		}
		//查询更新之前的信息
		BigcusDeliveryAddressEntity bigEntity = bigcusDeliveryAddressDao.queryBigcusDeliveryAddressEntityByCode(adEntity.getCode());
		//存在则修改，不存在则新增
		if(bigEntity !=null){
			bigEntity.setName(adEntity.getName());
			bigEntity.setParentDistrictCode(adEntity.getParentDistrictCode());
			bigEntity.setVirtualDistrictId(adEntity.getVirtualDistrictId());
			bigEntity.setDegree(adEntity.getDegree());
			bigEntity = bigcusDeliveryAddressDao.updateBigcusDeliveryAddress(bigEntity);
		}else{
			bigEntity=new BigcusDeliveryAddressEntity();
			bigEntity.setCode(adEntity.getCode());
			bigEntity.setName(adEntity.getName());
			bigEntity.setParentDistrictCode(adEntity.getParentDistrictCode());
			bigEntity.setVirtualDistrictId(adEntity.getVirtualDistrictId());
			bigEntity.setDegree(adEntity.getDegree());
			bigEntity= bigcusDeliveryAddressDao.addBigcusDeliveryAddress(bigEntity);
		}
		//推给OMS
		List<BigcusDeliveryAddressEntity> sendEntitys=new ArrayList<BigcusDeliveryAddressEntity>();
		sendEntitys.add(bigEntity);
		syncBigcusDeliveryAddressService.syncBigcusDeliveryAddressToOMS(sendEntitys);
		return bigEntity;
	}
	
	
	/** 
	 * <p>根据code查name</p> 
	 * @author 232607 
	 * @date 2016-5-28 上午10:55:59
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#querybigcusDeliveryAddressNameByCode(java.lang.String)
	 */
	@Override
	public String querybigcusDeliveryAddressNameByCode(String code) {
		// 非空检查
		if (StringUtils.isBlank(code)) {
			return null;
		}
		BigcusDeliveryAddressEntity entity = bigcusDeliveryAddressDao
				.queryBigcusDeliveryAddressEntityByCode(code);
		// 得到的实体的非空检查
		if (null == entity) {
			throw new BusinessException("根据code获取的数据,库中不存在！");
		}
		return entity.getName();
	}

	/** 
	 * <p>分页查询</p> 
	 * @author 232607 
	 * @date 2016-3-28 上午8:21:52
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryBigcusDeliveryAddressEntities(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity, int, int)
	 */
	@Override
	public List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressEntities(
			BigcusDeliveryAddressEntity entity, int start, int limit) {
		// 若实体对象为空，实例化一个对象
		if (null == entity) {
			entity = new BigcusDeliveryAddressEntity();
		}
		List<BigcusDeliveryAddressEntity> resultList =bigcusDeliveryAddressDao.queryBigcusDeliveryAddressList(
				entity, start, limit);
		//封装是否叶子节点
		if(CollectionUtils.isNotEmpty(resultList)){
			for(BigcusDeliveryAddressEntity eachEntity:resultList){//循环封装，是否叶子节点
				String childCode=this.queryMaxCodeChildRegions(eachEntity.getCode());
				if(StringUtil.isEmpty(childCode)){
					eachEntity.setIsLeaf("Y");
				}else{
					eachEntity.setIsLeaf("N");
				}
			}
		}
		//封装派送营业部名称
		attachName(resultList);
		return resultList;
	}
	/** 
	 * <p>分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2016-5-28 上午10:58:52
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	public long queryCount(BigcusDeliveryAddressEntity entity) {
		// 若实体对象为空，实例化一个对象
		if (null == entity) {
			entity = new BigcusDeliveryAddressEntity();
		}
		return bigcusDeliveryAddressDao.queryBigcusDeliveryAddressCount(entity);
	}
	/** 
	 * <p>根据编码查询区域实体，封装叶子节点、派送营业部名称</p> 
	 * @author 232607 
	 * @date 2016-3-31 下午2:57:35
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryBigcusDeliveryAddressByCode(java.lang.String)
	 */
	@Override
	public BigcusDeliveryAddressEntity queryBigcusDeliveryAddressByCode(String code) {
		//非空验证
		if(StringUtils.isBlank(code)){
			throw new BusinessException("传入参数为空");
		}
		BigcusDeliveryAddressEntity entity =bigcusDeliveryAddressDao.queryBigcusDeliveryAddressEntityByCode(code);
		//封装是否叶子节点
		if(entity!=null){                           
			String childCode=this.queryMaxCodeChildRegions(code);
			if(StringUtil.isEmpty(childCode)){
				entity.setIsLeaf("Y");
			}else{
				entity.setIsLeaf("N");
			}
		}
		//封装派送营业部名称
		this.attachName(entity);
		return entity;
	}

	/** 
	 * <p>查询根节点</p> 
	 * @author 232607 
	 * @date 2016-5-28 上午10:59:20
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryRoot()
	 */
	@Override
	public List<BigcusDeliveryAddressEntity> queryRoot() {
		return bigcusDeliveryAddressDao.queryRoot();
	}
	/** 
	 * 查询该区域的下级区域
	 * @author 232607 
	 * @date 2016-5-27 下午6:23:27
	 * @param parentDistrictCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryByParentDistrictCode(java.lang.String)
	 */
	@Override
	public List<BigcusDeliveryAddressEntity> queryByParentDistrictCode(
			String parentDistrictCode) {
		//非空验证
		if(StringUtils.isBlank(parentDistrictCode)){
			throw new BusinessException("传入的上级行政区域编码为空！");
		}
		//查询
		List<BigcusDeliveryAddressEntity> resultList=bigcusDeliveryAddressDao.queryBigcusDeliveryAddressByParentDistrictCode(parentDistrictCode);
		if(CollectionUtils.isNotEmpty(resultList)){
			for(BigcusDeliveryAddressEntity entity:resultList){//循环封装，是否叶子节点
				String childCode=this.queryMaxCodeChildRegions(entity.getCode());
				if(StringUtil.isEmpty(childCode)){
					entity.setIsLeaf("Y");
				}else{
					entity.setIsLeaf("N");
				}
			}
		}
		return resultList;
	}
	/** 
	 * <p>根据上级编码，查询子节点中的最大编码，用于判断是否为叶子节点</p> 
	 * @author 232607 
	 * @date 2016-5-28 上午11:00:18
	 * @param parentDistrictCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#queryMaxCodeChildRegions(java.lang.String)
	 */
	@Override
	public String queryMaxCodeChildRegions(
			String parentDistrictCode) {
		if(StringUtils.isBlank(parentDistrictCode)){
			throw new BusinessException("传入的上级行政区域编码为空！");
		}
		return bigcusDeliveryAddressDao.queryMaxCodeChildRegions(parentDistrictCode);
	}
	/**
	 * <p>封装派送营业部名称，参数为集合</p> 
	 * @author 232607 
	 * @date 2016-3-28 上午8:25:04
	 * @param list
	 * @return
	 * @see
	 */
	private List<BigcusDeliveryAddressEntity> attachName(
			List<BigcusDeliveryAddressEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			return list;
		}
		for (BigcusDeliveryAddressEntity bigcusDeliveryAddressEntity : list) {
			this.attachName(bigcusDeliveryAddressEntity);
		}
		return list;
	}

	/**
	 * <p>封装派送营业部名称，参数为实体</p> 
	 * @author 232607 
	 * @date 2016-3-28 上午8:28:27
	 * @param entity
	 * @return
	 * @see
	 */
	private BigcusDeliveryAddressEntity attachName(
			BigcusDeliveryAddressEntity entity) {
		// 若实体为空或者派送营业部编码为空，返回实体
		if (null == entity
				|| StringUtils.isBlank(entity.getDeliverySalesDeptCode())) {
			return entity;
		}
		//先通过组织表来查名称
		String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(entity
						.getDeliverySalesDeptCode());
		if(StringUtils.isNotEmpty(orgName)){
			entity.setDeliverySalesDeptName(orgName);
		}else{
			//如果组织查不到，再当做偏线代理网点来查名称（前台新增时可以选择营业部+偏线代理网点）
			OuterBranchEntity pxEntity= vehicleAgencyDeptService.queryOuterBranchByBranchCode(entity
					.getDeliverySalesDeptCode(),"PX");
			if(pxEntity!=null){
				entity.setDeliverySalesDeptName(pxEntity.getAgentDeptName());
			}
		}
		return entity;
	}
	/** 
	 * <p>作废行政区域，连带作废零担大客户派送地址库</p> 
	 * @author 232607 
	 * @date 2016-4-6 下午6:19:03
	 * @param codes
	 * @param deleteUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#SyncDeleteBigcusDeliveryAddress(java.lang.String[], java.lang.String)
	 */
	@Override
	public int deleteBigcusFromAdRegion(String[] codes) {
		if(codes==null||codes.length ==0){
			throw new BusinessException("传入的参数为空，作废失败");
		}
		int num=bigcusDeliveryAddressDao.deleteRegionsByCodes(codes);
		List<BigcusDeliveryAddressEntity> sendEntitys=new ArrayList<BigcusDeliveryAddressEntity>();
		for(String code:codes){
			BigcusDeliveryAddressEntity send=new BigcusDeliveryAddressEntity();
			send.setCode(code);
			send.setActive("N");
			send.setCreateDate(new Date());
			sendEntitys.add(send);
		}
		//推给OMS
		syncBigcusDeliveryAddressService.syncBigcusDeliveryAddressToOMS(sendEntitys);
		return num;
	}
	


	/**
	 * <p>查询该节点的所有下级节点</p> 
	 * @author 232607 
	 * @date 2016-5-30 上午8:46:39
	 * @param code
	 * @return
	 * @see
	 */
	private List<String> queryChildNode(String code) {
		List<String> list =new ArrayList<String>();
		List<BigcusDeliveryAddressEntity> results =queryByParentDistrictCode(code);
		//若不为空的话
		if(CollectionUtils.isNotEmpty(results)){
			for (BigcusDeliveryAddressEntity entity : results) {
				list.add(entity.getCode());
				list.addAll(queryChildNode(entity.getCode()));
			}
		}
		return list;
	}
	/**
	 * 特殊方法，用于公共选择器，
	 */
	/**
	 *<P>分页查询实体列表，加上界别节点，<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-8上午10:14:30
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<BigcusDeliveryAddressEntity> queryRegionsListAttachDegreeName(
			BigcusDeliveryAddressEntity entity, int start, int limit) {
		//查询实体列表，
		List<BigcusDeliveryAddressEntity> resultList =this.queryBigcusDeliveryAddressEntities(entity, start, limit);
		//当实体列表不为空时，
		/*if(CollectionUtils.isNotEmpty(resultList)){
			//查询数据字典的界别名称
			List<DataDictionaryValueEntity> values = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.DISTRICT_DEGREE);
			//设置操作行政级别的级别名称
			if (CollectionUtils.isNotEmpty(values)) {
				for (int i = 0; i < resultList.size(); i++) {
					for (int j = 0; j < values.size(); j++) {
						if (!StringUtil.isEmpty(resultList.get(i).getDegree()) && resultList.get(i).getDegree().equals(values.get(j).getValueCode())) {
							resultList.get(i).setDegreeName(values.get(j).getValueName());
						}
					}
				}
			}
		}*/
		return resultList;
	}
	
	/** 
	 * 修改本级派送网点类型时，连带修改上级派送网点类型，内部实现分为三步：
	 * 1、查出所有平级
	 * 2、统计平级各种类型的个数，决定上级应有的类型
	 * 3、修改上级类型入库
	 * @author 232607 
	 * @date 2016-4-6 下午2:57:38
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService#optimizeParentDeliveryNature(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	public BigcusDeliveryAddressEntity optimizeParentDeliveryNature(BigcusDeliveryAddressEntity entity){
		//实际只用的到区以下的派送属性，改街道会影响区，所以对上级区域的影响只需适用于街道
		if(!StringUtils.equals(entity.getDegree(), DictionaryValueConstants.TOWN_STREET_AGENCY)){
			return null;
		}
		//1、查出所有平级
		String parentCode=entity.getParentDistrictCode();
		List<BigcusDeliveryAddressEntity> childResults = this.queryByParentDistrictCode(parentCode);
		if(CollectionUtils.isEmpty(childResults)){
			return null;
		}
		//2、统计平级各种类型的个数，决定上级应有的类型
		int natureOne=0,natureTwo=0,natureThree=0,natureFour=0;
		for(BigcusDeliveryAddressEntity child:childResults){
			if(StringUtils.equals(child.getDeliveryType(), DictionaryValueConstants.OWN_WHOLE_DELIVERY)){
				natureOne++;
			}else if(StringUtils.equals(child.getDeliveryType(), DictionaryValueConstants.PX_WHOLE_DELIVERY)){
				natureTwo++;
			}else if(StringUtils.equals(child.getDeliveryType(), DictionaryValueConstants.NO_DELIVERY)){
				natureThree++;
			}else if(StringUtils.equals(child.getDeliveryType(), DictionaryValueConstants.MUCHHIGHER_DELIVERY)){
				natureFour++;
			}
		}
		//获取父节点实体，用于修改父节点派送网点类型
		BigcusDeliveryAddressEntity parentEntity=this.queryBigcusDeliveryAddressByCode(parentCode);
		String oldType=parentEntity.getDeliveryType();
		//子节点全部为“自有●全境派送“，则父节点派送属性为”自有●全境派送“
		if(natureOne==childResults.size()||(natureOne!=0&&(natureOne+natureTwo)==childResults.size())){
			parentEntity.setDeliveryType(DictionaryValueConstants.OWN_WHOLE_DELIVERY);   
		//子节点中全部为“偏线●全境派送“，则父节点派送属性为”偏线●全境派送“
		}else if(natureTwo==childResults.size()){
			parentEntity.setDeliveryType(DictionaryValueConstants.PX_WHOLE_DELIVERY);   
		//子节点全部为“不派送“，则父节点派送属性为”不派送“
		}else if(natureThree==childResults.size()){
			parentEntity.setDeliveryType(DictionaryValueConstants.NO_DELIVERY);   
		//子节点全部为“超远派送“，则父节点派送属性为”超远派送“
		}else if(natureFour==childResults.size()){
			parentEntity.setDeliveryType(DictionaryValueConstants.MUCHHIGHER_DELIVERY);   
		//其余情况父节点派送属性为”⊕部分派送“
		}else{
			parentEntity.setDeliveryType(DictionaryValueConstants.PART_DELIVERY);
		}
		//如果新老属性相同，则不需要修改入库，也不需要推送数据给其他系统，并提高性能
		if(StringUtils.equals(parentEntity.getDeliveryType(),oldType)){
			return null;
		}
		//3、修改上级类型入库
		parentEntity=bigcusDeliveryAddressDao.updateBigcusDeliveryAddress(parentEntity);
		return parentEntity;
	}

}
