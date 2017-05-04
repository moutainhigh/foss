/**
 * 
 */
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IMakeUpWaybillDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;

/**
 * @author niuly
 *
 */
public class MakeUpWaybillDao extends iBatis3DaoImpl implements IMakeUpWaybillDao {

	private static final String NAMESPACE = "tfr-makeupwaybill."; 
	/**
	 * @author niuly
	 * @date 2014-02-12 09:39:20
	 * @function 运单补录重量或体积发生变化或PDA更新重量体积时调用此方法
	 * @param entity
	 * @return int
	 */
	@Override
	public int addWaybillInfo(MakeUpWaybillEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"addWaybillInfo",entity);
	}
	/**
	 * @author niuly
	 * @date 2014-2-12下午3:32:27
	 * @function 查询该运单补录或PDA更新记录
	 * @param waybillNo
	 * @return List<MakeUpWaybillEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MakeUpWaybillEntity> queryMakeUpWaybill() {
		return this.getSqlSession().selectList(NAMESPACE+"queryMakeUpWaybill");
	}
	/**
	 * @author niuly
	 * @date 2014-2-12下午3:34:27
	 * @function 更新该运单的处理标志位和处理时间
	 * @param waybillId
	 */
	@Override
	public int updateMakeUpWaybill(String waybillId,Date doneTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillId", waybillId);
		map.put("doneTime", doneTime);
		return this.getSqlSession().update(NAMESPACE+"updateMakeUpWaybill", map);
		
	}

}
