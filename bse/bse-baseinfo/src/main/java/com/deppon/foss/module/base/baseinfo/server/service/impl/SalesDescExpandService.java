package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgAdministrativeInfoException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 营业部自提派送区域描述扩展类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2013-6-20 下午4:03:35
 */
public class SalesDescExpandService implements ISalesDescExpandService {

	@Inject
	private ISalesDescExpandDao salesDescExpandDao;
	
	/**
	 * 同步营业部自提派送区域描述扩给周边系统，订单，快递等
	 */
	/*private ISyncSalesDescExpandService syncSalesDescExpandService;

	public void setSyncSalesDescExpandService(
			ISyncSalesDescExpandService syncSalesDescExpandService) {
		this.syncSalesDescExpandService = syncSalesDescExpandService;
	}*/

	/**
	 * 
	 * 批量新增
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-6-21 下午6:49:12
	 */
	public void addSalesDescExpandByType(String[] list,
			SalesDescExpandEntity entity) {
		for (int i = 0; i < list.length; i++) {
			SalesDescExpandEntity entityNew = new SalesDescExpandEntity();
			//entityNew = entity;
			entityNew.setDescOrder((short) i);
			entityNew.setDescContent(list[i]);
			entityNew.setCode(entity.getCode());
			entityNew.setCreateUser(entity.getCreateUser());
			entityNew.setCreateUser(entity.getModifyUser());
			//addSalesDescExpand(entity);
			/*if (null == entity) {
				throw new OrgAdministrativeInfoException("",	"插入营业部自提派送区域描述时，组织编码为空！");
			}*/
			// 判断组织编码是否为空
			if (StringUtil.isEmpty(entityNew.getCode())) {
				throw new OrgAdministrativeInfoException("",
						"插入营业部自提派送区域描述时，组织编码为空！");
			}
			// 判断扩展类型是否为空
			entityNew.setDescType(entity.getDescType());
			if (StringUtil.isEmpty(entityNew.getDescType())) {
				throw new OrgAdministrativeInfoException("",
						"插入营业部自提派送区域描述时，扩展类型为空！");
			}
			// 判断扩展内容是否为空
			if (StringUtil.isEmpty(entityNew.getDescContent())) {
				throw new OrgAdministrativeInfoException("", "插入营业部自提派送区域描述时，内容为空！");
			}
			Date now = new Date();
			entityNew.setId(UUIDUtils.getUUID());
			entityNew.setActive(FossConstants.ACTIVE);
			entityNew.setCreateDate(now);
			// ModifyDate为2999年，为一个常量
			entityNew.setModifyDate(new Date(NumberConstants.ENDTIME));
			entityNew.setVersionNo(now.getTime());
			salesDescExpandDao.addSalesDescExpand(entityNew);
			//addSalesDescExpand(entity);
		}
		//Collections.addAll(synEntity, synEntitys);
		//l.asynEntity
		//syncSalesDescExpandService.syncSalesDescExpand(synEntity, "1");
		//return synEntity;
	}

	/**
	 * 
	 * 新增
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-6-20 下午5:04:18
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService#addSalesDescExpand(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity)
	 */
	@Override
	public int addSalesDescExpand(SalesDescExpandEntity entity) {
	
		if (null == entity) {
			return 0;
		}
		// 判断组织编码是否为空
		if (StringUtil.isEmpty(entity.getCode())) {
			throw new OrgAdministrativeInfoException("","插入营业部自提派送区域描述时，组织编码为空！");
		}
		// 判断扩展类型是否为空
		if (StringUtil.isEmpty(entity.getDescType())) {
			throw new OrgAdministrativeInfoException("","插入营业部自提派送区域描述时，扩展类型为空！");
		}
		// 判断扩展内容是否为空
		if (StringUtil.isEmpty(entity.getDescContent())) {
			throw new OrgAdministrativeInfoException("", "插入营业部自提派送区域描述时，内容为空！");
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setVersionNo(now.getTime());
		int addEntity = salesDescExpandDao
				.addSalesDescExpand(entity);
/*		if (0 != addEntity) {
			 调用同步接口给周边系统 1新增，2更新
			syncSalesDescExpandAround(entity, "1");
		}*/
		return addEntity;
	}

	/**
	 * 
	 * 作废
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-6-20 下午5:04:18
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService#addSalesDescExpand(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity)
	 */
	@Override
	public int updateSalesDescExpand(
			SalesDescExpandEntity entity) {
		// 作废时，组织编码不能为空
		// 判断组织编码是否为空
		if (StringUtil.isEmpty(entity.getCode())) {
			throw new OrgAdministrativeInfoException("",
					"删除营业部自提派送区域描述时，组织编码为空！");
		}
		// 只作废有效的数据
		Date now = new Date();
		//订单系统需要创建时间
		entity.setCreateDate(now);
		entity.setActive(FossConstants.INACTIVE);
		entity.setContionActive(FossConstants.ACTIVE);
		entity.setVersionNo(new Date().getTime()); // zxy 20140327 删除时候增加版本时间
		int addEntity = salesDescExpandDao.updateSalesDescExpand(entity);
		/*if (0 != addEntity) {
			// 调用同步接口给周边系统 1新增，2更新
			syncSalesDescExpandAround(entity, "2");
		}*/
		return addEntity;
	}
	/**
	 * 
	 * 重载新增方法，供oms用
	 * 
	 * @author 273311
	 * @date 2016-6-12
	 */
	@Override
	public void addSalesDescExpandByType(List<SalesDescExpandEntity> salesDescExpandEntitys,String[] list,SalesDescExpandEntity entity) {
		for (int i = 0; i < list.length; i++) {
			SalesDescExpandEntity entityNew = new SalesDescExpandEntity();
			//entityNew = entity;
			entityNew.setDescOrder((short) i);
			entityNew.setDescContent(list[i]);
			entityNew.setCode(entity.getCode());
			entityNew.setCreateUser(entity.getCreateUser());
			entityNew.setCreateUser(entity.getModifyUser());
			//addSalesDescExpand(entity);
			/*if (null == entity) {
				throw new OrgAdministrativeInfoException("",	"插入营业部自提派送区域描述时，组织编码为空！");
			}*/
			// 判断组织编码是否为空
			if (StringUtil.isEmpty(entityNew.getCode())) {
				throw new OrgAdministrativeInfoException("",
						"插入营业部自提派送区域描述时，组织编码为空！");
			}
			// 判断扩展类型是否为空
			entityNew.setDescType(entity.getDescType());
			if (StringUtil.isEmpty(entityNew.getDescType())) {
				throw new OrgAdministrativeInfoException("","插入营业部自提派送区域描述时，扩展类型为空！");
			}
			// 判断扩展内容是否为空
			if (StringUtil.isEmpty(entityNew.getDescContent())) {
				throw new OrgAdministrativeInfoException("", "插入营业部自提派送区域描述时，内容为空！");
			}
			Date now = new Date();
			entityNew.setId(UUIDUtils.getUUID());
			entityNew.setActive(FossConstants.ACTIVE);
			entityNew.setCreateDate(now);
			// ModifyDate为2999年，为一个常量
			entityNew.setModifyDate(new Date(NumberConstants.ENDTIME));
			entityNew.setVersionNo(now.getTime());
			salesDescExpandDao.addSalesDescExpand(entityNew);
			//同步所需数据
			salesDescExpandEntitys.add(entityNew);
		}
	}
	/**
	 * 
	 * 重载修改方法，供oms用
	 * 
	 * @author 273311
	 * @date 2016-6-12
	 */
	@Override
	public void  updateSalesDescExpand(List<SalesDescExpandEntity> salesDescExpandEntity,SalesDescExpandEntity entity) {
		// 作废时，组织编码不能为空
		// 判断组织编码是否为空
		if (StringUtil.isEmpty(entity.getCode())) {
			throw new OrgAdministrativeInfoException("","删除营业部自提派送区域描述时，组织编码为空！");
		}
		// 只作废有效的数据
		Date now = new Date();
		//订单系统需要创建时间
		entity.setCreateDate(now);
		entity.setActive(FossConstants.INACTIVE);
		entity.setContionActive(FossConstants.ACTIVE);
		entity.setVersionNo(new Date().getTime()); // zxy 20140327 删除时候增加版本时间
		salesDescExpandDao.updateSalesDescExpand(entity);
		//同步所需数据
		salesDescExpandEntity.add(entity);
	}
    /**
     * @author 273311
     * @date 2016-3-25 
     * @param entity
     * @param operateType
     */
	/*private void syncSalesDescExpandAround(SalesDescExpandEntity entity,
			String operateType) {
		List<SalesDescExpandEntity> synEntity = new ArrayList<SalesDescExpandEntity>();
		synEntity.add(entity);
		syncSalesDescExpandService.syncSalesDescExpand(synEntity, operateType);
	}*/

	/**
	 * 
	 * 根据组织编码查询有效记录
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-6-20 下午5:21:04
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService#querySalesDescExpandByCode(java.lang.String)
	 */
	@Override
	public List<SalesDescExpandEntity> querySalesDescExpandByCode(String code,
			String type) {
		// 判断组织编码是否为空
		if (StringUtil.isEmpty(code)) {
			throw new OrgAdministrativeInfoException("",
					"查询营业部自提派送区域描述时，组织编码为空！");
		}
		String active = FossConstants.ACTIVE;
		return salesDescExpandDao
				.querySalesDescExpandByCode(code, active, type);
	}

	@Override
	public List<SalesDescExpandEntity> querySalesDescExpandForDownloadByPage(
			SalesDescExpandEntity entity, int start, int lmited) {
		return salesDescExpandDao.querySalesDescExpandForDownloadByPage(entity,
				start, lmited);
	}

	public void setSalesDescExpandDao(ISalesDescExpandDao salesDescExpandDao) {
		this.salesDescExpandDao = salesDescExpandDao;
	}

}
