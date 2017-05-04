package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkActivitiesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 市场推广活动开展部门接口实现类Service层
 * 
 * @author 078816
 * @date 2014-04-01
 * 
 */
public class MarkActivitiesDeptService implements IMarkActivitiesDeptService {
	/**
	 * 定义日志记录
	 */
	private static final Logger log = Logger
			.getLogger(MarkActivitiesDeptService.class);

	// 活动开展部门DAO层接口
	private IMarkActivitiesDeptDao markActivitiesDeptDao;

	public IMarkActivitiesDeptDao getMarkActivitiesDeptDao() {
		return markActivitiesDeptDao;
	}

	public void setMarkActivitiesDeptDao(
			IMarkActivitiesDeptDao markActivitiesDeptDao) {
		this.markActivitiesDeptDao = markActivitiesDeptDao;
	}

	/**
	 * 新增一个活动开展部门对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int addMarkActivitiesDept(List<MarkActivitiesDeptEntity> list) {
		if (null == list) {
			throw new MarkActivitiesException("传入的活动开展部门对象为空");
		}
		int m = 0;
		m = markActivitiesDeptDao.addMarkActivitiesDept(list);
		return m;
	}

	/**
	 * 
	 * auther:wangpeng_078816 date:2014-4-8
	 * 
	 */
	@Override
	public int deleteMarkActivitiesDept(MarkActivitiesDeptEntity entity) {
		return 0;
	}

	/**
	 * 修改一个活动开展部门对象 
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int updateMarkActivitiesDept(MarkActivitiesDeptEntity entity) {
		if (null == entity) {
			throw new MarkActivitiesException("传入的活动开展部门对象为空");
		}
		int m = 0;
		String isActive = entity.getActive();
		// 如果传入的对象状态为N，就直接作废，为Y，就先作废，再新增
		if (StringUtils.equals(isActive, FossConstants.INACTIVE)) {
			entity.setActive(FossConstants.ACTIVE);
			m = markActivitiesDeptDao.updateMarkActivitiesDept(entity);
		} else if (StringUtils.equals(isActive, FossConstants.ACTIVE)) {
			markActivitiesDeptDao.updateMarkActivitiesDept(entity);
			List<MarkActivitiesDeptEntity> list = new ArrayList<MarkActivitiesDeptEntity>();
			list.add(entity);
			m = markActivitiesDeptDao.addMarkActivitiesDept(list);
		}
		return m;
	}

	/**
	 * 根据crmId查询活动开展部门是否存在 
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public boolean queryIsExistsMarkActivitiesDeptByCrmId(BigDecimal crmId) {
		// 标记该crmID对应的活动开展部门信息是否存在，默认不存在
		boolean flag = false;
		if (crmId == null) {
			throw new MarkActivitiesException("传入的crmID为空");
		}
		// 根据crmId查询是否存在活动
		MarkActivitiesDeptEntity entity = markActivitiesDeptDao
				.queryMarkActivitiesDeptByCrmId(crmId);

		if (null != entity) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	@Override
	public List<MarkActivitiesDeptEntity> queryMarkActivitiesDept(
			MarkActivitiesDeptEntity entity) {
		if(null == entity){
			
			return null;
		}
		List<MarkActivitiesDeptEntity> list = markActivitiesDeptDao.queryMarkActivitiesDept(entity);
		return list;
	}
	
	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）(根据时间建模，适用于更改单)
	 *
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	@Override
	public List<MarkActivitiesDeptEntity> queryMarkActivitiesDeptByBillTime(MarkActivitiesDeptEntity entity,Date billlingTime){
		if(null == entity || billlingTime==null){			
			return null;
		}
		List<MarkActivitiesDeptEntity> list = markActivitiesDeptDao.queryMarkActivitiesDeptByBillTime(entity,billlingTime);
		return list;
	}
	
	/**
	 * 批量修改一个活动开展部门对象 
	 * auther:shenweihua-132599
	 * date:2014-11-18
	 * 
	 */
	@Override
	public int updateMarkActivitiesDept(List<MarkActivitiesDeptEntity> list) {
		if (null == list) {
			throw new MarkActivitiesException("传入的活动开展部门对象为空");
		}
		int m=0,count=0;
		//用于记载失败的详细信息
		StringBuffer buffer = new StringBuffer();
		try{
			//循环修改活动开展部门对象
			for(MarkActivitiesDeptEntity entity:list){
				m=this.updateMarkActivitiesDept(entity);
				if(m<1){
					count++;
					buffer.append(entity.getOrgCode()).append(",").
					append(entity.getCrmId()).append(";");
					continue;
				}
			}
			if(count>0){
				throw new MarkActivitiesException("修改活动开展部门失败的条数是："+count+"具体信息是"+buffer);
			}
		}catch(BusinessException e){
			log.error(e.getMessage(), e);
			throw new MarkActivitiesException(e.getErrorCode(),e.getMessage(), e);
		}
		return m;
	}
}
