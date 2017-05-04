package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAutoDeliverHandoverDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 待自动交单DAO　　
 * @author 159231 meiying
 * 2015-4-20  上午11:18:47
 */
public class AutoDeliverHandoverDao extends iBatis3DaoImpl implements IAutoDeliverHandoverDao{
	/**
	 * 运单综合查询命名空间
	 */
	private static final String AUTO_DELIVER_HANDOVERBILL_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.AutoDeliverHandoverEntityMapper.";
	/**
	 * 根据运单号删除记录
	 * @author 159231 meiying
	 * 2015-5-28  下午3:43:47
	 * @param waybillNo
	 * @return
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		AutoDeliverHandoverEntity entity =new AutoDeliverHandoverEntity();
		entity.setWaybillNo(waybillNo);
		return getSqlSession().delete(AUTO_DELIVER_HANDOVERBILL_NAMESPACE + "deleteByExample", entity);
	}
	/**
	 * 添加一条记录
	 * @author 159231 meiying
	 * 2015-5-28  下午3:44:21
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(AutoDeliverHandoverEntity record) {
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(
				AUTO_DELIVER_HANDOVERBILL_NAMESPACE + "insertSelective", record);
	}
	/**
	 * 根据运单号查询待自动交单临时表
	 * @author 159231 meiying
	 * 2015-5-28  下午3:44:25
	 * @param waybillNo
	 * @return
	 */
	@Override
	public AutoDeliverHandoverEntity selectByWaybillNo(String waybillNo) {
		AutoDeliverHandoverEntity entity =new AutoDeliverHandoverEntity();
		entity.setWaybillNo(waybillNo);
		@SuppressWarnings("unchecked")
		List<AutoDeliverHandoverEntity> list =this.getSqlSession().selectList(AUTO_DELIVER_HANDOVERBILL_NAMESPACE +
                "selectByWaybillNo",entity);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
	}
	
	/**
	 * 查询待自动交单临时表集合
	 * @author 159231 meiying
	 * 2015-5-29  上午8:04:09
	 * @return
	 */
	@Override
	public List<AutoDeliverHandoverEntity> selectList() {
		AutoDeliverHandoverEntity entity = new AutoDeliverHandoverEntity();
		entity.setNum( NumberConstants.NUMBER_200);
		@SuppressWarnings("unchecked")
		List<AutoDeliverHandoverEntity> list =this.getSqlSession().selectList(AUTO_DELIVER_HANDOVERBILL_NAMESPACE +
                "selectList", entity);
        return list;
	}
	@Override
	public int updateAutoDeliverJobList(AutoPreDeliverHandoverbillDto record) {
		return this.getSqlSession().update(
				AUTO_DELIVER_HANDOVERBILL_NAMESPACE + "updateAutoDeliverJob", record);
	}
}