package com.deppon.foss.module.settlement.bho.server.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.bho.server.inter.DopRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentReceiveBillService;
import com.deppon.foss.util.define.FossConstants;

public class DopRepaymentServiceImpl implements DopRepaymentService {
	
	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(DopRepaymentServiceImpl.class);
	
	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;
	
	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 网上支付：查询未核销的应收单服务
	 */
	private IOnlinePaymentReceiveBillService onlinePaymentReceiveBillService;
	
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 注入运单IWaybillManagerService
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * message显示的最大长度
	 */
	private static final int MESSAGE_MAX_LENGTH = 100;

	/**
	 * 根据传入的一或多个运单单号，获取一或多条应收单
     * @author 邓大伟
     * @date 2014-12-12
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	@Override
	public String queryReceivableBill(String receivableBill) {
		logger.info("DOP查询应收单开始");
		//运单号结合
		List<String> waybillNos = new ArrayList<String>();
		//网上支付应收单查询dto
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		//返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		try{
			//将接收到的字符串转换成JSONArray
			JSONArray reJsonArray = JSONArray.fromObject(receivableBill);
			//将reJsonArray转换成应收数组
			List<ReceivableEntity> receivableList = JSONArray.toList(reJsonArray, ReceivableEntity.class);
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
			List<String> waybillList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
			//快递开单到付，限制补码前使用支付宝支付
			this.noPayNotAddCode(waybillNos);
			//通过运单号查询应收单
			receivableEntityList = billReceivableService.queryByWaybillNOs(waybillNos,waybillList);
			//循环处理
			for(int i = 0;i < receivableEntityList.size();i++){
				//获取应收单
				ReceivableEntity receivableEntity = receivableEntityList.get(i);
				//网厅最大锁定时间（分）
				String sysLockTimeStr = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_RECEIVABLE_LOCK_MINUTE);
				//设置锁定时间
				queryDto.setLockTime(Integer.parseInt(sysLockTimeStr));
				//应收单号
				queryDto.setReceivableNo(receivableEntity.getReceivableNo());
				//客户编码
				queryDto.setCustomerCode(receivableEntity.getCustomerCode());
				//锁定应收单
				billReceivableService.updateReceiveBillInfoForLock(queryDto);
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
			//将应收单集合转换成JSONArray
			JSONArray receivableJsonArray = JSONArray.fromObject(receivableEntityList);
			response.addHeader("ESB-ResultCode", "1");
			logger.info("DOP查询应收单结束");
			return receivableJsonArray.toString();
		}
	}

	/**
	 * 根据传入的一或多个运单单号，核销一或多条应收单
     * @author 邓大伟
     * @date 2014-12-12
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "finally" })
	@Override
	public String repaymentReceivableBill(String receivableBill) {
		logger.info("DOP核销应收单开始");
		//运单号结合
		List<String> waybillNos = new ArrayList<String>();
		//网上支付应收单查询dto
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		//返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		try{
			//将接收到的字符串转换成JSONArray
			JSONArray reJsonArray = JSONArray.fromObject(receivableBill);
			//将reJsonArray转换成应收数组
			List<ReceivableEntity> receivableList = JSONArray.toList(reJsonArray, ReceivableEntity.class);
			//循环处理运单号
			for(int i = 0;i < receivableList.size();i++){
				//获取DOP运单号
				ReceivableEntity reEntity = receivableList.get(i);
				//运单号
				String waybillNo = reEntity.getWaybillNo();
				//运单号加入list
				waybillNos.add(waybillNo);
				//打印DOP参数
				logger.info("DOP核销运单单号：" + reEntity.getWaybillNo());
				logger.info("DOP核销应收金额：" + reEntity.getUnverifyAmount());
				logger.info("DOP核销汇款编码：" + reEntity.getOnlineNo());
			}
			//查询未受理的更改单
			List<String> waybillList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
			//快递开单到付，限制补码前使用支付宝支付
			this.noPayNotAddCode(waybillNos);
			//通过运单号查询应收单
			receivableEntityList = billReceivableService.queryByWaybillNOs(waybillNos,waybillList);
			//系统当前时间
			Date date = new Date();
			//循环处理应收单进行核销
			for(int i = 0;i < receivableEntityList.size();i++){
				//获取应收单实体
				ReceivableEntity receivableEntity = receivableEntityList.get(i);
				//循环处理
				for(int j = 0;j < receivableList.size();j++){
					//获取DOP参数
					ReceivableEntity receivable = receivableList.get(j);
					//判断运单号是否一致
					if(receivableEntity.getWaybillNo().equals(receivable.getWaybillNo())){
						//在线支付编号
						queryDto.setOnlineNo(receivable.getOnlineNo());
						//金额
						queryDto.setAmount(receivable.getUnverifyAmount());
						//在线支付编号
						receivableEntity.setOnlineNo(receivable.getOnlineNo());
						//金额
						receivableEntity.setUnverifyAmount(receivable.getUnverifyAmount());
						continue;
					}
				}
				//设置应收单单号
				queryDto.setReceivableNo(receivableEntity.getReceivableNo());
				//记账日期
				queryDto.setAccountDate(date);
				//调用网上支付接口进行核销
				onlinePaymentReceiveBillService.paymentReceiveBillInfo(queryDto);
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
			logger.info("DOP核销异常：" + e.getErrorCode());
		}catch(Exception e1){
			//清楚集合
			receivableEntityList.clear();
			for(int i = 0; i < waybillNos.size(); i++){
				//创建应收对象
				ReceivableEntity receivableEntity = new ReceivableEntity();
				//是否错误
				receivableEntity.setIsError(FossConstants.YES);
				//错误消息
				receivableEntity.setErrorMsg(e1.getMessage().substring(0, MESSAGE_MAX_LENGTH));
				//运单号
				receivableEntity.setWaybillNo(waybillNos.get(i));
				//将应收实体添加到返回给DOP集合中
				receivableEntityList.add(receivableEntity);
			}
			logger.info("DOP核销异常：" + e1);
		}finally{
			//将应收单集合转换成JSONArray
			JSONArray receivableJsonArray = JSONArray.fromObject(receivableEntityList);
			response.addHeader("ESB-ResultCode", "1");
			logger.info("DOP核销应收单结束");
			return receivableJsonArray.toString();
		}
	}


	/**
	 * 快递开单到付，限制补码前使用支付宝支付
     * @author 黄乐为
     * @date 2016-01-07
	 */
	public void noPayNotAddCode(List<String> waybillNos) {
		//循环校验每一个运单
		for (int i = 0; i < waybillNos.size(); i++) {
			//调用接口进行查询运单实体
			WaybillDto waybillDto = waybillManagerService.queryWaybillByNo(waybillNos.get(i));
			//判断运单实体DTO是否为空
			if(waybillDto == null){
				throw new SettlementException("运单 " + waybillNos.get(i) + "没有对应的运单实体信息！");
			}
			//判断运单基础是否为空
			if(waybillDto.getWaybillEntity() == null){
				throw new SettlementException("运单 " + waybillNos.get(i) + "没有对应的运单基础信息!");
			}
			//如果是快递
			if(SettlementUtil.isPackageProductCode(waybillDto.getWaybillEntity().getProductCode())){
				//到付
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(waybillDto.getWaybillEntity().getPaidMethod())){
					//判断该运单是否有对应的快递信息
					if(waybillDto.getWaybillExpressEntity() == null){
						throw new SettlementException("运单 " + waybillNos.get(i) + "没有对应的快递信息!");
					}
					//未补码
					if(!FossConstants.ACTIVE.equals(waybillDto.getWaybillExpressEntity().getIsAddCode())){
						throw new SettlementException("请在运单 " + waybillNos.get(i) + "补码后再进行支付!");
					}
				}
			}
		}
	}
	
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setOnlinePaymentReceiveBillService(IOnlinePaymentReceiveBillService onlinePaymentReceiveBillService) {
		this.onlinePaymentReceiveBillService = onlinePaymentReceiveBillService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

}
