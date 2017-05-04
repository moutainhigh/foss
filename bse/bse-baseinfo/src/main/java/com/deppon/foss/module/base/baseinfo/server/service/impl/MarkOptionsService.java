package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkOptionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkOptionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 市场推广活动多选接口实现类Service层
 * 价格折扣信息
 * @author 078816
 * @date   2014-03-31
 *
 */
public class MarkOptionsService implements IMarkOptionsService {
	/**
	 * 定义日志记录
	 */
	private static final Logger log = Logger
			.getLogger(MarkOptionsService.class);

	//活动价格折扣DAO层接口
	private IMarkOptionsDao markOptionsDao;
	
	public IMarkOptionsDao getMarkOptionsDao() {
		return markOptionsDao;
	}

	public void setMarkOptionsDao(IMarkOptionsDao markOptionsDao) {
		this.markOptionsDao = markOptionsDao;
	}

	/**
	 *新增一条活动价格折扣信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	@Transactional
	public int addMarkActivitiesOptions(List<MarkOptionsEntity> list) {
		if(null == list){
			throw new MarkActivitiesException("传入的折扣信息为空");
		}
		int m = 0;
		m = markOptionsDao.addMarkActivitiesOptions(list);
		return m;
	}

	@Override
	public int deleteMarkActivitiesOptions(MarkOptionsEntity entity) {
		return 0;
	}

	/**
	 *修改一条活动价格折扣信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	@Transactional
	public int updateMarkActivitiesOptions(MarkOptionsEntity entity) {
		if(null == entity){
			throw new MarkActivitiesException("传入的折扣信息为空");
		}
		int m = 0;
		String isActive  = entity.getActive();
		//如果传入的对象状态为N，就直接作废，为Y，就先作废，再新增
		if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
			entity.setActive(FossConstants.ACTIVE);
			m = markOptionsDao.updateMarkActivitiesOptions(entity);
		}else if(StringUtils.equals(isActive, FossConstants.ACTIVE)){
			markOptionsDao.updateMarkActivitiesOptions(entity);
			List<MarkOptionsEntity> list = new ArrayList<MarkOptionsEntity>();
			list.add(entity);
			m = markOptionsDao.addMarkActivitiesOptions(list);
		}
		return m;
	}

	/**
	 *根据价格折扣的crmID查询该记录是否存在
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public boolean queryIsExistsMarkActivitiesOptionsByCrmId(BigDecimal crmId) {
		// 标记该crmID对应的活动的折扣是否存在，默认不存在
		boolean flag = false;
		if (crmId == null) {
			throw new MarkActivitiesException("传入的crmID为空");
		}
		// 根据crmId查询是否存在活动的折扣信息
		MarkOptionsEntity entity = markOptionsDao
				.queryMarkActivitiesOptionsByCrmId(crmId);

		if (null != entity) {
			flag = true;
		}
		return flag;
	}

	/**
	  * 查询活动的折扣信息（目前根据活动crmId和部门编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	@Override
	public List<MarkOptionsEntity> queryMarkActivityOptions(
			MarkOptionsEntity entity) {
		if(null == entity){
			
			return null;
		}
		List<MarkOptionsEntity> list = markOptionsDao.queryMarkActivityOptions(entity);
		return list;
	}

	@Override
	public MarkOptionsEntity queryMarkActivitiesOptionsByCrmId(BigDecimal crmId) {
		if (crmId == null) {
			throw new MarkActivitiesException("传入的crmID为空");
		}
		// 根据crmId查询是否存在活动的折扣信息
		MarkOptionsEntity entity = markOptionsDao
				.queryMarkActivitiesOptionsByCrmId(crmId);
		return entity;
	}
	
	/**
	 * 查询活动的折扣信息（根据时间建模查询，适用于更改单）
	 * 折扣信息
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	@Override
	public List<MarkOptionsEntity> queryMarkActivityOptionsByBillTime(MarkOptionsEntity entity,Date billlingTime){
		if (entity == null || billlingTime==null) {
			return null;
		}
		List<MarkOptionsEntity> list = markOptionsDao.queryMarkActivityOptionsByBillTime(entity,billlingTime);
		return list;
	}
	
	/**
	 * 批量修改活动价格折扣信息
	 * auther:shenweihua_132599
	 * date:2014-11-18
	 *
	 */
	@Override
	public int updateMarkActivitiesOptions(List<MarkOptionsEntity> list) {
		if (null == list) {
			throw new MarkActivitiesException("传入的活动价格折扣信息为空");
		}
		int m=0,count=0;
		//用于记载失败的详细信息
		StringBuffer buffer = new StringBuffer();
		try{
			//循环修改活动开展部门对象
			for(MarkOptionsEntity entity:list){
				m=this.updateMarkActivitiesOptions(entity);
				if(m<1){
					count++;
					buffer.append(entity.getName()).append(",").
					append(entity.getCrmId()).append(";");
					continue;
				}
			}
			if(count>0){
				throw new MarkActivitiesException("修改价格折扣信息失败的条数是："+count+"具体信息是"+buffer);
			}
		}catch(BusinessException e){
			log.error(e.getMessage(), e);
			throw new MarkActivitiesException(e.getErrorCode(),e.getMessage(), e);
		}
		return m;
	}

}
