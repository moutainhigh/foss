package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillBadAccountEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;

/**
 * 坏账单Dao实现类
 * 
 * @author foss-wangxuemin
 * @date Dec 5, 2012 10:07:44 AM
 */
public class BillBadAccountEntityDao extends iBatis3DaoImpl implements IBillBadAccountEntityDao {

	private static final String NAMESPACES = "foss.stl.BillBadAccountEntityDao.";

	/**
	 * 新增坏账单
	 * @author foss-wangxuemin
	 * @date Dec 5, 2012 10:07:24 AM
	 * @param entity
	 *   坏账单
	 * @return
	 */
	@Override
	public int add(BillBadAccountEntity entity) {
		if (entity != null) {
			return this.getSqlSession().insert(NAMESPACES + "insert", entity);
		}
		return 0;
	}

	/**
	 * 根据运单号查询是否存在坏账信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-24 上午11:51:20
	 * @param waybillNo
	 *            运单
	 * @return
	 */
	@Override
	public int queryByWaybillNO(String waybillNo) {
		Object obj=this.getSqlSession().selectOne(NAMESPACES+"selectByWaybillNo",waybillNo);
		//查询不到数据，之间返回一个0
		if(obj==null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	@Override
	public List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos) {
		@SuppressWarnings("unchecked")
		List<BillBadAccountEntity> billBadAccountEntitys=this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNos", waybillNos);
		//查询不到数据，之间返回一个0
		if(billBadAccountEntitys==null){
			return null;
		}
		return billBadAccountEntitys;
	}
}
