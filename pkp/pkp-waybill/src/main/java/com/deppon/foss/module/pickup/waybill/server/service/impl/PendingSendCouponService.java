package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.crm.CrmSendCouponRequest;
import com.deppon.esb.inteface.domain.crm.GoodsLineCity4Reduceprice;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.PendingSendCouponException;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendCouponVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.ReturnSendCouponParamVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PendingSendCouponService implements IPendingSendCouponService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PendingSendCouponService.class);
	//每次要查询的条数
	private static final String queryNum = "1000";
	//要查询的JobId
	public static final String queryJobId = "N/A";
	
	public static final int NUMBER_490 = 490;
	
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 在全国使用的短信模板
	 */
	public static final String SEND_COUPON_CONTENT_ALL = "foss.pendingSendCoupon.sendCouponContent_all";
//	public static final String SEND_COUPON_CONTENT_ALL = "-*promotionsFee*元优惠券，编码：***，有效期至*couponEndTime*，该优惠券在全国使用。用券详情咨询当地营业部！【德邦物流】";
	/**
	 * 线路限制的短信模板
	 */
	public static final String SEND_COUPON_CONTENT_LINE = "foss.pendingSendCoupon.sendCouponContent_line";
	/**
	 * 区域限制的短信模板
	 */
	public static final String SEND_COUPON_CONTENT_CITY = "foss.pendingSendCoupon.sendCouponContent_city";
	
	private String crmUrl;
	
	public String getCrmUrl() {
		return crmUrl;
	}

	public void setCrmUrl(String crmUrl) {
		this.crmUrl = crmUrl;
	}
	private IMessageBundle messageBundle; 
	
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	/**
	 * 注入pendingSendCouponService，为了实现事务
	 */
	private IPendingSendCouponService pendingSendCouponService;	
	
	public void setPendingSendCouponService(
			IPendingSendCouponService pendingSendCouponService) {
		this.pendingSendCouponService = pendingSendCouponService;
	}

	/**
	 * 待处理返券Dao
	 */
	private IPendingSendCouponDao pendingSendCouponDao;
	
	public void setPendingSendCouponDao(IPendingSendCouponDao pendingSendCouponDao) {
		this.pendingSendCouponDao = pendingSendCouponDao;
	}
	
	/**
	 * 待处理返券日志DAO
	 * 
	 * **/
	private IPendingSendCouponLogDao pendingSendCouponLogDao; 
	

	public void setPendingSendCouponLogDao(
			IPendingSendCouponLogDao pendingSendCouponLogDao) {
		this.pendingSendCouponLogDao = pendingSendCouponLogDao;
	}

	/**
	 * 添加待处理返券
	 * @author 
	 * @date 
	 */
	@Override
	public void addPendingSendCoupon(PendingSendCouponEntity pendingSendCoupon) {
		/**
		 * 判断添加待处理返券信息各个属性是否为空，如果为空，则抛出异常给予提示
		 */
		if (pendingSendCoupon == null) {
			throw new PendingSendCouponException(PendingSendCouponException.PENDING_SENDCOUPON_ENTITY_IS_NULL);
		}
		//添加待处理返券信息
		pendingSendCouponDao.addPendingSendCouponEntity(pendingSendCoupon);
	}
	
	/**
	 * 定时任务处理待发返券
	 * @author 
	 * @date 
	 */
	@Override
	public void batchjobs() {
		/**
		 * 创建VO，并设置受影响条数为queryNum，为了第一次能进入循环
		 */
		PendingSendCouponVo vo=new PendingSendCouponVo();
		vo.setResultNum(Integer.parseInt(queryNum));
		while(vo.getResultNum()==Integer.parseInt(queryNum)){
			String jobId = UUIDUtils.getUUID();
			//更新一定数量的JobId
			vo=pendingSendCouponService.updatePendingSendCouponForJobTopNum(jobId,queryNum);	
			//根据JobId查询待处理信息--状态为N的待返券信息
			List<PendingSendCouponEntity> psList = pendingSendCouponDao.queryPendingSendCouponEntityByJobID(vo.getJobId());
			if (psList!=null && psList.size() > 0) {			
				for (int i = 0; i < psList.size(); i++) {
					//执行返券
					executeSendCoupon(psList.get(i));
				}
			}
		}
	}
	
	/**
	 * 调用CRM的返券接口，删除已返券的返券信息--添加一条已删除的返券信息至日志表
	 * 
	 * @param pendingSendCouponEntity 优惠券使用条件信息
	 * 
	 * @author Foss-206860
	 * */
	@Transactional
	private void executeSendCoupon(
			PendingSendCouponEntity pendingSendCouponEntity) {
		try{
		    //实际返券时间=优惠券有效起始时间
		    String factSendCouponTime = dateformat.format(pendingSendCouponEntity.getCouponBeginTime());
		    //当前时间
		    String nowTime = dateformat.format(new Date());
		    
	       /**
	        * 满足以下条件调用CRM的返券接口：
	        * 	1、获取的数据中的是否已返券字段值为N
	        * 	2、数据中的收货人手机号码不为空
	        * 	3、当前时间大于实际返券时间
	        * */
			if(FossConstants.NO.equals(pendingSendCouponEntity.getSendCoupon()) 
					&& !pendingSendCouponEntity.getDeliveryCustomerMobilephone().isEmpty()
					&& nowTime.compareTo(factSendCouponTime) >= 0){
				//调用crm的接口进行返券--待写
				ReturnSendCouponParamVo returnSendCouponParamVo = createCoupon(pendingSendCouponEntity);
				//当返券成功，处理优惠券---将待返券当前信息保存至返券日志表中，删除待返券信息
				if(FossConstants.YES.equals(returnSendCouponParamVo.getSuccFlag())){
					//返券成功之后处理优惠券信息
					afterSendCoupon(pendingSendCouponEntity,returnSendCouponParamVo);
				}else{
					//当返券不成功,处理优惠券---将优惠券编码和异常信息更新至至待返券表
					pendingSendCouponEntity.setJobId(queryJobId);
					pendingSendCouponEntity.setCouponNo(returnSendCouponParamVo.getCouponNo());
					if(StringUtil.isNotEmpty(returnSendCouponParamVo.getExceptionInfo())){
						if(returnSendCouponParamVo.getExceptionInfo().length() > NumberConstants.NUMBER_300){
							pendingSendCouponEntity.setCouponExcep(returnSendCouponParamVo.getExceptionInfo().substring(0,NumberConstants.NUMBER_300));
						}else{
							pendingSendCouponEntity.setCouponExcep(returnSendCouponParamVo.getExceptionInfo());
						}
					}else{
						pendingSendCouponEntity.setCouponExcep("返券失败且CRM没有返回错误信息");
					}
					pendingSendCouponEntity.setModifyTime(new Date());
					pendingSendCouponDao.updatePendingSendCoupon(pendingSendCouponEntity);
				}
			}else{
				//若不返券，则将记录还原，将jobID-->N/A
				pendingSendCouponEntity.setJobId(queryJobId);
				pendingSendCouponEntity.setModifyTime(new Date());
				pendingSendCouponDao.updatePendingSendCouponJobToNA(pendingSendCouponEntity);	
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			String error = ExceptionUtils.getFullStackTrace(e);
			if(error.length() > NUMBER_490){
				pendingSendCouponEntity.setFaileReason("异常："+error.substring(0,NUMBER_490));
			}else{
				pendingSendCouponEntity.setFaileReason("异常："+error);
			}
			pendingSendCouponEntity.setOperateType(WaybillConstants.UNACTIVE_PENDINGCOUPON_SEND_FAILURE);
			pendingSendCouponEntity.setJobId(queryJobId);
			pendingSendCouponEntity.setModifyTime(new Date());
			//更新待返券记录表--->记录失败原因
			pendingSendCouponDao.updateSendCouponByID(pendingSendCouponEntity);
		}
		
		
		
	}
	
	/**
     * <p>替换短信内容里面的参数</p>.
     *
     */
	private String repalceParam(Map<String,String> map,String smsContent){
		if(StringUtil.isNotBlank(smsContent)){
		    for(Map.Entry<String, String> entry : map.entrySet()){
			    //获取参数对应的字符串
			    String paramValue = entry.getValue();
			    String paramKey = entry.getKey();
			    //把短信模板的内容根据传入的参数替换
			    smsContent = smsContent.replace("*"+paramKey+"*",paramValue != null ? paramValue : "");
		    }
		}
		return smsContent;
	}

	/**
	 * 调用crm的接口进行返券
	 * */
	@Transactional
	private ReturnSendCouponParamVo createCoupon(
			PendingSendCouponEntity pendingSendCouponEntity) {
		ReturnSendCouponParamVo returnSendCouponParamVo = new ReturnSendCouponParamVo();
		try{
			CrmSendCouponRequest crmSendCouponRequest = new CrmSendCouponRequest();
			 
			StringBuffer sendCouponCon = new StringBuffer(pendingSendCouponEntity.getSendCouponContent());
			//封装短信内容
			String sendCouponContent = "";
			Map<String,String> placeFieldMap = new HashMap<String, String>();
			placeFieldMap.put("promotionsFee", pendingSendCouponEntity.getPromotionsFee().toString());
			placeFieldMap.put("couponEndTime", dateformat.format(pendingSendCouponEntity.getCouponEndTime()));
			placeFieldMap.put("receiveCustomerCityName", pendingSendCouponEntity.getReceiveCustomerCityName());
			placeFieldMap.put("deliveryCustomerCityName",pendingSendCouponEntity.getDeliveryCustomerCityName());
			if(DictionaryValueConstants.IS_DEPARTURE_CITY.equals(pendingSendCouponEntity.getLineArea())){
				//区域限制--获取区域限制的短信模板
				String sendCouponContentCity = messageBundle.getMessage(SEND_COUPON_CONTENT_CITY,"");
				//区域限制
				sendCouponContent = sendCouponCon.append(repalceParam(placeFieldMap, sendCouponContentCity)).toString();
			}else if(DictionaryValueConstants.IS_LINE.equals(pendingSendCouponEntity.getLineArea())){
				//线路限制--获取线路限制的短信模板
				String sendCouponContentLine = messageBundle.getMessage(SEND_COUPON_CONTENT_LINE,"");
				//线路限制
				sendCouponContent = sendCouponCon.append(repalceParam(placeFieldMap, sendCouponContentLine)).toString();
			}else{
				//无线路区域限制---获取无线路区域限制的短信模板
				String sendCouponContentAll = messageBundle.getMessage(SEND_COUPON_CONTENT_ALL,"");
//				String sendCouponContentAll = SEND_COUPON_CONTENT_ALL;
				//无线路区域限制---全国使用
				sendCouponContent = sendCouponCon.append(repalceParam(placeFieldMap, sendCouponContentAll)).toString();
			}
			//来源运单号
			crmSendCouponRequest.setSourceWBNumber(pendingSendCouponEntity.getWaybillNo());
			//渠道来源
			crmSendCouponRequest.setRequest("FOSS");
			//请求时间
			crmSendCouponRequest.setRequestTime(new Date());
			//运单金额
			crmSendCouponRequest.setSourceWBValue(pendingSendCouponEntity.getBillAmount().toString());
			//手机号码
			crmSendCouponRequest.setSendtelPhone(pendingSendCouponEntity.getDeliveryCustomerMobilephone());
			//短信内容
			crmSendCouponRequest.setSmsContent(sendCouponContent);
			//优惠券使用开始日期
			crmSendCouponRequest.setBegintime(pendingSendCouponEntity.getCouponBeginTime());
			//优惠券使用结束日期
			crmSendCouponRequest.setEndtime(pendingSendCouponEntity.getCouponEndTime());
			//抵扣类型（运费、包装费、保价费、代收费、送货费、接货费）--默认运费
			crmSendCouponRequest.setDiscountType(PriceEntityConstants.PRICING_CODE_FRT);
			//优惠券金额
			crmSendCouponRequest.setUseCouponValue(pendingSendCouponEntity.getPromotionsFee().toString());
			//金额要求模式---“1” 分级抵扣模式，“2”严格抵扣模式   默认1
			crmSendCouponRequest.setCostMode("1");
			//金额可选类型---“FRT” 运费，“BILLING”开单金额	默认运费FRT
			crmSendCouponRequest.setCostType("FRT");
			//金额要求---不低于X元----X默认为1
			crmSendCouponRequest.setValue("1");
			//优惠券描述---默认“降价返券”
			crmSendCouponRequest.setDescribe("降价返券");
			//产品类型--列表
			ArrayList<String> productCodeList = new ArrayList<String>();
			if(StringUtil.isNotBlank(pendingSendCouponEntity.getProductCode())){
				String[] productCode =  pendingSendCouponEntity.getProductCode().split(",");
				for (int i = 0; i < productCode.length; i++) {
					productCodeList.add(i, productCode[i]);
				}
			}
			crmSendCouponRequest.getProductTypes().addAll(productCodeList);
			//订单来源--列表
			ArrayList<String> orderChannelList = new ArrayList<String>();
			if(StringUtil.isNotBlank(pendingSendCouponEntity.getOrderChannel())){
				String[] orderChannel =  pendingSendCouponEntity.getOrderChannel().split(",");
				for (int i = 0; i < orderChannel.length; i++) {
					orderChannelList.add(i, orderChannel[i]);
				}
			}
			crmSendCouponRequest.getOrderSources().addAll(orderChannelList);
			//客户等级--列表
			ArrayList<String> customerDegreeList = new ArrayList<String>();
			if(StringUtil.isNotBlank(pendingSendCouponEntity.getCustomerDegree())){
				String[] customerDegree =  pendingSendCouponEntity.getCustomerDegree().split(",");
				for (int i = 0; i < customerDegree.length; i++) {
					customerDegreeList.add(i, customerDegree[i]);
				}
			}
			crmSendCouponRequest.getCustLevels().addAll(customerDegreeList);
			//客户行业--列表
			ArrayList<String> customerIndustryList = new ArrayList<String>();
			if(StringUtil.isNotBlank(pendingSendCouponEntity.getCustomerIndustry())){
				String[] customerIndustry =  pendingSendCouponEntity.getCustomerIndustry().split(",");
				for (int i = 0; i < customerIndustry.length; i++) {
					customerIndustryList.add(i, customerIndustry[i]);
				}
			}
			crmSendCouponRequest.getCustTrades().addAll(customerIndustryList);
			
			GoodsLineCity4Reduceprice goodsLineCity4Reduceprice = new GoodsLineCity4Reduceprice();
			if(DictionaryValueConstants.IS_DEPARTURE_CITY.equals(pendingSendCouponEntity.getLineArea())){
				//区域限制---传入出发区域编码名称
				goodsLineCity4Reduceprice.setBeginDeptOrCityId(pendingSendCouponEntity.getReceiveCustomerCityCode());
				goodsLineCity4Reduceprice.setBeginDeptOrCityName(pendingSendCouponEntity.getReceiveCustomerCityName());
			}else if(DictionaryValueConstants.IS_LINE.equals(pendingSendCouponEntity.getLineArea())){
				//线路限制---传入出发到达区域编码名称
				goodsLineCity4Reduceprice.setBeginDeptOrCityId(pendingSendCouponEntity.getReceiveCustomerCityCode());
				goodsLineCity4Reduceprice.setBeginDeptOrCityName(pendingSendCouponEntity.getReceiveCustomerCityName());
				goodsLineCity4Reduceprice.setEndDeptOrCityId(pendingSendCouponEntity.getDeliveryCustomerCityCode());
				goodsLineCity4Reduceprice.setEndDeptOrCityName(pendingSendCouponEntity.getDeliveryCustomerCityName());
			}
			crmSendCouponRequest.getGoodsLinesCity().add(goodsLineCity4Reduceprice);
			//将HashMap转换成json格式
			String jsonParam = JSONObject.toJSONString(crmSendCouponRequest); 
			LOGGER.info(jsonParam);
			String url = crmUrl;
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setContentCharset("UTF-8");
			//构造PostMethod的实例
			PostMethod postMethod = new PostMethod(url);  
			RequestEntity entity = new StringRequestEntity(jsonParam,"application/json","UTF-8");
			postMethod.setRequestEntity(entity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			// 执行postMethod
			httpClient.executeMethod(postMethod);
			// 返回的文件头信息
//		    Header[] hs  = postMethod.getResponseHeaders();
//		    for (Header h : hs) {
//		          System.out.println(h.getName() + ":" + h.getValue());
//	        }
			//接口返回信息
			String responseBody = postMethod.getResponseBodyAsString();
//			System.out.println(responseBody);
			LOGGER.debug(responseBody);
			//将json格式的字符串转换成对象
			JSONObject obj = JSONObject.parseObject(responseBody);
//			//获取返回的值
			String couponCode = (String)obj.get("couponCode");
			returnSendCouponParamVo.setCouponNo(couponCode);
			returnSendCouponParamVo.setSuccFlag((String)obj.get("ifSuccess"));
			returnSendCouponParamVo.setSendCouponContent(sendCouponContent);
			returnSendCouponParamVo.setExceptionInfo((String)obj.get("errorMsg"));
			if(StringUtil.isEmpty(returnSendCouponParamVo.getExceptionInfo())){
				returnSendCouponParamVo.setExceptionInfo((String)obj.get("message"));
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			String error = ExceptionUtils.getFullStackTrace(e);
			if(error.length() > NUMBER_490){
				pendingSendCouponEntity.setFaileReason("异常："+error.substring(0,NUMBER_490));
			}else{
				pendingSendCouponEntity.setFaileReason("异常："+error);
			}
			pendingSendCouponEntity.setOperateType(WaybillConstants.UNACTIVE_PENDINGCOUPON_SEND_FAILURE);
			pendingSendCouponEntity.setJobId(queryJobId);
			pendingSendCouponEntity.setModifyTime(new Date());
			//更新待返券记录表--->记录失败原因
			pendingSendCouponDao.updateSendCouponByID(pendingSendCouponEntity);
		}
		return returnSendCouponParamVo;
	}
	

	/**
	 * 返券成功之后处理优惠券信息
	 * */
	@Transactional
	private void afterSendCoupon(PendingSendCouponEntity pendingSendCouponEntity,ReturnSendCouponParamVo returnSendCouponParamVo) {
		/**
		 * 封装待处理返券日志实体
		 */
		PendingSendCouponLogEntity pendingSendCouponLogEntity  = new PendingSendCouponLogEntity();
		pendingSendCouponLogEntity.setBillTime(pendingSendCouponEntity.getBillTime());
		pendingSendCouponLogEntity.setCouponBeginTime(pendingSendCouponEntity.getCouponBeginTime());
		pendingSendCouponLogEntity.setCouponEndTime(pendingSendCouponEntity.getCouponEndTime());
		pendingSendCouponLogEntity.setCreateTime(new Date());
		pendingSendCouponLogEntity.setCustomerDegree(pendingSendCouponEntity.getCustomerDegree());
		pendingSendCouponLogEntity.setCustomerIndustry(pendingSendCouponEntity.getCustomerIndustry());
		pendingSendCouponLogEntity.setDeliveryCustomerMobilephone(pendingSendCouponEntity.getDeliveryCustomerMobilephone());
		pendingSendCouponLogEntity.setFaileReason(pendingSendCouponEntity.getFaileReason());
		pendingSendCouponLogEntity.setJobId(pendingSendCouponEntity.getJobId());
		pendingSendCouponLogEntity.setLineArea(pendingSendCouponEntity.getLineArea());
		pendingSendCouponLogEntity.setModifyTime(new Date());
		pendingSendCouponLogEntity.setOperateType(WaybillConstants.UNACTIVE_PENDINGCOUPON_SEND_SUCCESS);
		pendingSendCouponLogEntity.setOrderChannel(pendingSendCouponEntity.getOrderChannel());
		pendingSendCouponLogEntity.setProductCode(pendingSendCouponEntity.getProductCode());
		pendingSendCouponLogEntity.setPromotionsFee(pendingSendCouponEntity.getPromotionsFee());
		pendingSendCouponLogEntity.setSendCoupon(FossConstants.YES);
		pendingSendCouponLogEntity.setSendCouponTime(pendingSendCouponEntity.getSendCouponTime());
		pendingSendCouponLogEntity.setWaybillNo(pendingSendCouponEntity.getWaybillNo());
		pendingSendCouponLogEntity.setId(pendingSendCouponEntity.getId());
		
		pendingSendCouponLogEntity.setBillAmount(pendingSendCouponEntity.getBillAmount());
		pendingSendCouponLogEntity.setReceiveCustomerCityCode(pendingSendCouponEntity.getReceiveCustomerCityCode());
		pendingSendCouponLogEntity.setReceiveCustomerCityName(pendingSendCouponEntity.getReceiveCustomerCityName());
		pendingSendCouponLogEntity.setDeliveryCustomerCityCode(pendingSendCouponEntity.getDeliveryCustomerCityCode());
		pendingSendCouponLogEntity.setDeliveryCustomerCityName(pendingSendCouponEntity.getDeliveryCustomerCityName());
		
		pendingSendCouponLogEntity.setSendCouponContent(returnSendCouponParamVo.getSendCouponContent());
		
		pendingSendCouponLogEntity.setCouponNo(returnSendCouponParamVo.getCouponNo());
		pendingSendCouponLogEntity.setCouponExcep(returnSendCouponParamVo.getExceptionInfo());
		
		//将已返券成功的返券信息添加至返券日志表中
		pendingSendCouponLogDao.addPendingSendCouponLogEntity(pendingSendCouponLogEntity);
		
		//当返券结束之后，根据ID删除该返券信息
		pendingSendCouponDao.deleteSendCouponById(pendingSendCouponEntity.getId());
	}

	/**
	 * 每次更新一定数量待处理返券JobId
	 * @author 
	 * @date 
	 */
	@Override
	public PendingSendCouponVo updatePendingSendCouponForJobTopNum(
			String jobId, String num) {
		PendingSendCouponVo vo=new PendingSendCouponVo();
		vo.setJobId(jobId);
		vo.setQueryNum(num);
		vo.setQueryJobId(queryJobId);
		int result=pendingSendCouponDao.updateJobIDTopByRowNum(vo);
		vo.setResultNum(result);
		return vo;
	}
	
	/**
	 * 根据运单号和开单时间确定PKP.T_SRV_PENDING_SENDCOUPON表中的唯一  加上条件 是否返券 = N
	 * */
	@Override
	public PendingSendCouponEntity queryPendingSendCoupon(
			String waybillNo, Date billTime) {
		return pendingSendCouponDao.queryPendingSendCoupon(waybillNo,billTime);
	}

	/**
	 * 更改单操作----根据ID更新返券的信息
	 * */
	@Override
	public int updateSendCouponByID(PendingSendCouponEntity pendingSendCoupon) {
		return pendingSendCouponDao.updateSendCouponByID(pendingSendCoupon);
	}
	
	/**
	 * 返券完成-----根据ID更新返券的信息
	 * */
	@Override
	public int updateSendCouponStatusByID(String id) {
		return pendingSendCouponDao.updateSendCouponStatusByID(id);
	}

}

