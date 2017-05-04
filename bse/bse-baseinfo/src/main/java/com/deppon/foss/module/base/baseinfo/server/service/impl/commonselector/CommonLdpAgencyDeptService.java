package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 快递代理网点公共选择器Serivce层
 * 
 * @author WangPeng
 * @date 2013-07-25 10:13 AM
 * 
 */
public class CommonLdpAgencyDeptService implements ICommonLdpAgencyDeptService {

	// 注入快递代理网点公共选择器Dao
	private ICommonLdpAgencyDeptDao commonLdpAgencyDeptDao;
	// 注入组织区域Service
	private IAdministrativeRegionsService administrativeRegionsService;

	/**
	 * 返回查询列表
	 * 
	 * @author WangPeng
	 * @Date 2013-7-25 上午10:14:35
	 * @param entity
	 * @param limit
	 * @param start
	 * @return List
	 * 
	 */
	@Override
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByCondtion(
			OuterBranchExpressEntity entity, int limit, int start) {
		if (null == entity) {
			entity = new OuterBranchExpressEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		List<OuterBranchExpressEntity> outerBranchLdpList = commonLdpAgencyDeptDao
				.queryLdpAgencyDeptsByCondtion(entity, limit, start);
		if (CollectionUtils.isNotEmpty(outerBranchLdpList)) {
			for (OuterBranchExpressEntity outEntity : outerBranchLdpList) {
				outEntity.setProvName(administrativeRegionsService
						.gainDistrictNameByCode(outEntity.getProvCode()));
				outEntity.setCityName(administrativeRegionsService
						.gainDistrictNameByCode(outEntity.getCityCode()));
				outEntity.setCountyName(administrativeRegionsService
						.gainDistrictNameByCode(outEntity.getCountyCode()));
			}
		}
		return outerBranchLdpList;
	}

	/**
	 * 记录查询行数
	 * 
	 * @author WangPeng
	 * @Date 2013-7-25 上午10:14:42
	 * @param entity
	 * @return Long
	 * 
	 */
	@Override
	public Long countRecordByCondition(OuterBranchExpressEntity entity) {
		if (null == entity) {
			entity = new OuterBranchExpressEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		return commonLdpAgencyDeptDao.countRecordByCondition(entity);
	}

	public void setCommonLdpAgencyDeptDao(
			ICommonLdpAgencyDeptDao commonLdpAgencyDeptDao) {
		this.commonLdpAgencyDeptDao = commonLdpAgencyDeptDao;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
}
