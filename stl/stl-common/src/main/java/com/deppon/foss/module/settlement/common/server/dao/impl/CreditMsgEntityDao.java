package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditMsgDto;

/**
 * 财务收支平衡消息表 DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-3-14 上午11:15:11
 * @since
 * @version
 */
public class CreditMsgEntityDao extends iBatis3DaoImpl implements ICreditMsgEntityDao {

	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACE = "foss.stl.CreditMsgEntityDao.";
	
	/**
	 * 新增财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-14 上午11:15:26
	 * @param entity
	 * @return
	 */
	@Override
	public int add(CreditMsgEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insert",entity);
	}

	/**
	 * 查询时间段内（结束时间默认为系统时间）所有未执行的财务收支平衡消息表
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:34:17
	 * @param startDate
	 * @param endDate
	 * @param creditType  客户/部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditMsgEntity> queryCreditMsgEntity(Date startDate,Date endDate, String creditType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("creditType", creditType);
		//未执行
		map.put("status", SettlementDictionaryConstants.CREDIT_MSG_STATUS_NOT_EXECUTE);
		return this.getSqlSession().selectList(NAMESPACE+"selectCreditMsgEntity",map);
	}

	/**
	 * 批量修改财务收支平衡消息的状态为已执行
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:44:40
	 * @param dto
	 */
	@Override
	public int updateBatchCreditMsgForStatus(CreditMsgDto dto) {
		return this.getSqlSession().update(NAMESPACE+"updateBatchCreditMsgForStatus",dto);
	}
	
	
    
}
