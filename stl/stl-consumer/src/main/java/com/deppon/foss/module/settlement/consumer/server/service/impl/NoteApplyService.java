/**
 * Copyright 2013 STL TEAM
 */

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteApplyDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteDetailsDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 小票申请服务
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:23:01
 */
public class NoteApplyService implements INoteApplyService {
	private static Logger logger = LogManager.getLogger(NoteApplyService.class);

	/**
	 * 注入小票单据申请DAO
	 */
	private INoteApplyDao noteApplyDao;
	
	/**
	 * 注入小票单据下发详细信息
	 */
	private INoteDetailsDao noteDetailsDao;

	/**
	 * 票据申请
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午3:12:59
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#applicationNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public void applicationNote(NoteApplyDto noteApplyDto,
			CurrentInfo currentInfo) {
		// 输入参数校验
		if (null == noteApplyDto) {
			throw new SettlementException("内部错误，小票申请DTO为空！");
		}
		logger.trace("entering service...");

		Date date = new Date();
		noteApplyDto.setId(UUIDUtils.getUUID());
		noteApplyDto
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__SUBMIT); // 已提交
		noteApplyDto
				.setApproveStatus(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__NOT_AUDIT);// 审批状态
		noteApplyDto
				.setWriteoffStatus(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF);// 核销状态
		noteApplyDto.setActive(FossConstants.ACTIVE);// 是否有效
		noteApplyDto.setCreateTime(date);
		noteApplyDto.setApplyUserName(currentInfo.getEmpName());
		noteApplyDto.setApplyUserCode(currentInfo.getEmpCode());

		setModifyInfo(noteApplyDto, currentInfo, date);// 更新修改人信息

		// 票据申请
		int retNum = noteApplyDao.addNoteApply(noteApplyDto);
		if (1 != retNum) {
			throw new SettlementException("申请失败!");
		}
		logger.info("successfully exit service");
	}

	/**
	 * 受理小票申请
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午3:14:40
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#approvalNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public void approvalNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (null == noteApplyDto || StringUtil.isEmpty(noteApplyDto.getId())) {
			throw new SettlementException("内部错误，小票票据为空！");
		}

		logger.info("entering service,id:" + noteApplyDto.getId());

		// 当审批通过时，如果用户输入备注则过滤掉
		if (StringUtil
				.equals(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE,
						noteApplyDto.getApproveStatus())) {
			noteApplyDto.setApproveNotes("");
		}
		Date date = new Date();
		noteApplyDto
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__SUBMIT);// 已提交
		noteApplyDto.setApproveOrgCode(currentInfo.getCurrentDeptCode());
		noteApplyDto.setApproveOrgName(currentInfo.getCurrentDeptName());
		noteApplyDto.setApproveTime(date);
		noteApplyDto.setApproveUserCode(currentInfo.getEmpCode());
		noteApplyDto.setApproveUserName(currentInfo.getEmpName());
		setModifyInfo(noteApplyDto, currentInfo, date);// 更新修改人信息

		// 受理小票申请
		int retNum = noteApplyDao.updateApproveById(noteApplyDto);
		if (1 != retNum) {
			throw new SettlementException("保存失败!");
		}
		logger.info("successfully exit service,id:" + noteApplyDto.getId());
	}

	/**
	 * 票据发放
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午3:15:50
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#providerNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public void providerNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (null == noteApplyDto || StringUtil.isEmpty(noteApplyDto.getId())) {
			throw new SettlementException("内部错误，小票票据为空！");
		}

		logger.info("entering service,id:" + noteApplyDto.getId());

		Date date = new Date();
		noteApplyDto.setIssuedOrgCode(currentInfo.getCurrentDeptCode());
		noteApplyDto.setIssuedOrgName(currentInfo.getCurrentDeptName());
		noteApplyDto.setIssuedTime(date);
		noteApplyDto.setIssuedUserCode(currentInfo.getEmpCode());
		noteApplyDto.setIssuedUserName(currentInfo.getEmpName());
		setModifyInfo(noteApplyDto, currentInfo, date);// 更新修改人信息

		// 票据发放
		int retNum = noteApplyDao.updateIssuedById(noteApplyDto);
		if (1 != retNum) {
			throw new SettlementException("票据发放失败!");
		}
		logger.info("successfully exit service,id:" + noteApplyDto.getId());
	}

	/**
	 * 票据入库
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午3:16:05
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#storageInNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public void storageInNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (null == noteApplyDto
				|| CollectionUtils.isEmpty(noteApplyDto.getNoteAppIds())) {
			throw new SettlementException("内部错误，票据实体为空！");
		}

		logger.info("entering service,ids:" + noteApplyDto.getNoteAppIds());

		Date date = new Date();
		noteApplyDto.setStorageUserCode(currentInfo.getEmpCode());
		noteApplyDto.setStorageUserName(currentInfo.getEmpName());
		noteApplyDto
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN);// 已入库
		noteApplyDto.setStorageTime(date);// 入库时间
		setModifyInfo(noteApplyDto, currentInfo, date);// 更新修改人信息

		// 票据入库
		int i = noteApplyDao.updateStorageByIds(noteApplyDto);
		if (i != noteApplyDto.getNoteAppIds().size()) {
			throw new SettlementException("票据入库失败!");
		}

		logger.info("successfully exit service,ids:"
				+ noteApplyDto.getNoteAppIds());
	}

	/**
	 * 票据核销
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午3:17:26
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#writeoffNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public void writeoffNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (null == noteApplyDto
				|| CollectionUtils.isEmpty(noteApplyDto.getNoteAppIds())) {
			throw new SettlementException("内部错误，查询条件为空！");
		}

		logger.info("entering service,ids:" + noteApplyDto.getNoteAppIds());

		Date date = new Date();
		noteApplyDto.setWriteoffOrgCode(currentInfo.getCurrentDeptCode());
		noteApplyDto.setWriteoffOrgName(currentInfo.getCurrentDeptName());
		noteApplyDto.setWriteoffUserCode(currentInfo.getEmpCode());
		noteApplyDto.setWriteoffUserName(currentInfo.getEmpName());
		noteApplyDto.setWriteoffTime(date);// 核销时间
		setModifyInfo(noteApplyDto, currentInfo, date);// 更新修改人信息

		// 票据核销
		int i = noteApplyDao.updateWriteoffByIds(noteApplyDto);
		if (i != noteApplyDto.getNoteAppIds().size()) {
			throw new SettlementException("票据核销失败!");
		}

		logger.info("successfully exit service,ids:"
				+ noteApplyDto.getNoteAppIds());
	}

	/**
	 * 公共--更改修改人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午11:01:29
	 */
	private void setModifyInfo(NoteApplyDto noteApplyDto,
			CurrentInfo currentInfo, Date date) {
		noteApplyDto.setModifyUserName(currentInfo.getEmpName());
		noteApplyDto.setModifyUserCode(currentInfo.getEmpCode());
		noteApplyDto.setBusinessDate(date);
		noteApplyDto.setModifyTime(date);
	}

	/**
	 * 小票数据列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-14 下午8:16:44
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#queryNoteApplyEntityByCondition(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto,
	 *      int, int)
	 */
	@Override
	public List<NoteApplyDto> queryNoteApplyEntityByCondition(
			NoteApplyDto noteApplyDto, int start, int limit) {
		// 输入参数校验
		if (null == noteApplyDto) {
			throw new SettlementException("内部错误，查询条件为空！");
		}
		logger.info("entering service");

		// 查询小票申请记录
		List<NoteApplicationEntity> queryList = noteApplyDao
				.queryNoteApplyEntityByCondition(noteApplyDto, start, limit);

		// 如果申请记录不为空，则查询小票申请下发起止编号
		List<NoteApplyDto> resultList = null;
		NoteApplyDto dto = null;
		if (CollectionUtils.isNotEmpty(queryList)) {

			// new 结果集合
			resultList = new ArrayList<NoteApplyDto>();

			// 循环查询结果集
			for (NoteApplicationEntity entity : queryList) {

				// 如果实体不为空,则查询起止编号
				if (entity != null && StringUtils.isNotEmpty(entity.getId())) {
					dto = noteDetailsDao.queryApplyDetailBeginAndEndNo(entity
							.getId());
					
					//如果为空,则新增一个
					if(dto == null){
						dto = new NoteApplyDto();
					}
					
					// 拷贝属性
					BeanUtils.copyProperties(entity, dto);

					// 加到结果集中
					resultList.add(dto);
				}
			}
		}

		//返回结果
		return resultList;
	}

	/**
	 * 查询小票数据的总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-14 下午8:16:54
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService#countNoteApplyEntityByCondition(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public Long countNoteApplyEntityByCondition(NoteApplyDto noteApplyDto) {
		// 输入参数校验
		if (null == noteApplyDto) {
			throw new SettlementException("内部错误，查询条件为空");
		}
		return noteApplyDao.countNoteApplyEntityByCondition(noteApplyDto);
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
	public NoteApplicationEntity queryNoteApplyEntityID(String idNoteApply) {
		if(StringUtils.isEmpty(idNoteApply)) {
			throw new SettlementException("ID参数为空");
		}
		return noteApplyDao.queryNoteApplyEntityID(idNoteApply);
	}
	
	/**
	 * @param noteApplyDao
	 */
	public void setNoteApplyDao(INoteApplyDao noteApplyDao) {
		this.noteApplyDao = noteApplyDao;
	}

	
	/**
	 * @param  noteDetailsDao  
	 */
	public void setNoteDetailsDao(INoteDetailsDao noteDetailsDao) {
		this.noteDetailsDao = noteDetailsDao;
	}

}
