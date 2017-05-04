package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.IPdaRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaRepaymentDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.ISettlementPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentDeatilSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentSuccessResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
/**
 * 
 * @ClassName: SettlementOfPaymentSuccessService 
 * @Description: TODO(结清货款模块刷卡成功数据--对接人ZYA) 
 * @author &245955  
 * @date 2016-03-10 
 *
 */
public class SettlementOfPaymentSuccessService implements IBusinessService<SettlementOfPaymentSuccessResult, SettlementOfPaymentSuccessEntity>{
	private static final Log LOG = LogFactory.getLog(SettlementOfPaymentService.class);
	private IPdaPosManageService pdaPosManageService;
	private IPdaRepaymentService pdaRepaymentService;
	private IDeliveryPdaDao deliveryPdaDao;
	//CUBC接口
	private ISettlementPdaToCubcService settlementPdaToCubcService;
	private IPdaToCubcService pdaToCubcService;
	//boolean flag=true;
	
	//解析参数
	@Override
	public SettlementOfPaymentSuccessEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		SettlementOfPaymentSuccessEntity entity = JsonUtil.parseJsonToObject(SettlementOfPaymentSuccessEntity.class,asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public SettlementOfPaymentSuccessResult service(AsyncMsg asyncMsg,
			SettlementOfPaymentSuccessEntity param) throws PdaBusiException {
		//CUBC实体
		PayInfoDO payInfoDO=null;
		// 校验参数的合法性
		this.validate(asyncMsg, param);
		// FOSS返回成功或者失败
		List<PdaRepaymentDto> result = null;
		SettlementOfPaymentSuccessResult list = new SettlementOfPaymentSuccessResult();
		//TODO
		List<String> msg = new ArrayList<String>();
		// 返回部分未核销成功数据
		List<SettlementOfPaymentResult> paymentResult = new ArrayList<SettlementOfPaymentResult>();
		try {
			RequestDO requestDo=new RequestDO();
			List<GetPaymentSuccessEntitys> getPaymentSuccessEntity=param.getGetPaymentSuccessEntitys();
			//运单号数组
			List<String> InvoiceNos = new ArrayList<String>();
			for (int i = 0; i < getPaymentSuccessEntity.size(); i++) {
				//运单号长度
				int len = getPaymentSuccessEntity.get(i).getGetPaymentDeatilSuccessEntity().size();
				for(int j=0;j<len;j++){
					InvoiceNos.add(getPaymentSuccessEntity.get(i).getGetPaymentDeatilSuccessEntity().get(j).getInvoiceNo());
				}
			}
			//对账单号
			requestDo.setSourceBillNos(InvoiceNos.toArray(new String[InvoiceNos.size()]));
			//来源单据类型 --对账单
			requestDo.setSourceBillType("W");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.SettlementOfPaymentSuccessService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			//根据对账单号调用CUBC灰度接口,判断走CUBC还是FOSS
			String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			LOG.error("PDA调用灰度接口返回参数："+gsService);
			if (!StringUtils.isBlank(gsService)) {
				VestResponse vestResponse = JSON.parseObject(gsService,
						VestResponse.class);
				// 由于返回的是个list,需要用到循环
				List<VestBatchResult> vestBatchResultList = vestResponse
						.getVestBatchResult();

				if (!CollectionUtils.isEmpty(vestBatchResultList)) {
					List<GetPaymentSuccessEntitys> getPaymentSuccessEntitys = param
							.getGetPaymentSuccessEntitys();
					List<GetPaymentSuccessEntitys> fossEntitys = new ArrayList<GetPaymentSuccessEntitys>();
					for (GetPaymentSuccessEntitys entity : getPaymentSuccessEntitys) {

						List<GetPaymentDeatilSuccessEntity> cubcList = new ArrayList<GetPaymentDeatilSuccessEntity>();
						List<GetPaymentDeatilSuccessEntity> fossList = new ArrayList<GetPaymentDeatilSuccessEntity>();

						xxx: for (GetPaymentDeatilSuccessEntity getPaymentDeatilSuccessEntity : entity
								.getGetPaymentDeatilSuccessEntity()) {

							for (int i = 0; i < vestBatchResultList.size(); i++) {
								VestBatchResult vestBatchResult = vestBatchResultList
										.get(i);
								String cubcOrFoss = vestBatchResult
										.getVestSystemCode();
								for (String vestObject : vestBatchResult
										.getVestObject()) {
									if (getPaymentDeatilSuccessEntity
											.getInvoiceNo().equals(vestObject)) {
										if (DeryConstant.GS_CUBC
												.equals(cubcOrFoss)) {
											cubcList.add(getPaymentDeatilSuccessEntity);
										} else {
											fossList.add(getPaymentDeatilSuccessEntity);
										}
										continue xxx;
									}
								}
							}
						}

						// 执行CUBC
						if (!cubcList.isEmpty()) {
							entity.setGetPaymentDeatilSuccessEntity(cubcList);
							payInfoDO = settlementPdaToCubcService
									.settlementOfPaymentPosCard(asyncMsg,
											entity);
							// 调用CUBC结清货款数据上传接口
							String responseStr = pdaToCubcService
									.getAccountStatementSuccess(payInfoDO);
							PayInfoDO payInfo = JSON.parseObject(responseStr,
									PayInfoDO.class);
							String isSuccess = payInfo.getIsSuccess();
							if (isSuccess != null && isSuccess.equals("flase")) {
								try {
									// CUBC返回false,保存到异常表
									savePosCardDataCubc(entity,
											payInfo.getErrorMessage(),
											asyncMsg.getContent());
									throw new FossInterfaceException(null,
											"PDA结清货款刷卡到CUBC失败!");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						// 执行FOSS
						if (!fossList.isEmpty()) {
							entity.setGetPaymentDeatilSuccessEntity(fossList);
							fossEntitys.add(entity);
						}
						if (!fossEntitys.isEmpty()) {
							param.setGetPaymentSuccessEntitys(fossEntitys);
							// 封装T+0报销参数
							List<PosCardEntity> entitys = wrapPosCardEntitys(
									asyncMsg, param);
							// 封装刷卡请求参数
							List<CommonQueryParamDto> recordList = wrapRecordList(
									asyncMsg, param);
							LOG.info("--------调用foss接口开始--------");
							long startTime = System.currentTimeMillis();
							LOG.info("--------T+0参数--------" + entitys);
							pdaPosManageService.insertPosCardData(entitys);
							// 调用结清货款刷卡方法
							LOG.info("----结清货款刷卡------");
							result = pdaRepaymentService.addPaymentInfo(recordList);
							long endTime = System.currentTimeMillis();
							LOG.info("调用foss接口耗时:" + (endTime - startTime));
						 }
					  }
//				     for (int i = 0; i < vestBatchResult.size(); i++) {
//				     // 归属系统编码
//					     String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
//					     if(cubcOrFoss.equals(DeryConstant.GS_CUBC)){//走CUBC
//					       List<GetPaymentSuccessEntitys> getPaymentSuccessEntitys=param.getGetPaymentSuccessEntitys();
//							for (GetPaymentSuccessEntitys entity : getPaymentSuccessEntitys) {
//								//调用CUBC封装参数接口
//								payInfoDO=settlementPdaToCubcService.settlementOfPaymentPosCard(asyncMsg,entity);
//								//调用CUBC结清货款数据上传接口
//								String responseStr= pdaToCubcService.getAccountStatementSuccess(payInfoDO);
//								PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
//								String isSuccess=payInfo.getIsSuccess();
//								if(isSuccess!=null && isSuccess.equals("flase")){
//									try {
//										//CUBC返回false,保存到异常表
//									    savePosCardDataCubc(entity,payInfo.getErrorMessage(),asyncMsg.getContent());
//									    throw new FossInterfaceException(null,"PDA结清货款刷卡到CUBC失败!");
//									} catch (Exception e) {
//										e.printStackTrace();
//									}
//								}
//							}
//					       
//					     }else{//默认走FOSS
//					    	// 将数据插入到T+0报表
//							LOG.info("--------调用foss接口开始--------");
//							long startTime = System.currentTimeMillis();
//							LOG.info("--------T+0参数--------"+entitys);
//							pdaPosManageService.insertPosCardData(entitys);
//							// 调用结清货款刷卡方法
//							LOG.info("----结清货款刷卡------");
//							result = pdaRepaymentService.addPaymentInfo(recordList);
//							long endTime = System.currentTimeMillis();
//							LOG.info("调用foss接口耗时:" + (endTime - startTime));
//					     }
//				   }
				}
			}
			if (result != null && !result.isEmpty()) {
				for (int i = 0; i < result.size(); i++) {
					if ("N".equals(result.get(i).getIsSuccess())) {
						// 刷卡失败的运单集合
						List<String> wayBillNoList = result.get(i)
								.getWayBillNoList();
						// 刷卡结果记录失败的错误信息
						Map<String, String> failMsgMap = result.get(i)
								.getMsgMap();
						// 部分未核销成功
						List<CommonQueryParamDto> commonList = result.get(i)
								.getCommonQueryParamDtoList();
						
						
						
						SettlementOfPaymentResult paymentT = null;
						if (wayBillNoList != null && !wayBillNoList.isEmpty()) {
							for (int j = 0; j < wayBillNoList.size(); j++) {
								String errcoMessage = wayBillNoList.get(j)
										+ ":"
										+ failMsgMap.get(wayBillNoList.get(j));
								// 保存刷卡失败的数据
								//savePaymentPosCardSuccess(
								//		param.getGetPaymentSuccessEntitys(),
								//		errcoMessage, asyncMsg.getContent());
								msg.add(errcoMessage);
							}
						}
						if (commonList != null && !commonList.isEmpty()) {
							for (int j = 0; j < commonList.size(); j++) {
								
								paymentT = new SettlementOfPaymentResult();
								// 收货人
								paymentT.setReceiveCustomerName(commonList.get(
										j).getReceiveCustomerName());
								// 业务时间
								paymentT.setCreateTime(commonList.get(j)
										.getCreateTime());
								//系统归属
								paymentT.setAffiliation(commonList.get(j).getOrigin());
								// 总金额
								paymentT.setToPayAmount(commonList.get(j)
										.getToPayAmount());
								// 未核销金额
								paymentT.setUnverifyAmount(commonList.get(j)
										.getUnverifyAmount());
								// 运单号
								paymentT.setWaybillNo(commonList.get(j)
										.getWaybillNo().get(0));
								//未付款总金额
								paymentT.setUnPayAmount(commonList.get(j).getUnPayAmount());
								paymentResult.add(paymentT);
							}
						}
					
					}
				}
			}

		} catch (BusinessException e) {
			// 保存刷卡数据异常信息
			//savePaymentPosCardSuccess(param.getGetPaymentSuccessEntitys(),
			//		LogUtil.logFormat(e), asyncMsg.getContent());
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());

		} catch (Exception e) {
			// 保存刷卡数据异常信息
			//savePaymentPosCardSuccess(param.getGetPaymentSuccessEntitys(),
			//		LogUtil.logFormat(e), asyncMsg.getContent());
			throw new FossInterfaceException(null, "出现未知异常");
		}
		//返回刷卡失败数据单号 list
		list.setErrcoMessage(msg);
		//返回部分未核销成功数据信息  list
		list.setSettlementOfPaymentResult(paymentResult);
		
		return list;
	}
	
	//封装T+0报表参数
     private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg, SettlementOfPaymentSuccessEntity param){
    	List<PosCardEntity> dto =  new ArrayList<PosCardEntity>();
 		
    	List<GetPaymentSuccessEntitys> entitys = param.getGetPaymentSuccessEntitys();
    	for (int i = 0; i < entitys.size(); i++) {
    		PosCardEntity posCardEntity = new PosCardEntity();
    		//所属模块
			if("settleCredit".equals(entitys.get(i).getBelongModule())){
				posCardEntity.setBelongModule("结清货款");
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
			posCardEntity.setCreateUserCode(entitys.get(i).getCreateUserCode());
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
    		List<GetPaymentDeatilSuccessEntity> detail=entitys.get(i).getGetPaymentDeatilSuccessEntity();
    		List<PosCardDetailEntity> details = new ArrayList<PosCardDetailEntity>();
    		for (int j = 0; j < detail.size(); j++) {
    			entity = new PosCardDetailEntity();
				//单据金额
				entity.setAmount(detail.get(j).getAmount());
				//交易流水号
				entity.setTradeSerialNo(detail.get(j).getTradeSerialNo());
				//单据类型
				entity.setInvoiceType("运单");
				//单据号
				entity.setInvoiceNo(detail.get(j).getInvoiceNo());
				
				details.add(entity);
			}
    		posCardEntity.setPosCardDetailEntitys(details);
			dto.add(posCardEntity);
    	}
    	return dto;
     }
     
     //结清货款模块--封装刷卡参数
     private List<CommonQueryParamDto> wrapRecordList(AsyncMsg asyncMsg, SettlementOfPaymentSuccessEntity param){
    	 List<GetPaymentSuccessEntitys> entitys = param.getGetPaymentSuccessEntitys();
    	 List<CommonQueryParamDto> arrayList = new ArrayList<CommonQueryParamDto>();
    	 for (int i = 0; i < entitys.size(); i++) {
    		 GetPaymentSuccessEntitys getPaymentSuccessEntitys = entitys.get(i);
    		 List<GetPaymentDeatilSuccessEntity> entity = getPaymentSuccessEntitys.getGetPaymentDeatilSuccessEntity();
    		 CommonQueryParamDto dto = new CommonQueryParamDto();
    		 //刷卡操作人工号
    		 dto.setEmpCode(getPaymentSuccessEntitys.getCreateUserCode());
    		 //刷卡操作人名称
    		 dto.setEmpName(getPaymentSuccessEntitys.getCreateUserName());
    		 //登录部门编码
    		 dto.setOrgCode(getPaymentSuccessEntitys.getCardDeptCode());
    		 //登录部门名称
    		 dto.setOrgName(getPaymentSuccessEntitys.getCardDeptName());
    		 //交易流水号
    		 dto.setTradeSerialNo(getPaymentSuccessEntitys.getTradeSerialNo());
    		 //交易流水号金额
    		 dto.setSerialAmount(getPaymentSuccessEntitys.getSerialAmount());
    		 List<String> list = new ArrayList<String>();
		     for (int j = 0; j < entity.size(); j++) {
		    	 GetPaymentDeatilSuccessEntity getPaymentDeatilSuccessEntity = entity.get(j);
		    	 //运单号
		    	 list.add(getPaymentDeatilSuccessEntity.getInvoiceNo());
			}
		     dto.setWaybillNo(list);
		     arrayList.add(dto);
    	 }
    	 return arrayList;
     }
    //结清货款模块--保存刷卡数据
 	/*private void savePaymentPosCardSuccess(List<GetPaymentSuccessEntitys>  param,String errorCause,String content){
 		try {
 			for(int i=0;i<param.size();i++){
 				param.get(i).setErrorCause(errorCause);
 				param.get(i).setContent(content);
 				deliveryPdaDao.saveNCIPaymentCardSuccess(param.get(i));
 			}
 		} catch (BusinessException e) {
 			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
 		}
 		
 	}*/

	// 保存CUBC刷卡异常
	private void savePosCardDataCubc(GetPaymentSuccessEntitys getPaymentSuccessEntitys,
			String errorCause, String content) {
		try {
			getPaymentSuccessEntitys.setErrorCause(errorCause);
			getPaymentSuccessEntitys.setContent(content);
			deliveryPdaDao.saveNCIPaymentCardSuccess(getPaymentSuccessEntitys);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	}
	//操作类型
	@Override
	public String getOperType() {		
		return DeliveryConstant.OPER_TYPE_SETTLE_PAYMENT_SUCCESS.VERSION;
	}

	//异步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * @Title: validate 
	 * @Description: TODO(检验参数的合法性)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 245955  
	 */
	private void validate(AsyncMsg asyncMsg,
			SettlementOfPaymentSuccessEntity entity) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		Argument.notNull(entity.getGetPaymentSuccessEntitys(),"entity.getPaymentSuccessEntitys");
//		// 工号
//		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
//		// 部门编号
//		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
//		// 用户类型
//		Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
//		// 运单号
//		List<GetPaymentSuccessEntitys> getPaymentSuccessEntitys = entity.getGetPaymentSuccessEntitys();
//		for (GetPaymentSuccessEntitys paymentEntity : getPaymentSuccessEntitys) {
//			List<GetPaymentDeatilSuccessEntity> getPaymentDeatilSuccessEntity = paymentEntity.getGetPaymentDeatilSuccessEntity();
//			for (GetPaymentDeatilSuccessEntity paymentDeatilEntity : getPaymentDeatilSuccessEntity) {
//				Argument.notEmpty(paymentDeatilEntity.getInvoiceNo(), "paymentDeatilEntity.invoiceNo");
//			}
//		}
//		// 所属模块
//		//Argument.hasText(entity.getBelongModule(), "entity.belongModule");
//		// 交易流水号
//		Argument.hasText(entity.getSerialNo(), "entity.serialNo");
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setPdaRepaymentService(IPdaRepaymentService pdaRepaymentService) {
		this.pdaRepaymentService = pdaRepaymentService;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setSettlementPdaToCubcService(
			ISettlementPdaToCubcService settlementPdaToCubcService) {
		this.settlementPdaToCubcService = settlementPdaToCubcService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}
	
}
