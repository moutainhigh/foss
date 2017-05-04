package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverbillReturnDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;

/**
 * 待排运单退回Dao实现
 * @author 239284
 *
 */
public class HandoverbillReturnDao  extends iBatis3DaoImpl   implements IHandoverbillReturnDao {
	
	//待排运单退回 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverbillReturnDao";


	/**
	 * 查询是否存在退回原因记录
	 * @param waybillNo
	 * @return  退回原因ID集合
	 */
	public  List<String>  queryIsWaitWaybillReturn(String waybillNo) {
		Map map = new HashMap();
		map.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE + ".selectIsHandBillReturn", map);
	}

	 /**
	  * 新增待排运单退回记录表
	  * @param entity 退回原因信息
	  */
	public void addWaitWaybillReturn(VisibleHandBillReturnEntity entity) {
		this.getSqlSession().insert(NAMESPACE + ".insertHandBillReturn", entity);
	}

	/**
	 * 更新待排运单退回记录表
	 */
	public void updateWaitWaybillReturn(VisibleHandBillReturnEntity entity) {
		this.getSqlSession().update(NAMESPACE + ".updateHandBillReturn", entity);
	}

	/**
	 * 根据运单号查询退回记录列表
	 * @author foss-sunyanfei
	 * 2015-8-12 下午 15:45:12
	 * @param waybillNo 运单号
	 * @return 退回信息列表
	 */
	@Override
	public List<VisibleHandBillReturnEntity> queryWaitWaybillReturnListByWaybillNo(
			VisibleHandBillReturnEntity entity) {
		@SuppressWarnings("unchecked")
		List<VisibleHandBillReturnEntity> list =this.getSqlSession().selectList(NAMESPACE +
                ".queryHandBillReturnByWaybillNo",entity);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
	}
	
}
