package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.IPdaRepaymentService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.QueryWaybillEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.QueryWaybillResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
/**
 * 
 * @ClassName: QueryWaybillIsIsWriteOffService 
 * @Description: TODO(查询运单号是否核销--对接人肖年贺) 
 * @author &245955 HKB
 * @date 2016-4-1 上午8:27:08 
 *
 */
public class QueryWaybillIsIsWriteOffService implements IBusinessService<QueryWaybillResult, QueryWaybillEntity>{
	private IPdaRepaymentService pdaRepaymentService;
	private static final Log LOG = LogFactory.getLog(QueryWaybillIsIsWriteOffService.class);
	//boolean flag=true;
	//CUBC接口
	private IPdaToCubcService pdaToCubcService;
	//解析参数
	@Override
	public QueryWaybillEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QueryWaybillEntity entity = JsonUtil.parseJsonToObject(QueryWaybillEntity.class,asyncMsg.getContent());
		return entity;
	}

	@Override
	public QueryWaybillResult service(AsyncMsg asyncMsg,
			QueryWaybillEntity param) throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		QueryWaybillResult result = new QueryWaybillResult();
		//调用FOSS接口，校验运单是否核销
		try{
			//封装调用灰度接口的参数
			RequestDO requestDo=new RequestDO();
			String[] versionNos = new String[1];
			versionNos[0]=param.getWaybillNo();
			//运单号
			requestDo.setSourceBillNos(versionNos);
			//来源单据类型 --运单号
			requestDo.setSourceBillType("W");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.QueryWaybillIsIsWriteOffService.service");
			//客户编码
			requestDo.setCustomerCodes(null);
			//客户类型
			requestDo.setCustomerType(null);
			//来源系统
			requestDo.setOrigin("PDA");
			//始发部门是否是合伙人
			requestDo.setIsStartDeptPartner(0);
			//根据运单号调用归属服务
			String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			if(!StringUtils.isBlank(gsService)){
				 VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
				 //由于返回的是个list,需要用到循环
				 List<VestBatchResult> vestBatchResult=vestResponse.getVestBatchResult();
				 for (int i = 0; i < vestBatchResult.size(); i++) {
					// 归属系统编码
					String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
					if (cubcOrFoss.equals(DeryConstant.GS_CUBC)) {
						//调用CUBC的接口
						String isVerification=pdaToCubcService.queryWaybill(param.getWaybillNo());
						//Map<String, Class> classMap = new HashMap<String, Class>();
						//classMap.put("", PdaStatementManageDto.class);
						List<QueryWaybillResult> resultList = JSONObject.parseArray(isVerification, QueryWaybillResult.class);
						if(resultList !=null && resultList.size()>0){
						   result.setIswriteOff("Y");
						}else{
						   result.setIswriteOff("N");
						}	
					}else{//默认走FOSS
						LOG.info("-------调用foss接口开始----------");
						long startTime = System.currentTimeMillis();
						String isVerification = pdaRepaymentService.checkVerification(param.getWaybillNo());
						result.setIswriteOff(isVerification);
						long endTime = System.currentTimeMillis();
						LOG.info("调用foss接口耗时:" + (endTime - startTime));
					}
				}
			}
		} catch(BusinessException e){	
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){	
		throw new FossInterfaceException(null,"出现未知异常");
	}
		return result;
	}

	//参数校验
	private void validate(AsyncMsg asyncMsg,QueryWaybillEntity entity){
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		Argument.notNull(entity.getWaybillNo(), "entity.WaybillNo");
	}
	//操作类型
	@Override
	public String getOperType() {
		
		return DeliveryConstant.OPER_TYPE_WAYBILL_CHECK.VERSION;
	}

	//是否异步
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaRepaymentService(IPdaRepaymentService pdaRepaymentService) {
		this.pdaRepaymentService = pdaRepaymentService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}

}
