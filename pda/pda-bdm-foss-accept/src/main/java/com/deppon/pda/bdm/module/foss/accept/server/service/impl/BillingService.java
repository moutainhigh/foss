package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ValueAddServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BillingScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ValueAddServiceEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file BillingService.java 
 * @description 提交开单信息
 * @author ChenLiang
 * @created 2012-12-29 下午2:34:33    
 * @version 1.0
 */
public class BillingService implements IBusinessService<Void, BillingScanEntity> {
	
	private Logger log = Logger.getLogger(getClass());

	private IAcctDao acctDao;

	private IPdaWaybillService pdaWaybillService;
	
	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

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
	public BillingScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		BillingScanEntity billingScan = JsonUtil.parseJsonToObject(BillingScanEntity.class, asyncMsg.getContent());
		//pda编号
		billingScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		billingScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		billingScan.setScanType(asyncMsg.getOperType());
		//用户编号
		billingScan.setScanUser(asyncMsg.getUserCode());
		
		billingScan.setUploadTime(asyncMsg.getUploadTime());
		//设备类型
		billingScan.setPdaType(asyncMsg.getPdaType());
		return billingScan;
	}
	
	/**
	 * 
	 * @description 校验数据合法性 描述
	 * @param billingScan
	 * @throws PdaBusiException 
	 * @created 2012-12-29 下午2:04:03
	 */
	private void validate(AsyncMsg asyncMsg, BillingScanEntity billingScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		// 判断开单实体
		Argument.notNull(billingScan, "BillingScanEntity");
		Argument.hasText(billingScan.getId(), "BillingScanEntity.id");
		// 订单号
		// Argument.hasText(billingScan.getOrderCode(), "BillingScanEntity.orderCode");
		// 判断操作类型
		Argument.hasText(billingScan.getScanType(), "BillingScanEntity.scanType");
		// 判断运单号
		Argument.hasText(billingScan.getWblCode(), "BillingScanEntity.wblCode");
		// 判断出发部门
		Argument.hasText(billingScan.getDepartDeptCode(), "BillingScanEntity.departDeptCode");
		// 判断目的地
		Argument.hasText(billingScan.getDestinationCode(), "BillingScanEntity.destinationCode");
		// 判断提货方式
		Argument.hasText(billingScan.getTakeType(), "BillingScanEntity.takeType");
		// 判断运输性质
		Argument.hasText(billingScan.getTransType(), "BillingScanEntity.transType");
		// 判断件数
		Argument.isPositiveNum(billingScan.getPieces(), "BillingScanEntity.pieces");
		// 判断重量
		//Argument.isPositiveNum(billingScan.getWeight(), "BillingScanEntity.weight");
		// 判断体积  2013-08-07 xujun 现在PDA开单在某些情况下可以没有体积（非青岛车队且非现付）
		//Argument.isPositiveNum(billingScan.getVolume(), "BillingScanEntity.volume");
		// 判断代包装类型
		Argument.hasText(billingScan.getWrapType(), "BillingScanEntity.wrapType");
		// 车牌号
		Argument.hasText(billingScan.getTruckCode(), "BillingScanEntity.truckCode");
		// 判断付款方式
		Argument.hasText(billingScan.getPaidType(), "BillingScanEntity.paidType");
		// 司机编号
		Argument.hasText(billingScan.getScanUser(), "BillingScanEntity.scanUser");
		// 扫描标识
		Argument.hasText(billingScan.getScanFlag(), "BillingScanEntity.scanFlag");
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param billingScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, BillingScanEntity billingScan) throws PdaBusiException {
		this.validate(asyncMsg, billingScan);
		billingScan.setSyncStatus(Constant.SYNC_STATUS_INIT);
		
		// 保存开单扫描数据
		log.debug("---保存开单扫描数据开始---");
		acctDao.saveScanBilling(billingScan);
		log.debug("---保存开单扫描数据开始---");
		
		//兼容前一版本。保证不为空能保存
        if(billingScan.getMarketingCode()==null){
        	billingScan.setMarketingCode("");
        }
        if(billingScan.getMarketingName()==null){
        	billingScan.setMarketingName("");
        }
		
		
		// 保存开单信息
		log.debug("---保存开单数据开始---");
		acctDao.saveBilling(billingScan);
		log.debug("---保存开单数据开始---");
		
		// 保存运单标签信息
		
		// 保存运单增值服务项
		List<ValueAddServiceEntity> appreciationService = billingScan.getAppreciationService();
		if (appreciationService != null && !appreciationService.isEmpty()) {
			log.debug("---保存运单增值服务项总条数：" + appreciationService.size() + "---");
			for (ValueAddServiceEntity valueAddService : appreciationService) {
				if (valueAddService != null) {
					valueAddService.setId(UUIDUtils.getUUID());
					valueAddService.setBillingID(billingScan.getId());
					log.debug("---保存运单增值服务项，第"+ (appreciationService.indexOf(valueAddService) + 1 ) +"条开始---");
					acctDao.saveBillVolAddService(valueAddService);
				}
			}
			log.debug("---保存运单增值服务项结束---");
		}
		
		WaybillPdaDto waybillPdaDto = new WaybillPdaDto();
		// 订单号
		waybillPdaDto.setOrderNo(billingScan.getOrderCode());
		// 运单号
		waybillPdaDto.setWaybillNo(billingScan.getWblCode());
		// 出发部门
		waybillPdaDto.setStartOrg(billingScan.getDepartDeptCode());
		// 提货方式
		waybillPdaDto.setReceiveMethod(billingScan.getTakeType());
		// 目的地编号
		waybillPdaDto.setTargetOrgCode(billingScan.getDestinationCode());
		// 运输性质
		waybillPdaDto.setProductCode(billingScan.getTransType());
		// 件数
		waybillPdaDto.setGoodsQty(billingScan.getPieces());
		// 重量（单位：千克）
		waybillPdaDto.setGoodsWeightTotal(BigDecimal.valueOf(billingScan.getWeight()));
		// 体积(单位：立方米)
		waybillPdaDto.setGoodsVolumeTotal(BigDecimal.valueOf(billingScan.getVolume()));
		// 代打木架体积(单位：立方米)
		waybillPdaDto.setWoodVolume(BigDecimal.valueOf(billingScan.getGallowsVolume()));
		// 代打木箱体积(单位：立方米)
		waybillPdaDto.setWoodBoxVolume(BigDecimal.valueOf(billingScan.getBoxVolume()));
		// 货物类型
		waybillPdaDto.setGoodsTypeCode(billingScan.getCrgType());
		// 车牌号
		waybillPdaDto.setLicensePlateNum(billingScan.getTruckCode());
		// 运费
		waybillPdaDto.setAmount(BigDecimal.valueOf(billingScan.getFreight()));
		// 实收运费
		waybillPdaDto.setActualFee(BigDecimal.valueOf(billingScan.getPaidFreight()));
		// 优惠券编号
		waybillPdaDto.setDiscountNo(billingScan.getCouponCode());
		// 优惠金额
		waybillPdaDto.setDiscountAmount(BigDecimal.valueOf(billingScan.getCouponMoney()));
		// 付款方式
		waybillPdaDto.setPaidMethod(billingScan.getPaidType());
		// 是否打木架
		waybillPdaDto.setIsWood(billingScan.getIsGallows());
		// 司机所在车队部门
		waybillPdaDto.setBillOrgCode(billingScan.getDeptCode());
		// 创建时间
		waybillPdaDto.setCreateTime(billingScan.getScanTime());
		// 创建人员
		waybillPdaDto.setCreateUserCode(billingScan.getScanUser());
		// 开单时间
		waybillPdaDto.setBillStart(billingScan.getScanTime());
		//用户编号
		waybillPdaDto.setBillUserNo(billingScan.getScanUser());
		
		//返单类别
		waybillPdaDto.setReturnBillType(billingScan.getReturnBillType());
		//退款类型
		waybillPdaDto.setRefundType(billingScan.getRefundType());
		
		//上传营销活动编码和名称
		MarkActivitiesQueryConditionDto markActivit = new MarkActivitiesQueryConditionDto();
		markActivit.setCode(billingScan.getMarketingCode());
		markActivit.setName(billingScan.getMarketingName());		
		waybillPdaDto.setActiveDto(markActivit);
		
		
		
		//包装类型
		String[] wrapType = billingScan.getWrapType().split("\\|");
		waybillPdaDto.setPaper(Integer.parseInt(wrapType[0]));
		waybillPdaDto.setWood(Integer.parseInt(wrapType[1]));
		waybillPdaDto.setFibre(Integer.parseInt(wrapType[2]));
		waybillPdaDto.setSalver(Integer.parseInt(wrapType[3]));
		waybillPdaDto.setMembrane(Integer.parseInt(wrapType[4]));
		if(wrapType.length>5){
			waybillPdaDto.setOtherPackageType(wrapType[5]);
		}
		//封装实体
		List<ValueAddServiceDto> valueAddServiceDtoList = new ArrayList<ValueAddServiceDto>();
		ValueAddServiceDto valueAddServiceDto = null;
		if (appreciationService != null && !appreciationService.isEmpty()) {
			for (ValueAddServiceEntity valueAddServiceVo : appreciationService) {
				valueAddServiceDto = new ValueAddServiceDto();
				valueAddServiceDto.setCode(valueAddServiceVo.getCode());
				valueAddServiceDto.setAmount(BigDecimal.valueOf(valueAddServiceVo.getAmount()));
				valueAddServiceDto.setSubType(valueAddServiceVo.getSubType());  
//				valueAddServiceDto.set
				valueAddServiceDtoList.add(valueAddServiceDto);
			}
		}
		// 增值服务项
		waybillPdaDto.setValueAddServiceDtoList(valueAddServiceDtoList);
		
		//这里取值赋值的都是地址名称对应的Code
		// 省
		waybillPdaDto.setReceiveCustomerProvCode(billingScan.getProvince());
		// 市
		waybillPdaDto.setReceiveCustomerCityCode(billingScan.getCity());
		// 区
		waybillPdaDto.setReceiveCustomerDistCode(billingScan.getRegion());
		
		
		log.debug("---调用FOSS提交订单接口开始---");
		ResultDto resultDto= null;
		try {
			long startTime = System.currentTimeMillis();
			resultDto= pdaWaybillService.submitWaybillByPDA(waybillPdaDto);
			log.debug("---调用FOSS提交订单接口返回结果："+resultDto.getCode()+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]提交订单接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS提交订单接口结束---");
		return null;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_BILLING.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
}
