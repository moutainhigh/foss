package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FocusRecordManagementException;
/**
 * 
 * 派送兑现时效管理   Service
 * @author 273311 
 * @date 2016-8-23 下午3:36:44
 * @since
 * @version
 */
public class LclDeliveryToCashManagementDeliveryService implements
		IlclDeliveryToCashManagementDeliveryService {
	/**
	 * 
	 * <p>派送兑现时效管理     新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:37:51
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#addLclDeliveryToCashManagementDelivery(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
	 */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity addLclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		// 检查参数是否为空
		if (null == entity) {
			return null;
		}
		entity = this.attachOrg(entity);
		entity.setStartDate(this.convert(entity.getStartDate()));
		entity.setEndDate(this.convert(entity.getEndDate()));
		List<LclDeliveryToCashManagementDeliveryEntity> entityCountion = this
				.queryLclDeliveryToCashManagementDeliveryByOrgCode(entity
						.getOrgCode());
		if (entityCountion != null) {
			for (LclDeliveryToCashManagementDeliveryEntity entity1 : entityCountion) {
				if (!((entity.getStartDate().getTime() >= entity1.getEndDate()
						.getTime()) || (entity.getEndDate().getTime() <= entity1
						.getStartDate().getTime()))) {
					throw new BusinessException("同一部门时间段不能交叉出现");
				}
			}
		}

		return lclDeliveryToCashManagementDeliveryDao
				.addLclDeliveryToCashManagementDeliveryEntity(entity);
	}

	/**
	 * 
	 * <p>
	 * 派送兑现时效管理  删除
	 * </p>
	 * @author 273311
	 * @date 2016-8-23 下午3:23:27
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#deletelclDeliveryToCashManagementDelivery(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
	 */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		// 验证合法性
		if (entity == null || StringUtils.isBlank(entity.getOrgCode())) {
			return null;
		}

		return lclDeliveryToCashManagementDeliveryDao
				.deletelclDeliveryToCashManagementDelivery(entity);
	}
   /**
    * 
    * <p>派送兑现时效管理  批量删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午3:39:16
    * @param ids
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#deletelclDeliveryToCashManagementDeliveryMore(java.lang.String[])
    */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDeliveryMore(
			String[] ids) {
		// 验证合法性
		if (ids == null || ids.length == 0) {
			return null;
		}
		return lclDeliveryToCashManagementDeliveryDao
				.deletelclDeliveryToCashManagementDeliveryMore(ids);
	}
    /**
     * 
     * <p>派送兑现时效管理  更新</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:40:03
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#updatelclDeliveryToCashManagementDelivery(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
     */
	@Transactional
	@Override
	public LclDeliveryToCashManagementDeliveryEntity updatelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		if (entity == null || entity.getOrgCode() == null
				|| entity.getId() == null) {
			return null;
		}
		// 验证数据是否存在
		LclDeliveryToCashManagementDeliveryEntity updateEntity = this
				.queryLclDeliveryToCashManagementDeliveryByIdCode(
						entity.getOrgCode(), entity.getId());
		// 存在更新
		if (updateEntity != null) {
			lclDeliveryToCashManagementDeliveryDao
					.deletelclDeliveryToCashManagementDelivery(updateEntity);
		}
		return this.addLclDeliveryToCashManagementDelivery(entity);
	}
     /**
      * 
      * <p>派送兑现时效管理  查询 用于分页</p> 
      * @author 273311 
      * @date 2016-8-23 下午3:40:31
      * @param entity
      * @param start
      * @param limit
      * @return 
      * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#queryLclDeliveryToCashManagementDeliveryExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity, int, int)
      */
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryExactByEntity(
			LclDeliveryToCashManagementDeliveryEntity entity, int start,
			int limit) {
		List<LclDeliveryToCashManagementDeliveryEntity> entityResults = lclDeliveryToCashManagementDeliveryDao
				.queryLclDeliveryToCashManagementDeliveryExactByEntity(entity,
						start, limit);
		entityResults = this.attachOrg(entityResults);
		return entityResults;
	}
    /**
     * 
     * <p>派送兑现时效管理  查询条数 用于分页</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:41:43
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#queryLclDeliveryToCashManagementDeliveryExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
     */
	@Override
	public long queryLclDeliveryToCashManagementDeliveryExactByEntityCount(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		return lclDeliveryToCashManagementDeliveryDao
				.queryLclDeliveryToCashManagementDeliveryExactByEntityCount(entity);
	}
    /**
     * 
     * <p>根据code查询</p> 
     * 提供给结算使用
     * @author 273311 
     * @date 2016-8-23 下午3:42:38
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#queryLclDeliveryToCashManagementDeliveryByOrgCode(java.lang.String)
     */
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		List<LclDeliveryToCashManagementDeliveryEntity> entityResult = lclDeliveryToCashManagementDeliveryDao
				.queryLclDeliveryToCashManagementDeliveryByOrgCode(code);
		return entityResult;
	}
   /**
    * 
    * <p>根据code ID 精确查询</p> 
    * @author 273311 
    * @date 2016-8-23 下午3:43:28
    * @param code
    * @param id
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService#queryLclDeliveryToCashManagementDeliveryByIdCode(java.lang.String, java.lang.String)
    */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity queryLclDeliveryToCashManagementDeliveryByIdCode(
			String code, String id) {
		if (StringUtils.isBlank(code) || StringUtils.isBlank(id)) {
			return null;
		}
		LclDeliveryToCashManagementDeliveryEntity entityResult = lclDeliveryToCashManagementDeliveryDao
				.queryLclDeliveryToCashManagementDeliveryByIdCode(code, id);
		return entityResult;
	}

	// 一些私有方法
	/**
	 * 
	 * <p>给部门添加名字</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:44:15
	 * @param entity
	 * @return
	 * @see
	 */
	private LclDeliveryToCashManagementDeliveryEntity attachOrg(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getOrgCode())) {
			return entity;
		}
		entity.setOrgName(orgAdministrativeInfoService
				.queryOrgAdministrativeInfoNameByCode(entity.getOrgCode()));
		return entity;
	}

	/**
	 * 
	 * <p>给部门批量添加名字</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:44:54
	 * @param entitys
	 * @return
	 * @see
	 */
	private List<LclDeliveryToCashManagementDeliveryEntity> attachOrg(
			List<LclDeliveryToCashManagementDeliveryEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}
		for (LclDeliveryToCashManagementDeliveryEntity entity : entitys) {
			this.attachOrg(entity);
		}
		return entitys;
	}

	/**
	 * 时间转换
	 */
	private Date convert(Date date) {
		SimpleDateFormat sb = new SimpleDateFormat("HH:mm");
		String dateString = sb.format(date);
		try {
			Date d = sb.parse(dateString);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new FocusRecordManagementException("日期转换错误");
		}
	}
   /**
    *  get set 方法
    */
	private IlclDeliveryToCashManagementDeliveryDao lclDeliveryToCashManagementDeliveryDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setLclDeliveryToCashManagementDeliveryDao(
			IlclDeliveryToCashManagementDeliveryDao lclDeliveryToCashManagementDeliveryDao) {
		this.lclDeliveryToCashManagementDeliveryDao = lclDeliveryToCashManagementDeliveryDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}
