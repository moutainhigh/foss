package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaStatementManageSerive;
import com.deppon.foss.module.settlement.common.api.shared.domain.PdaStatementManageEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.ISettlementPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetAccountResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetAccountStatementSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;

/**
 * 
 * @ClassName: GetAccountStatementSuccessService 
 * @Description: TODO(对账单刷卡上传    对接人ZYA) 
 * @author HKB
 * @date 2016-1-27 下午4:02:31 
 *
 */
public class GetAccountStatementSuccessService implements IBusinessService<GetAccountResult, GetAccountStatementSuccessEntity>{

	private static final Log LOG = LogFactory.getLog(GetAccountStatementSuccessService.class);
	private IPdaStatementManageSerive pdaStatementManageService;
	private IPdaPosManageService pdaPosManageService;
	private IDeliveryPdaDao deliveryPdaDao;
	private ISettlementPdaToCubcService settlementPdaToCubcService;
	//调用CUBC接口
	private IPdaToCubcService pdaToCubcService;
	//解析参数
	@Override
	public GetAccountStatementSuccessEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		GetAccountStatementSuccessEntity entity = JsonUtil.parseJsonToObject(GetAccountStatementSuccessEntity.class,asyncMsg.getContent());		
		return entity;
	}

	//方法入口
	@Override
	public GetAccountResult service(AsyncMsg asyncMsg,
			GetAccountStatementSuccessEntity param) throws PdaBusiException {
		//校验参数的合法性
		this.validate(asyncMsg, param);
		//FOSS实体
		List<PdaStatementManageDto> pdaStatementManage = null;
		//CUBC实体
		PayInfoDO payInfoDO=new PayInfoDO();
		GetAccountResult result = new GetAccountResult();
		RequestDO requestDo=new RequestDO();
		List<AccountStatementResult> acctRusult=new ArrayList<AccountStatementResult>();
		List<String> errcoResult=new ArrayList<String>();
		//封装查询灰度的实体
		try{
			List<AccountStatementEntitys> accountStatementEntity = param.getAccountStatementEntitys();
			LOG.info("accountStatementEntity+++++ :" +JSON.toJSONString(accountStatementEntity));
			//对账单号数组
			List<String> InvoiceNos = new ArrayList<String>();
			for (int i = 0; i < accountStatementEntity.size(); i++) {
			  //单据编号长度
			  int len = accountStatementEntity.get(i).getPosCardDetailEntitys().size();
			  //对账单编号数组
			  //String[] statementBillNos = new String[len];
			  for(int j=0;j<len;j++){
				  InvoiceNos.add(accountStatementEntity.get(i).getPosCardDetailEntitys().get(j).getInvoiceNo());
			  }
			  //对账单号
			  requestDo.setSourceBillNos(InvoiceNos.toArray(new String[InvoiceNos.size()]));
			}
			//来源单据类型 --对账单
			requestDo.setSourceBillType("DZ");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.GetAccountStatementSuccessService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			//根据对账单号调用CUBC灰度接口,判断走CUBC还是FOSS
			String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			LOG.info("gsService :" +JSON.toJSONString(gsService));
			if(!StringUtils.isBlank(gsService)){
			   VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
			   //由于返回的是个list,需要用到循环
			   List<VestBatchResult> vestBatchResultList=vestResponse.getVestBatchResult();
			   if(!CollectionUtils.isEmpty(vestBatchResultList)){
				   List<AccountStatementEntitys> fossEntitys=new ArrayList<AccountStatementEntitys>();
				   List<AccountStatementEntitys> accountStatementEntitys=param.getAccountStatementEntitys();
				   for ( AccountStatementEntitys entity : accountStatementEntitys) {
					   List<PosCardDetailEntity> cubcList = new ArrayList<PosCardDetailEntity>();
					   List<PosCardDetailEntity> fossList = new ArrayList<PosCardDetailEntity>();
					   xxx: for ( PosCardDetailEntity posCardDetailEntity : entity.getPosCardDetailEntitys()) {
						   for (int i = 0; i < vestBatchResultList.size(); i++) {
								VestBatchResult vestBatchResult = vestBatchResultList
										.get(i);
								String cubcOrFoss = vestBatchResult
										.getVestSystemCode();
								for (String vestObject : vestBatchResult
										.getVestObject()) {
									if (posCardDetailEntity.getInvoiceNo().equals(vestObject)) {
										if (DeryConstant.GS_CUBC
												.equals(cubcOrFoss)) {
											cubcList.add(posCardDetailEntity);
										} else {
											fossList.add(posCardDetailEntity);
										}
										continue xxx;
									}
								}
							}
					   }
					// 执行CUBC
				    if (!cubcList.isEmpty()) {
				    	entity.setPosCardDetailEntitys(cubcList);
				    	//封装CUBC刷卡参数
						payInfoDO=settlementPdaToCubcService.wrapCommonQueryPayInfoDO(asyncMsg, entity);
						LOG.info("payInfoDO :" +JSON.toJSONString(payInfoDO));
						//调用CUBC对账单刷卡信息上传接口
						String responseStr=pdaToCubcService.getAccountStatementSuccess(payInfoDO);
						LOG.info("responseStr :" +JSON.toJSONString(responseStr));
						PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
						String isSuccess=payInfo.getIsSuccess();
						try {
							//CUBC处理结果返回
							result=cubcErrorCodeResult(param,entity,isSuccess,asyncMsg);
							//if(!CollectionUtils.isEmpty(result.getAccountStatementResult())){
								acctRusult.addAll(result.getAccountStatementResult());
								result.setAccountStatementResult(acctRusult);
							//}
							//if(!CollectionUtils.isEmpty(result.getErrcoMessage())){
								errcoResult.addAll(result.getErrcoMessage());
								result.setErrcoMessage(errcoResult);
							//}
						} catch (Exception e) {
							LOG.error(ExceptionUtils.getStackTrace(e));
						}
				      }
				    //执行FOSS
					if (!fossList.isEmpty()) {
						entity.setPosCardDetailEntitys(fossList);
						fossEntitys.add(entity);
					 }
				    
					if (!fossEntitys.isEmpty()) {
						param.setAccountStatementEntitys(fossEntitys);
						//封装刷卡参数
						List<PdaStatementManageDto> dto = wrapCommonQueryParamDto(asyncMsg,param);
						//封装T+0报表参数
						List<PosCardEntity> entitys = wrapPosCardEntitys(asyncMsg,param);
						LOG.info("-------调用foss接口开始----");
						long startTime = System.currentTimeMillis();
						// 将数据插入到T+0报表
						LOG.info("T+0报表参数" + entitys);
						pdaPosManageService.insertPosCardData(entitys);
						// 调用刷卡
						LOG.info("-------对账单还款-------");
						pdaStatementManage = pdaStatementManageService
								.statementRepayment(dto);
						long endTime = System.currentTimeMillis();
						LOG.info("调用foss接口耗时:" + (endTime - startTime));
						// 封装刷卡数据返回值
						result = wrapAccountStatementResult(pdaStatementManage,
								asyncMsg, param);
						LOG.info("对账单刷卡数据返回值封装结束");
					}
				   }
				 }
			  }
				   
				   
//			     for (int i = 0; i < vestBatchResult.size(); i++) {
//			     // 归属系统编码
//			     String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
//			     LOG.info("cubcOrFoss :" +JSON.toJSONString(cubcOrFoss));
//			     if(cubcOrFoss.equals(DeryConstant.GS_CUBC)){
//			    	 List<AccountStatementEntitys> accountStatementEntitys=param.getAccountStatementEntitys();
//					 for ( AccountStatementEntitys entity : accountStatementEntitys) {
//						//封装CUBC刷卡参数
//						payInfoDO=settlementPdaToCubcService.wrapCommonQueryPayInfoDO(asyncMsg, entity);
//						LOG.info("payInfoDO :" +JSON.toJSONString(payInfoDO));
//						//调用CUBC对账单刷卡信息上传接口
//						String responseStr=pdaToCubcService.getAccountStatementSuccess(payInfoDO);
//						LOG.info("responseStr :" +JSON.toJSONString(responseStr));
//						PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
//						String isSuccess=payInfo.getIsSuccess();
//						try {
//							//CUBC处理结果返回
//							result=cubcErrorCodeResult(param,entity,isSuccess,asyncMsg);
//							//if(!CollectionUtils.isEmpty(result.getAccountStatementResult())){
//								acctRusult.addAll(result.getAccountStatementResult());
//								result.setAccountStatementResult(acctRusult);
//							//}
//							//if(!CollectionUtils.isEmpty(result.getErrcoMessage())){
//								errcoResult.addAll(result.getErrcoMessage());
//								result.setErrcoMessage(errcoResult);
//							//}
//						} catch (Exception e) {
//							LOG.error(ExceptionUtils.getStackTrace(e));
//						}
//					 } 
//			     }else {//默认走FOSS
//			    	 LOG.info("-------调用foss接口开始----");
//					  long startTime = System.currentTimeMillis();
//					  // 将数据插入到T+0报表
//					  LOG.info("T+0报表参数" + entitys);
//					  pdaPosManageService.insertPosCardData(entitys);
//					  // 调用刷卡
//					  LOG.info("-------对账单还款-------");
//					  pdaStatementManage = pdaStatementManageService
//							.statementRepayment(dto);
//					  long endTime = System.currentTimeMillis();
//					  LOG.info("调用foss接口耗时:" + (endTime - startTime));
//					  //封装刷卡数据返回值
//					  result = wrapAccountStatementResult(pdaStatementManage,asyncMsg,param);
//					  LOG.info("对账单刷卡数据返回值封装结束");
//			     }
		} catch(BusinessException e){
			//保存刷卡数据异常信息
			LOG.error(LogUtil.logFormat(e));
			//savePosCardData(param.getAccountStatementEntitys(),LogUtil.logFormat(e),asyncMsg.getContent());
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			
		} catch(Exception e){
			//保存刷卡数据异常信息 
			LOG.error(LogUtil.logFormat(e));
			//savePosCardData(param.getAccountStatementEntitys(),LogUtil.logFormat(e),asyncMsg.getContent());
			throw new FossInterfaceException(null,"出现未知异常");
		}
		
		return result;
	}

	//保存刷卡数据
	private void savePosCardData(List<AccountStatementEntitys>  param,String errorCause,String content){
		try {
			for(int i=0;i<param.size();i++){
				param.get(i).setErrorCause(errorCause);
				param.get(i).setContent(content);
				deliveryPdaDao.saveNCIPaymentCard(param.get(i));
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}
	//操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_ACCOUNT_STATEMENT_SUCCESS.VERSION;
	}

	//异步
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
	 * @param entity
	 * @author： 245955  HKB
	 */
	private void validate(AsyncMsg asyncMsg, GetAccountStatementSuccessEntity entity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		// 工号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		// 部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 用户类型
		Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
		//交易流水号
		Argument.notNull(entity.getAccountStatementEntitys(), "entity.AccountStatementEntitys");
		
		
	}
	//封装刷卡参数
	private List<PdaStatementManageDto> wrapCommonQueryParamDto(AsyncMsg asyncMsg, GetAccountStatementSuccessEntity entity){
		List<PdaStatementManageDto> dto = new ArrayList<PdaStatementManageDto>();
		List<AccountStatementEntitys> entitys = entity.getAccountStatementEntitys();
		
		for(int i=0;i<entitys.size();i++){
			PdaStatementManageDto pdaStatement = new PdaStatementManageDto();
			//客户编码
			pdaStatement.setCustomerCode(entitys.get(i).getCustomerCode());
			//客户名称
			pdaStatement.setCustomerName(entitys.get(i).getCustomerName());
			//单据编号长度
			int len = entitys.get(i).getPosCardDetailEntitys().size();
			//对账单编号数组
			String[] statementBillNos = new String[len];
			for(int j=0;j<len;j++){
				statementBillNos[j] = entitys.get(i).getPosCardDetailEntitys().get(j).getInvoiceNo();
			}
			pdaStatement.setStatementBillNos(statementBillNos);
			//对账单版本号数组
			String[] versionNos = new String[len];
			for(int j=0;j<len;j++){
				versionNos[j] = entitys.get(i).getPosCardDetailEntitys().get(j).getVersion();
			}
			pdaStatement.setVersionNos(versionNos);
			//还款方式
			pdaStatement.setRepaymentType("CD");
			//交易流水号
			pdaStatement.setRemittanceNumber(entitys.get(i).getTradeSerialNo());
			//刷卡金额
			pdaStatement.setRepaymentAmount(entitys.get(i).getSerialAmount());
			//员工编号
			pdaStatement.setEmpCode(entitys.get(i).getCreateUserCode());
			//员工名称
			pdaStatement.setEmpName(entitys.get(i).getCreateUserName());
			//部门编码
			pdaStatement.setCurrentDeptCode(entitys.get(i).getCardDeptCode());
			//部门名称
			pdaStatement.setCurrentDeptName(entitys.get(i).getCardDeptName());
			dto.add(pdaStatement);
			
		}
		return dto;
		
	}

	//封装参数
	private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg, GetAccountStatementSuccessEntity param){
		List<PosCardEntity> dto =  new ArrayList<PosCardEntity>();
		List<AccountStatementEntitys> entitys = param.getAccountStatementEntitys();
		//所属模块
		for(int i=0;i<entitys.size();i++){
			PosCardEntity posCardEntity = new PosCardEntity();
			if("accountStatement".equals(entitys.get(i).getBelongModule())){
				posCardEntity.setBelongModule("对账单");
			}
			//流水号金额
			posCardEntity.setSerialAmount(entitys.get(i).getSerialAmount());
			//流水号
			posCardEntity.setTradeSerialNo(entitys.get(i).getTradeSerialNo());
			//刷卡部门编码
			posCardEntity.setCardDeptCode(asyncMsg.getDeptCode());
			//刷卡部门名称
			posCardEntity.setCardDeptName(entitys.get(i).getCardDeptName());
			//刷卡时时间
			posCardEntity.setCardTime(entitys.get(i).getOperateTime());
			//创建人名称
			posCardEntity.setCreateUser(entitys.get(i).getCreateUserName());
			//创建人编码
			posCardEntity.setCreateUserCode(asyncMsg.getUserCode());
			if("NCI_DRIVER".equals(asyncMsg.getUserType())){
				//是否司机
				posCardEntity.setIsDriver("true");
			} else if("NCI_USER".equals(asyncMsg.getUserType())){
				//是否司机
				posCardEntity.setIsDriver("false");
			}
			//是否快递
			posCardEntity.setIsKd("false");
			PosCardDetailEntity entity = null;
			List<PosCardDetailEntity> detail = entitys.get(i).getPosCardDetailEntitys();
			List<PosCardDetailEntity> details = new ArrayList<PosCardDetailEntity>();
			for(int j=0;j<detail.size();j++){
				entity = new PosCardDetailEntity();
				//单据金额
				entity.setAmount(detail.get(j).getAmount());
				//交易流水号
				entity.setTradeSerialNo(detail.get(j).getTradeSerialNo());
				//单据类型
				entity.setInvoiceType("对账单");
				//单据号
				entity.setInvoiceNo(detail.get(j).getInvoiceNo());
				details.add(entity);
			}
			posCardEntity.setPosCardDetailEntitys(details);
			dto.add(posCardEntity);
		}
		
		return dto;
		
	}
	
	//封装返回值
	private GetAccountResult wrapAccountStatementResult(List<PdaStatementManageDto> pdaStatementManage,AsyncMsg asyncMsg,GetAccountStatementSuccessEntity param){
		GetAccountResult result = new GetAccountResult();
		List<String> msg = new ArrayList<String>();
		List<AccountStatementResult> statementResult = new ArrayList<AccountStatementResult>();
		int len = pdaStatementManage.size();
		for(int i=0;i<len;i++){
			
			if("N".equals(pdaStatementManage.get(i).getIsSuccess())){
				//刷卡失败的对账单集合
				List<String> statementNos = pdaStatementManage.get(i).getStatementNos();
				//刷卡失败记录错误信息
				Map<String, String> msgMap = pdaStatementManage.get(i).getMsgMap();
				
				if(statementNos != null && !statementNos.isEmpty()){
					for(int j=0;j<statementNos.size();j++){
						//失败单号及原因
						String errcoMessage = statementNos.get(j)+":"+msgMap.get(statementNos.get(j));
						//保存数据
						//savePosCardData(param.getAccountStatementEntitys(),errcoMessage,asyncMsg.getContent());
						//添加异常单号及原因
						msg.add(errcoMessage);
					}
				}	
				
			} else {
				// 部门未核销成功
				List<PdaStatementManageEntity> statementEntitys = pdaStatementManage
						.get(i).getStatementEntitys();
				AccountStatementResult entity = null;
				if (statementEntitys != null && !statementEntitys.isEmpty()) {
					for (int j = 0; j < statementEntitys.size(); j++) {
						
						entity = new AccountStatementResult();
						// 对账单号
						entity.setStatementNo(statementEntitys.get(j)
								.getStatementNo());
						// 客户编码
						entity.setCustomeCode(statementEntitys.get(j)
								.getCustomeCode());
						// 客户名称
						entity.setCustomerName(statementEntitys.get(j)
								.getCustomerName());
						// 对账单金额
						entity.setAmount(statementEntitys.get(j).getAmount());
						// 未核销金额
						entity.setUnVerifyAmount(statementEntitys.get(j)
								.getUnVerifyAmount());
						// 未还款金额
						entity.setUnpaidAmount(statementEntitys.get(j)
								.getUnpaidAmount());
						// 版本号
						entity.setVersion(statementEntitys.get(j).getVersion());
						statementResult.add(entity);
					}	
				}
			}
		}
		// 返回失败的单号及原因
		result.setErrcoMessage(msg);
		// 返回部分核销的数据
		result.setAccountStatementResult(statementResult);
		return result;
	} 
	/**
	 * CUBC处理结果
	 * @param param
	 * @param posCardDetailEntity
	 * @param isSuccess
	 * @param asyncMsg
	 * @return
	 */
	private GetAccountResult cubcErrorCodeResult(GetAccountStatementSuccessEntity param,AccountStatementEntitys accountStatementEntitys,String isSuccess,AsyncMsg asyncMsg){
		GetAccountResult result = new GetAccountResult();
		List<String> msg = new ArrayList<String>();
		List<String> statementNos=new ArrayList<String>();
		List<AccountStatementResult> statementResult = new ArrayList<AccountStatementResult>();

		List<PosCardDetailEntity> posCardDetailEntity = accountStatementEntitys.getPosCardDetailEntitys();
		
		if(!CollectionUtils.isEmpty(posCardDetailEntity)){
		   for (int i = 0; i < posCardDetailEntity.size(); i++) {
			String invoiceNo = posCardDetailEntity.get(i).getInvoiceNo();
			//statementNos.clear();
			statementNos.add(invoiceNo);
			    
			if (isSuccess.equals("false")) {
				if (statementNos != null && !statementNos.isEmpty()) {
					for (int k = 0; k < statementNos.size(); k++) {
						// 失败单号
						String errcoMessage = statementNos.get(k);
						// 保存错误信息
						savePosCardData(param.getAccountStatementEntitys(),
								isSuccess, asyncMsg.getContent());
						// 添加异常单号
						msg.add(errcoMessage);
					}
				}
			}else{
				// 部门未核销成功
				AccountStatementResult entity = null;
				if (accountStatementEntitys != null) {
					entity=new AccountStatementResult();
					// 对账单号
					entity.setStatementNo(invoiceNo);
					// 未核销金额
					entity.setUnVerifyAmount(accountStatementEntitys.getSerialAmount());
					entity.setCustomeCode("Y");
					statementResult.add(entity);
				}
			 }
		    }
		  }

		// 返回失败的单号及原因
		result.setErrcoMessage(msg);
		// 返回部分核销的数据
		result.setAccountStatementResult(statementResult);
		
		return result;
	}

	public void setPdaStatementManageService(
			IPdaStatementManageSerive pdaStatementManageService) {
		this.pdaStatementManageService = pdaStatementManageService;
	}
	

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}


	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}

	public void setSettlementPdaToCubcService(
			ISettlementPdaToCubcService settlementPdaToCubcService) {
		this.settlementPdaToCubcService = settlementPdaToCubcService;
	}
	
}
