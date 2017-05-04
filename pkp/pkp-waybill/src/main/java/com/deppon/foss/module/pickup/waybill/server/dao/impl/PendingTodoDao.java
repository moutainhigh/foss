/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class PendingTodoDao extends iBatis3DaoImpl implements
	IPendingTodoDao{
	private static final String NAMESPACE = "foss.pkp.PendingTodoMapper.";
	/**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
	public int insert(PendingTodoEntity record) {
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(
				NAMESPACE + "insert", record);
	}

	
	/**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
	public int delete(PendingTodoEntity record) {
		return this.getSqlSession().delete(
				NAMESPACE + "delete", record.getId());
	}

	/**
	 * 查询所有的需要生成异步代办的请求
	 */
	@SuppressWarnings("unchecked")
	public List<PendingTodoEntity> queryAllTodo() {
		Map<String, String> param = new HashMap<String, String>();
		return (List<PendingTodoEntity> ) this.getSqlSession().selectList(
				NAMESPACE + "queryAllTodo", param);
	}
	/**
	 * 查询所有的需要生成异步代办的请求
	 */
	@SuppressWarnings("unchecked")
	public List<PendingTodoEntity> queryAllTodo(int start, int limited) {
		RowBounds bounds = new RowBounds(start, limited);
		Map<String, String> param = new HashMap<String, String>();
		return (List<PendingTodoEntity> ) this.getSqlSession().selectList(
				NAMESPACE + "queryAllTodo", param,bounds);
	}
	
	
	
	/**
	 * 更新 异步代办的请求
	 * @param pendingTodoEntity
	 */
	public int update(PendingTodoEntity pendingTodoEntity) {
		return this.getSqlSession().update(
				NAMESPACE + "update", pendingTodoEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PendingTodoEntity queryPendingTodoByWaybillId(String waybillRfcId){
		Map<String, String> param = new HashMap<String, String>();
		param.put("waybillRfcId", waybillRfcId);
		List<PendingTodoEntity> list =  getSqlSession().selectList(NAMESPACE+"queryPendingTodoByWaybillId", param);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PendingTodoEntity> queryPendingTodoByIds(
			List<String> pendTodoIdList) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("pendTodoIdList", pendTodoIdList);
		return this.getSqlSession().selectList(NAMESPACE+"queryPendingTodoByIds", map);
	}

}
