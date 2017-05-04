package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISatellitePartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISatellitePartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncWxdbService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SatellitePartSalesDeptException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SatellitePartSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 卫星点部与营业部关系实现接口
 * 
 * @author 130566
 * 
 */
public class SatellitePartSalesDeptService implements
		ISatellitePartSalesDeptService {

	private ISatellitePartSalesDeptDao satellitePartSalesDeptDao;
	// 组织的service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	// 同步卫星点部到crm
	private ISyncWxdbService syncWxdbService;

	public void setSatellitePartSalesDeptDao(
			ISatellitePartSalesDeptDao satellitePartSalesDeptDao) {
		this.satellitePartSalesDeptDao = satellitePartSalesDeptDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSyncWxdbService(ISyncWxdbService syncWxdbService) {
		this.syncWxdbService = syncWxdbService;
	}

	/**
	 * <P>
	 * 添加实体
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:59:46
	 * @param entity
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public SatellitePartSalesDeptEntity addSatellitePartSales(
			SatellitePartSalesDeptEntity entity) {
		// 非空检查
		if (null == entity || StringUtils.isBlank(entity.getSalesDeptCode())
				|| StringUtils.isBlank(entity.getSatelliteDeptCode())) {
			throw new SatellitePartSalesDeptException("传入的参数为空");
		}
		// 去库中查询是否已经存在该卫星点编码
		SatellitePartSalesDeptEntity resultEntity = satellitePartSalesDeptDao
				.querySatellitePartSalesbySatelliteCode(entity
						.getSatelliteDeptCode());
		if (null != resultEntity) {
			throw new SatellitePartSalesDeptException("已经存在该卫星点对应的营业部关系了，无法新增");
		}
		// 给编码加上名称
		this.attachDeptName(entity);
		// 执行新增操作
		return satellitePartSalesDeptDao.addSatellitePartSales(entity);
	}

	/**
	 * <P>
	 * 给部门设置部门名称
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午2:24:36
	 * @param entity
	 */
	private SatellitePartSalesDeptEntity attachDeptName(
			SatellitePartSalesDeptEntity entity) {
		if (null == entity) {
			return null;
		}
		// 若营业部编码不为空,设置营业部名称
		if (StringUtils.isNotBlank(entity.getSalesDeptCode())) {
			entity.setSalesDeptName(orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getSalesDeptCode()));
		}
		// 若
		if (StringUtils.isNotBlank(entity.getSatelliteDeptCode())) {
			entity.setSatelliteDeptName(orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getSatelliteDeptCode()));
		}
		return entity;
	}

	/**
	 * <P>根据id 作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午2:37:15
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public int deleteSatellitePartSalesById(SatellitePartSalesDeptEntity entity) {
		if (null == entity || StringUtils.isBlank(entity.getId())) {
			throw new SatellitePartSalesDeptException("传入的参数ID为空");
		}
		String empCode = entity.getModifyUser();
		entity.setModifyUser(null);
		//查询的时候要把修改人设置为空
		List<SatellitePartSalesDeptEntity> deptEntities = this.querySatellitePartSalesList(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
		syncWxdbService.syncWxdbToCrm(deptEntities, FossConstants.SYNCCRM_DELETE);
		entity.setModifyUser(empCode);
		return satellitePartSalesDeptDao.deleteSatellitePartSalesById(entity);
	}
	/**
	 * 
	 *<P>批量作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午6:10:32
	 * @param idList
	 * @param modifyUser
	 * @return
	 */
	@Override
	public int deleteSatellitePartSalesByIdList(List<String> idList,
			CurrentInfo modifyUser) {
		if(CollectionUtils.isEmpty(idList)||null ==modifyUser){
			throw new SatellitePartSalesDeptException("传入的参数为空");
		}
		int count =0;
		List<SatellitePartSalesDeptEntity> deptEntities = new ArrayList<SatellitePartSalesDeptEntity>();
		for (String id : idList) {
			SatellitePartSalesDeptEntity entity =new SatellitePartSalesDeptEntity();
			entity.setId(id);
			SatellitePartSalesDeptEntity deptEntity = this.querySatellitePartSalesList(entity, NumberConstants.ZERO, Integer.MAX_VALUE).get(0);
			deptEntities.add(deptEntity);
			entity.setModifyUser(modifyUser.getEmpCode());
			//作废
			count +=this.deleteSatellitePartSalesById(entity);
		}
		//3表示推送到crm为删除
		syncWxdbService.syncWxdbToCrm(deptEntities, FossConstants.SYNCCRM_DELETE);
		return count==idList.size()?NumberConstants.ONE:NumberConstants.ZERO;
	}
	/**
	 *<P>根据卫星点部编码、营业部编码作废实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:39:24
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public SatellitePartSalesDeptEntity deleteSatelliteBySatelliteCodeAndSalesCode(
			SatellitePartSalesDeptEntity entity) {
		//两个编码都不能为空
		if(null ==entity ||StringUtils.isBlank(entity.getSalesDeptCode())||StringUtils.isBlank(entity.getSatelliteDeptCode())){
			throw new SatellitePartSalesDeptException("传入的参数为空");
		}
		String empCode = entity.getModifyUser();
		entity.setModifyUser(null);
		List<SatellitePartSalesDeptEntity> deptEntities = satellitePartSalesDeptDao.querySatellitePartSalesList(entity, 
				NumberConstants.ZERO, Integer.MAX_VALUE);
		syncWxdbService.syncWxdbToCrm(deptEntities, FossConstants.SYNCCRM_DELETE);
		entity.setModifyUser(empCode);
		return satellitePartSalesDeptDao.deleteSatellitePartSales(entity);
	}
	
	/**
	 * <P>
	 * 分页查询
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午2:54:26
	 * @param entity
	 * @param limit
	 * @return
	 */
	@Override
	public List<SatellitePartSalesDeptEntity> querySatellitePartSalesList(
			SatellitePartSalesDeptEntity entity, int start, int limit) {
		if(null == entity){
			entity =new SatellitePartSalesDeptEntity();
		}
		return satellitePartSalesDeptDao.querySatellitePartSalesList(entity,
				start, limit);
	}

	/**
	 * <P>
	 * 查询总数
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午2:59:09
	 * @param entity
	 * @return
	 */
	@Override
	public long querySatellitePartSalesCount(SatellitePartSalesDeptEntity entity) {
		return satellitePartSalesDeptDao.querySatellitePartSalesCount(entity);
	}

	/**
	 * <P>
	 * 根据营业部编码查询实体列表
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午3:00:39
	 * @param entity
	 * @return
	 */
	@Override
	public List<SatellitePartSalesDeptEntity> querySatellitepartSalesBySalesCode(
			String salesDeptcode) {
		if (StringUtils.isBlank(salesDeptcode)) {
			throw new SatellitePartSalesDeptException("营业部编码为空");
		}
		return satellitePartSalesDeptDao
				.querySatellitePartSalesListbySalesCode(salesDeptcode);
	}

	/**
	 * <P>
	 * 根据卫星点部编码查询
	 * <P>
	 * 
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午3:05:25
	 * @param satelliteDeptCode
	 * @return
	 */
	@Override
	public SatellitePartSalesDeptEntity querySatellitePartsalesBySatelliteCode(
			String satelliteDeptCode) {
		if (StringUtils.isBlank(satelliteDeptCode)) {
			throw new SatellitePartSalesDeptException("卫星点部编码为空");
		}
		return satellitePartSalesDeptDao
				.querySatellitePartSalesbySatelliteCode(satelliteDeptCode);
	}
	/**
	 *<P>新增实体列表<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午7:10:22
	 * @param addDeptEntities
	 * @param currentInfo
	 */
	@Override
	public List<SatellitePartSalesDeptEntity> addSatellitePartSalesList(
			List<SatellitePartSalesDeptEntity> addDeptEntities,
			String modifyUser) {
		if(CollectionUtils.isEmpty(addDeptEntities)){
			throw new SatellitePartSalesDeptException("新增实体列表为空");
		}
		List<SatellitePartSalesDeptEntity> resultList =new ArrayList<SatellitePartSalesDeptEntity>();
		for (SatellitePartSalesDeptEntity satellitePartSalesDeptEntity : addDeptEntities) {
			satellitePartSalesDeptEntity.setCreateUser(modifyUser);
			//执行新增操作
			resultList.add(this.addSatellitePartSales(satellitePartSalesDeptEntity));
		}
		syncWxdbService.syncWxdbToCrm(addDeptEntities,FossConstants.SYNCCRM_ADD);
		return resultList;
	}
	/**
	 *<P>修改映射关系列表<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:14:39
	 * @param vo
	 * @param modifyUser
	 * @return
	 */
	@Transactional
	@Override
	public List<SatellitePartSalesDeptEntity> updateSatellitePartSalesList(
			SatellitePartSalesDeptVo vo, String modifyUser) {
		if(null ==vo){
			return null;
		}
		List<SatellitePartSalesDeptEntity> resultList =new ArrayList<SatellitePartSalesDeptEntity>();
		//先新增
		//若新增列表不为空
		if(CollectionUtils.isNotEmpty(vo.getAddSatellitePartSalesList())){
			resultList.addAll(this.addSatellitePartSalesList(vo.getAddSatellitePartSalesList(), modifyUser));
		}
		//再作废（作废列表）
		if(CollectionUtils.isNotEmpty(vo.getDeleteSatellitePartSalesList())){
			//循环作废
			for (SatellitePartSalesDeptEntity deleteEntity : vo.getDeleteSatellitePartSalesList()) {
				deleteEntity.setModifyUser(modifyUser);
				resultList.add(this.deleteSatelliteBySatelliteCodeAndSalesCode(deleteEntity));
			}
		}
		return resultList;
	}
	//*******提供给行政组织中使用的*********
	/**
	 *<P>根据卫星点编码作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-8下午3:38:31
	 * @param entity
	 * @return
	 */
	@Override
	public SatellitePartSalesDeptEntity deleteSatelliteBySatelliteCode(
			SatellitePartSalesDeptEntity entity) {
		//卫星点编码都不能为空
		if(null ==entity||StringUtils.isBlank(entity.getSatelliteDeptCode())){
			throw new SatellitePartSalesDeptException("传入的参数为空");
		}
		return satellitePartSalesDeptDao.deleteSatellitePartSales(entity);
	}
	/**
	 *<P>根据营业部编码作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-8下午3:38:31
	 * @param entity
	 * @return
	 */
	@Override
	public SatellitePartSalesDeptEntity deleteSatelliteBySalesCode(
			SatellitePartSalesDeptEntity entity) {
		//营业部编码都不能为空
		if(null ==entity ||StringUtils.isBlank(entity.getSalesDeptCode())){
			throw new SatellitePartSalesDeptException("传入的参数为空");
		}
		return satellitePartSalesDeptDao.deleteSatellitePartSales(entity);
	}
}
