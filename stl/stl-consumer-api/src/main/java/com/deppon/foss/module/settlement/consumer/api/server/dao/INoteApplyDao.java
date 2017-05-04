package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;

/**
 * 小票单据申请Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午9:17:26
 */
public interface INoteApplyDao {

	/**
	 * 添加小票申请记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午9:11:39
	 */
	int addNoteApply(NoteApplyDto noteDto);

	/**
	 * 查询票据信息列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	List<NoteApplicationEntity> queryNoteApplyEntityByCondition(
			NoteApplyDto noteDto, int start, int limit);

	/**
	 * 查询票据列表总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	Long countNoteApplyEntityByCondition(NoteApplyDto noteDto);

	/**
	 * 审核操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:01
	 */
	int updateApproveById(NoteApplyDto noteApplyDto);

	/**
	 * 下发操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:04
	 */
	int updateIssuedById(NoteApplyDto noteApplyDto);

	/**
	 * 入库操作更改数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:06
	 */
	int updateStorageByIds(NoteApplyDto noteApplyDto);

	/**
	 * 核销操作更改操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:43:09
	 */
	int updateWriteoffByIds(NoteApplyDto noteApplyDto);
	
	/**
	 * 根据ID查询小票单据申请审批
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-11 上午9:36:42 
	 * @param id
	 * @return NoteApplicationEntity
	 */
	NoteApplicationEntity queryNoteApplyEntityID(String idNoteApply);
}
