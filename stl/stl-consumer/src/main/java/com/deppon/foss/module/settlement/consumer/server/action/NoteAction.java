package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.NoteVo;

/**
 * 小票单据Action.
 *
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-30 下午1:19:48
 */
public class NoteAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7142531313452305091L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(NoteAction.class);

	// 小票单据申请审批查询页面
	/** The Constant QUERY_PAGE_APPLY. */
	private static final String QUERY_PAGE_APPLY = "APPLY";

	// 小票单据发放查询页面
	/** The Constant QUERY_PAGE_ISSUE. */
	private static final String QUERY_PAGE_ISSUE = "ISSUE";

	// 小票单据入库查询页面
	/** The Constant QUERY_PAGE_STORAGE. */
	private static final String QUERY_PAGE_STORAGE = "STORAGE";

	// 小票单据核销查询页面
	/** The Constant QUERY_PAGE_WRITEOFF. */
	private static final String QUERY_PAGE_WRITEOFF = "WRITEOFF";

	/** 前端参数VO. */
	private NoteVo noteVo;

	/** 申请入库与明细Service. */
	private INoteService noteService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 票据申请初始化页面.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 下午5:09:48
	 */
	public String initApply() {
		return returnSuccess();
	}

	/**
	 * 查询小票单据信息.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:06:50
	 */
	@JSON
	public String queryApplyByCondition() {

		try {
			if (null == noteVo) {
				return returnError("查询参数无效");
			}
			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			// 小票单据申请审批查询
			if (StringUtils.equals(QUERY_PAGE_APPLY, noteVo.getQueryPage())) {
				if (StringUtils.isBlank(noteApplyDto.getApplyOrgCode())) {
					return returnError("申请部门无效,请输入");
				}
				// 验证申请开始日期是否为空
				if (null == noteApplyDto.getApplyStartTime()) {
					return returnError("请输入申请开始日期");
				}
				// 验证申请结束日期是否为空
				else if (null == noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请结束日期");
				}

				// 验证入库开始日期和结束日期 要么全空要么都不空
				if (null == noteApplyDto.getStorageStartTime()
						&& null != noteApplyDto.getStorageEndTime()) {
					return returnError("请输入入库开始日期");
				} else if (null != noteApplyDto.getStorageStartTime()
						&& null == noteApplyDto.getStorageEndTime()) {
					return returnError("请输入入库结束日期");
				}

				// 申请结束日期+1天
				noteApplyDto.setApplyEndTime(DateUtils.addDays(
						noteApplyDto.getApplyEndTime(), 1));

				// 校验入库日期是否为空
				if (null != noteApplyDto.getStorageStartTime()) {
					// 入库结束日期+1天
					noteApplyDto.setStorageEndTime(DateUtils.addDays(
							noteApplyDto.getStorageEndTime(), 1));
				}
			}// 小票单据下发查询
			else if (StringUtils
					.equals(QUERY_PAGE_ISSUE, noteVo.getQueryPage())) {
				// 验证申请开始日期和结束日期 要么全空要么都不空
				if (null == noteApplyDto.getApplyStartTime()
						&& null != noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请开始日期");
				} else if (null != noteApplyDto.getApplyStartTime()
						&& null == noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请结束日期");
				}

				// 验证下发开始日期和结束日期 要么全空要么都不空
				if (null == noteApplyDto.getIssuedStartTime()
						&& null != noteApplyDto.getIssuedEndTime()) {
					return returnError("请输入下发开始日期");
				} else if (null != noteApplyDto.getIssuedStartTime()
						&& null == noteApplyDto.getIssuedEndTime()) {
					return returnError("请输入下发结束日期");
				}

				// 开始日期与结束日期至少选择一个
				if (null == noteApplyDto.getApplyStartTime()
						&& null == noteApplyDto.getApplyEndTime()
						&& null == noteApplyDto.getIssuedStartTime()
						&& null == noteApplyDto.getIssuedEndTime()) {
					return returnError("申请日期与下发日期至少选择一个!");
				}

				// 申请日期是否为空
				if (null != noteApplyDto.getApplyEndTime()) {
					// 申请结束日期+1天
					noteApplyDto.setApplyEndTime(DateUtils.addDays(
							noteApplyDto.getApplyEndTime(), 1));
				}
				// 校验下发日期是否为空
				if (null != noteApplyDto.getIssuedEndTime()) {
					// 下发结束日期+1天
					noteApplyDto.setIssuedEndTime(DateUtils.addDays(
							noteApplyDto.getIssuedEndTime(), 1));
				}
			}// 小票单据入库查询
			else if (StringUtils.equals(QUERY_PAGE_STORAGE,
					noteVo.getQueryPage())) {

				// 验证申请开始日期是否为空
				if (null == noteApplyDto.getApplyStartTime()) {
					return returnError("请输入申请开始日期");
				}
				// 验证申请结束日期是否为空
				else if (null == noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请结束日期");
				}

				// 申请结束日期+1天
				noteApplyDto.setApplyEndTime(DateUtils.addDays(
						noteApplyDto.getApplyEndTime(), 1));

			}// 小票单据核销查询
			else if (StringUtils.equals(QUERY_PAGE_WRITEOFF,
					noteVo.getQueryPage())) {

				// 验证申请开始日期和结束日期 要么全空要么都不空
				if (null == noteApplyDto.getApplyStartTime()
						&& null != noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请开始日期");
				} else if (null != noteApplyDto.getApplyStartTime()
						&& null == noteApplyDto.getApplyEndTime()) {
					return returnError("请输入申请结束日期");
				}

				// 验证核销开始日期和结束日期 要么全空要么都不空
				if (null == noteApplyDto.getWriteoffStartTime()
						&& null != noteApplyDto.getWriteoffEndTime()) {
					return returnError("请输入核销开始日期");
				} else if (null != noteApplyDto.getWriteoffStartTime()
						&& null == noteApplyDto.getWriteoffEndTime()) {
					return returnError("请输入核销结束日期");
				}

				if (null == noteApplyDto.getApplyStartTime()
						&& null == noteApplyDto.getApplyEndTime()
						&& null == noteApplyDto.getWriteoffStartTime()
						&& null == noteApplyDto.getWriteoffEndTime()) {
					return returnError("申请日期与核销日期至少选择一个!");
				}

				// 申请日期是否为空
				if (null != noteApplyDto.getApplyEndTime()) {
					// 申请结束日期+1天
					noteApplyDto.setApplyEndTime(DateUtils.addDays(
							noteApplyDto.getApplyEndTime(), 1));
				}

				// 校验核销日期是否为空
				if (null != noteApplyDto.getWriteoffEndTime()) {
					// 核销结束日期+1天
					noteApplyDto.setWriteoffEndTime(DateUtils.addDays(
							noteApplyDto.getWriteoffEndTime(), 1));
				}
			} else {
				return returnError("查询有误");
			}

			// 设置用户数据查询权限
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			noteApplyDto.setCurrentUserCode(currentInfo.getEmpCode());

			// 查询总条数
			Long totalCount = noteService
					.countNoteApplyEntityByCondition(noteApplyDto);

			// 如果总条数大小0，查询小票申请列表
			if (null != totalCount && totalCount > 0) {
				// 获取申请信息列表
				List<NoteApplyDto> noteApplyEntityList = noteService
						.queryNoteApplyEntityByCondition(noteApplyDto,
								this.getStart(), this.getLimit());
				// 将列表传给前端Store
				noteVo.setNoteApplyEntityList(noteApplyEntityList);
				// 统计数据总记录数
				this.setTotalCount(totalCount);
			}

			// 返回成功
			return returnSuccess("查询成功");
		} catch (BusinessException e) {

			// 记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 查询小票单据明细信息.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:06:50
	 */
	@JSON
	public String queryNoteDetailsById() {
		try {
			// 获取前端信息
			NoteQueryDto noteQueryDto = noteVo.getNoteQueryDto();

			// 根据ID查询小票申请明细记录
			List<NoteQueryDto> noteQueryDtoList = noteService.queryNoteById(
					noteQueryDto, this.getStart(), this.getLimit());

			// 将结果放到VO中
			noteVo.setNoteQueryDtoList(noteQueryDtoList);

			// 根据ID查询小票申请明细记录总条数
			Long totalCount = noteService.countQueryNoteById(noteQueryDto);

			// 设置总count数
			this.setTotalCount(totalCount);

			// 返回成功
			return returnSuccess();
		} catch (BusinessException e) {

			// 记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 申请小票.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-16 上午10:53:08
	 */
	@JSON
	public String applicationNote() {
		try {
			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();
			/**
			 * 验证小票单据申请数据合法性
			 */
			if (null == noteApplyDto.getApplyAmount()
					|| noteApplyDto.getApplyAmount() <= 0) {
				return returnError("请输入申请数量!");
			}
			
			//限制小票单据一次只能申请一本
			if(noteApplyDto.getApplyAmount() != 1){
				return returnError("小票单据一次只能申请一本");
			}
			
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}

			// 新增小票申请
			noteService.applicationNote(noteApplyDto, currentInfo);

			// 返回成功
			return returnSuccess("保存成功");
		} catch (BusinessException e) {

			// 返回失败并记录日志
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 票据审核初始化页面.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 下午5:09:48
	 */
	public String initApprove() {
		return returnSuccess();
	}

	/**
	 * 小票单据下发.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-19 上午10:53:20
	 */
	@JSON
	public String createStockInNote() {
		List<MutexElement> mutexesList = new ArrayList<MutexElement>();
		try {
			// 获取前端信息
			NoteStockInDto noteStockInDto = noteVo.getNoteStockInDto();

			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			if (noteStockInDto.getExpressDeliveryNumber() != null
					&& noteStockInDto.getExpressDeliveryNumber().length() >= SettlementReportNumber.FIFTY) {
				throw new SettlementException("下发单号超过最长长度了");
			}

			if (CollectionUtils.isEmpty(noteStockInDto.getStockInList())) {
				throw new SettlementException("下发单号不能为空");
			}

			NoteStockInEntity entity = noteStockInDto.getStockInList().get(0);

			// add by liqin 2013-07-10 加上业务锁

			MutexElement mutexElement = null;

			int beginNo = entity.getBeginNo();
			int endNo = entity.getEndNo();

			for (int i = beginNo; i < endNo; i++) {
				mutexElement = new MutexElement(String.valueOf(i), "小票明细单据下发",
						MutexElementType.OTHER_REVENUE_NO);
				mutexesList.add(mutexElement);
			}

			if (CollectionUtils.isNotEmpty(mutexesList)) {
				businessLockService.lock(mutexesList,
						SettlementConstants.BUSINESS_LOCK_BATCH);
			}

			// 保存小票申请入库记录
			noteService.createNoteDetails(noteStockInDto, currentInfo);

			// 返回成功
			return returnSuccess("保存成功");
		} catch (BusinessException e) {

			// 记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		} finally {
			// 去除互斥锁
			if (CollectionUtils.isNotEmpty(mutexesList)) {
				// 解锁
				businessLockService.unlock(mutexesList);
			}
		}
	}

	/**
	 * 票据核销初始化页面.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 下午5:09:48
	 */
	public String initWriteoff() {
		return returnSuccess();
	}

	/**
	 * 票据入库初始化页面.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 下午5:09:48
	 */
	public String initStorage() {
		return returnSuccess();
	}

	/**
	 * 审批操作.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String approvalNote() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}

			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			/**
			 * 对审批参数进行校验
			 */
			if (StringUtils.isBlank(noteApplyDto.getId())) {
				return returnError("审核无效");
			}
			// 校验审批状态是否选择
			if (StringUtils.isBlank(noteApplyDto.getApproveStatus())) {
				return returnError("请选择审核状态");
			}
			
			if (noteApplyDto.getApproveStatus()
					.equals(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__AUDIT_DISAGREE)
					&& noteApplyDto.getApproveNotes() != null
					&& noteApplyDto.getApproveNotes().length() > SettlementReportNumber.NINE_HUNDRED_AND_NINETY_NINE) {
				throw new SettlementException("备注信息超过最大长度");
			}
			// 校验当审批状态为未通过且又未说明原因的情况
			if (StringUtils
					.equals(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__AUDIT_DISAGREE,
							noteApplyDto.getApproveStatus())
					&& StringUtils.isBlank(noteApplyDto.getApproveNotes())) {
				return returnError("请填写备注信息");
			}

			// 保存小票单据审核信息
			noteService.approvalNote(noteApplyDto, currentInfo);

			// 返回成功
			return returnSuccess("审核成功");
		} catch (BusinessException e) {

			// 打印日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 下发操作.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String providerNote() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}

			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			// 下发数据范围
			List<NoteStockInEntity> stockInList = noteApplyDto.getStockInList();
			if (CollectionUtils.isEmpty(stockInList)) {
				return returnError("请添加下发编号信息");
			}

			// 保存小票单据下发信息
			noteService.providerNote(noteApplyDto, currentInfo);

			// 返回成功
			return returnSuccess("下发成功");
		} catch (BusinessException e) {

			// 打印日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 入库操作.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String storageInNote() {

		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}

			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			// 判断是否选中记录
			if (CollectionUtils.isEmpty(noteApplyDto.getNoteAppIds())) {
				return returnError("请选择您要入库的记录");
			}

			// 保存入库记录
			noteService.storageInNote(noteApplyDto, currentInfo);

			// 返回成功
			return returnSuccess("入库成功");
		} catch (BusinessException e) {

			// 记录日期并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 入库数据查询.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String queryStockIn() {
		try {
			// 获取前端信息
			NoteStockInDto noteStockInDto = noteVo.getNoteStockInDto();

			//查询入库数据
			List<NoteStockInEntity> noteStockInEntityList = noteService
					.queryStockIn(noteStockInDto, this.getStart(),
							this.getLimit());
			
			//设置查询列表
			noteVo.setNoteStockInEntityList(noteStockInEntityList);
			
			//查询总条数
			Long totalCount = noteService.countQueryStockIn(noteStockInDto);
			
			//设置总条数
			this.setTotalCount(totalCount);
			
			//返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			
			//记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 小票核销操作.
	 *
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String writeoffNote() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			
			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			//判断是否选中记录
			if (CollectionUtils.isEmpty(noteApplyDto.getNoteAppIds())) {
				return returnError("请选择要核销的记录");
			}
			
			//判断备注信息的长度
			if (noteApplyDto.getWriteoffNotes() != null
					&& noteApplyDto.getWriteoffNotes().length() > SettlementReportNumber.ONE_THOUSAND) {
				throw new SettlementException("备注信息超过最大长度");
			}
			
			
			// 核销小票记录
			noteService.writeoffNote(noteApplyDto, currentInfo);
			
			//返回成功
			return returnSuccess("小票申请核销成功!");
		} catch (BusinessException e) {
			
			//记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	
	/**
	 * 收回小票申请核销操作.
	 *
	 * @return the string
	 * @author 096793 liqin
	 * @date 2012-10-25 上午10:08:35
	 */
	@JSON
	public String reWriteoffNote() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			
			// 获取前端信息
			NoteApplyDto noteApplyDto = noteVo.getNoteApplyDto();

			//判断是否选中记录
			if (CollectionUtils.isEmpty(noteApplyDto.getNoteAppIds())) {
				return returnError("请选择要核销收回的申请记录");
			}
			
			if(!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__APPLY_WRITEOFF, noteApplyDto.getWriteoffStatus())){
				return returnError("小票申请的状态为申请核销，才能收回");
			}
			//进行反核销设置申请小票核销申请为未核销			
			noteApplyDto.setWriteoffStatus(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF);
			// 核销小票记录
			noteService.writeoffNote(noteApplyDto, currentInfo);
			
			//返回成功
			return returnSuccess("收回核销申请成功!");
		} catch (BusinessException e) {
			
			//记录日志并返回失败
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	
	
	/**
	 * Gets the note vo.
	 *
	 * @return noteVo
	 */
	public NoteVo getNoteVo() {
		return noteVo;
	}

	/**
	 * Sets the note vo.
	 *
	 * @param noteVo the new note vo
	 */
	public void setNoteVo(NoteVo noteVo) {
		this.noteVo = noteVo;
	}

	/**
	 * Sets the note service.
	 *
	 * @param noteService the new note service
	 */
	public void setNoteService(INoteService noteService) {
		this.noteService = noteService;
	}
	

	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
}
