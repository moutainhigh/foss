package com.deppon.foss.module.pickup.pricing.api.server.dao;


import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;

/**
 * 
 * <p>
 * Description:增值服务详细信息<br />
 * </p>
 * @title IpricingValueAddedDetailDao.java
 * @package com.deppon.foss.module.pickup.pop.api.server.dao 
 * @author xx
 * @version 0.1 2014年10月11日
 */
public interface IPopValueAddedDetailDao {
	/**
	 * 
	 * <p>
	 * (根据计费过则ID查询计价方式明细列表)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceValueAddedDetailEntity> selectByValueAddedId(String valueAddId);
	/**
	 * 
	 * <p>
	 * Description:插入计费规则明细<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedDetailEntity
	 * void
	 */
	void insertSelective(PriceValueAddedDetailEntity priceValueAddedDetailEntity);
	/**
	 * 
	 * <p>
	 * Description:根据id更新增值服务明细<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedDetailEntity
	 * void
	 */
	void updateValueAddedDetailByPrimaryKey(PriceValueAddedDetailEntity priceValueAddedDetailEntity);
	/**
	 * 
	 * <p>
	 * Description:根据增值服务id修改增值服务明细<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedDetailEntity
	 * void
	 */
	void updateValueAddedDetailByValueAddedId(PriceValueAddedDetailEntity priceValueAddedDetailEntity);
	
	/**
	 * 
	 * <p>
	 * Description:删除byid<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * void
	 */
	void deleteByPrimaryKey(String id);
	/**
	 * 
	 * <p>
	 * Description:批量激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 * void
	 */
	void activeValueAddedDetailByValueAddedIds(List<String> valueAddedIds);
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 * void
	 */
	void deleteValueAddedDetailByValueAddedIds(List<String> valueAddedIds);

}
