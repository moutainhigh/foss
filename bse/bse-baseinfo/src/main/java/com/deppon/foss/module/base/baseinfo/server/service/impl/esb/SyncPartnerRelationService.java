package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.json.SyncPartnerRelationRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncPartnerRelationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.ows.inteface.domain.relation.PartnerRelation;
import com.deppon.ows.inteface.domain.relation.SyncPartnerRelationRequest;

/**
 * 
 * 网点映射关系信息同步到ptp系统
 * @author 308861 
 * @date 2016-9-6 下午7:07:41
 * @since
 * @version
 */
public class SyncPartnerRelationService implements ISyncPartnerRelationService{

	private static final Logger log = LoggerFactory
			.getLogger(SyncPartnerRelationService.class);
	
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_PARTNER_RELATION";
	
	@Override
	public void syncPartnerRelationDataToPtp(
			List<PartnerRelationEntity> partnerRelationList) {
		try {
			if(CollectionUtils.isNotEmpty(partnerRelationList)){
				SyncPartnerRelationRequest partnerRelationRequest=new SyncPartnerRelationRequest();
				List<PartnerRelation> relationList=new ArrayList<PartnerRelation>();
				
				StringBuilder versionNos = new StringBuilder();
				StringBuilder codes = new StringBuilder();

				for (PartnerRelationEntity entity : partnerRelationList) {
					PartnerRelation partnerInfo = this.transFossToEsb(entity);
					relationList.add(partnerInfo);
					versionNos.append(SymbolConstants.EN_COMMA);
					versionNos.append(new Date().getTime());
					codes.append(SymbolConstants.EN_COMMA);
					codes.append(entity.getOnePartnerUnifiedCode());
					codes.append(entity.getTwoPartnerUnifiedCode());
				}
				partnerRelationRequest.getRelation().addAll(relationList);
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
				accessHeader.setBusinessId(codes.toString());
				accessHeader.setBusinessDesc1("同步 网点映射关系信息");
				accessHeader.setBusinessDesc2(versionNos.toString());
				accessHeader.setVersion("0.1");
				
				log.info("开始调用 同步网点映射关系信息：\n"
						+ new SyncPartnerRelationRequestTrans()
								.fromMessage(partnerRelationRequest));

				ESBJMSAccessor.asynReqeust(accessHeader, partnerRelationRequest);

				log.info("结束调用 同步网点映射关系信息：\n"
						+ new SyncPartnerRelationRequestTrans()
								.fromMessage(partnerRelationRequest));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new SynchronousExternalSystemException("同步网点映射关系信息",
					" 同步网点映射关系信息 发送数据到ESB错误");
		}
		
	}
	
	
	private PartnerRelation transFossToEsb(PartnerRelationEntity fossEntity) {
		if (fossEntity == null) {
			return null;
		}
		PartnerRelation info = new PartnerRelation();
		//一级网点标杆编码非空验证
		if (StringUtils.isBlank(fossEntity.getOnePartnerUnifiedCode())){
			// 打印日志信息
			log.info("SyncPartnerRelationService 实体类一级网点标杆编码为空");
			// 返回空
			return null;
		}
		//二级网点标杆编码非空验证
		if (StringUtils.isBlank(fossEntity.getTwoPartnerUnifiedCode())){
			// 打印日志信息
			log.info("SyncPartnerRelationService 实体类二级网点标杆编码为空");
			// 返回空
			return null;
		}
		// ID
		info.setId(fossEntity.getId());
		//一级网点标杆编码
		info.setOnePartnerUniCode(fossEntity.getOnePartnerUnifiedCode());
		//二级网点标杆编码
		info.setTwoPartnerUniCode(fossEntity.getTwoPartnerUnifiedCode());
		//操作时间
		info.setBeginDate(new Date());
		//操作类型
		info.setOperateType(fossEntity.getOperateType());
		//操作人姓名
		info.setOperaterName(fossEntity.getModifyUser());
		//操作人工号
		info.setOperaterCode(fossEntity.getModifyUser());
		return info;
	}
}
