package com.deppon.foss.module.generalquery.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.shared.domain.QryRewardFineEntity;
import com.deppon.foss.module.generalquery.shared.domain.ReFineDetailEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.RewardFineDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.RewardFineDetailVo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;


/**   
 * @ClassName QueryRewardFineService  
 * @Description 查询差错(OA)
 * @author  092038 张贞献  
 * @date 2015-4-14    
 */ 
public class MistakeDetailService implements IBusinessService<List<ReFineDetailEntity>, QryRewardFineEntity> {
	private static final Log LOG = LogFactory.getLog(MistakeDetailService.class);
	
	private IPdaWaybillService pdaWaybillService;
	
	
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 解析包体
	 */
	@Override
	public QryRewardFineEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QryRewardFineEntity qryTraceInfoEntity = JsonUtil.parseJsonToObject(QryRewardFineEntity.class, asyncMsg.getContent());
		qryTraceInfoEntity.setEmpCode(asyncMsg.getUserCode());	
		
		return qryTraceInfoEntity;
	}
	
	/**
	 * 服务方法
	 */
	@Override
	public List<ReFineDetailEntity> service(AsyncMsg asyncMsg,
			QryRewardFineEntity qryRewardFineEntity) throws PdaBusiException {
		
		List<ReFineDetailEntity> result = null;		
		List<RewardFineDetailEntity>  rs = null;
		try {
			//验证数据有效性
			this.validate(qryRewardFineEntity);
			
			RewardFineDetailVo vo = new RewardFineDetailVo();
			vo.setEmpCode(qryRewardFineEntity.getEmpCode());
			vo.setStartTime(qryRewardFineEntity.getStartTime().getTime());
			vo.setEndTime(qryRewardFineEntity.getEndTime().getTime());
			
			
			rs = pdaWaybillService.queryRewardFineDetail(vo);
			
			
		
	     if(rs != null && rs.size()>0){
			//封装实体
	    	 result = new ArrayList<ReFineDetailEntity>();
	    	 ReFineDetailEntity dto = null;
	    	 for(RewardFineDetailEntity entity : rs){
	    		 dto = new ReFineDetailEntity();
	    	
	    		 dto.setEmpName(entity.getEmpName());//负激励人
	    		 dto.setErrorId(entity.getErrorId());  //差错id
	    		 dto.setMoney(entity.getMoney()); //金额
	    		 dto.setOrgName(entity.getOrgName()); //责任部门
	    		 dto.setSurveyContent(entity.getSurveyContent()); //调查内容
	    		 dto.setTypeName(entity.getTypeName());//差错类型
	    		 dto.setWaybillNo(entity.getWaybillId());//运单号
	    		 dto.setOperatorTime(entity.getOperatorTime());//处理时间
	    		 dto.setOperator(entity.getOperator());//处理人
	    		 
	    		 result.add(dto);
	    	 }
	    	 
	    	 
	    	 
		 }
			return result;
		} catch (BusinessException e) {
			LOG.error("查询差错服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch (IOException e) {
			throw new FossInterfaceException(e.getCause(),e.getMessage());
		}
	}
	
	/**
	 * 验证数据有效性
	 * @param qryTraceInfoEntity
	 */
	private void validate(QryRewardFineEntity qryRewardFine) {
		Argument.notNull(qryRewardFine, "QryRewardFine");		
	}
	

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_QUERY_MISTAKE.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}
