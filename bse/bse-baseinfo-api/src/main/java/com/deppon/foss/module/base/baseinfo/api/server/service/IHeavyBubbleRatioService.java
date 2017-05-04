package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;

/**
 * @author 218392 张永雪
 *
 * 2014-11-19下午3:26:48
 */
public interface IHeavyBubbleRatioService extends IService{
	/**
	 * 验证重泡比外场是否存在
	 */
	boolean queryOutfieldNameByHeavyBubbleRatio(String outfield);
	
	/**
	 * 根据外场code来查询重泡比参数
	 */
	String queryHeavyBubbleParamByOutfield(String outfield);
	
	/**
	 * 新增重泡比信息
	 */
	int addHeavyBubbleRatio(HeavyBubbleRatioEntity entity);
	
	/**
	 * 修改重泡比信息
	 */
	int updateHeavyBubbleRatio(HeavyBubbleRatioEntity entity);
	
	/**
	 * 作废重泡比信息
	 */
	int deleteHeavyBubbleRatio(List<String> idList);
	
	/**
	 * 根据传入的对象查询符合条件所有重泡比信息
	 */
	List<HeavyBubbleRatioEntity> queryAllHeavyBubbleRatio(HeavyBubbleRatioEntity entity,int limit,int start);
	
	/**
	 * 统计总记录数
	 */
	 Long queryRecordCount(HeavyBubbleRatioEntity entity);
	
}
