package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressDeptRelationRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressPartAndSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.RalationDetail;
import com.deppon.ows.inteface.domain.SyncExpressDeptRelationRequest;

/**
 * 同步快递点部和营业部的对应关系到CRM
 * 
 * @author WangPeng
 * @date 2013-8-1 下午5:34:58
 * 
 */
public class SyncExpressPartAndSalesDeptService implements
		ISyncExpressPartAndSalesDeptService {

	// 记录日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncExpressPartAndSalesDeptService.class);
	// esb接口服务编码
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_EXPRESS_DEPT";
	
	//注入组织的Service
	IOrgAdministrativeInfoService  orgAdministrativeInfoService;
	
	
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 同步快递点部和营业部的对应关系到CRM
	 * 
	 * @author WangPeng
	 * @Date 2013-8-1 下午5:34:58
	 * @param dto
	 * 
	 */
	@Override
	public void syncExpressPartAndDeptToCrm(
			List<ExpressPartSalesDeptEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessException("传入的对象为空");
		}
		// 发送请求消息类
		SyncExpressDeptRelationRequest request = new SyncExpressDeptRelationRequest();

		// 操作记录的ID
		String id = "";
		// 营业部名称
		String salesDeptName = "";
		// 营业部标杆编码
		String salesDeptCode = "";
		// 快递点部名称
		String expressDeptName = "";
		// 快递点部标杆编码
		String expressDeptCode = "";
		// 类型
		String type = "";

		// 定义一个组织对象
		OrgAdministrativeInfoEntity orgEntity = null;
		// 定义一个快递点部对象
		OrgAdministrativeInfoEntity expressEntity = null;
		
		// 传递给crm的参数list
		List<RalationDetail> relationList = new ArrayList<RalationDetail>();

		StringBuffer versionNos = new StringBuffer();
		StringBuffer codes = new StringBuffer();

		for (ExpressPartSalesDeptEntity entity : list) {

			// 传递参数的对象
			RalationDetail detail = new RalationDetail();
			
			if (null == entity) {
				continue;
			}
			id = entity.getId();
			//精确查询，不走缓存，直接查询数据
			orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getSalesDeptCode().trim());
			//走缓存查询
			//orgEntity = this.queryOrgCodeCache(entity.getSalesDeptCode().trim());
			if (null != orgEntity) {
				salesDeptName = orgEntity.getName();
				salesDeptCode = orgEntity.getUnifiedCode();
			}
			
			//精确查询，不走缓存，直接查询数据
			expressEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getPartCode().trim());
			//走缓存查询
			//expressEntity = this.queryOrgCodeCache(entity.getPartCode().trim());
			if (null != expressEntity) {
				expressDeptName = expressEntity.getName();
				expressDeptCode = expressEntity.getUnifiedCode();
			}
			if (StringUtils.equals(entity.getActive(), FossConstants.ACTIVE)) {
				type = "1";
			} else if (StringUtils.equals(entity.getActive(),
					FossConstants.INACTIVE)) {
				type = "3";
			} else {
				throw new BusinessException(entity.getId() + "该对象没有标记操作类型！");
			}

			versionNos.append(entity.getVersionNo()).append(
					SymbolConstants.EN_COMMA);
			codes.append(entity.getId()).append(SymbolConstants.EN_COMMA);

			detail.setId(id);
			detail.setSaleDeptName(salesDeptName);
			detail.setSaleDeptStandardNumber(salesDeptCode);
			detail.setNewExDeptName(expressDeptName);
			detail.setNewExDeptStandardNumber(expressDeptCode);
			detail.setOperateType(type);

			relationList.add(detail);

		}

		request.getRelations().addAll(relationList);

		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(codes.toString());
		accessHeader
				.setBusinessDesc1("send ExpressPartSalesDeptRelation to Crm System ：发送营业部和快递点部的消息到Crm \n");
		accessHeader.setBusinessDesc2(versionNos.toString());
		accessHeader.setVersion("0.1");
		try {
			LOGGER.info("start send ExpressPartSalesDeptRelation to Crm System ：发送营业部和快递点部的消息到Crm \n"
					+ new SyncExpressDeptRelationRequestTrans()
							.fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			LOGGER.info("end send ExpressPartSalesDeptRelation to Crm System ：发送营业部和快递点部的消息到Crm \n"
					+ new SyncExpressDeptRelationRequestTrans()
							.fromMessage(request));
		} catch (ESBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据组织编码获取组织信息
	 * 
	 * @author WangPeng
	 * @date 2013-8-8 下午2:56:48
	 * @param key
	 * @return
	 * @see
	 */
	/*@SuppressWarnings({ "unchecked", "rawtypes"})
	private OrgAdministrativeInfoEntity queryOrgCodeCache(String key) {
		OrgAdministrativeInfoEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.ORGANIZATION_ORGCODE_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (OrgAdministrativeInfoEntity) cache.get(key);
		} catch (Exception t) {
			LOGGER.error("cache找不到", t);
		}
		return result;
	}*/

}
