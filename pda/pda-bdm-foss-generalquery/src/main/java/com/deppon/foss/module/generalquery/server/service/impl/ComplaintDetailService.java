package com.deppon.foss.module.generalquery.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.shared.domain.ComplaintDetailEntity;
import com.deppon.foss.module.generalquery.shared.domain.QryComplaintDetailEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ComplaintDetail;
import com.deppon.foss.module.pickup.waybill.shared.vo.RewardFineDetailVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;


/**   
 * @ClassName QueryRewardFineService  
 * @Description 查询投诉（CRM）
 * @author  092038 张贞献  
 * @date 2015-4-14    
 */ 
public class ComplaintDetailService implements IBusinessService<List<ComplaintDetailEntity>, QryComplaintDetailEntity> {
	private static final Log LOG = LogFactory.getLog(ComplaintDetailService.class);
	
	private IPdaWaybillService pdaWaybillService;
	
	
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 解析包体
	 */
	@Override
	public QryComplaintDetailEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QryComplaintDetailEntity qryComplaintDetailEntity = JsonUtil.parseJsonToObject(QryComplaintDetailEntity.class, asyncMsg.getContent());
		qryComplaintDetailEntity.setEmpCode(asyncMsg.getUserCode());	
		
		return qryComplaintDetailEntity;
	}
	
	/**
	 * 服务方法
	 */
	@Override
	public List<ComplaintDetailEntity> service(AsyncMsg asyncMsg,
			QryComplaintDetailEntity qryComplaintDetailEntity) throws PdaBusiException {
		
		List<ComplaintDetailEntity> result = null;		
		List<ComplaintDetail> rs = null;
		try {
			//验证数据有效性
			this.validate(qryComplaintDetailEntity);
			RewardFineDetailVo vo = new RewardFineDetailVo();
			vo.setEmpCode(qryComplaintDetailEntity.getEmpCode());
			vo.setStartTime(qryComplaintDetailEntity.getStartTime().getTime());
			vo.setEndTime(qryComplaintDetailEntity.getEndTime().getTime());
			
			rs = pdaWaybillService.queryComplaintInfoDetail(vo);
			
			
		
		
	    if(rs != null && rs.size()>0){
			//封装实体
	    	 result = new ArrayList<ComplaintDetailEntity>();
	    	 ComplaintDetailEntity dto = null;
	    	 for(ComplaintDetail entity:rs){
	    		 dto = new ComplaintDetailEntity();
	    		 
	    		 dto.setCallCust(entity.getCallCust());//来电客户
	    		 dto.setCallCustLevel(entity.getCallCustLevel());//客户等级
	    		 dto.setCallCustType(entity.getCallCustType());//客户类型
	    		 dto.setCustDemand(entity.getCustDemand());//客户要求
	    		 dto.setDealMatter(entity.getDealMatter());//处理语言
	    		 dto.setDealNumber(entity.getDealNumber());//处理编号
	    		 dto.setDutyDepartment(entity.getDutyDepartment());//责任部门
	    		 dto.setDutyPerson(entity.getDutyPerson());//责任人
	    		 dto.setSource(entity.getSource());//工单来源
	    		 dto.setTimeReport(new Date(entity.getReportTime()));//时间
	    		 dto.setVoucherNumber(entity.getVoucherNumber());//凭证号
	    		 dto.setCompLevel(entity.getCompLevel());//工单级别
	    		 dto.setContactPhone(entity.getContactPhone());//联系电话
	    		 dto.setBussType(entity.getBussType());//业务类型
	             dto.setErrorId(UUIDUtils.getUUID());//唯一ID
	    		 result.add(dto);
	    		 
	    	 }
	    	
	    	 
	    	 
		 }
			return result;
		} catch (BusinessException e) {
			LOG.error("查询投诉接口异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch (IOException e) {
			LOG.error("查询投诉接口异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getMessage());
		}
	}
	
	/**
	 * 验证数据有效性
	 * @param qryTraceInfoEntity
	 */
	private void validate(QryComplaintDetailEntity qryComplaintDetailEntity) {
		Argument.notNull(qryComplaintDetailEntity, "QryComplaintDetailEntity");		
	}
	

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_QUERY_COMPLAINT.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}
