package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditMsgDto;

/**
 * 财务收支平衡消息 Service接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-18 上午10:54:57
 * @since
 * @version
 */
public interface ICreditMsgService {
	
	/**
	 * 新增财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午10:55:56
	 * @param entity
	 */
	void add(CreditMsgEntity entity);
	
	/**
	 * 查询时间段内（结束时间默认为系统时间）所有未执行的财务收支平衡消息表
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:22:19
	 * @param creditType
	 * @param date
	 * @return
	 */
	List<CreditMsgEntity> queryCreditMsgEntity(Date date,String creditType);
	
	/**
	 * 批量修改财务收支平衡消息的状态为已执行
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:41:54
	 * @param dto
	 */
	void batchUpdateCreditMsgForStatus(CreditMsgDto dto);
	
	/**
	 * 批量处理财务收支平衡消息表，来还原或增加客户的信用额度/部门的可用额度
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:07:12
	 * @param date
	 */
	void batchUpdateCreditMsg(Date date);
}
