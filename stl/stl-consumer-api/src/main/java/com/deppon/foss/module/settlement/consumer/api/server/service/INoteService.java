package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;
/**
 * 小票申请服务
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:23:01
 */
public interface INoteService extends IService {

	 /**
	 * 小票单据申请
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-14 下午8:41:46
	 */
	void applicationNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);
 
	/**
	 *受理小票申请
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	 void approvalNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);

	/**
	 *发放小票申请
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	 void providerNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);

	/**
	 * 小票数据列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	 List<NoteApplyDto> queryNoteApplyEntityByCondition(NoteApplyDto noteApplyDto,
			int start, int limit);
	
	/**
	 * 
	 * 查询小票申请数据的总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	Long countNoteApplyEntityByCondition(NoteApplyDto noteApplyDto);
	
	/**
	 * 小票单据明细列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	 List<NoteQueryDto> queryNoteById(NoteQueryDto noteQueryDto,
			int start, int limit);
	
	/**
	 * 
	 * 查询小票单据明细的总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	Long countQueryNoteById(NoteQueryDto noteQueryDto);
	
	/**
	 * 生成小票单据数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-22 上午8:08:16
	 */
	 void createNoteDetails(NoteStockInDto noteStockInDto,CurrentInfo currentInfo);
	 
	/**
	 * 小票单据入库操作
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:06:25
	 */
	 void storageInNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);
	
	/**
	 * 查询小票单据入库数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:06:25
	 */
	  List<NoteStockInEntity>  queryStockIn(NoteStockInDto noteStockInDto,int start, int limit);
	/**
	 * 
	 * 查询小票单据入库总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	Long countQueryStockIn(NoteStockInDto noteStockInDto);
	/**
	 * 小票单据核销操作
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:16:32
	 */
	 void writeoffNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);
}
