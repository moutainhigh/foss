package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ValueAddServiceEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ElecBillingScanEntity;

/**   
 * @ClassName ActiveEWaybillService  
 * @Description 激活散客电子运单（扫描上传）   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class ActiveIndividualEWaybillService implements IBusinessService<Void, ElecBillingScanEntity> {
	
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
	public ElecBillingScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		ElecBillingScanEntity billingScan = JsonUtil.parseJsonToObject(ElecBillingScanEntity.class, asyncMsg.getContent());
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
	private void validate(AsyncMsg asyncMsg, ElecBillingScanEntity billingScan) throws PdaBusiException {
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
	
		// 司机编号
		Argument.hasText(billingScan.getScanUser(), "BillingScanEntity.scanUser");
		// 扫描标识
		//Argument.hasText(billingScan.getScanFlag(), "BillingScanEntity.scanFlag");
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
	public Void service(AsyncMsg asyncMsg, ElecBillingScanEntity billingScan) throws PdaBusiException {
		this.validate(asyncMsg, billingScan);
		billingScan.setWaybillType(Constant.WAYBILLTYPE.E_INDIVIDUALCUSTOMER_WAYBILL);
		
		// 保存开单扫描数据
		log.debug("---保存开单扫描数据开始---");
		acctDao.saveIndividualEScanBilling(billingScan);
		log.debug("---保存开单扫描数据开始---");
		
		// 保存开单信息
		log.debug("---保存开单数据开始---");
		acctDao.saveIndividualEBilling(billingScan);
		log.debug("---保存开单数据开始---");
		
		// 保存开单分录信息
		log.debug("---保存开单数据开始---");
		acctDao.saveIndividualEBillingDetail(billingScan);
		log.debug("---保存开单数据开始---");
		
		
		log.debug("---保存增值服务开始--");
			
			ValueAddServiceEntity valueAddService = new ValueAddServiceEntity();
			valueAddService.setId(UUIDUtils.getUUID());
			valueAddService.setBillingID(billingScan.getId());
			valueAddService.setCode(billingScan.getReciveLoanType());
			valueAddService.setAmount(Double.valueOf(billingScan.getReviceMoneyAmount()));
			acctDao.saveBillVolAddService(valueAddService);	
			
			valueAddService = new ValueAddServiceEntity();
			valueAddService.setId(UUIDUtils.getUUID());
			valueAddService.setBillingID(billingScan.getId());
			valueAddService.setCode(billingScan.getReturnBillingType());  //返单类别
			valueAddService.setAmount(Double.valueOf(billingScan.getInsuredAmount()));//报价金额
			
			acctDao.saveBillVolAddService(valueAddService);		
		log.debug("---保存增值服务结束---");
		
		WaybillExpressPdaDto waybillExpressPdaDto=null;
		log.debug("---调用FOSS激活散客电子运单开始---");
		try {			
			waybillExpressPdaDto = billExpressPdaDtos(billingScan);
		long startTime = System.currentTimeMillis();
		pdaWaybillService.activeEWaybillByPda(waybillExpressPdaDto, billingScan.getRevenueCode());
		long endTime = System.currentTimeMillis();
		QueueMonitorInfo.addTotalFossTime(endTime-startTime);
		log.info("[asyncinfo]提交散客电子运单接口消耗时间:"+(endTime-startTime)+"ms");				
		} catch (BusinessException e) {
		throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS提交散客电子运单接口结束---");
		
		
		return null;
	}

	
	private  WaybillExpressPdaDto billExpressPdaDtos(ElecBillingScanEntity billingScan){
		WaybillExpressPdaDto dto = new WaybillExpressPdaDto();
		
		//单号
		dto.setWaybillNo(billingScan.getWblCode());
		//件数
		dto.setGoodsQty(billingScan.getPieces());
		//运输方式
		dto.setProductCode(billingScan.getTransType());
		//提货方式
		dto.setReceiveMethod(billingScan.getTakeType());
		//付款方式
		dto.setPaidMethod(billingScan.getPayType());
		//重量
		dto.setGoodsWeightTotal(BigDecimal.valueOf
				(Double.valueOf(billingScan.getWeight())));
		//体积
		dto.setGoodsVolumeTotal(BigDecimal.valueOf
				(Double.valueOf(billingScan.getVolume())));
		//保险声明价值
		dto.setInsuranceAmount(BigDecimal.valueOf
				(Double.valueOf(billingScan.getInsuredAmount())));
		
		//代收货款金额
		dto.setCodAmount(BigDecimal.valueOf
				(Double.valueOf(billingScan.getReviceMoneyAmount())));
		//返单类别
		dto.setReturnBillType(billingScan.getReturnBillingType());
		//优惠券
		dto.setDiscountNo(billingScan.getCouponNum());
		
		//车牌号
		dto.setLicensePlateNum(billingScan.getTruckCode());			
		//优惠金额
		dto.setDiscountAmount(BigDecimal.valueOf
				(Double.valueOf(billingScan.getCouponMoney())));		
	    //银行交易流水号
		dto.setBankTradeSerail(billingScan.getBankTradeSerail());
		
		//代收货款类型
		//private String reciveLoanType;
		dto.setReciveLoanType(billingScan.getReciveLoanType());
		//运单类型		
		//private String waybillType;
		dto.setWaybillType(billingScan.getWaybillType());
		
		return dto;
		
	}
	
	
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_INDIVIDUALEWAYBILLSCAN.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
}
