package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.settlement.closing.api.server.service.ICollectingPaymentService;
import com.deppon.foss.module.settlement.closing.api.server.service.ISynCollectingPaymentList;
import com.deppon.foss.module.settlement.closing.api.shared.domain.QueryCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentList;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryCubcService;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcResponse;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class SynCollectingPaymentList implements ISynCollectingPaymentList{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynCollectingPaymentList.class);
	
	/**
	 * 注入代收货款清单接口service
	 */
	private ICollectingPaymentService collectingPaymentService;
	
	public void setCollectingPaymentService(
			ICollectingPaymentService collectingPaymentService) {
		this.collectingPaymentService = collectingPaymentService;
	}
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
     * 业务互斥锁
     */
    private IBusinessLockService businessLockService;
    
    
    /**
     * 调用CUBC接口查询代收货款
     */
    IQueryCubcService queryCubcService;
	/**
	 * @param queryCubcService the queryCubcService to set
	 */
	public void setQueryCubcService(IQueryCubcService queryCubcService) {
		this.queryCubcService = queryCubcService;
	}

	@Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;

	@Override
	public String queryCollectingPaymentList(
			String collectingPaymentList) {
		LOGGER.info("查询代收货款传入json参数" + collectingPaymentList);
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_AGENT_CODE_LIST");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		//返回数据实体
		ReturnCollectingPaymentList response = new ReturnCollectingPaymentList();
		
		//请求参数实体
		QueryCollectingPaymentEntity queryCollectingPaymentEntity = JSON
				.parseObject(collectingPaymentList, QueryCollectingPaymentEntity.class);
		LOGGER.info("接收参数解析实体："
				+ ReflectionToStringBuilder.toString(queryCollectingPaymentEntity));
		
		//互斥对象
		MutexElement mutex = new MutexElement(queryCollectingPaymentEntity.getCustomerCode(),
				"CRM_GRADIENT_DISCOUNT_ID",
				MutexElementType.CRM_GRADIENT_DISCOUNT_ID);
		try {
			if(queryCollectingPaymentEntity != null){
				
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
				if(result){
					LOGGER.info("成功加锁：" + mutex.getBusinessNo());
					resp.setHeader("ESB-ResultCode", "1");
					response.setIfSuccess(true);
					
					//首先查询总条数
					LOGGER.info("首先查询总条数,开始查询...");
					//首先查询不分页的总的数据，然后取集合的长度=sum，以及供最后的统计使用
					List<ReturnCollectingPaymentEntity> entityListTotal = 
							collectingPaymentService.queryCollectingPaymentByConditonTotal(queryCollectingPaymentEntity);
					
					Long sum = (long) entityListTotal.size();
					//判断如果foss有数据则foss数据
					if(sum > 0){
						//开始查询代收货款清单 entityList
						LOGGER.info("接着按照CRM传入的条件开始查询...");
						List<ReturnCollectingPaymentEntity> entityList = 	
								collectingPaymentService.queryCollectingPaymentByConditon(queryCollectingPaymentEntity);
						
						//对查询结果进行遍历,然后汇总,set到response中 
						double codAmountTotal = 0;		//代收金额总额 
						double codFeeTotal = 0;			//手续费总额
						double payableAmountTotal = 0;	//应付款总额
						double payCodAmountTotal = 0;	//已付金额合计
						
						//对金额部分进行总的便利叠加
						for(ReturnCollectingPaymentEntity entity : entityListTotal){
							//应付金额 = 代收货款金额 - 手续费
							entity.setPayableAmount(entity.getCodAmount()-entity.getCodFee());
							
							//费用统计集合汇总:代收金额总额 、手续费总额、应付款总额、已付金额合计
							//1.代收金额总额 
							codAmountTotal += entity.getCodAmount();
							
							//2.手续费总额
							codFeeTotal += entity.getCodFee();
							
							//3.应付款总额
							payableAmountTotal += entity.getPayableAmount();
							
							//4.已付金额合计
							if(StringUtils.equals("RD", entity.getPaymentStatus())){//状态为已支付运单
								payCodAmountTotal += entity.getCodAmount();
							}
						}
						//进行set汇总
						DecimalFormat df=new DecimalFormat("0.00"); 
						
						codAmountTotal = new Double(df.format(codAmountTotal).toString()); 
						response.setCodAmountTotal(codAmountTotal);
						
						codFeeTotal = new Double(df.format(codFeeTotal).toString());
						response.setCodFeeTotal(codFeeTotal);
						
						payableAmountTotal = new Double(df.format(payableAmountTotal).toString());
						response.setPayableAmountTotal(payableAmountTotal);
						
						payCodAmountTotal = new Double(df.format(payCodAmountTotal).toString());
						response.setPayCodAmountTotal(payCodAmountTotal);
						
						//对分页查询出来的单页进行遍历,不对合计进行遍历，合计的放到下面的for循环中
						validaExtracted(entityList);
						
						//总记录数
						response.setTotalItems(sum);
						//对查询结果集set
						response.setReturnCollectingPaymentEntity(entityList);
					
					//如果foss没有数据则查询CUBC数据
					}else{
						
						//String url = "http://10.224.161.23:8080/around-web/webservice/v1/around/queryCodsByWaybillNos/QueryLockStatus";
						//查询请求参数
						CodQueryToCubcDto queryDto = new CodQueryToCubcDto();
						//开始日期
						queryDto.setBillStartDate(queryCollectingPaymentEntity.getBeginTime());
						//结束日期
						queryDto.setBillEndDate(queryCollectingPaymentEntity.getEndTime());
						//客户编码
						queryDto.setCustomerCode(queryCollectingPaymentEntity.getCustomerCode());
						//分页起始位置
						queryDto.setStart(queryCollectingPaymentEntity.getCurrentPage()==-1 ? 1 : queryCollectingPaymentEntity.getCurrentPage());
						//分页大小
						queryDto.setPageSize(queryCollectingPaymentEntity.getSinglePages()==-1 ? Integer.MAX_VALUE : queryCollectingPaymentEntity.getSinglePages());
						LOGGER.info("调用CUBC查询代收请求参数："+JSON.toJSONString(queryDto));
						
						//cubc返回数据实体
						CodQueryToCubcResponse respDto = new CodQueryToCubcResponse();
						try {
							respDto = queryCubcService
									.queryCubcCodList(queryDto);
						} catch (Exception e) {
							LOGGER.info("调用CUBC查询代收异常",e);
							if(respDto == null) {
								//异常则
								respDto = new CodQueryToCubcResponse();
							}
						}
						LOGGER.info("调用CUBC查询代收返回参数："
								+ JSON.toJSONString(respDto));

						// 总记录数
						response.setTotalItems(respDto.getTotalNum());
						
						//转换数据集合
						List<ReturnCollectingPaymentEntity> convertEntityList = convertEntity(respDto.getCodAuditList());
						validaExtracted(convertEntityList);
						
						//分页数据集合
						response.setReturnCollectingPaymentEntity(convertEntityList);
						//代收货款总金额
						response.setCodAmountTotal(respDto.getTotalAmount().doubleValue());
						//手续费总金额
						response.setCodFeeTotal(respDto.getCodFeeTotal().doubleValue());
						//应付总金额
						response.setPayableAmountTotal(respDto.getPayableAmountTotal().doubleValue());
						//已付总金额
						response.setPayCodAmountTotal(respDto.getPayCodAmountTotal().doubleValue());
					}
					
					
				}else{
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					 response.setIfSuccess(false);
					 resp.setHeader("ESB-ResultCode", "1");
					 response.setErrorMsg("加锁失败");
				}
				
			}
		}catch (Exception e) {
			LOGGER.error("查询代收货款异常",e);
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("查询异常...");
		}finally{
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}
		
		return JSONObject.fromObject(response).toString();
		
	}

	/**
	 * 将从cubc查询到的数据转换成CRM需要的实体集合
	 * @param codAuditList
	 * @return List<ReturnCollectingPaymentEntity>
	 */
	private List<ReturnCollectingPaymentEntity> convertEntity(
			List<CodDO> codAuditList) {
		//返回转换的实体集合
		List<ReturnCollectingPaymentEntity> entityList = new ArrayList<ReturnCollectingPaymentEntity>();
		if(CollectionUtils.isNotEmpty(codAuditList)){
			
			List<ReturnCollectingPaymentEntity> list = collectingPaymentService
					.queryWaybillInfoByNos(codAuditList);
			
			//遍历集合codAuditList
			for(final CodDO cod  : codAuditList){
				ReturnCollectingPaymentEntity entity = new ReturnCollectingPaymentEntity();
				
				//日期(运单表里对应FOSS的开单时间，精确到日)
				entity.setBillTime(DateUtils.convert(cod.getBillDate()));
				//代收货款金额---(代收货款表中金额)
				entity.setCodAmount(cod.getCodAmount().doubleValue());
				//运单号---(代收货款表)
				entity.setWaybillNo(cod.getWaybillNo());
				//收货公司---(运单表中收货客户名称)
				entity.setReceiveCompany(cod.getReceiveCustomerName());
				//手续费---(运单表中代收货款手续费)
				entity.setCodFee(cod.getCodFee().doubleValue());
				//发货人---(运单表中发货客户联系人)
				entity.setDeliverCustomer(cod.getDeliverCustomerName());
				//退款类型---(运单表中字段‘退款类型’)
				entity.setRefundType(cod.getCodType());
				//付款状态--(代收货款表中status代收货款状态,也是‘代收货款综合查询中的付款状态字段’)
				entity.setPaymentStatus(cod.getStatus());
				
				//根据运单号找到对应的returnEntity
				ReturnCollectingPaymentEntity returnEntity = (ReturnCollectingPaymentEntity) CollectionUtils
						.find(list, new Predicate() {
							@Override
							public boolean evaluate(Object object) {
								return ((ReturnCollectingPaymentEntity) object)
										.getWaybillNo().equals(
												cod.getWaybillNo());
							}
						});
				
				if(returnEntity != null){
					//(省)
					entity.setCustomerProvName(returnEntity.getCustomerProvName());
					//市
					entity.setCustomerCityName(returnEntity.getCustomerCityName());
					//区
					entity.setCustomerDistName(returnEntity.getCustomerDistName());
					//收货人街道,不是CRM需要的，需要的在下面receiveArea(运单表中具体的收货具体地址，有的只是街道)
					entity.setCustomerAddress(returnEntity.getCustomerAddress());
					
					//收货人固定电话----(运单表中电话)
					entity.setReceiverHomePhone(returnEntity.getReceiverHomePhone());
					//收货人个人手机----(运单表中手机号)
					entity.setReceiverPersonTel(returnEntity.getReceiverPersonTel());
				}
				
				entityList.add(entity);
			}
			
		}
		return entityList;
	}

	private void validaExtracted(List<ReturnCollectingPaymentEntity> entityList) {
		for(ReturnCollectingPaymentEntity entity : entityList){
			//应付金额 = 代收货款金额 - 手续费
			entity.setPayableAmount(entity.getCodAmount()-entity.getCodFee());
			
			//对收货人电话、手机判断set
			if(!StringUtils.isEmpty(entity.getReceiverPersonTel())){
				entity.setReceiverPhone(entity.getReceiverPersonTel());//优先手机号码
			}else{
				entity.setReceiverPhone(entity.getReceiverHomePhone());//当手机号码为空的时候就用电话
			}
			//对收货地区进行拼接
			String pro = entity.getCustomerProvName();
			String city = entity.getCustomerCityName();
			String dist = entity.getCustomerDistName();
			String address = entity.getCustomerAddress();
			//将街道中重复省市区的，就不合并在一起
//			if(!(address.contains(pro)) && !(address.contains(city)) && !(address.contains(dist))){
			entity.setReceiveArea(pro+city+dist+address);//进行拼接
//			}
			
			//对对款类型判断并且转换汉语意思
			if(StringUtils.equals("RA", entity.getRefundType())){
				entity.setRefundType("审核退");
			}else if(StringUtils.equals("R1", entity.getRefundType())){
				entity.setRefundType("即日退");
			}else if(StringUtils.equals("R3", entity.getRefundType())){
				entity.setRefundType("三日退");
			}else{
				entity.setRefundType("无");
			}
			
			//对付款状态进行判断并且转换对应汉语意思
			if(StringUtils.equals("NR", entity.getPaymentStatus())){
				entity.setPaymentStatus("未退款");
			}else if(StringUtils.equals("RG", entity.getPaymentStatus())){
				entity.setPaymentStatus("退款中");
			}else if(StringUtils.equals("RD", entity.getPaymentStatus())){
				entity.setPaymentStatus("已退款");
			}else if(StringUtils.equals("RF", entity.getPaymentStatus())){
				entity.setPaymentStatus("退款失败");
			}else if(StringUtils.equals("RFA", entity.getPaymentStatus())){
				entity.setPaymentStatus("退款失败申请");
			}else if(StringUtils.equals("AG", entity.getPaymentStatus())){
				entity.setPaymentStatus("待审核");
			}else if(StringUtils.equals("CA", entity.getPaymentStatus())){
				entity.setPaymentStatus("收银员审核");
			}else if(StringUtils.equals("SF", entity.getPaymentStatus())){
				entity.setPaymentStatus("营业部冻结");
			}else if(StringUtils.equals("FF", entity.getPaymentStatus())){
				entity.setPaymentStatus("资金部冻结");
			}else if(StringUtils.equals("NRS", entity.getPaymentStatus())){
				entity.setPaymentStatus("反汇款成功");
			}
		}
	}

}
