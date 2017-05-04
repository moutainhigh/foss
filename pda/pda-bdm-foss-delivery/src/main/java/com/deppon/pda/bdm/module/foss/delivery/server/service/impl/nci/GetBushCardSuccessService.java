package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCRecordEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillReturnEntity;
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
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.BushCardDetailEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
/**
 * 
 * @ClassName: GetBushCardSuccessService 
 * @Description: TODO(待刷卡上传--对接人ZYA) 
 * @author &245955 HKB
 * @date 2016-1-27 下午4:05:21 
 *
 */
public class GetBushCardSuccessService implements IBusinessService<List<String>, GetBushCardSuccessEntity>{

	private static final Log LOG = LogFactory.getLog(GetBushCardSuccessService.class);
	private IPdaPosManageService pdaPosManageService;
	 private IWSCWayBillManageService wscWayBillManageService;
	private IDeliveryPdaDao deliveryPdaDao;
	//CUBC接口
	private IPdaToCubcService pdaToCubcService;
	boolean flag=true;
	private ISettlementPdaToCubcService settlementPdaToCubcService;
	//解析参数
	@Override
	public GetBushCardSuccessEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		GetBushCardSuccessEntity entity = JsonUtil.parseJsonToObject(GetBushCardSuccessEntity.class,asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public List<String> service(AsyncMsg asyncMsg, GetBushCardSuccessEntity param)
			throws PdaBusiException {
		//校验参数的合法性
		this.validate(asyncMsg, param);
		
		WSCWayBillReturnEntity result = new WSCWayBillReturnEntity();
		//CUBC实体
		PayInfoDO payInfoDO=null;
		//封装查询灰度的实体
		RequestDO requestDo=new RequestDO();
		try{
		  List<GetBushCardEntitys> GetBushCardEntitys = param.getGetBushCardEntitys();
		  //运单号数组
		  List<String> InvoiceNos = new ArrayList<String>();
		  for (int i = 0; i < GetBushCardEntitys.size(); i++) {
			//单据编号长度
			int len = GetBushCardEntitys.get(i).getBushCardDetailEntity().size();
			//运单编号数组
			//String[] statementBillNos = new String[len];
			for(int j=0;j<len;j++){
				InvoiceNos.add(GetBushCardEntitys.get(i).getBushCardDetailEntity().get(j).getInvoiceNo());
			}
			  //对账单号
			requestDo.setSourceBillNos(InvoiceNos.toArray(new String[InvoiceNos.size()]));
		   }
		    //来源单据类型 --运单
			requestDo.setSourceBillType("W");
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.GetBushCardSuccessService.service");
			//来源系统
			requestDo.setOrigin("PDA");
			//根据对账单号调用CUBC灰度接口,判断走CUBC还是FOSS
			String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			if(!StringUtils.isBlank(gsService)){
				VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
				//由于返回的是个list,需要用到循环
				List<VestBatchResult> vestBatchResultList=vestResponse.getVestBatchResult();
				if(!CollectionUtils.isEmpty(vestBatchResultList)){
					List<GetBushCardEntitys> getBushCardEntitys = param.getGetBushCardEntitys();
					List<GetBushCardEntitys> fossEntitys = new ArrayList<GetBushCardEntitys>();
					for (GetBushCardEntitys entity : getBushCardEntitys) {

						List<BushCardDetailEntity> cubcList = new ArrayList<BushCardDetailEntity>();
						List<BushCardDetailEntity> fossList = new ArrayList<BushCardDetailEntity>();

						xxx: for (BushCardDetailEntity bushCardDetailEntity : entity
								.getBushCardDetailEntity()) {
							for (int i = 0; i < vestBatchResultList.size(); i++) {
								VestBatchResult vestBatchResult = vestBatchResultList.get(i);
								String cubcOrFoss = vestBatchResult
										.getVestSystemCode();
								for (String vestObject : vestBatchResult
										.getVestObject()) {
									if (bushCardDetailEntity
											.getInvoiceNo().equals(vestObject)) {
										if (DeryConstant.GS_CUBC
												.equals(cubcOrFoss)) {
											cubcList.add(bushCardDetailEntity);
										} else {
											fossList.add(bushCardDetailEntity);
										}
										continue xxx;
									}
								}
							}
						}

						// 执行CUBC
						if (!cubcList.isEmpty()) {
							entity.setBushCardDetailEntity(cubcList);
							//封装CUBC参数
							payInfoDO=settlementPdaToCubcService.wrapRecordQueryPayInfoDO(asyncMsg,entity);
							//调用CUBC接口
							String responseStr=pdaToCubcService.getAccountStatementSuccess(payInfoDO);
							PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
							String isSuccess=payInfo.getIsSuccess();
							if(isSuccess!=null && isSuccess.equals("flase")){
							   try {
								   //CUBC返回false,保存到异常表
								   savePosCardDataCubc(entity,payInfo.getErrorMessage(),asyncMsg.getContent());
							       throw new FossInterfaceException(null,"PDA待刷卡到CUBC失败!");
							   } catch (Exception e) {
								   e.printStackTrace();
							   }
							}
						}
						// 执行FOSS
						if (!fossList.isEmpty()) {
							entity.setBushCardDetailEntity(fossList);
							fossEntitys.add(entity);
						}
						if (!fossEntitys.isEmpty()) {
							param.setGetBushCardEntitys(fossEntitys);
							// 封装T+0报表参数
							List<PosCardEntity> entitys = wrapPosCardEntitys(
									asyncMsg, param);
							// 封装刷卡请求参数
							List<WSCRecordEntity> recordList = wrapRecordList(
									asyncMsg, param);
							// 将数据插入到T+0报表
							LOG.info("--------调用foss接口开始--------");
							long startTime = System.currentTimeMillis();
							LOG.info("T+0参数" + entitys);
							pdaPosManageService.insertPosCardData(entitys);
							// 调用刷卡
							LOG.info("----待刷卡记录------");
							result = wscWayBillManageService
									.recordSwipeCardResultBatch(recordList);
							long endTime = System.currentTimeMillis();
							LOG.info("调用foss接口耗时:" + (endTime - startTime));
						}
					}
				}
//				  for (int i = 0; i < vestBatchResult.size(); i++) {
//				   // 归属系统编码
//				   String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
//				   if(cubcOrFoss.equals(DeryConstant.GS_CUBC)){
//					List<GetBushCardEntitys> entity= param.getGetBushCardEntitys();
//					for (GetBushCardEntitys getBushCardEntitys : entity) {
//						//封装CUBC参数
//						payInfoDO=settlementPdaToCubcService.wrapRecordQueryPayInfoDO(asyncMsg,getBushCardEntitys);
//						//调用CUBC接口
//						String responseStr=pdaToCubcService.getAccountStatementSuccess(payInfoDO);
//						PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
//						String isSuccess=payInfo.getIsSuccess();
//						if(isSuccess!=null && isSuccess.equals("flase")){
//						   try {
//							   //CUBC返回false,保存到异常表
//							   savePosCardDataCubc(getBushCardEntitys,payInfo.getErrorMessage(),asyncMsg.getContent());
//						       throw new FossInterfaceException(null,"PDA待刷卡到CUBC失败!");
//						   } catch (Exception e) {
//							   e.printStackTrace();
//						   }
//						}
//					}
//					}else{
//						//将数据插入到T+0报表
//						LOG.info("--------调用foss接口开始--------");
//						long startTime = System.currentTimeMillis();
//						LOG.info("T+0参数"+entitys);
//						pdaPosManageService.insertPosCardData(entitys);
//						//调用刷卡
//						LOG.info("----待刷卡记录------");
//						result = wscWayBillManageService.recordSwipeCardResultBatch(recordList);
//						long endTime = System.currentTimeMillis();
//						LOG.info("调用foss接口耗时:" + (endTime - startTime));
//				    }
			if("0".equals(result.getResultCode())){
				//核销失败的运单集合
				List<String> failRecordWayBillNoList = result.getFailRecordWayBillNoList();
				//刷卡结果记录失败的错误信息
			 	Map<String, String> failRecordMsgMap = result.getFailRecordMsgMap();
				for(int j=0;j<failRecordWayBillNoList.size();j++){
					String errcoMessage = failRecordWayBillNoList.get(j)+":"+failRecordMsgMap.get(failRecordWayBillNoList.get(j));
					//保存核销失败的数据
					LOG.error("NCI待刷卡核销失败"+errcoMessage);
					//savePosCardData(param.getGetBushCardEntitys(),errcoMessage,asyncMsg.getContent());
				}
			}
			}
		  
		} catch(BusinessException e){
			//保存刷卡数据异常信息
			//savePosCardData(param.getGetBushCardEntitys(),e.getMessage(),asyncMsg.getContent());
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			
		} catch(Exception e){
			//保存刷卡数据异常信息
			//savePosCardData(param.getGetBushCardEntitys(),LogUtil.logFormat(e),asyncMsg.getContent());
			throw new FossInterfaceException(null,"出现未知异常");
		}
				
		return result.getFailRecordWayBillNoList();
	}

	//封装参数
	private List<PosCardEntity> wrapPosCardEntitys(AsyncMsg asyncMsg, GetBushCardSuccessEntity param){
		List<PosCardEntity> dto =  new ArrayList<PosCardEntity>();
		List<GetBushCardEntitys> entitys = param.getGetBushCardEntitys();
		for(int i=0;i<entitys.size();i++){
			PosCardEntity posCardEntity = new PosCardEntity();
			//所属模块
			if("POSList".equals(entitys.get(i).getBelongModule())){
				posCardEntity.setBelongModule("待刷卡单据");
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
			posCardEntity.setCreateUserCode(asyncMsg.getDeptCode());
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
			List<BushCardDetailEntity> detail = entitys.get(i).getBushCardDetailEntity();
			List<PosCardDetailEntity> details = new ArrayList<PosCardDetailEntity>();
			for(int j=0;j<detail.size();j++){
				entity = new PosCardDetailEntity();
				//单据金额
				BigDecimal amonut = new BigDecimal(detail.get(j).getWayBillAmount());
				entity.setAmount(amonut);
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
	
	//封装刷卡请求参数
	private List<WSCRecordEntity> wrapRecordList(AsyncMsg asyncMsg,GetBushCardSuccessEntity param){
		List<WSCRecordEntity> entity = new ArrayList<WSCRecordEntity>();
		WSCRecordEntity wscRecord = new WSCRecordEntity();
		List<WSCWayBillEntity> wscRecordList = new ArrayList<WSCWayBillEntity>();
		WSCWayBillEntity record = null;
		List<GetBushCardEntitys> entitys = param.getGetBushCardEntitys();
		for(int i=0;i<entitys.size();i++){
			List<BushCardDetailEntity> posCardDetailEntitys = entitys.get(i).getBushCardDetailEntity();
			for(int j=0;j<posCardDetailEntitys.size();j++){
				record = new WSCWayBillEntity();
				//运单条目
				record.setWscItemId(posCardDetailEntitys.get(j).getWscItemId());
				//运单号
				record.setWayBillNo(posCardDetailEntitys.get(j).getInvoiceNo());
				//已刷卡金额
				double alreadySwipeAmount = posCardDetailEntitys.get(j).getAmount().doubleValue();
				record.setAlreadySwipeAmount(alreadySwipeAmount);
				//刷卡部门编号
				record.setSwipeCardOrgCode(asyncMsg.getDeptCode());
				//刷卡部门名称
				record.setSwipeCardOrgName(entitys.get(i).getCardDeptName());
				//刷卡时间
				record.setSwipeCardTime(entitys.get(i).getOperateTime());
				//刷卡操作人编号
				record.setSwipeCardUserCode(asyncMsg.getUserCode());
				//刷卡操作人名称
				record.setSwipeCardUserName(entitys.get(i).getCreateUserName());
				//交易流水号
				record.setSerialNo(posCardDetailEntitys.get(j).getTradeSerialNo());
				
				wscRecordList.add(record);
			}
			wscRecord.setWscRecordList(wscRecordList);
		}
		entity.add(wscRecord);
		return entity;
	}
	//保存刷卡数据
	/*private void savePosCardData(List<GetBushCardEntitys>  param,String errorCause,String content){
		try {
			for(int i=0;i<param.size();i++){
				param.get(i).setErrorCause(errorCause);
				param.get(i).setContent(content);
				deliveryPdaDao.saveNCIPaymentCardByBush(param.get(i));
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}*/
	//保存CUBC刷卡异常
	private void savePosCardDataCubc(GetBushCardEntitys getBushCardEntitys,String errorCause,String content){
		try {
			getBushCardEntitys.setErrorCause(errorCause);
			getBushCardEntitys.setContent(content);
		    deliveryPdaDao.saveNCIPaymentCardByBush(getBushCardEntitys);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	

	//操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_NOT_CARD_SUCCESS.VERSION;
	}

	//异步
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @Title: validate 
	 * @Description: TODO(校验参数合法性)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg, GetBushCardSuccessEntity entity){
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "GetBushCardSuccessEntity");
		Argument.notNull(entity.getGetBushCardEntitys(), "entity.getBushCardEntitys");
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}

	public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}

	public void setSettlementPdaToCubcService(
			ISettlementPdaToCubcService settlementPdaToCubcService) {
		this.settlementPdaToCubcService = settlementPdaToCubcService;
	}
	
}
