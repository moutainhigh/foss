package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.OutFieldInfoRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.ISyncOutFieldInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.oa.ows.inteface.domain.ExternalPropertyInfo;
import com.deppon.oa.ows.inteface.domain.SyncExternalPropertyRequest;

/**
 * 用来同步FOSS外场信息给OA的Service接口实现类
 * @author 187862-dujunhui
 * @date 2015-1-5 下午5:06:33
 * @since
 * @version
 */
public class SyncOutFieldInfoService implements ISyncOutFieldInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncOutFieldInfoService.class);
    private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_MISS_GOODS";
    private static final String VERSION = "0.1";
    
	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
	}
    /**
     * <p>同步FOSS的外场信息给OA系统</p> 
     * @author 187862-dujunhui
     * @date 2015-1-5 下午5:06:33
     * @param siteInfoList “外场信息”封装实体列表
     * @throws SynchronousExternalSystemException 
     * @see 
     */
    @Override
    public void synchronizeOutfieldDataToOA(List<ExternalPropertyInfo> externalPropertyInfoList) throws SynchronousExternalSystemException {
		if (CollectionUtils.isNotEmpty(externalPropertyInfoList)) {
		    //执行数据发送封装
		    try {
			SyncExternalPropertyRequest externalPropertyInfoRequest = new SyncExternalPropertyRequest();
			externalPropertyInfoRequest.getExternalPropertyInfo().addAll(externalPropertyInfoList);
			AccessHeader accessHeader = new AccessHeader(); 
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUIDUtils.getUUID());
			accessHeader.setVersion(VERSION);
			
			LOGGER.info("start to send outfield info to other platform 开始 同步外场信息 到OA：\n"
				+ new OutFieldInfoRequestTrans()
					.fromMessage(externalPropertyInfoRequest));
			
			syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, externalPropertyInfoRequest);
			
			LOGGER.info("end to send outfield info to other platform 结束同步外场信息 到OA：\n"
				+ new OutFieldInfoRequestTrans()
					.fromMessage(externalPropertyInfoRequest));
	
		    } catch (Exception e) {
				LOGGER.error("start to send outfield info to other platform exception", e);
				throw new SynchronousExternalSystemException(
					"start to send outfield info to other platform exception",
					"start to send outfield info to other platform exception 发送数据到ESB错误");
		    }
		}
    }
    
}