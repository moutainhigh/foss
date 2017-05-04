package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ITPSSignSettleService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-05 15:58:02
 * FOSS接收TPS传送的签收信息
 *
 */
public class TPSSignSettleService implements ITPSSignSettleService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TPSReverseSignSettleService.class);

	// 运单签收结果
	private IWaybillSignResultService waybillSignResultService;

	//结清货款表
	private IRepaymentService repaymentService;
	
	//实际承运表
	private IActualFreightService actualFreightService;
	
	@Override
	@Transactional
	public void signSettle(WaybillSignResultEntity entity) {

		// 查询实际货物信息
		ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
					
		// 判断运单 是否已终止或已作废
		if (actentity != null && (WaybillConstants.ABORTED.equals(actentity.getStatus()) 
				|| WaybillConstants.OBSOLETE.equals(actentity.getStatus()))) {
			// 抛出业务异常
			LOGGER.error("TPS同步签收信息至FOSS失败"+RepaymentException.WAYBILLRFC_ERROR);//记录日志
			throw new RepaymentException(RepaymentException.WAYBILLRFC_ERROR); 
		}	
		
		try {
			//设置id
			entity.setId(UUIDUtils.getUUID());
			//设置签收时间
			entity.setSignTime(new Date());
			entity.setCreateTime(new Date());
			//设置修改时间
			Timestamp buydate=Timestamp.valueOf("2999-12-31 00:00:00");
			entity.setModifyTime(buydate);
			
			RepaymentEntity repaymentEntity = new RepaymentEntity();
			// 生成付款编号
			StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			//拼接付款编号
			dateStr = dateStr.append(entity.getWaybillNo());
			//运单号
			repaymentEntity.setWaybillNo(entity.getWaybillNo());
			//付款编号
			repaymentEntity.setRepaymentNo(dateStr.toString());
			//付款时间
			repaymentEntity.setPaymentTime(new Date());
			//付款方式
			if(StringUtils.isNotBlank(entity.getTotalPaymentType())){ //整车传入的到付运费付款方式
				repaymentEntity.setPaymentType(entity.getTotalPaymentType());
			}else if(StringUtils.isNotBlank(entity.getCodPaymentType())){ //整车传入的代收货款付款方式
				repaymentEntity.setPaymentType(entity.getCodPaymentType());
			}else{
				repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			}
			// 结清是否有效
			repaymentEntity.setActive(FossConstants.YES);
			//操作人
			repaymentEntity.setOperator(entity.getCreator());
			//操作人编码
			repaymentEntity.setOperatorCode(entity.getCreatorCode());
			//操作部门
			repaymentEntity.setOperateOrgName(entity.getCreateOrgName());
			//操作部门编码
			repaymentEntity.setOperateOrgCode(entity.getCreateOrgCode());
			//币种
			repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 实收代收货款费用
			repaymentEntity.setCodAmount(null == entity.getCodAmount() ? BigDecimal.ZERO  : entity.getCodAmount());
			// 实收到付运费
			repaymentEntity.setActualFreight(null == entity.getToPayAmount() ? BigDecimal.ZERO  : entity.getToPayAmount());
			// 设置财务单据未生成
			repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			// job id
			repaymentEntity.setJobId(UUIDUtils.getUUID());
			
			repaymentService.addPaymentInfo(repaymentEntity);
			
			//更新运单的结清状态
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
			//已结清
			actualFreightEntity.setSettleStatus(FossConstants.YES);
			//结清时间
			actualFreightEntity.setSettleTime(new Date());
			//更新actualFreight表
			actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
					
			
			/**
			 * @author 330547
			 * @date 2017-03-3 17:07:30
			 * 说明:(1)VTS的业务，部分签收之后，不允许反签收；考虑的业务是反签收时候他们页面扛不住
			 * (2)下面的逻辑是首先去签收结果表中查询根据单号+active为Y，如果查询结果为空继续走原来的；
			 * (3)如果有再判断签收状态是部分签收还是全部签收: SIGN_STATUS_ALL 全部签收; SIGN_STATUS_PARTIAL 部分签收
			 * (4)如果查询出来的数据签收状态是部分签收，把上次签收的记录置成N，新增的为Y
			 * (5)如果查询出来的数据签收状态是全部签收，插入一条为N的记录(这是考虑到异步接口，接收到同一个单号的签收数据不知道谁先谁后)
			 */
			WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
			resultEntity.setWaybillNo(entity.getWaybillNo());
			resultEntity.setActive(FossConstants.ACTIVE);
			WaybillSignResultEntity querySignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
			
			
			
			//如果查询结果为空，那就说明，FOSS签收结果表中还没有签收记录，执行原来的操作不变
			if(querySignResultEntity == null){				
				//插入签收结果信息
				waybillSignResultService.addWaybillSignResult(entity);
				
			}else{//如果查询结果不为空走else，里面再继续判断
				//把之前有效的运单签收结果表数据做update操作
				WaybillSignResultEntity updateEntity = new WaybillSignResultEntity();
				updateEntity.setId(querySignResultEntity.getId());
				updateEntity.setActive(FossConstants.INACTIVE);
				updateEntity.setModifyTime(new Date());
				waybillSignResultService.updateWaybillSignResult(updateEntity);
				//插入新的签收结果信息
				// 运单签收结果的签收件数=原先运单签收的件数+到达联签收件数
				entity.setSignGoodsQty(entity.getSignGoodsQty() + querySignResultEntity.getSignGoodsQty());
	            // 签收情况
				entity.setSignSituation(SignConstants.NORMAL_SIGN.equals(entity.getSignSituation()) ? querySignResultEntity.getSignSituation() : SignConstants.UNNORMAL_SIGN);
				waybillSignResultService.addWaybillSignResult(entity);		
			}
		} catch (SignException e) {
			LOGGER.error("TPS同步签收信息至FOSS失败"+e.getErrorCode(),e);//记录日志
			throw new SignException("TPS同步签收信息至FOSS失败！");
		}
		
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

}
