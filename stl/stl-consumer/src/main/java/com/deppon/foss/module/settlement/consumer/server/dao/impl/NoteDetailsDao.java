package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteDetailsDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteDetailsEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;

/**
 * 小票单据明细Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:51:27
 */
public class NoteDetailsDao extends iBatis3DaoImpl implements INoteDetailsDao {

	private static final String NAMESPACE = "foss.stl.noteDetailDao.";// 小票单据明细命名空间

	/**
	 * 新加小票票据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:59:41
	 */
	@Override
	public int addNoteDetails(List<NoteDetailsEntity> noteDetailsLst) {

		// 如果参数为空，抛出异常
		if (CollectionUtils.isEmpty(noteDetailsLst)) {
			throw new SettlementException("参数List为空，不能批量插入小票单据");
		}

		// 循环遍历List,插入小票单据
		for (NoteDetailsEntity entity : noteDetailsLst) {
			// 执行插入操作
			getSqlSession().insert(NAMESPACE + "batchSaveNoteDetailsIn", entity);
		}

		//返回插入成功条数
		return noteDetailsLst.size();
	}

	/**
	 * 入库操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	@Override
	public int updateStorageInNoteByAppIds(NoteDetailsDto noteDetailsDto) {
		return getSqlSession().insert(NAMESPACE + "updateStorageByNoteAppIds",
				noteDetailsDto);
	}

	/**
	 * 核销操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	@Override
	public int updateWriteoffByNoteAppIds(NoteDetailsDto noteDetailsDto) {
		return getSqlSession().insert(NAMESPACE + "updateWriteoffByNoteAppIds",
				noteDetailsDto);
	}

	/**
	 * 更新使用人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	@Override
	public int updateUserInfoByDetailNo(NoteDetailsDto noteDetailsDto) {
		return getSqlSession().update(NAMESPACE + "updateUserInfoByDetailNo",
				noteDetailsDto);
	}

	/**
	 * 
	 * 查询申请记录下发起止编号
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-14 下午4:09:48
	 */
	@Override
	public NoteApplyDto queryApplyDetailBeginAndEndNo(String noteApplyId) {
		return (NoteApplyDto) getSqlSession().selectOne(
				NAMESPACE + "queryApplyDetailBeginAndEndNo", noteApplyId);
	}

	
	/** 
	 *查询小票明细编号是否已经重复发放
	 * @author 095793-foss-LiQin
	 * @date 2013-6-18 下午3:46:42
	 * @param noteDetailsLst
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.INoteDetailsDao#queryIsRepNoteDetailsNos(java.util.List)
	 */
	@Override
	public int queryIsRepNoteDetailsNos(Map<String,String> map) {
		return getSqlSession().selectList(NAMESPACE+"queryIsNotesDetailNos",map).size();
	}
}
