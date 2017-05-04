package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.oa.ows.inteface.domain.ExternalPropertyInfo;
/**
 * 用来同步FOSS外场信息给OA的Service接口：无SUC
 * @author 187862-dujunhui
 * @date 2015-1-5 下午5:00:55
 * @since
 * @version
 */
public interface ISyncOutFieldInfoService extends IService {

    /**
     * <p>同步FOSS的外场信息给OA系统</p> 
     * @author 187862-dujunhui
     * @date 2015-1-5 下午5:06:33
     * @param externalPropertyInfoList “外场信息”封装实体列表
     * @throws SynchronousExternalSystemException
     * @see
     */
    public void synchronizeOutfieldDataToOA(List<ExternalPropertyInfo> externalPropertyInfoList) throws SynchronousExternalSystemException;
  
}
