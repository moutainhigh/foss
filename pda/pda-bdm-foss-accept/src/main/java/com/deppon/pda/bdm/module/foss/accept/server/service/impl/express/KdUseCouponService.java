package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AmountInfoEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.UseCoponDetailEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.UseCouponsEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file UseCouponService.java 
 * @description 快递使用优惠券服务类
 * @author ChenLiang
 * @created 2012-12-31 下午2:24:02    
 * @version 1.0
 */
public class KdUseCouponService implements IBusinessService<UseCoponDetailEntity, UseCouponsEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	private IPdaWaybillService pdaWaybillService;
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UseCouponsEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		UseCouponsEntity useCoupons = JsonUtil.parseJsonToObject(UseCouponsEntity.class, asyncMsg.getContent());
		return useCoupons;
	}
	
	/**
	 * 
	 * @description 数据校验
	 * @param asyncMsg
	 * @param useCoupons 
	 * @created 2012-12-31 下午3:31:45
	 */
	private void validate(AsyncMsg asyncMsg, UseCouponsEntity useCoupons) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//对实体字段进行非空性验证
		Argument.notNull(useCoupons, "UseCouponsEntity");
		//优惠券编号
		Argument.hasText(useCoupons.getCouponCode(), "UseCouponsEntity.couponCode");
		//运单号
		Argument.hasText(useCoupons.getWaybillCode(), "UseCouponsEntity.waybillCode");
		//Argument.hasText(useCoupons.getOrderCode(), "UseCouponsEntity.orderCode");
		//Argument.hasText(useCoupons.getOrderSource(), "UseCouponsEntity.orderSource");
		//Argument.hasText(useCoupons.getProductType(), "UseCouponsEntity.productType");
		//运单金额
		Argument.notNull(useCoupons.getWblMoney(), "UseCouponsEntity.wblMoney");
		//Argument.hasText(useCoupons.getConsignorMobile(), "UseCouponsEntity.consignorMobile");
		//Argument.hasText(useCoupons.getConsignorTelephone(), "UseCouponsEntity.consignorTelephone");
		//运单金额明细
		Argument.notEmpty(useCoupons.getWblMoneyDetail(), "UseCouponsEntity.wblMoneyDetail");
		//客户编号 快递取消客户编码为空校验
		//Argument.hasText(useCoupons.getCustomerCode(), "UseCouponsEntity.customerCode");
		//出发部门
		Argument.hasText(useCoupons.getDepartDeptCode(), "UseCouponsEntity.departDeptCode");
		//到达部门
		Argument.hasText(useCoupons.getAssemblyDeptCode(), "UseCouponsEntity.assemblyDeptCode");
		//出发部门外场 2013-11-06 新增
        //Argument.hasText(useCoupons.getLeaveOutDept(), "UseCouponsEntity.leaveOutDept");
        //到达部门外场  2013-11-06 新增
        //Argument.hasText(useCoupons.getArrivedOutDept(), "UseCouponsEntity.arrivedOutDept");
		
		//使用标志
		Argument.hasText(useCoupons.getUseMark(), "UseCouponsEntity.useMark");
	}

	/**
	 * 
	 * @description 使用优惠券服务
	 * @param asyncMsg
	 * @param useCoupons
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public UseCoponDetailEntity service(AsyncMsg asyncMsg, UseCouponsEntity useCoupons) throws PdaBusiException {
		this.validate(asyncMsg, useCoupons);
		CouponInfoDto couponInfo = new CouponInfoDto();
		// 优惠劵编码
		couponInfo.setCouponNumber(useCoupons.getCouponCode());
		// 是否使用
		couponInfo.setUsed("1".equals(useCoupons.getUseMark())? true: false);
		CouponWaybillInfoDto couponWaybillInfo= new CouponWaybillInfoDto();
		// 运单号
		couponWaybillInfo.setWaybillNumber(useCoupons.getWaybillCode());
		// 订单号
		couponWaybillInfo.setOrderNumber(useCoupons.getOrderCode());
		// 订单来源
		couponWaybillInfo.setOrderSource(useCoupons.getOrderSource());
		// 产品
		couponWaybillInfo.setProduceType(useCoupons.getProductType());
		// 发货人手机
		couponWaybillInfo.setLeaveMobile(useCoupons.getConsignorMobile());
		// 发货人电话
		couponWaybillInfo.setLeavePhone(useCoupons.getConsignorTelephone());
		// 客户编码
		couponWaybillInfo.setCustNumber(useCoupons.getCustomerCode());
		// 发货部门
		couponWaybillInfo.setLeaveDept(useCoupons.getDepartDeptCode());
		// 收货部门
		couponWaybillInfo.setArrivedDept(useCoupons.getAssemblyDeptCode());
		//出发外场 2013-11-06 新增
		couponWaybillInfo.setLeaveOutDept(useCoupons.getLeaveOutDept());
		//到达外场  2013-11-06 新增
		couponWaybillInfo.setArrivedOutDept(useCoupons.getArrivedOutDept());
		
		//运费
		couponWaybillInfo.setTotalAmount(new BigDecimal(useCoupons.getWblMoney()));
		
		List<AmountInfoDto> wblMoneyDetail = new ArrayList<AmountInfoDto>();
		AmountInfoDto amountInfoDto = null;
		for (AmountInfoEntity amountInfoVo : useCoupons.getWblMoneyDetail()) {
			amountInfoDto = new AmountInfoDto();
			// 计价条目编码
			amountInfoDto.setValuationCode(amountInfoVo.getCode());
			// 计价金额
			amountInfoDto.setValuationAmonut(BigDecimal.valueOf(amountInfoVo.getValue()));
			wblMoneyDetail.add(amountInfoDto);
		}
		// 费用明细
		couponWaybillInfo.setAmountInfoList(wblMoneyDetail);
		//运单信息
		couponInfo.setWaybillInfo(couponWaybillInfo);
		CouponInfoResultDto couponInfoResult = null;
		try {
			log.debug("---调用FOSS快递使用优惠劵接口开始---");
			long startTime = System.currentTimeMillis();
			couponInfoResult = pdaWaybillService.validateCoupon(couponInfo);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]快递使用优惠券接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS快递使用优惠劵接口结束---");
		if(!couponInfoResult.isCanUse()){
			throw new FossInterfaceException(null,couponInfoResult.getCanNotUseReason());
		}
		UseCoponDetailEntity useCoponDetail = new UseCoponDetailEntity();
		useCoponDetail.setIsUse(couponInfoResult.isCanUse()? "1":"0");
		useCoponDetail.setCouponMoney(couponInfoResult.getCouponAmount()!=null?couponInfoResult.getCouponAmount().doubleValue():0);
		useCoponDetail.setReason(couponInfoResult.getCanNotUseReason());
		log.debug("---返回快递使用优惠劵明细成功---");
		return useCoponDetail;
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_USE_COUPON.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
