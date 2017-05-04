package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

public interface IMinFeePlanService extends IService {

	/**
	 * 
	 * 添加自提价最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午11:43:59
	 */
	public int addMinFeePlan(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 
	 * 根据指定条件分页查询最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 上午11:44:21
	 */
	public List<MinFeePlanEntity> getMinFeePlanEntityByCondition(MinFeePlanEntity minFeePlanEntity, int start, int limit);

	/**
	 * 
	 * 根据指定条件查询记录总数
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 下午3:30:18
	 */
	public Long countMinFeePlanByCondition(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 根据id删除最低一票方案
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 上午8:32:13
	 */
	public void deleteMinFeePlan(List<String> idList);

	/**
	 * 
	 * 根据id查询最低一票记录
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 上午10:54:13
	 */
	public MinFeePlanEntity getMinFeePlanEntityById(String id);

	/**
	 * 更新记录
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 上午11:26:20
	 */
	public void updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 
	 * 查询指定日期的已激活的最低一票方案对应的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 下午3:27:00
	 */
	public List<MinFeePlanEntity> getMinFeePlanBySpecifiedDate(Date businessDate);

	/**
	 * 
	 * 查询指定渠道编码和日期的已激活的最低一票方案的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 下午3:37:44
	 */
	public List<ProductEntity> getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(String channelCode, Date businessDate);

	/**
	 * 
	 * 查询指定渠道编码、产品编码和日期的已激活的最低一票方案的产品，如果指定日期为空，则默认为服务器当前日期
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-15 下午3:40:14
	 */
	public ProductEntity getProductOfMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(String channelCode, String productCode, Date businessDate);
	
	/**
	 * 查询指定渠道编码、产品编码和日期的已激活的最低一票方案，如果指定日期为空，则默认为服务器当前日期
	 * @param channelCode
	 * @param productCode
	 * @param businessDate
	 * @return
	 */
	public MinFeePlanEntity getMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(String channelCode, String productCode, Date businessDate);
	
	/**
	 * 立即激活最低一票方案
	 * @param id
	 */
	public void activateImmediatelyMinFeePlan(String id, Date newBeginTime);
	
	/**
	 * 立即中止最低一票方案
	 * @param id
	 * @param newEndTime
	 */
	public void stopImmediatellyMinFeePlan(String id, Date newEndTime);
	
	/**
	 * 查询3级产品信息
	 * @return
	 */
	public List<ProductEntity> findProductByCondition();

}
