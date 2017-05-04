package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackEntity;

/**
 * 事后折详细信息DAO
 * @author 132599
 * ShenWeiHua 2015-02-06
 */
public interface IDiscountBackInfoDao {

	/**
	 * 把从CRM系统同步过来的事后折数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2015-02-06 上午9:28:02
	 */
	int insertDiscountBackInfo(DiscountBackEntity entity);
	
	/**
	 * 把从CRM系统同步过来的事后折数据更新到FOSS表内
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2015-02-06 上午9:29:02
	 */
	int updateDiscountBackInfo(DiscountBackEntity entity);
	
	/**
	 * 根据crmId验证该信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param crmId
	 * @date 2015-02-06 下午5:09:02
	 * @return
	 */
	boolean queryDiscountBackInfoByCrmId(BigDecimal crmId);		
}
