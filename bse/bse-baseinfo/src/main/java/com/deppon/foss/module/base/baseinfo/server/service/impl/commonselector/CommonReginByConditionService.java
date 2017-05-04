package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonReginByConditionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
/**
 * 公共查询选择器--(多选)行政区域Service
 * @author 130346-foss-lifanghong
 * @date 2013-06-24 
 */
public class CommonReginByConditionService implements
		ICommonReginByConditionService {
	private IAdministrativeRegionsDao administrativeRegionsDao;
	/**
     *根据entity查询 行政区域
     * @author  130346-foss-lifanghong
     * @param 
     * @date 2013-06-24 
     * @return 
     */
	public List<AdministrativeRegionsEntity> commonReginByCondition(
			AdministrativeRegionsEntity administrativeRegionsEntity, int start,
			int limit) {
		// TODO Auto-generated method stub
		return administrativeRegionsDao.queryAdministrativeRegionsByEntity(administrativeRegionsEntity, start, limit);
	}
	 /**
     * 根据entity查询 行政区域记录总数,用于分页
     * 
     * @author 130346-foss-lifanghong
     * @date 2013-06-24 
     */
	public long queryReginCountByCondition(
			AdministrativeRegionsEntity administrativeRegionsEntity) {
		// TODO Auto-generated method stub
		return administrativeRegionsDao.queryAdministrativeRegionsByEntityCount(administrativeRegionsEntity);
	}
	public IAdministrativeRegionsDao getAdministrativeRegionsDao() {
		return administrativeRegionsDao;
	}
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}

}
