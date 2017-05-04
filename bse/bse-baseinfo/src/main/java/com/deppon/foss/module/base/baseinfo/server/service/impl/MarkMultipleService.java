package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkMultipleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkMultipleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定义市场推广活动枚举接口类
 * 
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkMultipleService implements IMarkMultipleService {
	/**
	 * 定义日志记录
	 */
	//private static final Logger log = Logger
			//.getLogger(MarkMultipleService.class);
	
	//枚举DAO层接口类
	private IMarkMultipleDao  markMultipleDao;
	
	public IMarkMultipleDao getMarkMultipleDao() {
		return markMultipleDao;
	}

	public void setMarkMultipleDao(IMarkMultipleDao markMultipleDao) {
		this.markMultipleDao = markMultipleDao;
	}

	/**
	 * 新增一个活动枚举对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int addMarkActivitiesMultiple(List<MarkMultipleEntity> list) {
		if(null == list){
			throw new MarkActivitiesException("传入的活动枚举对象为空");
		}
		int m  = 0;
		m = markMultipleDao.addMarkActivitiesMultiple(list);
		return m;
	}

	/**
	 * 根据活动CRMID作废其关联的枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-6-27
	 *
	 */
	@Override
	public int deleteMarkActivitiesMultiple(MarkMultipleEntity entity) {
		if(null == entity){
			throw new MarkActivitiesException("传入的活动枚举对象为空");
		}
		return markMultipleDao.deleteMarkActivitiesMultiple(entity);
	}

	/**
	 * 修改一个活动枚举对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	@Transactional
	public int updateMarkActivitiesMultiple(List<MarkMultipleEntity> list,String isActive) {
		int m = 0;
		//如果状态是无效的，就作废，是有效的就插入
		if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
			m = markMultipleDao.updateMarkActivitiesMultiple(list);
		}else if(StringUtils.equals(isActive, FossConstants.ACTIVE)){
			if(!CollectionUtils.isEmpty(list)){
				
			m = markMultipleDao.addMarkActivitiesMultiple(list);
			}
		}
		return m;
	}

	/**
	 * 根据活动crmID和枚举类型查询枚举信息
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public List<MarkMultipleEntity> queryIsExistsMarkActivitiesMultiplieByCrmId(
			BigDecimal crmId, String type) {
		List<MarkMultipleEntity> list = markMultipleDao.queryMarkActivitiesMultiplieByCrmId(crmId, type);
		return list;
	}

	/**
	  * 查询活动的枚举信息（目前根据活动crmId和部门编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	@Override
	public List<MarkMultipleEntity> queryMarkActivityMultiplie(
			MarkMultipleEntity entity) {
		if(null == entity){
			
			return null;
		}
		List<MarkMultipleEntity> list = markMultipleDao.queryMarkActivityMultiplie(entity);
		return list;
	}
	
	
	/**
	 * 修改一个活动枚举对象
	 * auther:shenweihua_132599 
	 * date:2014-11-18
	 * 
	 */
	@Override
	public int updateMarkActivitiesMultiple(List<MarkMultipleEntity> list) {
		/*if (null == list) {
			throw new MarkActivitiesException("传入的活动开展部门对象为空");
		}
		String active = list.get(0).getActive();//这样写的逻辑是 不对的，目前暂时不用 故这样写，如果以后用到请根据当时的业务去写！
		return this.updateMarkActivitiesMultiple(list,active);*/
		return 0;
	}

}
