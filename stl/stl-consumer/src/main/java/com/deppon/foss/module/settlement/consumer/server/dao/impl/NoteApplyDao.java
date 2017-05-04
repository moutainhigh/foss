package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteApplyDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;

/**
 * 小票单据申请Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午9:17:26
 */
public class NoteApplyDao extends iBatis3DaoImpl implements INoteApplyDao {
	
	private static final String NAMESPACE = "foss.stl.noteApplyDao.";// 小票单据申请命名空间
	
	/**
	 * 添加小票申请记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午9:11:39
	 */
	public int addNoteApply(NoteApplyDto noteApplyDto) {
		return getSqlSession().insert(NAMESPACE+"addApplyNote", noteApplyDto);
	}
	
	/**
	 * 查询票据信息列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoteApplicationEntity> queryNoteApplyEntityByCondition(
			NoteApplyDto noteApplyDto, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"searchNoteApplyByCondition", noteApplyDto, rowBounds);
	}
	
	/**
	 * 根据ID查询小票单据申请审批
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-11 上午9:36:42 
	 * @param id
	 * @return NoteApplicationEntity
	 */
	@Override
	public NoteApplicationEntity queryNoteApplyEntityID(String idNoteApply) {
		
		return (NoteApplicationEntity) getSqlSession().selectOne(NAMESPACE+"queryNoteApplyEntityID", idNoteApply);
	}

	/**
	 * 查询票据列表总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	@Override
	public Long countNoteApplyEntityByCondition(NoteApplyDto noteApplyDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"countNoteApplyByCondition", noteApplyDto);
	}
	
	/**
	 * 审核操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:01
	 */
	@Override
	public int updateApproveById(NoteApplyDto noteApplyDto) {
		return getSqlSession().update(NAMESPACE+"updateApproveById", noteApplyDto);
	}

	/**
	 * 下发操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:04
	 */
	@Override
	public int updateIssuedById(NoteApplyDto noteApplyDto) {
		return getSqlSession().update(NAMESPACE+"updateIssuedById", noteApplyDto);
	}

	/**
	 * 入库操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:06
	 */
	@Override
	public int updateStorageByIds(NoteApplyDto noteApplyDto) {
		return getSqlSession().update(NAMESPACE+"updateStorageByIds", noteApplyDto);
	}

	/**
	 * 核销操作更改操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:09
	 */
	@Override
	public int updateWriteoffByIds(NoteApplyDto noteApplyDto) {
		return getSqlSession().update(NAMESPACE+"updateWriteoffByIds", noteApplyDto);
	}

}
