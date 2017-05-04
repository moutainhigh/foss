package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.dop.AddressEntity;
import com.deppon.esb.inteface.domain.dop.RuleDetailEntity;
import com.deppon.esb.inteface.domain.dop.WayBillDistributeRequestEntity;
import com.deppon.esb.pojo.transformer.jaxb.WayBillDistributeRequestEntityTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRookieWaybillJBDService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncRookieWaybillJBDService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TaobaoDepponDistrictMapEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * 同步FOSS的集包地基础资料给dop，oms
 * @author 273311
 * @date 2016-1-21 上午11:50:25
 */
public class SyncRookieWaybillJBDService implements ISyncRookieWaybillJBDService {
	/**
	 * 日志信息
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SyncRookieWaybillJBDService.class);
	/**
	 * 服务编码
	 */
	private static final String ESB_SERVICE_CODE_TO_DOP = "ESB_FOSS2ESB_JBD_SYNC_HAND";
    
	private static final String VERSION = "1.0";
	/**
	 * 物流公司编码
	 */
	private String companyCode;
	
	/**
	 * 操作公司 DEPPON
	 */
	private String company;
	
	private IRookieWaybillJBDService  rookieWaybillJBDService;
	
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setRookieWaybillJBDService(
			IRookieWaybillJBDService rookieWaybillJBDService) {
		this.rookieWaybillJBDService = rookieWaybillJBDService;
	}

	/**
	 * 同步FOSS的集包地基础资料给dop，oms
	 * @author 273311
	 * @date 2016-1-21 上午11:50:25
	 */
	@Override
	public void syncToDopOms(List<RookieWaybillJBDEntity> entitys,
			String actionType) {
		if(CollectionUtils.isEmpty(entitys)){
		throw new BusinessException("传入对象为空");	
		}
		WayBillDistributeRequestEntity request = new WayBillDistributeRequestEntity();
		request = this.transFossToEsb(entitys, actionType);
		// 创建服务头信息
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE_TO_DOP);
				accessHeader.setBusinessId(UUID.randomUUID().toString());
				accessHeader.setBusinessDesc1("同步集包地基础资料到dop");
				accessHeader.setBusinessDesc2("同步集包地基础资料到oms");
				accessHeader.setVersion(VERSION);
				try {
					logger.info("开始调用 同步快集包地至dop,oms：\n"
							+ new WayBillDistributeRequestEntityTrans().fromMessage(request));
					ESBJMSAccessor.asynReqeust(accessHeader, request);
					logger.info("结束调用 同步快递派送区域至dop,oms：\n"
							+ new WayBillDistributeRequestEntityTrans().fromMessage(request));
				} catch (ESBException e) {
					e.printStackTrace();
				}
				logger.info("send RookieWaybillJBDEntity to dopoms System ：同步数据到dop,oms \n");
	}

	/**
	 * 将FOSS的集包地信息set给实体
	 * 
	 * @author 273311
	 * @date 2016-1-22 上午11:50:25
	 */
	private WayBillDistributeRequestEntity transFossToEsb(List<RookieWaybillJBDEntity> entitys,String actionType) {
		if(null==entitys){
			return null;
		}
		WayBillDistributeRequestEntity request = new WayBillDistributeRequestEntity();
		List<RuleDetailEntity> ruleDetails = request.getRuleDetails();
		RuleDetailEntity ruleDetail = null;
		TaobaoDepponDistrictMapEntity sendEntity=null;
		TaobaoDepponDistrictMapEntity receiveEntity=null;
		AddressEntity sendAddressEntity;
		AddressEntity reciveAddressEntity;
		for(RookieWaybillJBDEntity entity:entitys){
			ruleDetail = new RuleDetailEntity();
			sendEntity=new TaobaoDepponDistrictMapEntity();
			receiveEntity=new TaobaoDepponDistrictMapEntity();
			sendAddressEntity = new AddressEntity();
			reciveAddressEntity=new AddressEntity();
			if(StringUtils.isEmpty(entity.getStartProvinceName())){
				ruleDetail.setSendAddress(null);
			}else{
				sendEntity.setDepponCounty(entity.getStartCountyName());
				sendEntity.setDepponCity(entity.getStartCityName());
				sendEntity.setDepponProvince(entity.getStartProvinceName());
				sendEntity=rookieWaybillJBDService.queryDistrictIfEqual(sendEntity);
				
				if(sendEntity!=null){
					//发货地址
					sendAddressEntity.setProvince(sendEntity.getTaobaoProvince());
					sendAddressEntity.setCity(sendEntity.getTaobaoCity());
					sendAddressEntity.setArea(sendEntity.getTaobaoCounty());
					ruleDetail.setSendAddress(sendAddressEntity);
				}else{
					//发货地址
					sendAddressEntity.setProvince(entity.getStartProvinceName());
					sendAddressEntity.setCity(entity.getStartCityName());
					sendAddressEntity.setArea(entity.getStartCountyName());
					ruleDetail.setSendAddress(sendAddressEntity);
				}	
			}	
			receiveEntity.setDepponCounty(entity.getReachCountyName());
			receiveEntity.setDepponCity(entity.getReachCityName());
			receiveEntity.setDepponProvince(entity.getReachProvinceName());
			receiveEntity=rookieWaybillJBDService.queryDistrictIfEqual(receiveEntity);
			if(receiveEntity!=null){ 
				reciveAddressEntity.setProvince(receiveEntity.getTaobaoProvince());
				reciveAddressEntity.setCity(receiveEntity.getTaobaoCity());
				reciveAddressEntity.setArea(receiveEntity.getTaobaoCounty());
				ruleDetail.setReceiveAddress(reciveAddressEntity);
			}else{
				reciveAddressEntity.setProvince(entity.getReachProvinceName());
				reciveAddressEntity.setCity(entity.getReachCityName());
				reciveAddressEntity.setArea(entity.getReachCountyName());
				ruleDetail.setReceiveAddress(reciveAddressEntity);
			}  
			//编码
			ruleDetail.setNumValue(entity.getJbdCode());
			//大头笔/集包地
			ruleDetail.setTextValue(entity.getBigHeadOrJBD());
			ruleDetails.add(ruleDetail);
		}
		request.setAction(actionType);
		request.setOperator(company);
		request.setRuleType(entitys.get(0).getTypeCode());
		request.setCompanyCode(companyCode);
		return request;
	}

}
