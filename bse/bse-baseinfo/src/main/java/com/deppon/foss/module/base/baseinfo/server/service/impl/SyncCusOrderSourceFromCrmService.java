package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.crm.CusOrderSourceInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusOrderSourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncCusOrderSourceFromCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusOrderSourceEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ws.syncdata.CommonException;

/**
 * 对CRM行业、客户等级、订单来源信息进行操作的接口
 * @author dujunhui-187862
 * @date 2014-9-26 下午2:42:12
 */
public class SyncCusOrderSourceFromCrmService implements
		ISyncCusOrderSourceFromCrmService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncCusOrderSourceFromCrmService.class);

	/**
	 * 定义常量
	 */
	private static final String USER_CODE = "CRM";

	// 客户行业信息的Service层接口
	private ICusOrderSourceService cusOrderSourceService;
	
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setCusOrderSourceService(
			ICusOrderSourceService cusOrderSourceService) {
		this.cusOrderSourceService = cusOrderSourceService;
	}

	/**
	 * 接收CRM同步过来客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-27 上午9:22:35
	 *
	 */
	@Override
	@Transactional
	public void syncCusOrderSourceInfo(
			CusOrderSourceInfo rInfo) throws CommonException {
		if (null != rInfo) {
		    	//CRM行业、客户等级、订单来源信息对象
				CusOrderSourceEntity cusOrderSourceEntity = new CusOrderSourceEntity();
				try{
		    		if(StringUtil.isEmpty(rInfo.getImportPattern())) {
//		    			throw new CusOrderSourceException("传入的实体类型为空");
		    			LOGGER.info("传入的实体类型为空");
		    		}
		    		cusOrderSourceEntity.setId(UUIDUtils.getUUID());
		    		cusOrderSourceEntity.setImportPattern(rInfo.getImportPattern());
		    		cusOrderSourceEntity.setFirDegreeProfessionCode(rInfo.getFirIndustryCode());
		    		cusOrderSourceEntity.setFirDegreeProfessionName(rInfo.getFirIndustryName());
		    		cusOrderSourceEntity.setSecDegreeProfessionCode(rInfo.getSecIndustryCode());
		    		cusOrderSourceEntity.setSecDegreeProfessionName(rInfo.getSecIndustryName());
		    		cusOrderSourceEntity.setCustomerDegreeCode(rInfo.getCustomerDegreeCode());
		    		cusOrderSourceEntity.setCustomerDegreeName(rInfo.getCustomerDegreeName());
		    		cusOrderSourceEntity.setOrderSourceCode(rInfo.getOrderSourceCode());
		    		cusOrderSourceEntity.setOrderSourceName(rInfo.getOrderSourceName());
			
		    		if(StringUtils.equals(rInfo.getOperateType(), NumberConstants.NUMERAL_S_ONE)) {
		    			LOGGER.info("新增CRM行业、客户等级、订单来源信息开始。。。。。。。。。。。。。。。。。。");
		    			// 根据同步的动作执行对应的"新增"
		    			cusOrderSourceEntity.setActive(FossConstants.ACTIVE);
		    			cusOrderSourceEntity.setCreateDate(new Date());
		    			cusOrderSourceEntity.setCreateUser(USER_CODE);
		    			cusOrderSourceService.addCusOrderSource(cusOrderSourceEntity);
		    			LOGGER.info("新增CRM行业、客户等级、订单来源结束。。。。。。。。。。。。。。。。。。");
		    		}else if(StringUtils.equals(rInfo.getOperateType(), NumberConstants.NUMERAL_S_TWO)) {
		    			LOGGER.info("修改CRM行业、客户等级、订单来源开始。。。。。。。。。。。。。。。。。。");
		    			// 根据同步的动作执行对应的"删除"
		    			cusOrderSourceEntity.setModifyUser(USER_CODE);
		    			cusOrderSourceEntity.setModifyDate(new Date());
		    			cusOrderSourceEntity.setActive(FossConstants.INACTIVE);
		    			cusOrderSourceEntity.setVersionNo(new Date().getTime());
		    			cusOrderSourceService.updateCusOrderSource(cusOrderSourceEntity);
		    			LOGGER.info("修改CRM行业、客户等级、订单来源结束。。。。。。。。。。。。。。。。。。");
		    		}else if(StringUtils.equals(rInfo.getOperateType(), NumberConstants.NUMERAL_S_THREE)) {
		    			LOGGER.info("作废CRM行业、客户等级、订单来源开始。。。。。。。。。。。。。。。。。。");
		    			// 根据同步的动作执行对应的"删除"
		    			cusOrderSourceEntity.setModifyUser(USER_CODE);
		    			cusOrderSourceEntity.setModifyDate(new Date());
		    			cusOrderSourceEntity.setActive(FossConstants.INACTIVE);
		    			cusOrderSourceEntity.setVersionNo(new Date().getTime());
		    			cusOrderSourceService.deleteCusOrderSource(cusOrderSourceEntity);
		    			LOGGER.info("作废CRM行业、客户等级、订单来源结束。。。。。。。。。。。。。。。。。。");
		    		}else{
//		    			throw new CusOrderSourceException("传入的操作类型有误");
		    			LOGGER.info("传入的操作类型有误");
		    		}
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
				}
		}
	}
}