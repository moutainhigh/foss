package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDiscountBackInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackEntity;
/**
 * 事后折概要信息service实现类
 * @author 132599
 * ShenWeiHua 2015-02-06
 */
public class DiscountBackInfoService implements IDiscountBackInfoService{
	
	/**
	 * 事后折详细信息dao
	 */
	private IDiscountBackInfoDao discountBackInfoDao;
	/**
	 * 把从CRM系统同步过来的事后折数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2015-02-06 上午9:28:02
	 */
	@Override
	public int insertDiscountBackInfo(DiscountBackEntity entity) {
		return discountBackInfoDao.insertDiscountBackInfo(entity);
	}
	/**
	 * 把从CRM系统同步过来的事后折数据更新到FOSS表内
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2015-02-06 上午9:29:02
	 */
	@Override
	public int updateDiscountBackInfo(DiscountBackEntity entity) {
		return discountBackInfoDao.updateDiscountBackInfo(entity);
	}
	/**
	 * 根据crmId验证该信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param crmId
	 * @date 2015-02-06 下午5:09:02
	 * @return
	 */
	@Override
	public boolean queryDiscountBackInfoByCrmId(BigDecimal crmId) {
		return discountBackInfoDao.queryDiscountBackInfoByCrmId(crmId);
	}
	
	/**
	 * 设置事后折概要信息dao
	 * @param discountBackInfoDao
	 */
	public void setDiscountBackInfoDao(IDiscountBackInfoDao discountBackInfoDao) {
		this.discountBackInfoDao = discountBackInfoDao;
	}
	
}
