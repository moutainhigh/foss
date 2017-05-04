package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;

/**
 * 小票申请服务
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:23:01
 */
public  interface INoteApplyService extends IService {

	/**
	 * 申请票据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:27
	 */
	  void applicationNote(NoteApplyDto noteDto,CurrentInfo currentInfo);
	
	/**
	 *受理小票申请
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	  void approvalNote(NoteApplyDto noteDto,CurrentInfo currentInfo);

	/**
	 *发放小票申请
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	  void providerNote(NoteApplyDto noteDto,CurrentInfo currentInfo);
	
	/**
	 * 小票单据入库操作
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:06:25
	 */
	  void storageInNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);
	
	/**
	 * 小票单据核销操作
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:16:32
	 */
	  void writeoffNote(NoteApplyDto noteApplyDto,CurrentInfo currentInfo);
	/**
	 * 小票数据列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	  List<NoteApplyDto> queryNoteApplyEntityByCondition(NoteApplyDto noteDto,
			int start, int limit);
	/**
	 * 
	 * 查询小票数据的总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	Long countNoteApplyEntityByCondition(NoteApplyDto noteDto);
	
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
