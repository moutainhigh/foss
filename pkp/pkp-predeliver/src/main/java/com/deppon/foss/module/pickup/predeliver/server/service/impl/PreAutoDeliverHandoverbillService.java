
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAutoDeliverHandoverDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IPreAutoDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 一、系统识别同时满足以下4个条件的运单，可以自动交单给调度排单：
1、	货物在库存中
2、	货物有送货日期
3、	到达部门为当前部门
4、	在自动交单的设置范围之内
【自动交单管理：参见ASP-用户需求说明书-交单管理】：
（1）	若交单管理中，操作部门的“自动交当日运单”时间已设置，则在此时间内自动读取满足货物在库存中且库存件数等于开单件数、送货日期为交单之日的、无异常未处理的运单；
（2）	若交单管理中，操作部门的“自动交第二日运单”时间已设置，则在此时间范围内，系统自动读取货物在库存中，送货日期为【交单日+1日】的、无异常未处理的运单。
（3）	若交单管理的自动交单管理“只自动交入库位的运单”字段进行了勾选设置，表示在设置的自动交单之间内【第（1）、（2）规则的时间设置】，只能交已进行库位确认的运单。
（4）	若不满足上述自动交单的条件的，不自动交单，但可人工交单。
（5）	自动交单时发现有更改单单未受理和有异常未处理，自动过滤。
二、自动交单不能交单的情形（满足其中之一即可）：
1、	若自动交单的运单有更改单未处理；
2、	有异常未处理完毕
3、	运单未补录
4、	开单为网上支付未付款的。
三、自动交单运单流向规则说明：
1、	若自动交单的部门为非驻地到达部门，交单之后，运单流向此非驻地到达部门的排单池【创建派送单（新）功能】
2、	若自动交单为驻地派送部，则交单之后运单流入对应顶级车队的排单池【创建派送单(新)功能】，供车队进行排单。
3、	若自动交单为驻地部门，但无对应的车队，交单之后，运单自动流入此驻地部门的排单池【创建派送单（新）功能】
4、	以上货物的流向与现在创建派送单功能的查询规则的数据获取方向是一致的，可以对此规则进行参考。
四、若运单的件数已经全部排单，则不自动交单（如：交单的运单在以前的创建派送单已经创建，则更新过滤）
五、自动交单后，待排单列表的数据更新最新待交单状态，运单状态变为已交单。


 * 
 * 查询满足自动交单信息service
 * @author 159231 meiying
 * 2015-4-20  上午11:24:28
 */
public class PreAutoDeliverHandoverbillService implements IPreAutoDeliverHandoverbillService { 
	
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.predeliver.server.service.impl.PreAutoDeliverHandoverbillService";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreAutoDeliverHandoverbillService.class);
	
	private ICUBCGrayService cUBCGrayService;
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}
	private IWaybillDao waybillDao;
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	/** 
	 * The Constant LOGGER
	 */
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	private IAutoDeliverHandoverDao autoDeliverHandoverDao;
	private ITakingService takingService;
	/**
	 * 设置deliverHandoverbillDao  
	 * @param deliverHandoverbillDao deliverHandoverbillDao 
	 */
	public void setDeliverHandoverbillDao(
			IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}	
	
	/**
	 * 初始化查询待自动交单运单查询条件
	 * @author 159231 meiying
	 * 2015-4-21  上午9:13:32
	 * @param 
	 */
	private void initPreDeliverHandoverbillQuery(AutoPreDeliverHandoverbillDto pre){
		pre.setActive(FossConstants.YES);
		// 默认运单不等于空运、偏线 快递的，整车
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE 
				,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,
				ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE};
		pre.setProductCodes(productCodes);
		// 默认查询中派送方式--派送
		pre.setReceiveMethodTmp(WaybillConstants.DELIVER_FREE);	
		
	}
	/**
	 * 查询满足自动交单信息		
	 * @author 159231 meiying
	 * 2015-5-28  下午1:54:30
	 * @param pre
	 * @return
	 */
	@Override
	@Transactional
	public void queryByAutoDeliverHandover() {
		AutoPreDeliverHandoverbillDto pre=new AutoPreDeliverHandoverbillDto();
		initPreDeliverHandoverbillQuery(pre);
		SimpleDateFormat myFmt2=new SimpleDateFormat("HH:mm:ss");
        String nowHMS= myFmt2.format(new Date());
        pre.setOneDayNowTime(nowHMS);
		List<AutoPreDeliverHandoverbillDto> oneDayDtos=deliverHandoverbillDao.queryByAutoDeliverHandover(pre);
		if(CollectionUtils.isNotEmpty(oneDayDtos)){
			for (AutoPreDeliverHandoverbillDto autoPreDeliverHandoverbillDto : oneDayDtos) {
				if(StringUtils.isNotBlank(autoPreDeliverHandoverbillDto.getPaidMethod()) 
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(autoPreDeliverHandoverbillDto.getPaidMethod()) ){
					List<String> waybillNoList=new ArrayList<String>();
					waybillNoList.add(autoPreDeliverHandoverbillDto.getWaybillNo());
					//调用结算接口判断 如果网上支付未完成时 给出相应提示
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
					String vestSystemCode = null;
					List<String> notPayWaybillNos = null;
                    try {
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNoList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryByAutoDeliverHandover",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode = list.get(0).getVestSystemCode();		
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".queryByAutoDeliverHandover");
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						notPayWaybillNos=takingService.unpaidOnlinePay(waybillNoList);
		                if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
		                	continue;
		                }
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//调用CUBC查询物流交易单  应收信息校验  353654
							FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
							requestDto1.setWaybillNos(waybillNoList);
							FossSearchTradeResponseDO responseDto1 = null;
							try {
								responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
							} catch (Exception e) {
								LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
								throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
							}
							if(responseDto1 != null){
								if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
									LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
									throw new SettlementException(responseDto1.getMsg());
								}
								Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
								List<TradeDO> tradeslist = dataMap.get(autoPreDeliverHandoverbillDto.getWaybillNo());
								if(!CollectionUtils.isEmpty(tradeslist)){
									for (TradeDO tradeDO : tradeslist) {
										if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
												equals(tradeDO.getOrderSubType())&&
												SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
												equals(tradeDO.getOrderSubType())
												&&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
											//添加数据
											notPayWaybillNos.add(autoPreDeliverHandoverbillDto.getWaybillNo());
											if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
							                	continue;
							                }
										}
									}	
								}
							}
					}
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
				}
				AutoDeliverHandoverEntity record=new AutoDeliverHandoverEntity();
				record.setWaybillNo(autoPreDeliverHandoverbillDto.getWaybillNo());
				record.setCreateTime(new Date());
				autoDeliverHandoverDao.insertSelective(record);
			}
		}
		nowHMS= myFmt2.format(new Date());
        pre.setTwoDayNowTime(nowHMS);
        pre.setOneDayNowTime(null);
		List<AutoPreDeliverHandoverbillDto> twoDayDtos=deliverHandoverbillDao.queryByAutoDeliverHandover(pre);
		if(CollectionUtils.isNotEmpty(twoDayDtos)){
			for (AutoPreDeliverHandoverbillDto autoPreDeliverHandoverbillDto : twoDayDtos) {
				if(StringUtils.isNotBlank(autoPreDeliverHandoverbillDto.getPaidMethod()) 
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(autoPreDeliverHandoverbillDto.getPaidMethod()) ){
					List<String> waybillNoList=new ArrayList<String>();
					waybillNoList.add(autoPreDeliverHandoverbillDto.getWaybillNo());
					//调用结算接口判断 如果网上支付未完成时 给出相应提示
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
					String vestSystemCode = null;
					List<String> notPayWaybillNos = null;
                    try {
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNoList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryByAutoDeliverHandover",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode = list.get(0).getVestSystemCode();		
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".queryByAutoDeliverHandover-237行");
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						notPayWaybillNos=takingService.unpaidOnlinePay(waybillNoList);
		                if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
		                	continue;
		                }
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						//调用CUBC查询物流交易单  应收信息校验  353654
							FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
							requestDto1.setWaybillNos(waybillNoList);
							FossSearchTradeResponseDO responseDto1 = null;
							try {
								responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
							} catch (Exception e) {
								LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
								throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
							}
							if(responseDto1 != null){
								if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
									LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
									throw new SettlementException(responseDto1.getMsg());
								}
								Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
								List<TradeDO> tradeslist = dataMap.get(autoPreDeliverHandoverbillDto.getWaybillNo());
								if(!CollectionUtils.isEmpty(tradeslist)){
									for (TradeDO tradeDO : tradeslist) {
										if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
												equals(tradeDO.getOrderSubType())&&
												SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
												equals(tradeDO.getOrderSubType())
												&&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
											//添加数据
											notPayWaybillNos.add(autoPreDeliverHandoverbillDto.getWaybillNo());
											if(CollectionUtils.isNotEmpty(notPayWaybillNos)){
							                	continue;
							                }
										}
									}	
								}
							}
					}
					//开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
				}
				AutoDeliverHandoverEntity record=new AutoDeliverHandoverEntity();
				record.setWaybillNo(autoPreDeliverHandoverbillDto.getWaybillNo());
				record.setCreateTime(new Date());
				autoDeliverHandoverDao.insertSelective(record);
			}
		}
	}
	
	/**
	 * 设置autoDeliverHandoverDao  
	 * @param autoDeliverHandoverDao autoDeliverHandoverDao 
	 */
	public void setAutoDeliverHandoverDao(
			IAutoDeliverHandoverDao autoDeliverHandoverDao) {
		this.autoDeliverHandoverDao = autoDeliverHandoverDao;
	}
	/**
	 * 设置takingService  
	 * @param takingService takingService 
	 */
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}
	
}