package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkLineDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkLineDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkLineDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 市场推广活动线路（出发到达部门）接口实现类Service
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkLineDeptService implements IMarkLineDeptService {
	
	/**
	 * 定义日志记录
	 */
	private static final Logger log = Logger
			.getLogger(MarkLineDeptService.class);

	//市场推广活动线路DAO层接口
	private IMarkLineDeptDao markLineDeptDao;
	
	public IMarkLineDeptDao getMarkLineDeptDao() {
		return markLineDeptDao;
	}

	public void setMarkLineDeptDao(IMarkLineDeptDao markLineDeptDao) {
		this.markLineDeptDao = markLineDeptDao;
	}

	/**
	 * 新增一个活动线路信息对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int addMarkActivitiesLineDept(List<MarkLineDeptEntity> list) {
		if(null == list){
			throw new MarkActivitiesException("传入的开展部门对象为空");
		}
		int m = 0;
		m = markLineDeptDao.addMarkActivitiesLineDept(list);
		return m;
	}

	@Override
	public int deleteMarkActivitiesLineDept(MarkLineDeptEntity entity) {
		return 0;
	}

	/**
	 * 修改一个活动线路信息对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int updateMarkActivitiesLineDept(MarkLineDeptEntity entity) {
		int m = 0;
		String isActive  = entity.getActive();
		//如果传入的对象状态为N，就直接作废，为Y，就先作废，再新增
		if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
			entity.setActive(FossConstants.ACTIVE);
			m = markLineDeptDao.updateMarkActivitiesLineDept(entity);
		}else if(StringUtils.equals(isActive, FossConstants.ACTIVE)){
			markLineDeptDao.updateMarkActivitiesLineDept(entity);	
			List<MarkLineDeptEntity> list = new ArrayList<MarkLineDeptEntity>();
			list.add(entity);
			m = markLineDeptDao.addMarkActivitiesLineDept(list);
		}
		return m;
	}

	/**
	 * 根据crmID查询线路信息是否存在
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public boolean queryIsExistsMarkActivitiesLineDeptByCrmId(BigDecimal crmId) {
		// 标记该crmID对应的线路信息信息是否存在，默认不存在
		boolean flag = false;
		if (crmId == null) {
			throw new MarkActivitiesException("传入的crmID为空");
		}
		// 根据crmId查询是否存在活动
		MarkLineDeptEntity entity = markLineDeptDao
				.queryMarkActivitiesLineDeptByCrmId(crmId);

		if (null != entity) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查询活动的线路部门信息（目前根据活动crmId和部门编码查询）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	@Override
	public List<MarkLineDeptEntity> queryMarkActivityLineDept(
			MarkLineDeptEntity entity) {
		if(null == entity){
			
			return null;
		}
		List<MarkLineDeptEntity> list = markLineDeptDao.queryMarkActivityLineDept(entity);
		return list;
	}
	
	/**
	 * 批量修改活动线路（出发到达部门）信息
	 *
	 * auther:shenweihua-132599
	 * date:2014-11-18
	 *
	 */
	@Override
	public int updateMarkActivitiesLineDept(List<MarkLineDeptEntity> list) {
		if (null == list) {
			throw new MarkActivitiesException("传入的活动线路信息为空");
		}
		int m=0,count=0;
		//用于记载失败的详细信息
		StringBuffer buffer = new StringBuffer();
		try{
			//循环修改活动开展部门对象
			for(MarkLineDeptEntity entity:list){
				m=this.updateMarkActivitiesLineDept(entity);
				if(m<1){
					count++;
					buffer.append(entity.getOrigOrgName()).append(",").
					append(entity.getDestOrgName()).append(",").
					append(entity.getCrmId()).append(";");
					continue;
				}
			}
			if(count>0){
				throw new MarkActivitiesException("修改活动线路信息失败的条数是："+count+"具体信息是"+buffer);
			}
		}catch(BusinessException e){
			log.error(e.getMessage(), e);
			throw new MarkActivitiesException(e.getErrorCode(),e.getMessage(), e);
		}
		return m;
	}

}
