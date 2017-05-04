package com.deppon.foss.module.settlement.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.dubbo.uip.api.define.ReceivableEntity;
import com.deppon.foss.dubbo.uip.api.define.exception.SettlementException;
import com.deppon.foss.dubbo.uip.api.service.DopRepaymentDubboService;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.dubbo.api.define.ConfigurationParamsConstants;
import com.deppon.foss.module.settlement.dubbo.api.service.IWaybillRfcService4dubbo;
import com.deppon.foss.util.define.FossConstants;

public class DopRepaymentServiceImpl4dubbo implements DopRepaymentDubboService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(DopRepaymentServiceImpl4dubbo.class);
	
	@Resource
	private IWaybillRfcService4dubbo waybillRfcService4dubbo;

	/**
	 * @author 327090
	 * 根据传入的一或多个运单单号，获取一或多条应收单
	 * @date 2016-12-7
	 */
	@SuppressWarnings("finally")
	@Override
	public List<ReceivableEntity> queryReceivableBill(
			List<ReceivableEntity> receivableList) {
		
		logger.info("DOP查询应收单开始");
		//运单号结合
		List<String> waybillNos = new ArrayList<String>();
		//网上支付应收单查询dto
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		//返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		try{
			//循环处理运单号
			for(int i = 0;i < receivableList.size();i++){
				//获取DOP运单号
				ReceivableEntity reEntity = receivableList.get(i);
				//运单号
				String waybillNo = reEntity.getWaybillNo();
				//运单号加入list
				waybillNos.add(waybillNo);
				//打印DOP参数
				logger.info("DOP查询应收单运单号：" + waybillNo);
			}
			//查询未受理的更改单
			List<String> waybillList = waybillRfcService4dubbo.isExsitsWayBillRfcs(waybillNos);
			//快递开单到付，限制补码前使用支付宝支付
			waybillRfcService4dubbo.noPayNotAddCode(waybillNos);
			//通过运单号查询应收单
			receivableEntityList = waybillRfcService4dubbo.queryByWaybillNOs(waybillNos,waybillList);
			//循环处理
			for(int i = 0;i < receivableEntityList.size();i++){
				//获取应收单
				ReceivableEntity receivableEntity = receivableEntityList.get(i);
				//网厅最大锁定时间（分）
				String sysLockTimeStr = waybillRfcService4dubbo.queryConfValueByCode(ConfigurationParamsConstants.STL_RECEIVABLE_LOCK_MINUTE);
				//设置锁定时间
				queryDto.setLockTime(Integer.parseInt(sysLockTimeStr));
				//应收单号
				queryDto.setReceivableNo(receivableEntity.getReceivableNo());
				//客户编码
				queryDto.setCustomerCode(receivableEntity.getCustomerCode());
				//锁定应收单
				waybillRfcService4dubbo.updateReceiveBillInfoForLock(queryDto);
			}
		}catch(SettlementException e){
			//清楚集合
			receivableEntityList.clear();
			for(int i = 0; i < waybillNos.size(); i++){
				//创建应收对象
				ReceivableEntity receivableEntity = new ReceivableEntity();
				//是否错误
				receivableEntity.setIsError(FossConstants.YES);
				//错误消息
				receivableEntity.setErrorMsg(e.getErrorCode());
				//运单号
				receivableEntity.setWaybillNo(waybillNos.get(i));
				//将应收实体添加到返回给DOP集合中
				receivableEntityList.add(receivableEntity);
			}
			logger.info("DOP查询异常：" + e.getErrorCode());
		}catch(Exception e1){
			//清楚集合
			receivableEntityList.clear();
			for(int i = 0; i < waybillNos.size(); i++){
				//创建应收对象
				ReceivableEntity receivableEntity = new ReceivableEntity();
				//是否错误
				receivableEntity.setIsError(FossConstants.YES);
				//错误消息
				receivableEntity.setErrorMsg(e1.getMessage());
				//运单号
				receivableEntity.setWaybillNo(waybillNos.get(i));
				//将应收实体添加到返回给DOP集合中
				receivableEntityList.add(receivableEntity);
			}
			logger.info("DOP查询异常：" + e1);
		}finally{
			logger.info("DOP查询应收单结束");
			return receivableEntityList;
		}
	}

}
