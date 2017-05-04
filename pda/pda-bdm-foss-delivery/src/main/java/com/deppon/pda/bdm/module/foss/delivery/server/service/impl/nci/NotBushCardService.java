package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.NotBushCardEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.WSCWayBillReturnResult;

/**
 * 
 * @ClassName: NotBushCardService 
 * @Description: TODO(查询登录人对应部门的所有待刷卡数据--对接人肖年贺) 
 * @author &245955  HKB
 * @date 2016-1-27 下午4:33:12 
 *
 */
public class NotBushCardService implements
		IBusinessService<List<WSCWayBillReturnResult>, NotBushCardEntity> {
	private static final Log LOG = LogFactory.getLog(NotBushCardService.class);
    private IWSCWayBillManageService wscWayBillManageService;
    //保存归属系统是CUBC还是FOSS
  	//String isSystem="";
    //调用CUBC接口
  	private IPdaToCubcService pdaToCubcService;
	//解析参数
	@Override
	public NotBushCardEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		NotBushCardEntity entity = JsonUtil.parseJsonToObject(
				NotBushCardEntity.class, asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public List<WSCWayBillReturnResult> service(AsyncMsg asyncMsg, NotBushCardEntity param)
			throws PdaBusiException {
		// 验证数据有效性
		this.validate(asyncMsg, param);
		//封装请求参数
		WSCWayBillParamEntity entity = new WSCWayBillParamEntity();
		entity.setOrgCode(asyncMsg.getDeptCode());
		List<WSCWayBillReturnResult> result = null;
		List<WSCWayBillEntity> wscWayBillEntityCubc=new ArrayList<WSCWayBillEntity>();
		List<WSCWayBillEntity> wscWayBillEntityFoss=new ArrayList<WSCWayBillEntity>();
		try {
			//封装调用灰度接口的参数
			RequestDO requestDo=new RequestDO();
			String[] versionNos = new String[1];
			versionNos[0]=param.getNumber();
			//运单号
			requestDo.setSourceBillNos(versionNos);
			//来源单据类型 --运单号
			requestDo.setSourceBillType("W");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.NotBushCardService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			if(param.getNumber()==null){
				try {
					String responseStr = pdaToCubcService.setNotBushCard(entity);
					wscWayBillEntityCubc= JSONObject.parseArray(responseStr,WSCWayBillEntity.class);
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
				try {
					//调用FOSS
					wscWayBillEntityFoss = wscWayBillManageService.getWayBillListByOrgCode(entity);
				} catch (Exception e) {
					LOG.error(ExceptionUtils.getStackTrace(e));
				}
				
			}else{
			  //根据运单号调用归属服务
			  String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			  if(!StringUtils.isBlank(gsService)){
			   VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
			   //由于返回的是个list,需要用到循环
			   List<VestBatchResult> vestBatchResult=vestResponse.getVestBatchResult();
				for (int i = 0; i < vestBatchResult.size(); i++) {
				  // 归属系统编码
				  String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
				  if(cubcOrFoss.equals(DeryConstant.GS_CUBC)){
					// 调用CUBC方法,查询所对用的部门的代刷卡数据
					String responseStr = pdaToCubcService.setNotBushCard(entity);
					wscWayBillEntityCubc = JSONObject.parseArray(responseStr,WSCWayBillEntity.class);
					/*if (null != wscWayBillEntityCubc && wscWayBillEntityCubc.size()>0) {
						result= wrapNotBushCardResult(wscWayBillEntityCubc);
						isSystem="Y";
					} */ 
				  }else{
					// 调用FOSS接口
					LOG.info("调用foss接口开始----查询待刷卡数据------");
					long startTime = System.currentTimeMillis();
					wscWayBillEntityFoss = wscWayBillManageService.getWayBillListByOrgCode(entity);
					long endTime = System.currentTimeMillis();
					LOG.info("调用foss接口耗时:" + (endTime - startTime));
					//result = wrapNotBushCardResult(wscWayBillReturn);
					LOG.info("查询待刷卡数据返回值封装结束"); 
			   }
			 }
		   }
		 }
		 wscWayBillEntityFoss=generatePdaStatementManageList(wscWayBillEntityCubc,wscWayBillEntityFoss);
		 
		 if(wscWayBillEntityFoss!=null && !CollectionUtils.isEmpty(wscWayBillEntityFoss)){
			 //返回结果
			 result= wrapNotBushCardResult(wscWayBillEntityFoss);
		 }
		
		} catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			
		} catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}
		return result;
	}
	
	//合并结果集
	private List<WSCWayBillEntity> generatePdaStatementManageList(List<WSCWayBillEntity> fossList, List<WSCWayBillEntity> cubcList){
		List<WSCWayBillEntity> list=new ArrayList<WSCWayBillEntity>();
		if(fossList!=null && !CollectionUtils.isEmpty(fossList)){
			list.addAll(fossList);
		}
		if(cubcList!=null && !CollectionUtils.isEmpty(cubcList)){
			list.addAll(cubcList);
		}
		return list;
	}
	//封装返回值
	private List<WSCWayBillReturnResult> wrapNotBushCardResult(List<WSCWayBillEntity> wscWayBillReturn){
		List<WSCWayBillReturnResult> list = new ArrayList<WSCWayBillReturnResult>();
		WSCWayBillReturnResult result = null;
		for(WSCWayBillEntity WSCWaybill : wscWayBillReturn){
			result = new WSCWayBillReturnResult();
			//发货人姓名
			result.setSendCustomerName(WSCWaybill.getSendCustomerName());
			//发货人编码
			result.setSendCustomerCode(WSCWaybill.getSendCustomerCode());
			//待刷卡金额
			result.setWaitSwipeAmount(WSCWaybill.getWaitSwipeAmount());
			//单据编号
			result.setWayBillNo(WSCWaybill.getWayBillNo());
			//单据来源 1:运单开单   2 :运单更改
			if("1".equals(WSCWaybill.getWayBillSource())){
				result.setWayBillSource("运单开单");
			} else if ("2".equals(WSCWaybill.getWayBillSource())){
				result.setWayBillSource("运单更改");
			}else{
				result.setWayBillSource(WSCWaybill.getWayBillSource());
			}
			//数据条目编号
			result.setWscItemId(WSCWaybill.getWscItemId());
			//运单总金额
			result.setWayBillAmount(WSCWaybill.getWayBillAmount());
			//归属系统
			if(WSCWaybill.getSystemSour()!=null && WSCWaybill.getSystemSour().equals(DeryConstant.GS_CUBC)){
				result.setAffiliation(DeryConstant.GS_CUBC);
			}else{
				result.setAffiliation(DeryConstant.GS_FOSS);
			}
			list.add(result);
		}
		return list;
	}

	//操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_All_NOT_BUSH_CARD.VERSION;
	}

	// 同步
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数的合法性)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param notBushCardEntity
	 * @author： 245955  HKB
	 */
	private void validate(AsyncMsg asyncMsg, NotBushCardEntity notBushCardEntity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(notBushCardEntity, "notBushCardEntity");
		// 工号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		// 部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 用户类型
		//Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
		//单号
		/*Argument.notEmpty(notBushCardEntity.getNumber(),
				"notBushCardEntity.number");*/
	}

	

	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}

}