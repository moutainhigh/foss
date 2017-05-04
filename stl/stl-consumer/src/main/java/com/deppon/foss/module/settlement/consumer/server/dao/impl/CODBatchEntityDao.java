package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODBatchEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;

/**
 * 
 * 代收货款批次号实体DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-29 下午2:27:25
 */
public class CODBatchEntityDao extends iBatis3DaoImpl implements
		ICODBatchEntityDao {

	private static final String NAMESPACE = "foss.stl.CodBatchEntityDao.";

	/**
	 * 新增代收货款批次号
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:28:55
	 */
	@Override
	public int insert(CODBatchEntity entity) {
		return getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
	 * 
	 * 根据批次号查询代收货款批次号
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:29:23
	 */
	@Override
	public CODBatchEntity queryByBatchNo(String batchNo) {
		CODBatchEntity entity = (CODBatchEntity) getSqlSession().selectOne(
				NAMESPACE + "selectByBatchNo", batchNo);
		return entity;
	}

	/**
	 * 
	 * 获取批次号SEQ
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午2:30:03
	 */
	@Override
	public long selectBatchNoSeq() {
		Long seq = (Long) getSqlSession().selectOne(
				NAMESPACE + "selectBatchNoSeq");
		return seq;
	}

	/**
	 * 
	 * 更新代收货款批次状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午8:09:12
	 */
	public int updateCODBatchStatus(CODBatchDto dto) {
		return getSqlSession().update(NAMESPACE + "updateCODBatchStatus", dto);
	}
}
