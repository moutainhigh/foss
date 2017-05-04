package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDiscountBackItemInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDiscountBackItemInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackItemEntity;
/**
 * 事后折详细信息service实现类
 * @author 132599
 * ShenWeiHua 2015-02-06
 */
public class DiscountBackItemInfoService implements IDiscountBackItemInfoService{
	
	/**
	 * 事后折详细信息dao
	 */
	private IDiscountBackItemInfoDao discountBackItemInfoDao; 
	/**
	 * 把从CRM系统同步过来的事后折数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2015-02-06 上午9:28:02
	 */
	@Override
	public int insertDiscountBackItemInfo(DiscountBackItemEntity entity) {
		return discountBackItemInfoDao.insertDiscountBackItemInfo(entity);
	}
	/**
	 * 把从CRM系统同步过来的事后折数据更新到FOSS表内
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2015-02-06 上午9:29:02
	 */
	@Override
	public int updateDiscountBackItemInfo(DiscountBackItemEntity entity) {
		return discountBackItemInfoDao.updateDiscountBackItemInfo(entity);
	}
	/**
	 * 根据crmId验证该信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param crmId
	 * @date 2015-02-06 下午5:09:02
	 * @return
	 */
	@Override
	public boolean queryDiscountBackItemInfoByCrmId(BigDecimal crmId) {
		return discountBackItemInfoDao.queryDiscountBackItemInfoByCrmId(crmId);
	}
	
	/**
	 * 设置事后折信息dao
	 * @param discountBackItemInfoDao
	 */
	public void setDiscountBackItemInfoDao(
			IDiscountBackItemInfoDao discountBackItemInfoDao) {
		this.discountBackItemInfoDao = discountBackItemInfoDao;
	}
	
}
