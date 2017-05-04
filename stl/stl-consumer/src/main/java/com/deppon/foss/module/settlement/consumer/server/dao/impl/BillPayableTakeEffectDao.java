package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillPayableTakeEffectDao;

/**
 * 生效应付单(装卸费)Dao实现
 * @author foss-zhangxiaohui
 * @date Dec 4, 2012 4:06:47 PM
 */
public class BillPayableTakeEffectDao extends iBatis3DaoImpl implements IBillPayableTakeEffectDao {

	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACES = "foss.stl.BillPayableEntityDao.";
	
	/**
	 * 查询出符合条件的应付单
	 * @author foss-zhangxiaohui
	 * @date Nov 22, 2012 1:51:33 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBillPayableLaborFee(String billType, Date startAccountDate,Date endAcclountDate,String effectiveStatus, String active)
			throws SettlementException {		
		//声明传参的map
		Map<String, Object> map = new HashMap<String, Object>();	
		//添加开始记账日期到map
		map.put("startAccountDate",startAccountDate);		
		//添加结束记账日期到map
		map.put("endAccountDate",endAcclountDate);	
		//添加单据类型到map
		map.put("billType", billType);		
		//添加生效状态到map
		map.put("effectiveStatus",effectiveStatus);
		//添加单据状态到map
		map.put("active",active);			
		//返回按map为参数查询的结果集List
		return this.getSqlSession().selectList(NAMESPACES +"queryBillPayableLaborFee", map);
	}
}
