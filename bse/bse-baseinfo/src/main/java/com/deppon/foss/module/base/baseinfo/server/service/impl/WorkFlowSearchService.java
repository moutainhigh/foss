package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.bpmsapi.module.client.api.IBranchRuleManager;
import com.deppon.bpmsapi.module.client.api.IDpBpmsClientFacade;
import com.deppon.bpmsapi.module.client.api.IParticipantRuleManager;
import com.deppon.bpmsapi.module.client.api.impl.DpBpmsClientFacadeImpl;
import com.deppon.bpmsapi.module.client.domain.BpmsOperateInfo;
import com.deppon.bpmsapi.module.client.domain.BranchRule;
import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.bpmsapi.module.client.util.BPMSConstant;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkFlowSearchDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWorkFlowSearchService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;
import com.deppon.foss.module.base.baseinfo.server.util.BranchRuleManagerImpl;
import com.deppon.foss.module.base.baseinfo.server.util.ParticipantRuleManagerImpl;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 078816-WangPeng
 * @date   2014-02-11
 * @description 工作流操作Service
 *
 */
public class WorkFlowSearchService implements IWorkFlowSearchService {

	//工作流操作Dao接口
	private IWorkFlowSearchDao workFlowSearchDao;
	
	//营业部派送部接口
	private ISaleDepartmentService saleDepartmentService;
	
	//附件类接口
	private IAttachementService attachementService;
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 根据条件查询工作流的内容
	 *
	 */
	@Override
	public DeptTempArrearsWorkFlowDto queryWorkFlowInfos(
			DeptTempArrearsWorkFlowDto entity) {
		DeptTempArrearsWorkFlowDto workFlowDto = null;
		if(null != entity){
			if(StringUtils.isEmpty(entity.getProcInstId())){
				throw new BusinessException("获取的工作流号为空");
			}else{
				workFlowDto = workFlowSearchDao.queryWorkFlowInfos(entity);
			}
		}else{
			throw new BusinessException("获取OA的对象为空！");
		}
		return workFlowDto;
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存起草的工作流的内容
	 *
	 */
	@Override
	@Transactional
	public int saveWorkFlowDraftInfos(DeptTempArrearsWorkFlowDto entity,List<String> idList) throws BusinessException{
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContext.getCurrentUser().getUserName(),
				FossUserContext.getCurrentUser().getEmpName());
		
		//流程定义名称
		String processDefName = "com.deppon.bpms.module.foss.bpsdesign.financial.temporaryDebtLimit";
		//流程实例名称
		String processInstName = "临时欠款额度申请";
		//流程描述
		String processDesc = "临时欠款额度申请";
		//业务编码（待定）
		String bizCode = this.createUniqueBizCode();
		boolean flag = false;
		DeptTempArrearsWorkFlowDto dto = new  DeptTempArrearsWorkFlowDto();
		dto.setBizCode(bizCode);
		dto.setActive(FossConstants.ACTIVE);
		flag = this.queryIsExistWorkFlowDtoByBizCode(dto);
		while(flag){
			bizCode = this.createUniqueBizCode();
			dto.setBizCode(bizCode);
			dto.setActive(FossConstants.ACTIVE);
			flag = this.queryIsExistWorkFlowDtoByBizCode(dto);
		}
		

		// 执行创建并起草方法(生成工作流号)
		long workNum = 0;
		workNum = client.createAndStartProcess(processDefName,
				processInstName, processDesc, bizCode);
		if(workNum == 0){
			throw new BusinessException("调用OA接口生成工作流失败！");
		}
		String workFlowNo = String.valueOf(workNum);
		entity.setId(UUIDUtils.getUUID());
		entity.setProcInstId(workFlowNo);
		entity.setWorkFlowName("临时欠款额度申请");
		entity.setApplyMan(FossUserContext.getCurrentUser().getUserName());
		entity.setApplyTime(new Date());
		entity.setActive(FossConstants.ACTIVE);
		entity.setWorkFlowStatus("WF_STATUS_APPROVALING");
		entity.setProcessDefName(processDefName);
		entity.setWorkFlowType("TEMPORARYDEBTLIMIT_WF");
		entity.setBizCode(bizCode);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentUser().getUserName());
		entity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
		entity.setNote(this.replaceBlank(entity.getNote()));
		
		int m = workFlowSearchDao.saveWorkFlowDraftInfos(entity);
		
		AttachementEntity attachEntity = new AttachementEntity();
		if(idList.size() != 0){
			attachEntity.setRelatedKey(entity.getId());
			for (String attachId : idList) {
				attachEntity.setId(attachId);
				attachementService.updateAttachementInfo(attachEntity);
			}
		}
		return m;
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存审批结果
	 *
	 */
	@Override
	@Transactional
	public int saveWorkFlowExamineInfos(DeptTempArrearsWorkFlowDto entity)
			throws BusinessException {
		// TODO
//		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl("044288",
//				"陈阳");
		
//		 获取工作流系统客户端接口
		 IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
		 FossUserContext.getCurrentUser().getUserName(), FossUserContext
		 .getCurrentUser().getEmpName());

		if (null == entity) {
			throw new BusinessException("前台传入的对象为空！");
		}
		// 审批结果
		String approvalResult = entity.getApprovalResult();
		if (StringUtils.isEmpty(approvalResult)) {
			throw new BusinessException("前台传入的审批结果为空！");
		}

		// 构造审批人操作信息
		BpmsOperateInfo boi = new BpmsOperateInfo();
		// 设置审批时间
		boi.setOperateDate(entity.getApprovalTime());
		// 流程实例ID
		long processId = Long.parseLong(entity.getProcInstId());
		// 工作项ID
		long workItemId = 0;
		workItemId = this.getCurrentWorkItemInfo(processId).getWorkItemID();
		if (approvalResult.equals("Y")) {

			// 设置审批类型
			boi.setOperateType(BPMSConstant.APPROVE_OPERATETYPE_AGREE);
			// 设置审批意见
			boi.setApproveOpinion(this.replaceBlank(entity.getApprovalViews()));

		} else if (approvalResult.equals("N")) {
			// 设置审批类型
			boi.setOperateType(BPMSConstant.APPROVE_OPERATETYPE_DISAGREE);
			// 设置审批意见
			boi.setApproveOpinion(this.replaceBlank(entity.getApprovalViews()));
		}
		// 003679|044288|094692|104699这几个人审批
		int m = 0;
		try {
			if (approvalResult.equals("Y")) {
				// 操作类型 选择审批同意
				int operateType = BPMSConstant.APPROVE_OPERATETYPE_AGREE;
				BranchRule[] br = client.getNextActivities(workItemId, processId,
						operateType);
				// 如果是最后一个结点，审批完成后，自动更新营业部表中的最大临时欠款额度
				boolean flag = false;
				if (br.length != 0) {
					String defId = br[0].getNextActivity().getActivityDefId();
					if ("End".equals(defId)) {
						flag = true;
					}
				}
				if (flag) {

					entity.setModifyUser(entity.getCurrentApprover());
					entity.setWorkFlowStatus("WF_STATUS_APPROVAL_OVER");
					m = workFlowSearchDao.saveWorkFlowExamineInfos(entity);
					// TODO
//					SaleDepartmentEntity salesEntity = saleDepartmentService
//							.querySaleDepartmentByCodeNoCache("W011302040209");
					//wp_078816_2014-04-19 
					//130566 --增加卫星点部门申请，
					//获取申请人部门
					DeptTempArrearsWorkFlowDto dto	=this.queryWorkFlowInfos(entity);
					//非空校验
					if(null==dto ||StringUtils.isEmpty(dto.getApplyTempDept())){
						throw new BusinessException("获取申请人的部门编码为空！");
					}
					//String deptCode = entity.getApplyManDept();
					 SaleDepartmentEntity salesEntity = saleDepartmentService.querySaleDepartmentInfoByCode(dto.getApplyTempDept());
					if (null != entity.getAddNewTempArrears()) {
						salesEntity.setMaxTempArrears(salesEntity
								.getMaxTempArrears().add(
										entity.getAddNewTempArrears()));
					}
					saleDepartmentService.updateSaleDepartment(salesEntity);
				} else {
					entity.setModifyUser(entity.getCurrentApprover());
					entity.setWorkFlowStatus("WF_STATUS_APPROVALING");
					m = workFlowSearchDao.saveWorkFlowExamineInfos(entity);
				}
				// 调用send方法发送到下一步
				client.send(workItemId, processId, boi, null);
			} else {
				entity.setModifyUser(entity.getCurrentApprover());
				entity.setWorkFlowStatus("WF_STATUS_APPROVAL_OVER");
				m = workFlowSearchDao.saveWorkFlowExamineInfos(entity);
				// 调用send方法发送到下一步
				client.send(workItemId, processId, boi, null);
			}
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		return m;
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 查询工作流的操作记录
	 *
	 */
	@Override
	public List<ApprovalInfo> queryWorkFlowTrackRecord(
			DeptTempArrearsWorkFlowDto entity) {
		if(null == entity){
			throw new BusinessException("前台传入的对象为空");
		}
		if (StringUtils.isBlank(entity.getProcInstId())) {
			throw new BusinessException("工作流号为空");
		}
		IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
				FossUserContext.getCurrentUser().getUserName(),
				FossUserContext.getCurrentUser().getEmpName());
		return client
				.queryApprovalInfoByProcessInstID(Long.parseLong(entity.getProcInstId()));
	}

	public IWorkFlowSearchDao getWorkFlowSearchDao() {
		return workFlowSearchDao;
	}

	public void setWorkFlowSearchDao(IWorkFlowSearchDao workFlowSearchDao) {
		this.workFlowSearchDao = workFlowSearchDao;
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-12
	 * @description 获取审批中工作项ID（就是该步骤可能存在几个并行的节点）
	 *
	 */
	@Override
	public WorkItemInfo getCurrentWorkItemInfo(long processInstId)throws BusinessException {
		try {
			IDpBpmsClientFacade client = createDpBpmsClientFacadeImpl(
					FossUserContext.getCurrentUser().getUserName(),
					FossUserContext.getCurrentUser().getEmpName());
			WorkItemInfo[] workItems=client.getRuningWorkItems(processInstId,null);
			if(workItems.length <= 0){
				throw new BusinessException("调用OA接口获取当前工作项的对象为空！");
			}
			return workItems[0];
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
	}
  }

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-12
	 * @description 创建客户端实例
	 *
	 */
	@Override
	public IDpBpmsClientFacade createDpBpmsClientFacadeImpl(String userCode,
			String userName) {
		//业务系统实现的分支规则
		IBranchRuleManager brm = new BranchRuleManagerImpl();
		//业务系统实现的参与者规则
		IParticipantRuleManager prm = new ParticipantRuleManagerImpl();
		// API门面接口客户端
		IDpBpmsClientFacade client = new DpBpmsClientFacadeImpl(brm, prm,
				userCode, userName);
		return client;
	}

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-24
	 * @description 生成十九位不重复的业务编码
	 *
	 */
	private String createUniqueBizCode(){
		String bizCode = "";
		SimpleDateFormat dt = new SimpleDateFormat("yymmdd");
		String m = dt.format(new Date());
	    bizCode = "FOSSBSE"+m+workFlowSearchDao.recordCurrentDayWorkNums();
		return bizCode;
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-24
	 * @description 根据业务编码判断该编码是否已经存在
	 *
	 */
	private boolean queryIsExistWorkFlowDtoByBizCode(DeptTempArrearsWorkFlowDto entity){
		boolean flag = false;
		DeptTempArrearsWorkFlowDto dto = null;
		dto = workFlowSearchDao.queryWorkFlowInfos(entity);
		if( null != dto){
			flag = true;
		}
		return flag;
	}
	
	public IAttachementService getAttachementService() {
		return attachementService;
	}

	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-03-11
	 * @description java去除字符串中的空格、回车、换行符、制表符
	 *
	 */
	private String replaceBlank(String str) {
		        String dest = "";
		        if (str!=null) {
		            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		            Matcher m = p.matcher(str);
		            dest = m.replaceAll("");
		        }
		        return dest;
    }

}
