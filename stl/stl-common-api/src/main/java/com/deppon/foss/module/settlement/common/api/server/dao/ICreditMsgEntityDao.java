package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditMsgDto;

/**
 * 财务收支平衡消息Dao接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-18 上午10:55:15
 * @since
 * @version
 */
public interface ICreditMsgEntityDao {
	/**
	 * 新增财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午10:53:55
	 * @param entity
	 * @return
	 */
	int add(CreditMsgEntity entity);
	
	/**
	 * 查询时间段内所有未执行的财务收支平衡消息表
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:20:53
	 * @param startDate
	 * @param endDate
	 * @param creditType  收支平衡表类型，客户或者部门收支平衡表
	 * @return
	 */
	List<CreditMsgEntity> queryCreditMsgEntity(Date startDate,Date endDate,String creditType);
	
	/**
	 * 批量修改财务收支平衡消息的状态为已执行
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:43:40
	 * @param dto
	 * @return
	 */
	int updateBatchCreditMsgForStatus(CreditMsgDto dto);
	
}
