package com.deppon.foss.module.settlement.bho.server.ws;

import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.ccmanager.CodInfo;
import com.deppon.esb.inteface.domain.ccmanager.LanguageQueryCodRequest;
import com.deppon.esb.inteface.domain.ccmanager.LanguageQueryCodResponse;
import com.deppon.esb.inteface.domain.ccmanager.QueryCodRequest;
import com.deppon.esb.inteface.domain.ccmanager.QueryCodResponse;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.ccmanagerservice.CcmanagerService;
import com.deppon.foss.inteface.ccmanagerservice.CommonException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryCubcService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcResponse;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 配合CC接口,包括查询代收货款信息.
 * 
 * @author 163576
 * @date 2014-07-24 下午2:16:56
 */
public class CcmanagerServiceImpl implements CcmanagerService{

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CcmanagerServiceImpl.class);
	
	/**
	 * 代收货款综合服务
	 */
	private ICodCommonService codCommonService;
	
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * QueryCubcService
	 */
	private IQueryCubcService queryCubcService;
	/**
	 * 
	 * 根据运单号查询代收货款
	 * 
	 * @author 163576
	 * @date 2014-07-25 08:50:41
	 * @see
	 * @throws CommonException
	 */
	@Override
	public LanguageQueryCodResponse languageQueryCodInfo(
			Holder<ESBHeader> esbHeader,
			LanguageQueryCodRequest languageQueryCodRequest)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * 根据运单号查询代收货款
	 * 
	 * @author 163576
	 * @date 2014-07-25 08:50:41
	 * @see
	 * @throws CommonException
	 */
	@Override
	public QueryCodResponse queryCodInfo(Holder<ESBHeader> esbHeader,
			QueryCodRequest queryCodRequest) throws CommonException {
		//记录日志
				logger.info("Service queryCodInfo start ...");
				//返回esb头信息
				ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
				//实例化返回实体
				QueryCodResponse response = new QueryCodResponse();
				try {
					//判空
					if (queryCodRequest != null) {
						// 获取运单号集合、分页号、分页大小
						List<String> waybillNos = queryCodRequest.getWayBillNos();
						//分页参数
						int pageNo = queryCodRequest.getPageNo();
						//分页参数
						int limit = queryCodRequest.getPageSize();
						// 如果运单号不为空
						if (CollectionUtils.isNotEmpty(waybillNos)) {
							
							// modify by 269044--先查询下Cubc的数据，得到所有的代收集合和条数
							CodQueryToCubcDto dto = new CodQueryToCubcDto();
							//设置参数
							dto.setWayBillNo(waybillNos);
							//定义响应
							CodQueryToCubcResponse codResponse = null;
							try {
								//调用接口
								codResponse = queryCubcService.queryCubcCodList(dto);
							} catch (Exception e ) {
								//打印异常
								logger.info("查询cubc报错" + e.getMessage());
							}
							//给limit一个默认值1,cc传过来的pageNo，limit参数都为0
							limit = 1; 
							//cubc的代收总条数
							long cubcSize = 0;
							int cubcPages = 0;
							int fossSize = 0;
							int fossPages = 0;
							//cubc代收的list
							List<CodDO> cubcCodList = null; 
							//判空
							if(null != codResponse && codResponse.getTotalNum() != 0){
								//获取总条数
								cubcSize = codResponse.getTotalNum();
							}
							// 查询代收货款信息大小
							fossSize = codCommonService.queryCODByWaybillNumsSize(waybillNos);
							//判空
							if(cubcSize != 0) {
								//cubc总条数分页
								int j = (int)cubcSize/limit;
								cubcPages = cubcSize%limit  > 0 ? j+1 : j;
							}
							if(fossSize != 0) {
								//foss总条数分页
								int i = fossSize/limit;
								fossPages = fossSize%limit  > 0 ? i+1 : i;
							}
							// 总页数
							int totalPages = cubcPages + fossPages;
							
							// 查询代收货款信息
							List<CODDto> queryResult = null;
							List<WaybillEntity> waybillResult = null;
							if(totalPages > 0){
								// 如果分页号小于1，则查第一页
								if (pageNo < 1) {
									//设置分页为第一页
									pageNo = 1;
								}
								// 如果分页大小小于1，则总条数
								if (limit < 1) {
									//设置分页大小为总条数
									limit = fossSize + (int)cubcSize;
								}
								//再用当前页跟foss和cubc的页数进行对比
								if(pageNo <= fossPages) {
									//查询foss数据
									queryResult = codCommonService.queryCODByWaybillNumsForCC(waybillNos, (pageNo - 1)* limit, limit);
								//去cubc查询
								} else if (pageNo >= fossPages && pageNo <= totalPages) {
									//新参数
									CodQueryToCubcDto newCodDto = new CodQueryToCubcDto();
									//设置参数
									newCodDto.setWayBillNo(waybillNos);
									//设置参数
									newCodDto.setStart((pageNo - fossPages - 1)* limit);
									//设置参数
									newCodDto.setPageSize(limit);
									//响应参数
									CodQueryToCubcResponse codList = null;
									try {
										//调用接口
										codList = queryCubcService.queryCubcCodList(newCodDto);
									} catch (Exception e ) {
										//打印异常
										logger.info("查询cubc报错" + e.getMessage());
									}
									//判空
									if(null != codList && CollectionUtils.isNotEmpty(codList.getCodAuditList())){
										//获取总条数
										cubcCodList = codList.getCodAuditList();
									}	
								}								
								//
								waybillResult = waybillManagerService.queryWaybillBasicByNoList(waybillNos);
							}
							
							//判空
							vallidaCodeInfo(response, queryResult,
									waybillResult,cubcCodList);
							// 代收货款信息大小
							response.setCount(fossPages * limit + (int)cubcSize);
						}
					}
				//异常处理
				} catch (BusinessException e) {
					//记录日志
					logger.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}catch (Exception e) {
					//记录日志
					logger.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
				//记录日志
				logger.info("Service queryCodInfo end ...");
				//返回信息
				return response;
	}

	private void vallidaCodeInfo(QueryCodResponse response,
			List<CODDto> queryResult, List<WaybillEntity> waybillResult, List<CodDO> cubcCodList)
			throws Exception {
		//声明代收货款实体
		CodInfo refund = null;
		if (CollectionUtils.isNotEmpty(queryResult)) {			
			//循环处理
			for (final CODDto dto : queryResult) {
				// 构建返回值对象并设置值
				refund = new CodInfo();
				// 运单号、发货人、收款人、银行账号、银行
				refund.setWayBillNo(dto.getWaybillNo());
				//设置值
				refund.setSender(dto.getPayableCustomerName());
				//设置值
				refund.setPayee(dto.getPayeeName());
				//设置值
				refund.setAccountNo(dto.getPayeeAccount());
				//设置值
				refund.setBankNo(dto.getBank());
				// 代收货款类型、代收货款金额、冲应收金额、应退金额、代收货款状态
				String codType = dto.getCodType();
				//类型转换
				int type = transferRefundType(codType);
				//判断类型
				if (type > 0) {
					//判断类型
					refund.setRefundType(String.valueOf(type));
				}
				//设置值
				refund.setColletionAmount(dto.getAmount());
				//设置值
				refund.setDeductionAmount(dto.getVerReceivableAmount());
				//设置值
				refund.setRefundAmount(dto.getReturnAmount());
				//获取转台
				String status = dto.getStatus();
				//类型转换
				int refundStatus = this.transferRefundStatus(status);
				//判断值
				if (refundStatus > 0) {
					//类型转换
					refund.setRefundStatus(String.valueOf(refundStatus));
				}
				
				
				WaybillEntity waybill = (WaybillEntity) CollectionUtils.find(waybillResult, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						return ((WaybillEntity)object).getWaybillNo().equals(dto.getWaybillNo());
					}
				});
				//出发部门,到货部门,收货人,开单时间,签收时间,代收手续费
				//汇款导出时间,汇款成功时间,备注
				refund.setOrigOrgName(dto.getOrigOrgName());
				refund.setDestOrgName(dto.getDestOrgName());
				refund.setCosignee(waybill.getReceiveCustomerName());
				refund.setBillTime(SettlementUtil.dateToXmlDate(waybill.getBillTime()));
				refund.setSignTime(SettlementUtil.dateToXmlDate(dto.getSignDate()));
				refund.setCodFee(waybill.getCodFee());
				refund.setExportTime(SettlementUtil.dateToXmlDate(dto.getCodExportTime()));
				refund.setSuccessTime(SettlementUtil.dateToXmlDate(dto.getRefundSuccessTime()));
				refund.setNotes(dto.getRemittanceFailNotes());
				
				// 加入结果集
				response.getCodInfo().add(refund);
			}
		}
		if (CollectionUtils.isNotEmpty(cubcCodList)) {
			//判断cubc集合
			for (final CodDO dto : cubcCodList) {
				// 构建返回值对象并设置值
				refund = new CodInfo();
				// 运单号、发货人、收款人、银行账号、银行
				refund.setWayBillNo(dto.getWaybillNo());
				//设置值
				refund.setSender(dto.getCustomerCode());
				//设置值
				refund.setPayee(dto.getPayeeName());
				//设置值
				refund.setAccountNo(dto.getAccountNo());
				//设置值
				refund.setBankNo(dto.getBankHqName());
				// 代收货款类型、代收货款金额、冲应收金额、应退金额、代收货款状态
				String codType = dto.getCodType();
				//类型转换
				int type = transferRefundType(codType);
				//判断类型
				if (type > 0) {
					//判断类型
					refund.setRefundType(String.valueOf(type));
				}
				//设置值
				refund.setColletionAmount(dto.getCodAmount());
				//设置值
				refund.setDeductionAmount(dto.getVerifyCodAmount());
				//设置值
				refund.setRefundAmount(dto.getCodAmount().subtract(dto.getVerifyCodAmount()));
				//获取转台
				String status = dto.getStatus();
				//类型转换
				int refundStatus = this.transferRefundStatus(status);
				//判断值
				if (refundStatus > 0) {
					//类型转换
					refund.setRefundStatus(String.valueOf(refundStatus));
				}
				//出发部门,到货部门,收货人,开单时间,签收时间,代收手续费
				//汇款导出时间,汇款成功时间,备注
				refund.setOrigOrgName(dto.getOrigOrgName());
				refund.setDestOrgName(dto.getDestOrgName());
				refund.setCosignee(dto.getReceiveCustomerName());
				refund.setBillTime(SettlementUtil.dateToXmlDate(dto.getBillDate()));
				refund.setSignTime(SettlementUtil.dateToXmlDate(dto.getSignDate()));
				refund.setCodFee(dto.getCodFee());
				refund.setExportTime(SettlementUtil.dateToXmlDate(dto.getCodExportDate()));
				refund.setSuccessTime(SettlementUtil.dateToXmlDate(dto.getRefundSuccessDate()));
				refund.setNotes(dto.getRemitTanceFailNotes());
				
				// 加入结果集
				response.getCodInfo().add(refund);
			}
		}
	}

	/**
	 * 
	 * 代收货款类型转换
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 上午9:48:45
	 */
	private int transferRefundType(String codType) {
		//公共未知类型
		int type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_UNKNOWN;
		//判断即日退
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(codType)) {
			//即日退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_1_DAY;
		//判断3日退
		} else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(codType)) {
			//3日退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_3_DAY;
		//判断审核退
		} else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(codType)) {
			//审核退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_APPROVE;
		}
		//返回类型
		return type;
	}
	
	/**
	 * 
	 * 转换代收货款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 上午10:03:02
	 */
	private int transferRefundStatus(String status) {
		//代收货款状态
		int refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_UNKNOWN;
		// 未退款、待审核、营业部冻结、收银员审核、资金部冻结
		if (SettlementDictionaryConstants.COD__STATUS__NOT_RETURN.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__APPROVING.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE.equals(status)) {
			//未退款
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__NOT_RETURN;
		}

		// 退款中、退款失败申请、反汇款成功
		else if (SettlementDictionaryConstants.COD__STATUS__RETURNING.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS.equals(status)) {
			// 退款中
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURNING;
		}
		// 已退款
		else if (SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(status)) {
			// 已退款
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURNED;
		}

		// 退款失败
		else if (SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE.equals(status)) {
			// 退款失败
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURN_FAILURE;
		}
		//返回
		return refundStatus;
	}
	
	/**
	 * @param codCommonService the codCommonService to set
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setQueryCubcService(IQueryCubcService queryCubcService) {
		this.queryCubcService = queryCubcService;
	}

}
