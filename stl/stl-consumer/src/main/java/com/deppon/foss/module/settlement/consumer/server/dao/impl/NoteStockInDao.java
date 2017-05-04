package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteStockInDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;

/**
 * 小票单据入库Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-14 下午7:46:53
 */
public class NoteStockInDao extends iBatis3DaoImpl implements INoteStockInDao {

	private static final String NAMESPACE = "foss.stl.noteStockInDao.";// 小票单据入库命名空间

	/**
	 * 票据下发
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-19 上午11:32:49
	 */
	@Override
	public int addStockIn(List<NoteStockInEntity> stockInLst) {
		return getSqlSession().insert(NAMESPACE + "batchSaveStockIn",
				stockInLst);
	}

	/**
	 * 校验是否重复下发
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-22 下午2:21:40
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoteStockInEntity> checkStockInEntity(
			NoteStockInEntity noteStockInEntity) {
		return getSqlSession().selectList(NAMESPACE + "checkBeginWithEndNo",
				noteStockInEntity);
	}

	/**
	 * 根据申请Id 查询入库数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:55:43
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoteStockInEntity> queryStockInById(
			NoteStockInDto noteStockInDto, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryStockInById",
				noteStockInDto, rowBounds);
	}

	/**
	 * 根据申请Id 查询入库数据总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:56:11
	 */
	@Override
	public Long countQueryStockInById(NoteStockInDto noteStockInDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "countStockInById",
				noteStockInDto);
	}
}
