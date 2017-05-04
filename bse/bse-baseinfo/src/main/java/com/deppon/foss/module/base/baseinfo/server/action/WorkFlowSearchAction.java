package com.deppon.foss.module.base.baseinfo.server.action;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWorkFlowSearchService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AttachementVo;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.WorkFlowEntityVo;
import com.deppon.foss.module.base.baseinfo.server.util.EncryptUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 临时欠款额度工作流Aciton类
 * @author 078816-WangPeng
 * @date   2014-02-11
 *
 */
public class WorkFlowSearchAction extends AbstractAction implements ModelDriven<DeptTempArrearsWorkFlowDto>{

	/**
	 * VersionUID
	 */
	private static final long serialVersionUID = -8390094176408478124L;
	
	//存储工作流的相关信息
	private WorkFlowEntityVo  workFlowEntityVo = new WorkFlowEntityVo();
	
	//存储上传文件的相关信息
	private AttachementVo attachementVo = new AttachementVo();
	
	//临欠额度工作流DTO
	private DeptTempArrearsWorkFlowDto deptTempArrearsWorkFlowDto = new DeptTempArrearsWorkFlowDto();
	
	//工作流接口类
	private IWorkFlowSearchService workFlowSearchService;
	
	//营业部接口类
	private  ISaleDepartmentService saleDepartmentService;
	
	//文件上传下载接口
	private IAttachementService attachementService;
	
	/**
	 *<p>获取部门的最大临时欠款额度</p>	
	 * @date 2014-6-25 下午3:50:58
	 * @author 130566-ZengJunfan
	 * @return
	 */
	@JSON
	public String queryMaxTempArrears(){
		try {
			String deptCode =attachementVo.getSelectedSalesDept();
			//获取营业部门信息
			SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentByCodeNoCache(deptCode);
			
			BigDecimal tempArrears =null;
			if(null !=entity && null != entity.getMaxTempArrears()){
				tempArrears = entity.getMaxTempArrears().multiply(new BigDecimal(NumberConstants.NUMBER_100));
			}
			if(null == tempArrears){
				throw new BusinessException("目前该部门的最大临时欠款额度为空，不能起草！");
			}
			attachementVo.setMaxTempArrears(tempArrears);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage(),e);
		}
	}
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-19
	 * 根据业务逻辑键id查询上传的附件信息
	 */
	@JSON
	public String queryAttachmentContent(){
		String relatedKey = attachementVo.getAttachementEntity().getRelatedKey();
		if(StringUtils.isEmpty(relatedKey)){
			throw new BusinessException("业务 键为空！");
		}else{
			List<AttachementEntity> entity = attachementService.getAttachementInfos(relatedKey,null);
			attachementVo.setAttachementEntityList(entity);
		}
		return returnSuccess();
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 查看工作流详情
	 */
	@JSON
	public String arrearsWorkFlowQuery(){
		
		//对从OA传递过来的数据进行解密
		this.decryptDeptTempArrearsWorkFlowDto();
		//TODO
//		String workFlowNo = "13492802";
//		deptTempArrearsWorkFlowDto.setProcInstId(workFlowNo);
		//当前状态
//		String currentStatus = "";

		//当前审批人的集合
		String currentApprovalName = "";
		
		DeptTempArrearsWorkFlowDto workFlowDto = workFlowSearchService.queryWorkFlowInfos(deptTempArrearsWorkFlowDto);
		//获取工作流号
		String workFlowNo = deptTempArrearsWorkFlowDto.getProcInstId();
		if(null == workFlowDto){
			throw new BusinessException("查询不到工作流："+workFlowNo+"的相关信息!");
		}
		String workFlowStatus = workFlowDto.getWorkFlowStatus();
		if(StringUtils.isEmpty(workFlowStatus)){
			throw new BusinessException("工作流"+workFlowNo+"数据异常，工作流状态为空！");
		}else{
			if("WF_STATUS_APPROVALING".equals(workFlowStatus)){
				//TODO
				//拿到当前工作项的对象
				WorkItemInfo  workItemInfo = workFlowSearchService.getCurrentWorkItemInfo(Integer.parseInt(workFlowNo));
				currentApprovalName = workItemInfo.getPartiName();
//				currentStatus = workItemInfo.getActivityDefID();
				workFlowDto.setCurrentApproverName(currentApprovalName);
				workFlowDto.setCurrentApprover(FossUserContext.getCurrentUser().getUserName());
			}
		}
		//查询部门名称
		SaleDepartmentEntity entity =saleDepartmentService.querySaleDepartmentByCodeNoCache(workFlowDto.getApplyTempDept());
		if(null !=entity){
			workFlowDto.setApplyTempDeptName(entity.getName());
		}
		
		workFlowDto.setPageStatus("VIEW");
		workFlowEntityVo.setWorkFlowEntity(workFlowDto);
		ActionContext.getContext().put("workFlowEntityVo", workFlowEntityVo);
		return returnSuccess();
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 工作流起草
	 */
	@JSON
	public String arrearsWorkFlowDraft(){
		
		//对从OA传递过来的数据进行解密
	    this.decryptDeptTempArrearsWorkFlowDto();
//		String deptCode = "W011302040209";
		
		//获取当前登录人的所述部门编码
		String deptCode = FossUserContext.getCurrentDeptCode();
		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentByCodeNoCache(deptCode);
		//获取该部门的最大临时欠款额度

		BigDecimal tempArrears = entity.getMaxTempArrears().multiply(new BigDecimal(NumberConstants.NUMBER_100));
		if(null == tempArrears){
			throw new BusinessException("目前该部门的最大临时欠款额度为空，不能起草！");
		}
		DeptTempArrearsWorkFlowDto  workFlowDto = new DeptTempArrearsWorkFlowDto();
		workFlowDto.setCurrentTempArrears(tempArrears);
		workFlowDto.setApplyMan(FossUserContext.getCurrentUser().getUserName());
		workFlowDto.setApplyManName(FossUserContext.getCurrentUser().getEmpName());
		workFlowDto.setTitle(FossUserContext.getCurrentUser().getTitle());
		workFlowDto.setApplyManDeptName(FossUserContext.getCurrentDept().getName());
		workFlowEntityVo.setWorkFlowEntity(workFlowDto);
		ActionContext.getContext().put("workFlowEntityVo", workFlowEntityVo);
		return returnSuccess();
		
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 工作流审批 办理
	 */
	public String arrearsWorkFlowExamine(){
		//TODO
//		String workFlowNo = "13492799";
//		deptTempArrearsWorkFlowDto.setProcInstId(workFlowNo);
		//当前状态
//		String currentStatus = "";
		
		//对从OA传递过来的数据进行解密
		this.decryptDeptTempArrearsWorkFlowDto();
		//当前审批人的集合
		String currentApprovalName = "";
		
		//审批明细
		try {
			
			if(null == deptTempArrearsWorkFlowDto){
				throw new BusinessException("OA传递的对象为空，该工作流已经审批结束！");
			}else{
				if(StringUtils.isEmpty(deptTempArrearsWorkFlowDto.getProcInstId())){
					throw new BusinessException("执行办理操作时从OA传递的工作流号为空(该工作流可能已经审批结束，请重新查询)！");
				}
			}
			DeptTempArrearsWorkFlowDto workFlowDto = workFlowSearchService.queryWorkFlowInfos(deptTempArrearsWorkFlowDto);
			//获取工作流号
			String workFlowNo = deptTempArrearsWorkFlowDto.getProcInstId();
			if(null == workFlowDto){
				throw new BusinessException("查询不到工作流："+workFlowNo+"的相关信息!");
			}else{
				String workFlowStatus = workFlowDto.getWorkFlowStatus();
				if(StringUtils.isEmpty(workFlowStatus)){
					throw new BusinessException("工作流："+workFlowNo+"在FOSS系统中状态为空，请FOSS开发查看！");
				}else if(workFlowStatus.equals("WF_STATUS_APPROVAL_OVER")){
					throw new BusinessException("工作流："+workFlowNo+"已经审批结束！");
				}
			}
			
			
			//拿到当前工作项的对象
			WorkItemInfo  workItemInfo = workFlowSearchService.getCurrentWorkItemInfo(Integer.parseInt(workFlowNo));
			currentApprovalName = workItemInfo.getPartiName();
			
			workFlowDto.setPageStatus("APPROVAL");
			workFlowDto.setCurrentApproverName(currentApprovalName);
			workFlowDto.setCurrentApprover(FossUserContext.getCurrentUser().getUserName());
			//查询部门名称
			SaleDepartmentEntity entity =saleDepartmentService.querySaleDepartmentByCodeNoCache(workFlowDto.getApplyTempDept());
			if(null !=entity){
				workFlowDto.setApplyTempDeptName(entity.getName());
			}
			workFlowEntityVo.setWorkFlowEntity(workFlowDto);
//		ActionContext.getContext().put("approvalList", com.alibaba.fastjson.JSON.toJSON(approvalList));
			ActionContext.getContext().put("workFlowEntityVo", workFlowEntityVo);
			return returnSuccess();
		} catch (BusinessException e) {
			ActionContext.getContext().put("examineException", e.getMessage());
			return ERROR;
	  }
 }
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 查询该工作流审批记录
	 */
	@JSON
	public String queryWorkFlowTrackRecordsView(){
		try {
			//TODO
//			String workFlowNo = "13492802";
			String workFlowNo = workFlowEntityVo.getWorkFlowEntity().getProcInstId();
			deptTempArrearsWorkFlowDto.setProcInstId(workFlowNo);
			workFlowEntityVo.setApprovalList(workFlowSearchService.queryWorkFlowTrackRecord(deptTempArrearsWorkFlowDto));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 工作流起草保存
	 */
	@JSON
	public String addNewDeptTempArrearsWorkFlow(){
		deptTempArrearsWorkFlowDto = workFlowEntityVo.getWorkFlowEntity();
		List<String> idList = workFlowEntityVo.getAttachIdList();
		try {
			int m = workFlowSearchService.saveWorkFlowDraftInfos(deptTempArrearsWorkFlowDto,idList);
			if(m <= 0){
				workFlowEntityVo.setReturnInt(-1);
				throw new BusinessException("工作流起草失败！");
			}
			workFlowEntityVo.setReturnInt(NumberConstants.NUMBER_1);
			return returnSuccess();
		} catch (BusinessException e) {
		    return returnError(e.getMessage());
		}
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * 工作流审批保存(工作流审批意见)
	 */
	@JSON
	public String submitWorkFlowApprovalViews(){
		deptTempArrearsWorkFlowDto = workFlowEntityVo.getWorkFlowEntity();
		try {
		   int m = workFlowSearchService.saveWorkFlowExamineInfos(deptTempArrearsWorkFlowDto);
		   if(m <= 0){
				workFlowEntityVo.setReturnInt(-1);
				throw new BusinessException("该环节审批失败，请检查审批选项后重新提交！");
			}
			workFlowEntityVo.setReturnInt(NumberConstants.NUMBER_1);
		 return returnSuccess();
			} catch (BusinessException e) {
		 return returnError(e.getMessage());
	     }
	}
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-22
	 * 获取工作流流程图
	 */
	@JSON
	public String getWorkFlowFlowChart(){
		try {
			if(workFlowEntityVo.getReturnInt() == 0 && workFlowEntityVo.getApprovalList() == null
					&& workFlowEntityVo.getAttachIdList() == null && workFlowEntityVo.getWorkFlowEntity() == null
					&& workFlowEntityVo.getWorkFlowEntityList()== null){
				return returnSuccess();
			}
			String workFlowNum = workFlowEntityVo.getWorkFlowEntity().getProcInstId();
			if(StringUtils.isEmpty(workFlowNum)){
				throw new BusinessException("前台传递过来的工作流号为空，无法获取工作流流程图！");
			}
			ActionContext.getContext().put("workFlowNum", workFlowNum);
			return returnSuccess();
		} catch (BusinessException e) {
		    return returnError(e);
		}
	}
	
	/**
	 * 解密DeptTempArrearsWorkFlowDto
	 * @author lqs
	 * @date 2013-9-11 下午3:20:10
	 * @return
	 * @see
	 */
	private void decryptDeptTempArrearsWorkFlowDto(){

		deptTempArrearsWorkFlowDto.setProcInstId(EncryptUtil.decrypt(deptTempArrearsWorkFlowDto.getProcInstId(), EncryptUtil.DEFAULT_KEY));
		deptTempArrearsWorkFlowDto.setProcessDefName(EncryptUtil.decrypt(deptTempArrearsWorkFlowDto.getProcessDefName(), EncryptUtil.DEFAULT_KEY));
	}
	
	public WorkFlowEntityVo getWorkFlowEntityVo() {
		return workFlowEntityVo;
	}

	public void setWorkFlowEntityVo(WorkFlowEntityVo workFlowEntityVo) {
		this.workFlowEntityVo = workFlowEntityVo;
	}

	public void setWorkFlowSearchService(IWorkFlowSearchService workFlowSearchService) {
		this.workFlowSearchService = workFlowSearchService;
	}

	@Override
	public DeptTempArrearsWorkFlowDto getModel() {
		return deptTempArrearsWorkFlowDto;
	}

	public AttachementVo getAttachementVo() {
		return attachementVo;
	}

	public void setAttachementVo(AttachementVo attachementVo) {
		this.attachementVo = attachementVo;
	}

	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

}
