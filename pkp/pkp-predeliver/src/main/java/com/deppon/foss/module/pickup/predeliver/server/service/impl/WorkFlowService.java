package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.bpms.module.shared.domain.CommonConstant;
import com.deppon.bpms.module.shared.domain.ProcessInfo;
import com.deppon.bpmsapi.module.client.api.IBranchRuleManager;
import com.deppon.bpmsapi.module.client.api.IDpBpmsClientFacade;
import com.deppon.bpmsapi.module.client.api.IParticipantRuleManager;
import com.deppon.bpmsapi.module.client.api.impl.DpBpmsClientFacadeImpl;
import com.deppon.bpmsapi.module.client.domain.BpmsOperateInfo;
import com.deppon.bpmsapi.module.client.domain.BranchRule;
import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.bpmsapi.module.client.util.BPMSConstant;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWorkFlowDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WorkFlowConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WorkFlowApproveDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.WorkFlowException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.WorkFlowStatusVo;
import com.deppon.foss.module.pickup.predeliver.bpm.BranchRuleManagerImpl;
import com.deppon.foss.module.pickup.predeliver.bpm.ParticipantRuleManagerImpl;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 工作流service
 * 
 * @author 李龙根
 * @date 2014-1-20 下午1:41:50
 * @since
 * @version
 */
public class WorkFlowService implements IWorkFlowService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WorkFlowService.class);

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private IAbandonGoodsApplicationService abandonGoodsApplicationService;

	private IWorkFlowDao workFlowDao;

	private IWaybillSignResultService waybillSignResultService;

	private IAbandonGoodsApplicationDao abandonGoodsApplicationDao;

	private INoLabelGoodsService noLabelGoodsService;
	/**
	 * 运单
	 * service接口
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;

	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

	/**
	 * 运单状态
	 */
	protected IActualFreightService actualFreightService;

	/**
	 * 中转出库
	 */
	protected IStockService stockService;

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * 货签信息数据持久层接口
	 */
	protected ILabeledGoodDao labeledGoodDao;
	/**
	 * 配置参数开关接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 开单接口
	 */
	private IWaybillService waybillService;
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	
	private IWaybillExpressService waybillExpressService;
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setAbandonGoodsApplicationDao(
			IAbandonGoodsApplicationDao abandonGoodsApplicationDao) {
		this.abandonGoodsApplicationDao = abandonGoodsApplicationDao;
	}

	/**
	 * 附件
	 */
	private IAttachementService attachementService;

	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setAbandonGoodsApplicationService(
			IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

	public void setWorkFlowDao(IWorkFlowDao workFlowDao) {
		this.workFlowDao = workFlowDao;
	}

	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}
	/**
	 *  营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 查询当前工作项集合
	 * 
	 * @param flowCode
	 * @return
	 * @see com.deppon.network.module.mgr.api.server.service.IWorkFlowService#workFlowProcDetail(java.lang.String)
	 */
	@Override
	public List<ApprovalInfo> workFlowProcDetail(String flowCode) {
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());
		return client
				.queryApprovalInfoByProcessInstID(Long.parseLong(flowCode));
	}

	/**
	 * 创建客户端实例
	 * 
	 * @param userCode
	 * @param userName
	 * @return
	 * @see com.deppon.network.module.mgr.api.server.service.IWorkFlowService#createDpBpmsClientFacadeImpl(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public IDpBpmsClientFacade createDpBpmsClientFacadeImpl(String userCode,
			String userName) {
		IBranchRuleManager brm = new BranchRuleManagerImpl();
		IParticipantRuleManager prm = new ParticipantRuleManagerImpl();
		// 获取客户端
		IDpBpmsClientFacade client = new DpBpmsClientFacadeImpl(brm, prm,
				userCode, userName);
		return client;
	}

	private String insertWorkFlow(String waybillNo, String serialNumber) {
		String orgCode = FossUserContextHelper.getOrgCode();
		// 查询大区类型
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
		// 当前人部门找到当前的上级事业部 然后找事业部的标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
		// 查询大区类型找不到
		if (org == null) {
			bizTypes = new ArrayList<String>();
			// 查询部门类型列表
			bizTypes.add(BizTypeConstants.ORG_DIVISION);
			// 事业部
			// 当前人部门找到当前的上级事业部 然后找事业部的标杆编码
			org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
			if (org == null) {
				// // 找不到事业部
				// return setUpResultDto(ResultDto.FAIL,
				// "申请弃货工作流失败,没有大区,没有事业部,请手动起草");
			}
		}
		WorkFlowEntity entity = new WorkFlowEntity();
		if (waybillNo.startsWith("w")) {
			// 无标签货
			AbandonedGoodsSearchDto AbandonedGoodsSearchDto = new AbandonedGoodsSearchDto();
			AbandonedGoodsSearchDto.setWaybillNo(waybillNo);
			List<AbandonGoodsResultDto> list = abandonGoodsApplicationDao
					.searchNoTagAbandonGoodsList(AbandonedGoodsSearchDto);
			if (!CollectionUtils.isEmpty(list)) {
				entity.setCustomerCooperateStatus(list.get(0)
						.getCustomerCooperateStatus());
			} else {
				throw new WorkFlowException(
						WorkFlowException.WORK_FLOW_DRAFT_FAILURE, "");
			}

		} else {
			// 正常弃货
			AbandonedGoodsDetailDto dto = abandonGoodsApplicationService
					.getAbandoneGoodsDto(waybillNo);
			entity.setCustomerCooperateStatus(dto.getCustomerCooperateStatus());
		}
		
		String bizCode;
		//判断是否存在该业务编码
		for(;;){
			bizCode = newBizCode();
			entity.setBizCode(bizCode);
			WorkFlowEntity workFlowEntity=this.workFlowDao.workFlowQuery(entity);
			if(null==workFlowEntity){
				break;
			}
		}
		
		entity.setId(UUIDUtils.getUUID());
		entity.setCustomerCooperateStatus(FossConstants.NO);
		entity.setCreateUserName(FossUserContextHelper.getUserName());
		entity.setCreateUserCode(FossUserContextHelper.getUserCode());
		entity.setCreateOrgCode(orgCode);
		entity.setCreateOrgName(FossUserContextHelper.getOrgName());
		entity.setRespectiveRegional(org.getUnifiedCode());
		entity.setRespectiveRegionalName(org.getName());
		entity.setWaybillNo(waybillNo);
		entity.setSerialNumber(serialNumber);
		entity.setCreateUserTitle(FossUserContext.getCurrentUser()
				.getEmployee().getTitle());
		this.workFlowDao.insertWorkFlow(entity);
		return bizCode;
	}

	@Override
	public WorkFlowEntity workFlowQuery(WorkFlowEntity entity) {
		return this.workFlowDao.workFlowQuery(entity);
	}

	private void updateWorkFlow(long processId, String waybillNo, String bizCode) {
		this.workFlowDao.updateWorkFlow(processId, bizCode, waybillNo);
	}

	@Override
	public String getIdByWaybillNo(String waybillNo, String serialNumber) {
		return (String) this.workFlowDao.getIdByWaybillNo(waybillNo,
				serialNumber);

	}

	/**
	 * 获取审批中工作项ID
	 * 
	 * @param processInstId
	 * @param activityDefId
	 * @return
	 * @see com.deppon.network.module.mgr.api.server.service.IWorkFlowService#getWorkItemId(long,
	 *      java.lang.String)
	 */
	@Override
	public WorkItemInfo getWorkItemId(long processInstId, String activityDefId) {
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());
		WorkItemInfo[] workItemInfo = client.getRuningWorkItems(processInstId,
				activityDefId);
		if (workItemInfo.length != 0) {
			return workItemInfo[0];
		} else {
			return null;
		}

	}

	@Override
	public WorkFlowStatusVo getWorkFlowStatus(long processInstId) {
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());
		WorkItemInfo[] workItemInfo = client.getRuningWorkItems(processInstId,
				null);
		WorkFlowStatusVo vo = new WorkFlowStatusVo();
		if (workItemInfo.length > 0) {
			WorkItemInfo workInfo = workItemInfo[0];
			vo.setPartiName(workInfo.getPartiName());
			vo.setFlowCode(String.valueOf(processInstId));
			vo.setCurrentStatus(workInfo.getActivityInstName().isEmpty() ? "已结束"
					: workItemInfo[0].getActivityInstName() + "审批中");
			vo.setDuty(client.getDutyByWorkItemId(workInfo.getWorkItemID()));
			vo.setActivityDefId(workInfo.getActivityDefID());
		}
		if (workItemInfo.length == 0) {
			vo.setFlowCode(String.valueOf(processInstId));
			vo.setCurrentStatus("已结束");
			Map map = new HashMap();
			map.put(CommonConstant.PROCESSINSTID, processInstId);
			List<ProcessInfo> list = client.getProcessInfo(map);
			vo.setResult(list.get(0).getCondition());

		}
		return vo;

	}

	/**
	 * 工作流审批
	 */
	@Transactional
	@Override
	public void workFlowApprove(WorkFlowApproveDto workFlowApproveDto) {
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());

		// 获取工作流信息
		WorkFlowEntity entity = new WorkFlowEntity();
		entity.setFlowCode(String.valueOf(workFlowApproveDto.getFlowCode()));
		entity = this.workFlowDao.workFlowQuery(entity);

		// 封装参数start
		BpmsOperateInfo boi = new BpmsOperateInfo();
		int operateType = BPMSConstant.APPROVE_OPERATETYPE_AGREE;
		if (!workFlowApproveDto.getApproveType()
				.equals(WorkFlowConstants.AGREE)) {
			operateType = BPMSConstant.APPROVE_OPERATETYPE_DISAGREE;
		}
		boi.setOperateType(operateType);
		boi.setOperateDate(new Date());
		boi.setApproveOpinion(workFlowApproveDto.getOpinion());
		// 封装参数end

		// 附件保存
		if (!CollectionUtils.isEmpty(workFlowApproveDto.getAttachementFiles())) {
			String id = getIdByWaybillNo(entity.getWaybillNo(),
					entity.getSerialNumber());
			saveAttachment(workFlowApproveDto.getAttachementFiles(), id);
		}

		// 工作流审批结束，行为start
		long processInstID = workFlowApproveDto.getFlowCode();
		WorkItemInfo itemIds = getWorkItemId(processInstID, null);
		if (null == itemIds) {
			throw new WorkFlowException(WorkFlowException.WORK_FLOW_END);
		}

		long workItemId = itemIds.getWorkItemID();
		BranchRule[] br = client.getNextActivities(workItemId, processInstID,
				BPMSConstant.APPROVE_OPERATETYPE_AGREE);

		// 审批状态
		String status = null;
		boolean isSendInvoiceInfo=false;
		WaybillEntity waybill=null;
		ActualFreightEntity af=null;
		
		//原单号
		boolean isSendInvoiceInfoOrg=false;
		WaybillEntity waybillOrg=null;
		ActualFreightEntity afOrg=null;
		
		if (("End".equals(br[0].getNextActivity().getActivityDefId()))
				|| (!workFlowApproveDto.getApproveType().equals(
						WorkFlowConstants.AGREE))) {

			// 创建弃货更新对象
			AbandonGoodsApplicationEntity abandonentity = ceateAbandonGoodsApplication(
					entity, workFlowApproveDto);

			// 查询传递参数dto
			AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
			dto.setWaybillNo(entity.getWaybillNo());
			dto.setProcessId(String.valueOf(processInstID));
			dto.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL);

			// 根据oa审批人信息创建用户
			CurrentInfo currentInfo = null;

			List<AbandonGoodsResultDto> isNoTagList = abandonGoodsApplicationDao
					.searchNoTagAbandonGoodsList(dto);

			// 操作人员信息
			currentInfo = createCurrentInfo();
			// 操作时间
			abandonentity.setOperateTime(new Date());

			// 如果是无标签货
			if (!CollectionUtils.isEmpty(isNoTagList)) {

				AbandonGoodsResultDto temp = isNoTagList.get(0);

				// 设置abandonentity的ID
				abandonentity.setId(temp.getId());

				// 审批同意

				if (workFlowApproveDto.getApproveType().equals(
						WorkFlowConstants.AGREE)) {
					// 审批通过
					status = AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS;

					// 中转 更新接口
					int result = 0;
					try {
						/*result = noLabelGoodsService
								.updateNoLabelGoodsProcessStatus(temp
										.getErrorNumber());*/
					} catch (Exception e) {
						throw new WorkFlowException(
								WorkFlowException.WORK_FLOW_OTHER_REASON);
					}
					if (Integer.valueOf(result) == 1) {
						throw new WorkFlowException(
								WorkFlowException.WORK_FLOW_ERROR);
					}

					if (Integer.valueOf(result) == 2) {
						throw new WorkFlowException(
								WorkFlowException.WORK_FLOW_APPROVE_FAILURE);
					}

				} else {
					// 审批不通过
					status = AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE;
				}

			} else {
				// 查询弃货
				List<AbandonGoodsResultDto> abandonedGoodsList = abandonGoodsApplicationDao
						.searchAbandonGoodsList(dto);

				// 设置abandonentity的ID
				abandonentity.setId(abandonedGoodsList.get(0).getId());

				if (workFlowApproveDto.getApproveType().equals(
						WorkFlowConstants.AGREE)) {
					try {
						// 中转出库
						outStock(entity.getWaybillNo(), currentInfo);
						ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
						//根据运单号查询 查询运单货物总件数
						WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(entity.getWaybillNo());
						if(null == waybillEntity){
							LOGGER.error("--该运单号不存在");//记录日志
							throw new WorkFlowException(SignException.WAYBILLNO_NULL);
						}
						isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybillEntity,actualFreightEntity);//是否将发票信息传给发票系统
						// 更新ActualFreight表中的结清状态为已结清
						updateActualFreight(actualFreightEntity);
						// 弃货签收接口
						waybillSignResultService.addAbandonGoodsSign(
								entity.getWaybillNo(), currentInfo);
						// 需要调用业务完结接口
						overWaybillTransaction(entity.getWaybillNo());
						waybill=waybillEntity;
						af=actualFreightEntity;
						if(WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){//快递
							WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(entity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
							if(waybillExpressEntity!=null){
								throw new WorkFlowException("该单号已经返货新开单,请使用新单号进行操作!");//该运单号不存在
							}
							List<WaybillExpressEntity> expList = waybillExpressService.queryWaybillListByWaybillNo(entity.getWaybillNo());
							WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(expList) ? expList.get(0) : null;
							if(newWaybillNoEntity!=null && StringUtils.isNotEmpty(newWaybillNoEntity.getOriginalWaybillNo())){//是新单号
								WorkFlowEntity entityOrg = new WorkFlowEntity();
								entityOrg.setWaybillNo(newWaybillNoEntity.getOriginalWaybillNo());
								dealOriginalWaybillNo(entityOrg, waybillOrg, afOrg, isSendInvoiceInfoOrg, currentInfo);
							}
						}
					} catch (BusinessException e) {
						throw e;
					}
					// 审批通过
					status = AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS;

				}

			}

			// 审批不同意
			if (workFlowApproveDto.getApproveType().equals(
					WorkFlowConstants.DISAGREE)) {
				status = AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE;
			}

			// 客户要求返货
			if (workFlowApproveDto.getApproveType().equals(
					WorkFlowConstants.CANCEL)) {
				status = AbandGoodsApplicationConstants.ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE;
			}

			abandonentity.setStatus(status);

			// 更新审批结果
			abandonGoodsApplicationDao.updateByKey(abandonentity);
			
//			//add by 231438，快递100，轨迹推送需求RFOSS2015031706 
//			// 丢货、弃货违禁品签收不给签收人和封装的签收方法分开区分
//			if(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS.equals(status)){
//				//弃货签收，推送轨迹 
//				WaybillTrackDto trackDto = new WaybillTrackDto(); // 货物轨迹推送Dto
//				//运单号
//				trackDto.setWaybillNo(entity.getWaybillNo());
//				//弃货签收，不需要描述信息;不需要签收人
//				trackDto.setOperateDesc(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN);
//				//登录信息：操作部门（编码、民称）
//				trackDto.setCurrentInfo(currentInfo);
//				//调用轨迹接口，推送轨迹
//				sendWaybillTrackService.sendTrackings(trackDto); //-- --add by 231438
//			}
		}

		// 发送审批
		boolean result;
		try {
			result = client.send(workItemId, processInstID, boi, null);
			if (!result) {
				throw new WorkFlowException(
						WorkFlowException.WORK_FLOW_APPROVE_FAILURE);
			}
			if(isSendInvoiceInfo){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybill,af);
			}
			if(isSendInvoiceInfoOrg){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybillOrg,afOrg);
			}
		} catch (Exception e) {
			throw new WorkFlowException(
					WorkFlowException.WORK_FLOW_APPROVE_FAILURE);
		}

	}
	private void dealOriginalWaybillNo(WorkFlowEntity entity,WaybillEntity waybill,ActualFreightEntity af,boolean isSendInvoiceInfoOrg,CurrentInfo currentInfo){
		// 中转出库
		outStockNoException(entity.getWaybillNo(), currentInfo);
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		//根据运单号查询 查询运单货物总件数
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(entity.getWaybillNo());
		if(null == waybillEntity){
			LOGGER.error("--该运单号不存在");//记录日志
			throw new WorkFlowException(SignException.WAYBILLNO_NULL);
		}
		isSendInvoiceInfoOrg=waybillSignResultService.isNeedSendInvoiceInfo(waybillEntity,actualFreightEntity);//是否将发票信息传给发票系统
		// 更新ActualFreight表中的结清状态为已结清
		updateActualFreight(actualFreightEntity);
		// 弃货签收接口
		waybillSignResultService.addAbandonGoodsSign(
				entity.getWaybillNo(), currentInfo);
		// 需要调用业务完结接口
		overWaybillTransaction(entity.getWaybillNo());
		waybill=waybillEntity;
		af=actualFreightEntity;
	}
	/**
	 * 创建弃货更新对象
	 * 
	 * @param req
	 * @return
	 */
	private AbandonGoodsApplicationEntity ceateAbandonGoodsApplication(
			WorkFlowEntity entity, WorkFlowApproveDto workFlowApproveDto) {
		// 工作流号 PROCESSINSTID number
		// 运单号 ORDERNO VARCHAR2(50)
		// 最后审批人工号 userid VARCHAR2(50)
		// 流程名称 workFlowName VARCHAR2(100)
		// 最后审批人工号 userid VARCHAR2(50)
		// 最后审批人名称 lastName VARCHAR2(50)
		// 最后审批部门标杆编码 finacode VARCHAR2(50)
		// 最后审批部门名称 deptName VARCHAR2(120)
		// 审批结果 result number
		// 不同意原因 reason VARCHAR2(2)

		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		// 创建弃货更新对象
		abandonentity.setWaybillNo(entity.getWaybillNo());
		// 事由
		abandonentity.setProcessId(entity.getFlowCode());
		// 工作流号
		return abandonentity;
		// 返回弃货更新对象
	}

	/**
	 * 
	 * <p>
	 * 保存附件<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-6
	 * @param fileList
	 * @param id
	 *            void
	 */
	private void saveAttachment(List<AttachementEntity> fileList, String id) {
		// 是否有附件
		if (CollectionUtils.isNotEmpty(fileList)) {
			for (AttachementEntity attachementEntity : fileList) {
				// 关联上传组件和业务
				attachementEntity.setRelatedKey(id);
				// 更新上传组件数据
				attachementService.updateAttachementInfo(attachementEntity);
			}
		} else {
			LOGGER.info("没有附件，不需要保存");
		}

	}

	/**
	 * 工作流退回
	 */
	@Override
	public void workFlowGoBack(WorkFlowApproveDto workFlowApproveDto) {
		// 构造审批人操作信息
		BpmsOperateInfo boi = new BpmsOperateInfo();
		// 设置审批类型
		boi.setOperateType(BPMSConstant.APPROVE_OPERATETYPE_GOBACK);
		// 设置审批时间
		boi.setOperateDate(new Date());
		// 设置审批意见
		boi.setApproveOpinion(workFlowApproveDto.getOpinion());
		// 调用方法获取可退回节点，此方法应该在点击回退按钮，或初始化页面时执行，
		// 本文此处调用以获取rules

		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());

		long processInstID = workFlowApproveDto.getFlowCode();
		WorkItemInfo itemIds = getWorkItemId(processInstID, null);
		long workItemID = itemIds.getWorkItemID();
		BranchRule[] rules = client.getNextActivities(workItemID,
				processInstID, BPMSConstant.APPROVE_OPERATETYPE_GOBACK);
		// 获取目标回退节点的定义ID（此处假定一个节点为用户所选节点）
		String defId = rules[0].getNextActivity().getActivityDefId();
		// 获取当前活动节点的实例ID
		long actInstId = rules[0].getCurrentActivity().getActivityInstId();
		// 判断节点是否可退回
		boolean result = client.canBackAcitivity(actInstId, defId);
		if (!result) {
			// 该节点不可退回
		}
		// 退回目标活动定义ID数组
		String[] destDefID = new String[1];
		destDefID[0] = defId;
		// 执行退回操作
		boolean result2 = client.backActivity(workItemID, processInstID, boi,
				destDefID);
		if (!result2) {
			throw new WorkFlowException(
					WorkFlowException.WORK_FLOW_GO_BACK_FAILURE);
		}

	}

	/**
	 * 工作流起草
	 */
	@Override
	public long createAndStartProcess(String waybillNo, String serialNumber) {
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContextHelper.getUserCode(),
				FossUserContextHelper.getUserName());
		
			// 保存工作流信息
			String bizCode = insertWorkFlow(waybillNo, serialNumber);
			// 执行创建并起草方法 -返回工作流id
			long processId= client.createAndStartProcess(
					WorkFlowConstants.STORE_EXCEPTION_GOODS,
					WorkFlowConstants.PROCESS_INST_NAME,
					WorkFlowConstants.PROCESS_DESC, bizCode);
			// 更新工作流号工作流信息
			updateWorkFlow(processId, waybillNo, bizCode);
			
			return processId;
	}

	/**
	 * 业务编码生成规则
	 * 
	 * @return
	 */
	private String newBizCode() {
		// 业务编码命名规范：系统编码（FOSSPKP）+日期（yyyymmdd）+随机生成6位不重复，
		// 共19位，例：FOSSPKP201401130001
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String code = sdf.format(new Date());
		code = "FOSSPKP" + code + getBiz();
		return code;
	}

	
	private static String getBiz(){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","u",
			"t","s","o","x","v","p","q","r","w","y","z"};
	
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < NumberConstants.NUMBER_6; i++)
		{
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		
		return str.toString();
	}
	
	/**
	 * 审批人信息创建用户
	 * 
	 * @return
	 */
	private CurrentInfo createCurrentInfo() {
		// 创建currentInfo对象
		return new CurrentInfo(FossUserContext.getCurrentUser(),
				FossUserContext.getCurrentDept());
		// 返回操作人
	}

	/**
	 * 中转出库
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param currentInfo
	 *            用户
	 * update by 231434-FOSS-bieyexiong 2015-5-18 上午10:45:03
	 */
	public void outStock(String waybillNo, CurrentInfo currentInfo) {
		// 获得该运单号对应的流水号列表
		List<LabeledGoodEntity> labelGoodList = labeledGoodDao
				.queryAllSerialByWaybillNo(waybillNo);

		if (labelGoodList != null && !labelGoodList.isEmpty()) {
			// 流水号列表不为空
			for (Iterator<LabeledGoodEntity> iterator = labelGoodList
					.iterator(); iterator.hasNext();) {
				// 迭代列表
				LabeledGoodEntity labeledGoodEntity = iterator.next();
				// 流水号

				InOutStockEntity stockEntity = new InOutStockEntity();
				// 创建出库对象
				stockEntity.setCreateDate(new Date());// 出库时间
				stockEntity.setCreateUser(currentInfo.getEmpCode());
				// 出库人
				stockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				// 出入库设备类型PC
				stockEntity.setWaybillNO(waybillNo);
				// 运单号
				stockEntity.setSerialNO(labeledGoodEntity.getSerialNo());
				// 流水号
				stockEntity.setOperatorCode(currentInfo.getEmpCode());
				// 操作人编号
				stockEntity.setOperatorName(currentInfo.getEmpName());
				// 操作人名
				stockEntity
						.setInOutStockType(StockConstants.ABANDON_GOODS_OUT_STOCK_TYPE);
				// 弃货出库出库
				stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
				// org code
				int resultStock = stockService.outStockPC(stockEntity);
			}

		}
		

		
	}
	
	/**
	 * 中转出库
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param currentInfo
	 *            用户
	 */
	private void outStockNoException(String waybillNo, CurrentInfo currentInfo) {
		// 获得该运单号对应的流水号列表
		List<LabeledGoodEntity> labelGoodList = labeledGoodDao
				.queryAllSerialByWaybillNo(waybillNo);

		if (labelGoodList != null && !labelGoodList.isEmpty()) {
			// 流水号列表不为空
			for (Iterator<LabeledGoodEntity> iterator = labelGoodList
					.iterator(); iterator.hasNext();) {
				// 迭代列表
				LabeledGoodEntity labeledGoodEntity = iterator.next();
				// 流水号

				InOutStockEntity stockEntity = new InOutStockEntity();
				// 创建出库对象
				stockEntity.setCreateDate(new Date());// 出库时间
				stockEntity.setCreateUser(currentInfo.getEmpCode());
				// 出库人
				stockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				// 出入库设备类型PC
				stockEntity.setWaybillNO(waybillNo);
				// 运单号
				stockEntity.setSerialNO(labeledGoodEntity.getSerialNo());
				// 流水号
				stockEntity.setOperatorCode(currentInfo.getEmpCode());
				// 操作人编号
				stockEntity.setOperatorName(currentInfo.getEmpName());
				// 操作人名
				stockEntity
						.setInOutStockType(StockConstants.ABANDON_GOODS_OUT_STOCK_TYPE);
				// 弃货出库出库
				stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
				// org code
				stockService.outStockDelivery(stockEntity);
			}
		}
	}

	/**
	 * 更新ActualFreight表中的结清状态为已结清
	 * 
	 * @param waybillNo
	 * update by 231434-FOSS-bieyexiong 2015-5-18 下午14:02:21
	 */
	public void updateActualFreight(ActualFreightEntity actualFreightEntity) {
		if (actualFreightEntity != null) {
			// 更新到达未出库数量为0
			actualFreightEntity
					.setArriveNotoutGoodsQty(NumberUtils.INTEGER_ZERO);
			// 运单号
			ActualFreightEntity actualFreightEntityForUpdate = new ActualFreightEntity();
			// 创建对象
			actualFreightEntityForUpdate.setId(actualFreightEntity.getId());
			// ID
			actualFreightEntityForUpdate.setSettleStatus("Y");
			// 已结清
			actualFreightEntityForUpdate.setSettleTime(new Date());
			//到达未出库件数为0
			actualFreightEntityForUpdate.setArriveNotoutGoodsQty(NumberUtils.INTEGER_ZERO);
			// 结清时间
			actualFreightService
					.updateWaybillActualFreight(actualFreightEntityForUpdate);
		}
		// UPDATE
	}

	/**
	 * 需要调用业务完结接口
	 * 
	 * @param req
	 * update by 231434-FOSS-bieyexiong 2015-5-18 下午14:10:29
	 */
	public void overWaybillTransaction(String waybillNo) {
		// 创建业务完结对象
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		// 设置运单号
		waybillTransactionEntity.setWaybillNo(waybillNo);
		// 业务完结 YES
		waybillTransactionEntity.setBusinessOver(FossConstants.YES);
		// 业务完结 update
		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
	}

	@Override
	public String queryOrgCodeByUnifiedCode(String unifiedCode) {
		return workFlowDao.queryOrgCodeByUnifiedCode(unifiedCode);
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}
	
}