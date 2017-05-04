package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.impl.GrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillQueryException;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.waybill.waybillstatusback.WayBillStatusBackRequest;

public class WeixinSendService implements IWeixinSendService {
	protected final static Logger LOG = LoggerFactory.getLogger(WeixinSendService.class);
   /**
	 * 运单 Service接口
	 */
	private IWaybillManagerService waybillManagerService;	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 系统配置服务类
	 */
	private ISysConfigService pkpsysConfigService;
	/**
	 * 数据字典服务类
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	/**
	 * 偏线服务接口
	 */
	private IVehicleAgencyDeptService  vehicleAgencyDeptService;
	/**
	 * 快递代理理外发代理服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 灰度测试的服务类
	 */
	@Autowired
	private GrayScaleService grayScaleService;
	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
	private IWaybillExpressService waybillExpressService;
		
	/**
	 * <a>
	 * 这里我只想做一个简单的接口，一切数据需要你自己去进行封装，我只负责传送消息而已
	 * </a>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月10日 18:32:45
	 * @param WeixinSendDto 
	 * @param String
	 */
	@Override
	public ResultDto sysnWeixinInfoForWebSiteDirectly(WeixinSendDto dto, String weixinType) {
		ResultDto resultDto = new ResultDto();
		//判断是否开启微信消息推送功能
		if(!isStartWeixinSendServiceUpload()){
			LOG.info("系统关闭了推送消息功能，不需要推送消息");
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("系统关闭了推送消息功能");
			return resultDto;
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(dto.getWaybillNo());
		WayBillStatusBackRequest request = new WayBillStatusBackRequest();
		revertDataToRequest(dto, request);
		// 发送请求
		try {
			LOG.info("==消息头："+ReflectionToStringBuilder.toString(header)+"==");
			LOG.info("==request请求参数："+ReflectionToStringBuilder.toString(request)+"=======");
			ESBJMSAccessor.asynReqeust(header, request);
			LOG.info("================消息推送成功==============");
		} catch (Exception e) {
			// 对异常进行处理
			LOG.error("微信消息推送失败：", e);
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("JMS请求发送失败："+e.getMessage());
			return resultDto;
		}
		resultDto.setCode(ResultDto.SUCCESS);
		resultDto.setMsg("");
		return resultDto;
	}
	

	/**
	 * <a>
	 * 这里只不过是多加了一个运单详情的参数，我只不过是想减少数据的查询
	 * WeixinSendDto传参的问题：
	 * 1、如果是开单，其实dto没啥用
	 * 2、到达：需要传递的参数是到达的独有的数据，如到达确定的时间，操作人等独有的数据
	 * 3、派送：需要的参数为：司机的code、姓名、手机号码、派送的部门等独有的数据
	 * 4、签收：这边需要注意的是这有几种独有的数据类型：所以我建议传递签收WeixinConstants.WEIXIN_SIGN_TYPE
	 * 根据里面的weixinSendDto的stateType区分开来
	 * </a>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月10日 18:32:45
	 * @param WeixinSendDto 
	 * @param WaybillEntity
	 * @param String
	 */
	@Override
	public ResultDto pullWeixinInfoWithWaybillInfo(WeixinSendDto dto, WaybillEntity waybillEntity, String weixinType) {
		ResultDto resultDto = new ResultDto();
		//判断是否开启微信消息推送功能
		if(!(isStartWeixinSendServiceUpload())){
			LOG.info("系统关闭了推送消息功能，不需要推送消息");
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("系统关闭了推送消息功能");
			return resultDto;
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(dto.getWaybillNo());
		WayBillStatusBackRequest request = new WayBillStatusBackRequest();
		//封装常用的基础资料
		revertBasicDataToRequest(dto, waybillEntity, weixinType, request);
		//状态所发生的时间
  		if(StringUtils.equals(WeixinConstants.WEIXIN_CREATE_TYPE, weixinType)){
  			//开单业务时间
  			request.setStatusTime(waybillEntity.getBillTime() == null ? new Date() : waybillEntity.getBillTime());
  		}else{
  			//状态发生时间
  			request.setStatusTime(dto.getCreateTime() == null ? new Date() : dto.getCreateTime());
  		}
  		
  		//一、开单
  		if(StringUtils.equals(WeixinConstants.WEIXIN_CREATE_TYPE, weixinType)){
	  		//状态类型
	  	  	request.setStatusType(StringUtil.defaultIfNull(WeixinConstants.WEIXIN_CREATE_TYPE));
	  	  	
  		//二、到达或者分批到达
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DESTARRIVED_TYPE, weixinType)
  				|| StringUtils.equals(weixinType, WeixinConstants.WEIXIN_PART_DESTARRIVED_TYPE)){
  			//到付未核销金额
  			getarrivePayAmount(dto, waybillEntity, weixinType, request);
  			//状态类型
	  	  	request.setStatusType(WeixinConstants.WEIXIN_DESTARRIVED_TYPE);
		    //当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));

		    //到达网点部门编码
		    request.setReceiveDeptCode(StringUtil.defaultIfNull(dto.getCustomerPickUpOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getCustomerPickUpOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCustomerPickUpOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//提货网点名称
		    	    request.setReceiveDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //提货网点地址
		    	    request.setReceiveDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	    //提货网点电话
		    	    request.setReceiveDeptphone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	}
		    }
		//三、派送
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DELIVER_TYPE, weixinType)){
  			//当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));
  			//状态类型
	  	  	request.setStatusType(StringUtil.defaultIfNull(WeixinConstants.WEIXIN_DELIVER_TYPE));
	    	//派送人
		    request.setDispatcher(StringUtil.defaultIfNull(dto.getDeliverManName()));
		    //派送人手机号
		    request.setDispatcherPhone(StringUtil.defaultIfNull(dto.getDeliverManMobile()));
			//派送部门编码
		    request.setDispatchDeptCode(StringUtil.defaultIfNull(dto.getDeliverOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getDeliverOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeliverOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//派送部门名称
		    	    request.setDispatchDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //派送部门电话
		    	    request.setDispatchDeptPhone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	    //派送部门地址
		    	    request.setDispatchDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	}
		    }
	  	
	  	//四、派送拉回
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE, weixinType)){
			//派送拉回，则需获取对应的派送失败的原因。
			DataDictionaryValueEntity data = dataDictionaryValueService
					.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PULLBACK_REASON, dto.getStateType());
			if(data!=null){
				//派送拉回  查询数据字典，查询具体原因
				request.setDispatchFailReason(StringUtil.defaultIfNull(data.getValueName()));
			}else{
				//派送拉回  查询数据字典，查询具体原因
				request.setDispatchFailReason(StringUtil.defaultIfNull(dto.getStateType()));
			}
			//派送失败标识符
			request.setStatusType(WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE);
			//派送人
		    request.setDispatcher(StringUtil.defaultIfNull(dto.getDeliverManName()));
		    //派送人手机号
		    request.setDispatcherPhone(StringUtil.defaultIfNull(dto.getDeliverManMobile()));
			//派送部门编码
		    request.setDispatchDeptCode(StringUtil.defaultIfNull(dto.getDeliverOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getDeliverOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeliverOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//派送部门名称
		    	    request.setDispatchDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //派送部门电话
		    	    request.setDispatchDeptPhone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	    //派送部门地址
		    	    request.setDispatchDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	}
		    }
		
		//五、自提签收/派送
		}else if(StringUtils.equals(WeixinConstants.WEIXIN_SIGN_TYPE, weixinType)){
			//签收人
		    request.setSigner(StringUtil.defaultIfNull(dto.getSignName()));
		    //当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));
			//正常签收
			if(SignConstants.NORMAL_SIGN.equals(dto.getStateType())){
				request.setStatusType(WeixinConstants.WEIXIN_NORMAL_SIGN_TYPE);
			//部分签收
			}else if(SignConstants.PARTIAL_SIGN.equals(dto.getStateType())){
				request.setStatusType(WeixinConstants.WEIXIN_PART_SIGN_TYPE);
			//异常签收
			}else{
				request.setStatusType(WeixinConstants.WEIXIN_EXCEPTION_SIGN_TYPE);
			}
			//如果不为正常签收，需要查询异常原因
			if(!SignConstants.NORMAL_SIGN.equals(dto.getStateType())){
				//签收，则需获取对应的派送失败的原因。取数据字典的异常数据，有条理性
				DataDictionaryValueEntity data = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_SIGN_SITUATION, dto.getStateType());
				//异常签收  查询数据字典，查询具体原因
				if(data == null){
					request.setSignFailReason(StringUtil.defaultIfNull(dto.getStateType()));
				}else{
					request.setSignFailReason(StringUtil.defaultIfNull(data.getValueName()));					
				}
			}
		}
	    
		//六、发送请求
		try {
			LOG.info("================消息头："+ReflectionToStringBuilder.toString(header)+"==========");
			LOG.info("================request请求参数："+ReflectionToStringBuilder.toString(request)+"=======");
			ESBJMSAccessor.asynReqeust(header, request);
			LOG.info("================消息推送成功:");
		} catch (Exception e) {
			// 对异常进行处理
			LOG.error("微信消息推送失败：", e);
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("JMS请求发送失败,传入参数："+request+"异常信息："+e.getMessage());
			return resultDto;
		}
		resultDto.setCode(ResultDto.SUCCESS);
		resultDto.setMsg("");
		return resultDto;
	}
	
	

	/**
	 * <a>
	 * WeixinSendDto传参的问题：
	 * 1、如果是开单，其实dto没啥用
	 * 2、到达：需要传递的参数是到达的独有的数据，如到达确定的时间，操作人等独有的数据
	 * 3、派送：需要的参数为：司机的code、姓名、手机号码、派送的部门等独有的数据
	 * 4、签收：这边需要注意的是这有几种独有的数据类型：所以我希望在weixinType传递签收WeixinConstants.WEIXIN_SIGN_TYPE，
	 * 根据里面的weixinSendDto的stateType区分开来
	 * </a>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月10日 18:32:45
	 * @param WeixinSendDto 
	 * @param WaybillEntity
	 * @param String
	 */
	@Override
	public ResultDto sysnWeixinInfoForWebSiteUnDirectly(WeixinSendDto dto, String weixinType) {
		ResultDto resultDto = new ResultDto();
		//判断是否开启微信消息推送功能
		if(!(isStartWeixinSendServiceUpload())){
			LOG.info("系统关闭了推送消息功能，不需要推送消息");
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("系统关闭了推送消息功能");
			return resultDto;
		}
		
		LOG.info("微信传送类型为："+weixinType+"=====传递的参数为："+ReflectionToStringBuilder.toString(dto)+"====");
		
		//①如果单号不为空，则按照单号查询
		if(StringUtils.isNotBlank(dto.getWaybillNo())){
			LOG.info("==================运单号为："+dto.getWaybillNo()+"======================");
			WaybillEntity entity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(entity != null){
				resultDto = this.pullWeixinInfoWithWaybillInfo(dto, entity, weixinType);
			}else{
				LOG.info("运单号："+dto.getWaybillNo()+"对应运单信息不存在");
				resultDto.setCode(ResultDto.FAIL);
				resultDto.setMsg("运单号："+dto.getWaybillNo()+"对应运单信息不存在");
			}
		}
		return resultDto;
	}
	/**
	 * <a>封装常见基础数据到请求参数里面</a>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月7日 17:28:44
	 * @param dto
	 * @param waybillEntity
	 * @param weixinType
	 * @param request
	 */
	private void revertBasicDataToRequest(WeixinSendDto dto, WaybillEntity waybillEntity,
			String weixinType, WayBillStatusBackRequest request) {
		//运单号
		request.setBillNo(StringUtil.defaultIfNull(waybillEntity.getWaybillNo()));
		//订单号
		request.setOrderNo(StringUtil.defaultIfNull(waybillEntity.getOrderNo()));
		//如果该部门不存在配置参数信息，则根据词条编码和值编码获取数据字典信息
		if(StringUtils.isNotEmpty(waybillEntity.getOrderChannel())){
			DataDictionaryValueEntity data = dataDictionaryValueService
					.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.ORDER_TYPE, waybillEntity.getOrderChannel());
			//订单来源
			if(data == null){
				request.setBillSrc(waybillEntity.getOrderChannel());
			}else{
				request.setBillSrc(StringUtil.defaultIfNull(data.getValueName()));
			}
		}
		//运输类型
		request.setTranType(StringUtil.defaultIfNull(waybillEntity.getProductCode()));
		//货物名称
		request.setGoodName(StringUtil.defaultIfNull(waybillEntity.getGoodsName()));
		//发货人
		request.setSender(StringUtil.defaultIfNull(waybillEntity.getDeliveryCustomerContact()));
		//发货人手机号码
		request.setSenderMobile(StringUtil.defaultIfNull(waybillEntity.getDeliveryCustomerMobilephone()));
		//发货人ID
		request.setSenderId(StringUtil.defaultIfNull(waybillEntity.getDeliverCustContactId()));
		//发货人编码
		request.setSenderCode(StringUtil.defaultIfNull(waybillEntity.getDeliveryCustomerCode()));
		//收货人名称
		request.setConsignee(StringUtil.defaultIfNull(waybillEntity.getReceiveCustomerContact()));
		//收货人电话
		request.setConsigneeMobile(StringUtil.defaultIfNull(waybillEntity.getReceiveCustomerMobilephone()));
		//总件数
		request.setPieces(BigInteger.valueOf(waybillEntity.getGoodsQtyTotal()));
		//到付金额
		request.setRefundFee(waybillEntity.getToPayAmount());
		//开单总金额
		request.setTotalAmount(waybillEntity.getTotalFee());
		//代收货款金额
		request.setAgencyCollectMoney(waybillEntity.getCodAmount());
		// 读取出发、到达城市数据
	    SqlUtil.loadCache = true;
	    //出发城市
	    OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
	    if (null != orgInfo) {
	    	//发货城市
			request.setSendCity_0020(orgInfo.getCityName());
	    }else{
	    	request.setSendCity_0020(dto.getCustomerPickUpOrgCode());
	    }
	    //到达城市
	    if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, waybillEntity.getProductCode()) 
	        || StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
	    	//偏线或者空运
	    	OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(waybillEntity.getCustomerPickupOrgCode(), null);
	    	if(outerBranchEntity != null){
	    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(outerBranchEntity.getCityCode());
	    		if(city != null){
	    			request.setConsignCity_0020(StringUtil.defaultIfNull(city.getName()));
	    		}
	    	}else{
	    		request.setConsignCity_0020(dto.getCustomerPickUpOrgCode());
	    	}
	    }else if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
	    	//快递
	    	OuterBranchExpressEntity outerBranchEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybillEntity.getCustomerPickupOrgCode(), FossConstants.YES);
	    	if(outerBranchEntity != null){
	    		request.setConsignCity_0020(outerBranchEntity.getCityName());
	        }else{
	        	//营业部或者其他组织,因为目前快递也可能是公司的自有网点
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
		    	if(orgDeliveryInfo != null){
		    		request.setConsignCity_0020(orgDeliveryInfo.getCityName());
		    	}
	        }
	    }else{
	    	//营业部或者其他组织
	    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
	    	if(orgDeliveryInfo != null){
	    		request.setConsignCity_0020(orgDeliveryInfo.getCityName());
	    	}
	    }
	}

	
	/**
	 * <a>进行数据的简单封装</a>
	 * @param dto
	 * @param request
	 */
	private void revertDataToRequest(WeixinSendDto dto, WayBillStatusBackRequest request) {
		//运单号
		request.setBillNo(StringUtil.defaultIfNull(dto.getWaybillNo()));
		//订单号
		request.setOrderNo(StringUtil.defaultIfNull(dto.getOrderNo()));
		//订单渠道来源
		if(StringUtils.isNotEmpty(dto.getOrderChannel())){
			//如果该部门不存在配置参数信息，则根据词条编码和值编码获取数据字典信息
			DataDictionaryValueEntity data = dataDictionaryValueService
					.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.ORDER_TYPE, dto.getOrderChannel());
			//订单来源
			if(data == null){
				request.setBillSrc(dto.getOrderChannel());
			}else{
				request.setBillSrc(StringUtil.defaultIfNull(data.getValueName()));
			}
		}
		//运输类型
		request.setTranType(StringUtil.defaultIfNull(dto.getProductCode()));
		//货物名称
		request.setGoodName(StringUtil.defaultIfNull(dto.getGoodsName()));
		//到付金额
		request.setRefundFee(dto.getArrivePayAmount());
		//发货人
		request.setSender(StringUtil.defaultIfNull(dto.getShippCustomerName()));
		//发货人手机号码
		request.setSenderMobile(StringUtil.defaultIfNull(dto.getShippMobile()));
		//发货人ID
		request.setSenderId(StringUtil.defaultIfNull(dto.getShippCustomerId()));
		//发货人编码
		request.setSenderCode(StringUtil.defaultIfNull(dto.getShippCustomerCode()));
		//收货人名称
		request.setConsignee(StringUtil.defaultIfNull(dto.getConsigneeName()));
		//收货人电话
		request.setConsigneeMobile(StringUtil.defaultIfNull(dto.getConsigneeMobile()));
		//状态类型
		request.setStatusType(StringUtil.defaultIfNull(dto.getStateType()));
		//状态发生时间
		request.setStatusTime(dto.getCreateTime() == null ? new Date() : dto.getCreateTime());
		//到付金额
		request.setRefundFee(dto.getArrivePayAmount());
		//总金额
		request.setTotalAmount(dto.getTotalAmount());
		//代收货款金额
		request.setAgencyCollectMoney(dto.getRefundAmount());
		// 读取出发、到达城市数据
	    SqlUtil.loadCache = true;
	    //出发城市
	    OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCreateOrgCode());
	    if (null != orgInfo) {
	    	//发货城市
			request.setSendCity_0020(orgInfo.getCityName());
	    }else{
	    	request.setSendCity_0020(dto.getCreateOrgCode());
	    }
	    //到达城市
	    if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, dto.getProductCode()) 
	        || StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, dto.getProductCode())){
	    	//空运或者偏线
	    	OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(dto.getCustomerPickUpOrgCode(), null);
	    	if(outerBranchEntity != null){
	    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(outerBranchEntity.getCityCode());
	    		if(city != null){
	    			request.setConsignCity_0020(StringUtil.defaultIfNull(city.getName()));
	    		}
	    	}else{
	    		request.setConsignCity_0020(dto.getCustomerPickUpOrgCode());
	    	}
	    }else if(waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getProductCode(), dto.getCreateTime())){
	    	//快递快递代理
	    	OuterBranchExpressEntity outerBranchEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(dto.getCustomerPickUpOrgCode(), FossConstants.YES);
	      if(outerBranchEntity != null){
	    	  request.setConsignCity_0020(outerBranchEntity.getCityName());
	        }else{
	        	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCustomerPickUpOrgCode());
		          if(orgDeliveryInfo != null){
		        	  request.setConsignCity_0020(orgDeliveryInfo.getCityName());
		          }else{
		        	  request.setConsignCity_0020(dto.getCustomerPickUpOrgCode());
		          }
	        }
	    }else{
	    		//公司自有网点
	          OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCustomerPickUpOrgCode());
	          if(orgDeliveryInfo != null){
	        	  request.setConsignCity_0020(orgDeliveryInfo.getCityName());
	          }else{
	        	  request.setConsignCity_0020(dto.getCustomerPickUpOrgCode());
	          }
	    }
	    //总件数
	    request.setPieces(BigInteger.valueOf(dto.getProcessedPieces()));
	  //一、开单
  		if(StringUtils.equals(WeixinConstants.WEIXIN_CREATE_TYPE, dto.getStateType())){
	  		//状态类型
	  	  	request.setStatusType(StringUtil.defaultIfNull(WeixinConstants.WEIXIN_CREATE_TYPE));
	  	  	
  		//二、到达或者分批到达
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DESTARRIVED_TYPE, dto.getStateType())
  				|| StringUtils.equals(dto.getStateType(), WeixinConstants.WEIXIN_PART_DESTARRIVED_TYPE)){
  			//未核销金额
  			request.setNotCancelMoney(dto.getRepayAmount());
  			//状态类型
	  	  	request.setStatusType(WeixinConstants.WEIXIN_DESTARRIVED_TYPE);
		    //当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));

		    //到达网点部门编码
		    request.setReceiveDeptCode(StringUtil.defaultIfNull(dto.getCustomerPickUpOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getCustomerPickUpOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCustomerPickUpOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//提货网点名称
		    	    request.setReceiveDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //提货网点地址
		    	    request.setReceiveDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	    //提货网点电话
		    	    request.setReceiveDeptphone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	}
		    }
		//三、派送
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DELIVER_TYPE, dto.getStateType())){
  			//当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));
  			//状态类型
	  	  	request.setStatusType(StringUtil.defaultIfNull(WeixinConstants.WEIXIN_DELIVER_TYPE));
	    	//派送人
		    request.setDispatcher(StringUtil.defaultIfNull(dto.getDeliverManName()));
		    //派送人手机号
		    request.setDispatcherPhone(StringUtil.defaultIfNull(dto.getDeliverManMobile()));
			//派送部门编码
		    request.setDispatchDeptCode(StringUtil.defaultIfNull(dto.getDeliverOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getDeliverOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeliverOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//派送部门名称
		    	    request.setDispatchDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //派送部门电话
		    	    request.setDispatchDeptPhone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	    //派送部门地址
		    	    request.setDispatchDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	}
		    }
	  	
	  	//四、派送拉回
  		}else if(StringUtils.equals(WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE, dto.getStateType())){
			//派送拉回，则需获取对应的派送失败的原因。
			DataDictionaryValueEntity data = dataDictionaryValueService
					.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PULLBACK_REASON, dto.getStateType());
			if(data!=null){
				//派送拉回  查询数据字典，查询具体原因
				request.setDispatchFailReason(StringUtil.defaultIfNull(data.getValueName()));
			}else{
				//派送拉回  查询数据字典，查询具体原因
				request.setDispatchFailReason(StringUtil.defaultIfNull(dto.getStateType()));
			}
			//派送失败标识符
			request.setStatusType(WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE);
			//派送人
		    request.setDispatcher(StringUtil.defaultIfNull(dto.getDeliverManName()));
		    //派送人手机号
		    request.setDispatcherPhone(StringUtil.defaultIfNull(dto.getDeliverManMobile()));
			//派送部门编码
		    request.setDispatchDeptCode(StringUtil.defaultIfNull(dto.getDeliverOrgCode()));
		    if(StringUtils.isNotEmpty(dto.getDeliverOrgCode())){
		    	OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeliverOrgCode());
		    	if(orgDeliveryInfo != null){
		    		//派送部门名称
		    	    request.setDispatchDeptName(StringUtil.defaultIfNull(orgDeliveryInfo.getName()));
		    	    //派送部门电话
		    	    request.setDispatchDeptPhone(StringUtil.defaultIfNull(orgDeliveryInfo.getDepTelephone()));
		    	    //派送部门地址
		    	    request.setDispatchDeptAddr(StringUtil.defaultIfNull(orgDeliveryInfo.getAddress()));
		    	}
		    }
		
		//五、自提签收/派送
		}else if(StringUtils.equals(WeixinConstants.WEIXIN_SIGN_TYPE, dto.getStateType())){
			//签收人
		    request.setSigner(StringUtil.defaultIfNull(dto.getSignName()));
		    //当前处理件数
		    request.setCurrentPieces(BigInteger.valueOf(dto.getCurrentPieces()));
		    //已经处理件数
		    request.setWorkedPieces(BigInteger.valueOf(dto.getProcessedPieces()));
			//正常签收
			if(SignConstants.NORMAL_SIGN.equals(dto.getStateType())){
				request.setStatusType(WeixinConstants.WEIXIN_NORMAL_SIGN_TYPE);
			//部分签收
			}else if(SignConstants.PARTIAL_SIGN.equals(dto.getStateType())){
				request.setStatusType(WeixinConstants.WEIXIN_PART_SIGN_TYPE);
			//异常签收
			}else{
				request.setStatusType(WeixinConstants.WEIXIN_EXCEPTION_SIGN_TYPE);
			}
			//如果不为正常签收，需要查询异常原因
			if(!SignConstants.NORMAL_SIGN.equals(dto.getStateType())){
				//签收，则需获取对应的派送失败的原因。取数据字典的异常数据，有条理性
				DataDictionaryValueEntity data = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_SIGN_SITUATION, dto.getStateType());
				//异常签收  查询数据字典，查询具体原因
				if(data == null){
					request.setSignFailReason(StringUtil.defaultIfNull(dto.getStateType()));
				}else{
					request.setSignFailReason(StringUtil.defaultIfNull(data.getValueName()));					
				}
			}
		}
	}

	/**
	 * 创建消息头
	 * @param waybillNo
	 * @return
	 */
	private AccessHeader createAccessHeader(String waybillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		//ESB_FOSS2ESB_RECEIVE_ORDERSTATUS
		LOG.info("微信推送编码为："+WeixinConstants.ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_CODE+"版本号:"+WeixinConstants.ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_VERSION);
		header.setEsbServiceCode(WeixinConstants.ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_CODE);
		//版本随意  1.0
		header.setVersion(WeixinConstants.ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_VERSION);
		//business id 随意
		header.setBusinessId(waybillNo);
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(waybillNo);
		return header;
	}
	
	private void getarrivePayAmount(WeixinSendDto dto, WaybillEntity waybillEntity, String weixinType, WayBillStatusBackRequest request){
		// 自提，判断此单到付未核销金额是否大于0
		//根据运单号，取得此单对应的到付未核销金额
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(dto.getWaybillNo());
		String sourceBillType = "W";
		String active = FossConstants.YES;
		BigDecimal zero = BigDecimal.valueOf(0);
		BigDecimal arrivePayTotal = zero;
		//根据运单号，获取应收单对应信息
		//灰度改造 （323098王鹏涛）
		//List<BillReceivableEntity> billReceivableEntitys = billReceivableService.queryBySourceBillNOs(sourceBillNos, sourceBillType, active);
		List<BillReceivableEntity> billReceivableEntitys = queryBySourceBillNOsToGrayCubc(sourceBillNos, sourceBillType, active);
		//判断对应信息是否为空
		if(!CollectionUtils.isEmpty(billReceivableEntitys)
				&& billReceivableEntitys.size() > 0){
			//循环获取总金额
			for(BillReceivableEntity billEntity : billReceivableEntitys){
				//如果未核销金额不为零，且放款方式为到付
				if(null != billEntity.getUnverifyAmount()
						&& StringUtils.equals(billEntity.getPaymentType(), "FC")){
					//将所有到付未核销金额相加
					arrivePayTotal = arrivePayTotal.add(billEntity.getUnverifyAmount());
				}
			}
		}
		//有未核销到付款
		if(arrivePayTotal.compareTo(zero) > 0){
			LOG.info("到付未核销金额为"+arrivePayTotal);
			request.setNotCancelMoney(arrivePayTotal);
		}
	}


	/**
	 * @功能 判断是否已经开启微信开关
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-1-13 21:03:04
	 * @return boolean
	 */
	private boolean isStartWeixinSendServiceUpload() {
		boolean start = false;
		// 获取配置参数
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.WEIXIN_PULL_START,FossConstants.ROOT_ORG_CODE);
		if (config != null) {
			//判断是否开启开关
			if (FossConstants.YES.equals(config.getConfValue())) {
				start = true;
			}
		}
		return start;
	}

	/**
	 * 偏线服务接口
	 */
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 快递代理递代理外发代理服务接口
	 */
	public void setLdpAgencyDeptService(
			ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	/**
	 * 数据字典服务类
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/**
	 * 数据字典服务类
	 */
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	/**
	 * 运单 Service接口
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 组织信息 Service接口
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	/**
	 * 应收账服务
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	//根据运单号，获取应收单对应信息 （323098王鹏涛）
	//List<BillReceivableEntity> billReceivableEntitys = billReceivableService.queryBySourceBillNOs(sourceBillNos, sourceBillType, active);
	public List<BillReceivableEntity> queryBySourceBillNOsToGrayCubc(List<String> sourceBillNosList, String sourceBillType, String active){
	    	RequestDO requestDO = new RequestDO();		
		requestDO.setServiceCode(WeixinSendService.class.getName()+".getarrivePayAmount");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(sourceBillNosList.toArray((new String[sourceBillNosList.size()])));
	    
		List<BillReceivableEntity> billReceivableEntitysFOSS = null;
	    	List<BillReceivableEntity> billReceivableEntityCUBC = new ArrayList<BillReceivableEntity>();
		
		VestResponse response;
		try {
		    response = grayScaleService.vestAscription(requestDO);
		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		    //billReceivableEntitysFOSS = billReceivableService.queryBySourceBillNOs(sourceBillNosList, sourceBillType, active);
		    //return billReceivableEntitysFOSS;
		    throw new WaybillQueryException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
			List<VestBatchResult> batchResults = response.getVestBatchResult();
			List<String> waybillNosFOSS=null;
			List<String> wyabillNosCUBC=null;
			for (int i = 0; i < batchResults.size(); i++) {
				if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResults.get(i).getVestSystemCode())){
					waybillNosFOSS=batchResults.get(i).getVestObject();
				}else if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())){
					wyabillNosCUBC=batchResults.get(i).getVestObject();
				}
			}
			for(int i = 0; i < batchResults.size(); i++){
			    	
    			    	VestBatchResult batchResult = batchResults.get(i);
            			if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResult.getVestSystemCode())){
            				billReceivableEntitysFOSS = billReceivableService.queryBySourceBillNOs(waybillNosFOSS, sourceBillType, active);
            			}else if (Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResult.getVestSystemCode())){
            				//TODO
        			    List<TradeDO> tradeDOList=null;
				    try {
					tradeDOList = queryBySourceBillNOsToCUBC(wyabillNosCUBC, sourceBillType,active);
				    } catch (Exception e) {
					// TODO Auto-generated catch block
					    throw new WaybillQueryException(Constants.GRAY_SERVICE_EXCEPTION);
				    }
        			    for (int j = 0; j < tradeDOList.size(); j++) {
        				BillReceivableEntity billReceivableEntity = converFromTradeDo(tradeDOList.get(j));
        				billReceivableEntityCUBC.add(billReceivableEntity);
        			    }
        			}
        		}
		}
		if (billReceivableEntitysFOSS !=null) {
			billReceivableEntitysFOSS.addAll(billReceivableEntityCUBC);
		}
		return billReceivableEntitysFOSS;
	}
	//访问遗漏灰度接口（323098王鹏涛）
	private List<TradeDO> queryBySourceBillNOsToCUBC(List<String> sourceBillNosList, String sourceBillType, String active) throws Exception{
	    Map<String, Object> toCubcMap=new HashMap<String, Object>();
	    toCubcMap.put("sourceBillNosList", sourceBillNosList);
	    toCubcMap.put("sourceBillType", sourceBillType);
	    toCubcMap.put("active", active);
	    String requestparams=JSON.toJSONString(toCubcMap);
	    LOG.info("cubc的访问接口queryBySourceBillNOsToCUBC方法的json信息"+requestparams);
	    return (List<TradeDO>) grayScaleService.grayQueryOrderByList(requestparams);
	}
	//转化类 （323098王鹏涛）
	private BillReceivableEntity converFromTradeDo(TradeDO tradeDO){
	    BillReceivableEntity billReceivableEntity = new BillReceivableEntity();
	    billReceivableEntity.setNotes(tradeDO.getRemark());//备注
	    billReceivableEntity.setCustomerCode(tradeDO.getMasterCustomerCode());// 主账户客户编码
	    billReceivableEntity.setCustomerName(tradeDO.getMasterCustomerName()); // 主账户客户名称
	    billReceivableEntity.setAccountDate(tradeDO.getStatementDate());// 记账日期
	    billReceivableEntity.setBillType(tradeDO.getOrderSubType());// 物流交易子类型  (单据子类型)
	    billReceivableEntity.setWaybillId(tradeDO.getSourceBillNoId());// 来源单据编号ID(运单ID)
	    billReceivableEntity.setReceivableNo(tradeDO.getOrderNo());// 物流交易号(应收单号)
	    billReceivableEntity.setCurrencyCode(tradeDO.getCurrencyType());// 币种
	    billReceivableEntity.setWaybillNo(tradeDO.getWaybillNo());// 运单号
	    billReceivableEntity.setSourceBillNo(tradeDO.getSourceBillNo());// 来源单据编号(来源单据单号)
	    billReceivableEntity.setSourceBillType(tradeDO.getSourceBillType());// 来源单据类型(来源单据类型)
	    billReceivableEntity.setCreateTime(tradeDO.getCreateDate());// 创建时间
	    billReceivableEntity.setModifyDate(tradeDO.getModifyDate());// 修改时间
	    billReceivableEntity.setCreateUserCode(tradeDO.getCreateUserCode());// 创建人工号 （制单人编码）
	    billReceivableEntity.setCreateUserName(tradeDO.getCreateUserName());;// 创建人名称（制单人名称）
	    billReceivableEntity.setCreateOrgCode(tradeDO.getCreateOrgCode());// 创建部门编码 （制单部门编码）
	    billReceivableEntity.setCreateOrgName(tradeDO.getCreateOrgName());// 创建部门名称（制单部门名称）
	    billReceivableEntity.setModifyUserCode(tradeDO.getModifyUserCode());// 修改人工号（修改人编码）
	    billReceivableEntity.setModifyUserName(tradeDO.getModifyUserName());// 修改人姓名(修改人名称)
	    billReceivableEntity.setReceivableOrgCode(tradeDO.getActionOrgCode());// 应收/付部门编码(应收部门编码)
	    billReceivableEntity.setReceivableOrgName(tradeDO.getActionOrgName());// 应收/付部门名称(应收部门名称)
	    billReceivableEntity.setGeneratingComName(tradeDO.getBurdenOrgName());// 收入/付款部门（费用承担部门）名称，本来需要谁来收款或者付款的部门(收入部门名称)
	    billReceivableEntity.setGeneratingComCode(tradeDO.getBurdenOrgCode());// 收入/付款部门（费用承担部门）编码，本来需要谁来收款或者付款的部门(收入部门编码)
	    billReceivableEntity.setContractOrgCode(tradeDO.getContractOrgCode());// 合同部门编码
	    billReceivableEntity.setContractOrgName(tradeDO.getContractOrgName()); // 合同部门名称
	    billReceivableEntity.setUnifiedSettlement(tradeDO.getUnifiedSettlement());// 统一结算(是否统一结算)
	    billReceivableEntity.setInvoiceMark(tradeDO.getInvoiceMark());// 发票标记(发票标记)
	    billReceivableEntity.setProductId(tradeDO.getProductId());// 产品ID(产品ID)
	    billReceivableEntity.setOrigOrgName(tradeDO.getTradeAttributeDO().getOrigOrgName());// 始发部门名称(出发部门名称)
	    billReceivableEntity.setOrigOrgCode(tradeDO.getTradeAttributeDO().getOrigOrgCode());// 始发部门编码(出发部门编码)
	    billReceivableEntity.setDestOrgCode(tradeDO.getTradeAttributeDO().getDescOrgCode());// 到达部门编码(到达部门编码)
	    billReceivableEntity.setDestOrgName(tradeDO.getTradeAttributeDO().getDescOrgName());// 到达部门名称(到达部门名称)
	    billReceivableEntity.setDeliveryCustomerCode(tradeDO.getTradeAttributeDO().getDeliveryCustomerCode());// 发货客户编码(发货客户编码)
	    billReceivableEntity.setDeliveryCustomerName(tradeDO.getTradeAttributeDO().getDeliveryCustomerName());// 发货客户名称(发货客户名称)
	    billReceivableEntity.setReceiveCustomerCode(tradeDO.getTradeAttributeDO().getReceiveCustomerCode());// 收货客户编码(收货客户编码)
	    billReceivableEntity.setReceiveCustomerName(tradeDO.getTradeAttributeDO().getReceiveCustomerName());// 收货客户名称(收货客户名称)
	    billReceivableEntity.setVerifyAmount(tradeDO.getVerifyAmount());// 已核销金额(已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setUnverifyAmount(tradeDO.getUnInvoiceAmount());// 未核销金额(未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setTransportFee(tradeDO.getTradeAttributeDO().getPublishFreinghtTax());// 公布价运费(公布价运费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setPickupFee(tradeDO.getTradeAttributeDO().getDoorServiceReceiveFee());// 接货费(接货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setDeliveryGoodsFee(tradeDO.getTradeAttributeDO().getDeliveryFee());// 送货费(送货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setPackagingFee(tradeDO.getTradeAttributeDO().getPackingFee());// 包装费（包装手续费）
	    billReceivableEntity.setCodFee(tradeDO.getTradeAttributeDO().getCollectionChargesPoundage());// 代收货款手续费(代收货款手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setInsuranceFee(tradeDO.getTradeAttributeDO().getInsuranceFee());// 保价费(保价费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setInsuranceAmount(tradeDO.getTradeAttributeDO().getInsuranceStatement());// 保价费(保价声明价值)
	    billReceivableEntity.setOtherFee(tradeDO.getTradeAttributeDO().getOtherFee());// 其他费用
	    billReceivableEntity.setBusinessDate(tradeDO.getBusinessDate());// 业务发生时间（业务日期）
	    billReceivableEntity.setVersionNo(new Short(String.valueOf(tradeDO.getVersionNo())));// 版本号
	    billReceivableEntity.setWithholdStatus(tradeDO.getPayStatus());// 支付/扣款状态
	    billReceivableEntity.setPaymentType(tradeDO.getPaymentType());// 付款方式(付款方式)
	    billReceivableEntity.setAmount(tradeDO.getAmount());// 应收付金额(金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）)
	    billReceivableEntity.setGoodsName(tradeDO.getTradeAttributeDO().getGoodsName());// 货物品名(货物名称)
	    billReceivableEntity.setReceiveMethod(tradeDO.getTradeAttributeDO().getPickupWay());// 提货方式(提货方式)
	    if(null!=tradeDO.getTradeAttributeDO()&&!StringUtils.isEmpty(tradeDO.getTradeAttributeDO().getPickupWay())){
		billReceivableEntity.setGoodsQtyTotal(new BigDecimal(tradeDO.getTradeAttributeDO().getPackageQuantity()));// 货物总件数
	    }
	    billReceivableEntity.setGoodsVolumeTotal(tradeDO.getTradeAttributeDO().getVolume());// 货物总体积
	    billReceivableEntity.setBillWeight(tradeDO.getTradeAttributeDO().getChargeWeight());// 计费重量
	    return billReceivableEntity;
	}
}
