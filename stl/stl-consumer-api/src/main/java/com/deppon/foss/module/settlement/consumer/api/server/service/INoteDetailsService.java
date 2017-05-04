package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;

/**
 * 小票使用管理服务
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:30:42
 */
public interface INoteDetailsService extends IService {

	/**
	 * 生成小票单据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:31:03
	 */
	void createNoteDetails(NoteStockInDto noteStockInDto,CurrentInfo currentInfo);

	/**
	 * 小票核销
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:32:42
	 */
	void writeoffNote(NoteDetailsDto noteDetailsDto);

	/**
	 * 更新小票单据明细入库操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:14:38
	 */
	void storageInNote(NoteDetailsDto noteDetailsDto);

	/**
	 * 查询小票单据入库数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:53:06
	 */
	List<NoteStockInEntity> queryStockInById(NoteStockInDto noteStockInDto,
			int start, int limit);

	/**
	 * 小票单据入库数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:52:58
	 */
	Long countQueryStockInById(NoteStockInDto noteStockInDto);

	/**
	 * 小票单据详细列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	List<NoteQueryDto> queryNoteById(NoteQueryDto noteQueryDto, int start,
			int limit);
    /**
     * 校验小票单号是否存在
     * @author 101911-foss-zhouChunlai
     * @date 2012-12-3 下午2:18:13
     */
    NoteQueryDto queryNoteApplyInfoByDetailNo(String detailNo,String applyOrgCode,String status);
    
    /**
     * 校验上一部门最大结束编号对应的小票单号是否已使用
     * @author 101911-foss-zhouChunlai
     * @date 2012-12-4 下午5:33:05
     */
    NoteQueryDto queryMixNoUseDetailsNo(String endNo,String applyOrgCode);
	/**
	 * 小票单据详细总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	Long countQueryNoteById(NoteQueryDto noteQueryDto);
	
	/**
	 * 更新使用人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	void updateUserInfoByDetailNo(NoteDetailsDto noteDetailsDto);
}
