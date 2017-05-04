package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;


import java.util.List;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SyncBigcusDeliveryAddressRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncBigcusDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;
import com.deppon.oms.inteface.domain.BigcusDeliveryAddressInfo;
import com.deppon.oms.inteface.domain.SyncBigcusDeliveryAddressRequest;


public class SyncBigcusDeliveryAddressService implements ISyncBigcusDeliveryAddressService  {
	// 记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncBigcusDeliveryAddressService.class);
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_LTL_CUSTOMER_DELIVERY";
	private static final String VERSION = "1.0";

	/** 
	 * <p>同步零担大客户派送地址库至OMS</p> 
	 * @author 232607 
	 * @date 2016-5-31 上午9:09:44
	 * @param entity 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressInsuredLimitService#synInfoToCRMCCGW(com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	public void syncBigcusDeliveryAddressToOMS(List<BigcusDeliveryAddressEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入参数为空");
		}
		SyncBigcusDeliveryAddressRequest request = converter(entitys);
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步零担大客户派送地址库至OMS");
		accessHeader.setVersion(VERSION);
		try {
			LOGGER.info("开始调用，同步零担大客户派送地址库至OMS：\n"
					+ new SyncBigcusDeliveryAddressRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			LOGGER.info("结束调用，同步零担大客户派送地址库至OMS：\n"
					+ new SyncBigcusDeliveryAddressRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>转换方法</p> 
	 * @author 232607 
	 * @date 2016-5-31 上午9:28:24
	 * @param entitys
	 * @return
	 * @see
	 */
	private SyncBigcusDeliveryAddressRequest converter(List<BigcusDeliveryAddressEntity> entitys){
		SyncBigcusDeliveryAddressRequest request = new SyncBigcusDeliveryAddressRequest();
		List<BigcusDeliveryAddressInfo> list=request.getBigcusDeliveryAddressInfos();
		for(BigcusDeliveryAddressEntity entity:entitys){
			BigcusDeliveryAddressInfo info=new BigcusDeliveryAddressInfo();
			info.setId(entity.getId());
			info.setCode(entity.getCode());
			info.setName(entity.getName());
			info.setParentDistrictCode(entity.getParentDistrictCode());
			info.setVirtualDistrictId(entity.getVirtualDistrictId());
			info.setDegree(entity.getDegree());
			info.setDeliveryAddTime(entity.getDeliveryAddTime());
			info.setDeliverySalesDeptCode(entity.getDeliverySalesDeptCode());
			info.setDeliveryType(entity.getDeliveryType());
			info.setDeliveryRemark(entity.getDeliveryRemark());
			info.setActive(entity.getActive());
			info.setCreateTime(entity.getCreateDate());
			info.setModifyTime(entity.getModifyDate());
			info.setCreateUserCode(entity.getCreateUser());
			info.setModifyUserCode(entity.getModifyUser());
			list.add(info);
		}
		return request;
	}
	
	
}
