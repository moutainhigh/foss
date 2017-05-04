package com.deppon.foss.module.settlement.cubcitf.server.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PayInfoDO;
import com.deppon.foss.module.settlement.common.api.shared.domain.PayInfoDetailDO;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossPayInfoRequest;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.GreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.cubcitf.server.inter.PayInfoManagerService;

/**
 * 打款信息实现
 * 
 * @ClassName: PayInfoManagerServiceImpl
 * @author &269052 |周禹安
 * @date 2017-1-4 下午10:23:27
 */
public class PayInfoManagerServiceImpl implements PayInfoManagerService {
	private static Logger logger = LoggerFactory
			.getLogger(PayInfoManagerServiceImpl.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 暂存查询
	 */
	private IGreenHandWrapWriteoffService greenHandWrapWriteoffService;

	/**
	 * POS机管理查询
	 */
	private IPdaPosManageService pdaPosManageService;

	/**
	 * 裹裹，pda开单，查询数据，根据类型区分
	 * 
	 * @Title: queryPayInfoByParam
	 * @author： 269052 |周禹安
	 * @date 2017-1-4 下午10:19:21
	 */
	@SuppressWarnings("finally")
	@Override
	public Response queryPayInfoByParam(String param) {
		logger.error("cubc查询:" + JSONObject.toJSONString(param));
		PayInfoDO infoDO = new PayInfoDO();
		FossPayInfoRequest fossRequest = new FossPayInfoRequest();
		FossPayInfoRequest fossResponse = new FossPayInfoRequest();
		List<PayInfoDO> payInfoDOs = new ArrayList<PayInfoDO>();
		try {
			if (null == param) {
				logger.error("查询参数为空");
				fossResponse.setIsSuccess("false");
				fossResponse.setErrorMessage("查询参数为空！");
				throw new SettlementException("查询参数为空！");
			}
			// 获取
			fossRequest = JSONObject.parseObject(param,
					FossPayInfoRequest.class);

			/**
			 * 判断类型
			 */
			String queryType = fossRequest.getQueryType();
			if ("TRADE_GG".equals(queryType)) {
				/**
				 * 获取运单号
				 */
				String waybillNo = fossRequest.getWayBillNo();
				if (StringUtils.isEmpty(fossRequest.getWayBillNo())) {
					logger.error("运单号为空！");
					throw new SettlementException("运单号为空！");
				}

				List<GreenHandWrapWriteoffEntity> greenHandList = greenHandWrapWriteoffService
						.queryGreenHandWrapByWaybillNo(waybillNo);

				if (CollectionUtils.isEmpty(greenHandList)) {
					logger.error("不存在裹裹暂存信息！");
					throw new SettlementException("不存在果果暂存信息！");
				}
				/**
				 * 存在暂存信息
				 */
				GreenHandWrapWriteoffEntity entity = greenHandList.get(0);

				/**
				 * 通过暂存信息封装打款信息
				 */
				infoDO.setPayCode(waybillNo);
				infoDO.setAmount(entity.getAmount());
				infoDO.setPayType("OL");
				infoDO.setPayDate(entity.getDopTime());
				payInfoDOs.add(infoDO);
				fossResponse.setPayInfoDOs(payInfoDOs);
				fossResponse.setIsSuccess("true");
				// PDA开单补录
			} else if ("TRADE_BILL".equals(queryType)) {
				logger.error("PDA开单补录查询！");
				String wayBillNo = fossRequest.getWayBillNo();
				PosCardDetailEntity detail = new PosCardDetailEntity();
				detail.setInvoiceNo(wayBillNo);
				List<PosCardDetailEntity> list = pdaPosManageService
						.queryPosDetailsByNo(detail);
				for (PosCardDetailEntity posCardDetailEntity : list) {
					/**
					 * 获取交易流水号查询
					 */
					String serialNo = posCardDetailEntity.getTradeSerialNo();
					/**
					 * 根据交易流水号查询T+0
					 */
					PosCardManageDto dto = new PosCardManageDto();
					dto.setTradeSerialNo(serialNo);
					// 交易流水号只会有一条
					PosCardManageDto posCardDto = pdaPosManageService
							.queryPosCardData(dto);
					if (null == posCardDto
							|| CollectionUtils.isEmpty(posCardDto
									.getPosCardEntitys())) {
						logger.error("查询不到数据！");
						throw new SettlementException("查询不到数据！");
					}
					List<PosCardEntity> entitys = posCardDto
							.getPosCardEntitys();
					/**
					 * 通过T+0封装打款信息
					 */
					payInfoDOs = this.buildPayInfoDOByPosCard(entitys,queryType);

					if (CollectionUtils.isEmpty(payInfoDOs)) {
						fossResponse.setIsSuccess("false");
						throw new SettlementException("未使用金额小于等于0！");
					}
					fossResponse.setIsSuccess("true");
					fossResponse.setPayInfoDOs(payInfoDOs);
				}
				// 查询T+0
			} else if ("POS_CARD".equals(queryType)) {
				/**
				 * 直接根据交易流水号查询
				 */
				String serialNo = fossRequest.getSerialNo();
				String deptCode = fossRequest.getDeptCode();
				if (StringUtils.isEmpty(serialNo)) {
					logger.error("交易流水号为空！");
					throw new SettlementException("交易流水号为空！");
				}
				if (StringUtils.isEmpty(deptCode)) {
					logger.error("部门编码为空！");
					throw new SettlementException("部门编码为空！");
				}
				PosCardManageDto dto = new PosCardManageDto();
				dto.setTradeSerialNo(serialNo);
				dto.setOrgCode(fossRequest.getDeptCode());
				// 交易流水号只会有一条
				PosCardManageDto posCardDto = pdaPosManageService
						.queryPosCardData(dto);

				if (null == posCardDto
						|| CollectionUtils.isEmpty(posCardDto
								.getPosCardEntitys())) {
					infoDO.setIsSuccess("false");
					throw new SettlementException("查询不到数据！");
				}

				List<PosCardEntity> posCardEntitys = posCardDto
						.getPosCardEntitys();
				/**
				 * 通过T+0封装打款信息
				 */
				payInfoDOs = this.buildPayInfoDOByPosCard(posCardEntitys,queryType);

				if (CollectionUtils.isEmpty(payInfoDOs)) {
					fossResponse.setIsSuccess("false");
					throw new SettlementException("未使用金额小于等于0！");
				}
				fossResponse.setIsSuccess("true");
				fossResponse.setPayInfoDOs(payInfoDOs);
			} else {
				logger.error("查询参数异常！");
				throw new SettlementException("查询类型封装出错！");
			}
		} catch (SettlementException e) {
			logger.error("cubc查询异常错误："+ExceptionUtils.getStackTrace(e));
			fossResponse.setIsSuccess("false");
			fossResponse.setErrorMessage(e.getErrorCode());
		} catch (Exception e) {
			logger.error("cubc查询异常错误："+ExceptionUtils.getStackTrace(e));
			fossResponse.setIsSuccess("false");
			fossResponse.setErrorMessage(ExceptionUtils.getStackTrace(e));
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			return Response.ok(JSONObject.toJSONString(fossResponse))
					.header("ESB-ResultCode", 1).build();
		}
	}

	/**
	 * 通过打款信息
	 * 
	 * @Title: builPayInfoDOByPosCard
	 * @author： 269052 |周禹安
	 * @date 2017-1-4 下午11:48:32
	 */
	private List<PayInfoDO> buildPayInfoDOByPosCard(List<PosCardEntity> list,String queryType) {
		if (null == list) {
			throw new SettlementException("pos机刷卡信息为空！");
		}
		PayInfoDO infoDO = new PayInfoDO();
		List<PayInfoDO> payInfoDOs = new ArrayList<PayInfoDO>();
		for (PosCardEntity posCardEntity : list) {
			if (posCardEntity.getUsedAmount().compareTo(BigDecimal.ZERO) > 0) {
				continue;
			}
			/**
			 * 设置打款信息
			 */
			infoDO = new PayInfoDO();
			infoDO.setPayCode(posCardEntity.getTradeSerialNo());
			infoDO.setAmount(posCardEntity.getSerialAmount());
			infoDO.setUsedAmount(posCardEntity.getUsedAmount());
			infoDO.setUnusedAmount(posCardEntity.getUnUsedAmount());
			infoDO.setPayDate(posCardEntity.getCardTime());
			infoDO.setCreateDate(posCardEntity.getCreateTime());
			infoDO.setPayDeptCode(posCardEntity.getCardDeptCode());
			infoDO.setPayDeptName(posCardEntity.getCardDeptName());
			infoDO.setPayUnifiedCode(posCardEntity.getCardDeptBMCode());
			infoDO.setBigRegionCode(posCardEntity.getBelongRegionCode());
			infoDO.setBigRegionName(posCardEntity.getBelongRegionName());
			infoDO.setSmallRegionCode(posCardEntity.getBusinessAreaCode());
			infoDO.setSmallRegionName(posCardEntity.getBusinessAreaName());
			infoDO.setCreateDate(posCardEntity.getCreateDate());
			infoDO.setCreateUserCode(posCardEntity.getCreateUserCode());
			infoDO.setCreateUserName(posCardEntity.getCreateUserName());
			infoDO.setPayType("CD");
			
			/**
			 * 假如pda开单补录的单子,通过交易流水号查询明细
			 */
			if("TRADE_BILL".equals(queryType)){
				/**
				 * 
				 */
				PosCardDetailEntity query = new PosCardDetailEntity();
				String payCode = posCardEntity.getTradeSerialNo();
				query.setTradeSerialNo(posCardEntity.getTradeSerialNo());
				List<PayInfoDetailDO> payInfoDetailDOs = new ArrayList<PayInfoDetailDO>();
				PayInfoDetailDO payInfoDetailDO = new PayInfoDetailDO();
				List<PosCardDetailEntity> detail  = pdaPosManageService.queryPosDetailsByNo(query);
				for (PosCardDetailEntity posCardDetailEntity : detail) {
					payInfoDetailDO = new PayInfoDetailDO();
					payInfoDetailDO.setPayCode(payCode);
					payInfoDetailDO.setSourceBillNo(posCardDetailEntity.getInvoiceNo());
					payInfoDetailDO.setSourceBillType("W");
					payInfoDetailDO.setAmount(posCardDetailEntity.getAmount());
					payInfoDetailDOs.add(payInfoDetailDO);
				}
				// 设置明细
				infoDO.setPayInfoDetailDO(payInfoDetailDOs);
			}
			payInfoDOs.add(infoDO);
		}
		return payInfoDOs;
	}

	/**
	 * 根据明细修改POS金额，插入明细
	 * 
	 * @Title: queryPayInfoByParam
	 * @author： 269052 |周禹安
	 * @date 2017-1-4 下午10:19:21
	 */
//	@SuppressWarnings("finally")
//	@Override
//	public Response updatePosCardByParam(String param) {
//		logger.error("请求参数");
//		PayInfoDetailDO detail = JSONObject.parseObject(param,
//				PayInfoDetailDO.class);
//		PayInfoDetailDO fossResponse = new PayInfoDetailDO();
//		try {
//			PosCardEntity entity = new PosCardEntity();
//			entity.setTradeSerialNo(detail.getPayCode());
//
//			PosCardManageDto dto = new PosCardManageDto();
//			dto.setTradeSerialNo(detail.getPayCode());
//			// 交易流水号只会有一条
//			PosCardManageDto posCardDto = pdaPosManageService
//					.queryPosCardData(dto);
//
//			PosCardEntity posEntity = posCardDto.getPosCardEntitys().get(0);
//
//			/**
//			 * 设置金额
//			 */
//			// 使用金额
//			BigDecimal amount = detail.getAmount();
//
//			posEntity.setUsedAmount(posEntity.getUsedAmount().add(amount));
//			posEntity.setUnUsedAmount(posEntity.getUnUsedAmount().subtract(
//					amount));
//			posEntity.setModifyDate(new Date());
//			posEntity.setModifyUser(detail.getModifyUserName());
//			posEntity.setModifyUserCode(detail.getModifyUserCode());
//			pdaPosManageService.updatePosCardMessageAmount(posEntity);
//
//			// 准备数据
//			PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
//			posCardDetailEntity.setTradeSerialNo(detail.getPayCode());
//			posCardDetailEntity.setInvoiceType("W2");
//			posCardDetailEntity.setInvoiceNo(detail.getSourceBillNo());
//			posCardDetailEntity.setAmount(detail.getAmount());
//			posCardDetailEntity.setOccupateAmount(detail.getAmount());
//			posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);
//			posCardDetailEntity.setCreateUser(detail.getCreateUserName());
//			posCardDetailEntity.setCreateUserCode(detail.getCreateUserCode());
//
//			pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);
//
//			fossResponse.setIsError("N");
//		} catch (SettlementException e) {
//			logger.error(ExceptionUtils.getStackTrace(e));
//			fossResponse.setIsError("Y");
//			fossResponse.setRemark(ExceptionUtils.getStackTrace(e));
//		} catch (Exception e) {
//			logger.error(ExceptionUtils.getStackTrace(e));
//			fossResponse.setIsError("Y");
//			fossResponse.setRemark(ExceptionUtils.getStackTrace(e));
//		} finally {
//			response.addHeader("ESB-ResultCode", "1");
//			return Response.ok(JSONObject.toJSONString(fossResponse))
//					.header("ESB-ResultCode", 1).build();
//		}
//	}

	/**
	 * 作废打款信息表
	 * 
	 * @Title: cancelPosCardBySerialNo
	 * @author： 269052 |周禹安
	 * @date 2017-1-4 下午10:19:21
	 */
	@SuppressWarnings("finally")
	@Override
	public Response cancelPosCardBySerialNo(String param) {
		logger.error("作废T+0");
		FossPayInfoRequest fossResponse = new FossPayInfoRequest();
		FossPayInfoRequest request = JSONObject.parseObject(param,
				FossPayInfoRequest.class);
		try {
			/**
			 * 获取交易流水号，作废
			 */
			String serialNo = request.getSerialNo();
			pdaPosManageService.updatePosCardByParam(serialNo);
			fossResponse.setIsSuccess("true");
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			fossResponse.setIsSuccess("false");
			fossResponse.setErrorMessage(ExceptionUtils.getStackTrace(e));
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			return Response.ok(JSONObject.toJSONString(fossResponse))
					.header("ESB-ResultCode", 1).build();
		}
	}
	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setGreenHandWrapWriteoffService(
			IGreenHandWrapWriteoffService greenHandWrapWriteoffService) {
		this.greenHandWrapWriteoffService = greenHandWrapWriteoffService;
	}
}
