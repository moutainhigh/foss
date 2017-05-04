package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODMergeEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODMergeEntity;

/**
 * 代收货款合并 实现类
 * 
 * @author 163576
 * @date 2014-10-11 10:33:56
 *
 */
public class CODMergeEntityDao extends iBatis3DaoImpl implements ICODMergeEntityDao{

	private static final String NAMESPACE = "foss.stl.CODMergeEntityDao.";
	
	@Override
	public int insert(CODMergeEntity entity) {
		if (entity != null) {
			// 调用插入方法
			return this.getSqlSession().insert(NAMESPACE + "insert", entity);
		} else {
			throw new SettlementException("内部错误，参数为为空");
		}
	}

	@Override
	public int updateMergeCODInvalidByBatch(List<String> mergeCodeList) {
		if (CollectionUtils.isNotEmpty(mergeCodeList)) {
			return getSqlSession().update(
					NAMESPACE + "updateMergeCODInvalidByBatch", mergeCodeList);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 获取生成合并编号
	 * @param rule
	 * @return
	 */
	@Override
	public String createMergeCode(SettlementNoRuleEnum rule) {
		
		if (rule == null) {
			throw new SettlementException("获取生成合并编号出错");
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("sequenceName", rule.getSequence());
		
		// sqlSqlSession未关闭之前，如果对于同样条件进行重复查询，采用的是local session cache
		// 参见 http://blog.csdn.net/x43816138/article/details/7868990
		// 用mybatis 3.1 以后可以使用 <setting name="localCacheScope" value="STATEMENT"/> 来禁止缓存
		// 参见http://code.google.com/p/mybatis/issues/detail?id=482
		this.getSqlSession().clearCache();
		long nextVal = (Long)this.getSqlSession().selectOne("foss.stl.StlSequenceDao.selectNextVal", map);
		String seqStr = String.format("%09d",nextVal);

		// 当前时间字符串yyyymmdd
		//String dateStr = DateUtils.getDay(new Date());
		
		//String seqCode = rule.getCode() + dateStr + seqStr;
		String seqCode = rule.getCode() + seqStr;
		
		logger.debug(rule.getName() + " :" + seqCode);
		
		return seqCode;
	}
}