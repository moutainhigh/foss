package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;

/**
 * TODO(CodRefund的Service接口)
 * @author 187862-dujunhui
 * @date 2014-7-16 上午10:40:09
 * @since
 * @version v1.0
 */
public interface ICodRefundService extends IService {
	
	/**
     * 
     * 添加单条代收货款打包退款基础信息 
     */ 
	CodRefundEntity addCodRefund(CodRefundEntity entity,List<String> fileNames );
    
    /**
     * 
     * 作废单条代收货款打包退款基础信息 
     * 
     */
	CodRefundEntity deleteCodRefund(CodRefundEntity entity);
	
	/**
     * 
     * 批量作废单条代收货款打包退款基础信息 
     * 
     */
	int deleteCodRefunds(String[] codeList,String modifyUser);


    /**
     * 
     * 更新单条代收货款打包退款基础信息
     *
     */
	CodRefundEntity updateCodRefund(CodRefundEntity entity);
    
    /**
     * 
     * 根据ID查询代收货款打包退款基础信息详情
     *
     */
	CodRefundEntity queryCodRefundById(String id);

    /**
     * 
     * 按条件查询代收货款打包退款基础信息列表
     *
     */
    List<CodRefundEntity> queryCodRefundListByCondition(CodRefundEntity entity, int start, int limit);
    
    /**
     * 
     * 根据实体查询代收货款打包退款基础资料信息用于导出
     */
	List<CodRefundEntity> queryCodRefundListForExport(CodRefundEntity entity);

	/**
	 * 
	 * 按条件查询代收货款打包退款基础信息列表信息条数
	 *
	 */
	long queryCodRefundListCountByCondition(CodRefundEntity entity);

	/**
	 * <p>TODO(根据实体查询代收货款打包退款基础资料信息用于导出)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午4:41:22
	 * @param entity
	 * @return
	 * @see
	 */
//	InputStream queryExportCodRefunds(CodRefundEntity entity);
}
