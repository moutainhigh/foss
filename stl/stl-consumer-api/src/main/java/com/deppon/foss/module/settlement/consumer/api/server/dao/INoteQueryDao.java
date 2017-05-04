package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;

/**
 * 小票单据QueryDao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午9:17:26
 */
public interface INoteQueryDao {

	/**
	 * 查询票据详细信息列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	List<NoteQueryDto> queryNoteDetailsById(NoteQueryDto noteQueryDto,
			int start, int limit);
	/**
	 * 判断小票单号是否存在
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	public NoteQueryDto queryNoteApplyInfoByDetailNo(String detailNo,String applyOrgCode,String status);

	/**
	 * 校验上一部门最大结束编号对应的小票单号是否已使用
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-4 下午5:21:39
	 */
	public NoteQueryDto queryMixNoUseDetailsNo(String endNo,String applyOrgCode);
	/**
	 * 查询票据详细信息总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	Long countQueryNoteDetailsById(NoteQueryDto noteQueryDto);
}
