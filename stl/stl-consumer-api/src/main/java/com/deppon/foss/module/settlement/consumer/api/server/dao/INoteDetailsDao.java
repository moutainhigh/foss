package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteDetailsEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;

/**
 * 小票单据明细Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:51:27
 */
public interface INoteDetailsDao {

	/**
	 * 新加小票票据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:59:41
	 */
	int addNoteDetails(List<NoteDetailsEntity> noteDetailsLst);

	/**
	 * 入库操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	int updateStorageInNoteByAppIds(NoteDetailsDto noteDetailsDto);

	/**
	 * 核销操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	int updateWriteoffByNoteAppIds(NoteDetailsDto noteDetailsDto);

	/**
	 * 更新使用人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	int updateUserInfoByDetailNo(NoteDetailsDto noteDetailsDto);

	/**
	 * 
	 * 查询申请记录下发起止编号
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-14 下午4:09:48
	 */
	NoteApplyDto queryApplyDetailBeginAndEndNo(String noteApplyId);
	
	
	/**
	 * 检查小票明细号是否已经存在
	 * @author 095793-foss-LiQin
	 * @date 2013-6-18 下午3:35:34
	 * @param noteDetailsLst
	 * @return
	 */
	int queryIsRepNoteDetailsNos(Map<String,String> map);
}
