package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;

/**
 * TODO(CodRefund的Dao层接口)
 * @author 187862-dujunhui
 * @date 2014-7-16 上午8:37:26
 * @since
 * @version v1.0
 */
public interface ICodRefundDao {
	
	/**
     * 
     * 增加单条代收货款打包退款基础资料信息
     */
	int addCodRefund(CodRefundEntity entity);
	
	/**
     * 
     * 删除选中的单条代收货款打包退款基础资料信息
     */
	int deleteCodRefund(CodRefundEntity entity);
	
	/**
     * 
     * 批量删除选中的代收货款打包退款基础资料信息
     */
	int deleteCodRefunds(String[] codeList,String modifyUser);
	
	/**
     * 
     * 更新单条代收货款打包退款基础资料信息
     */
	int updateCodRefund(CodRefundEntity entity);
	
	/**
     * 
     * 根据ID查询单条代收货款打包退款基础资料信息
     */
	CodRefundEntity queryCodRefundById(String id);
	
	/**
     * 
     * 根据条件查询代收货款打包退款基础资料信息
     */
	List<CodRefundEntity> queryCodRefundByCondition(CodRefundEntity entity, int start, int limit);
	
	/**
     * 
     * 统计根据条件查询的代收货款打包退款基础资料信息条数
     */
	Long queryCodRefundCountByCondition(CodRefundEntity entity);
	
	/**
     * 
     * 根据实体查询代收货款打包退款基础资料信息用于导出
     */
	List<CodRefundEntity> queryCodRefundListForExport(CodRefundEntity entity);
	

}
