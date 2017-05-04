package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;

/**
 * 快递派送电子地图管理的Dao层接口
 * @author 187862-dujunhui
 * @date 2014-12-19 上午8:21:26
 * @since
 * @version v1.0
 */
public interface IExpressDeliveryMapManageDao {
	
	/**
     * 批量查看选中的快递派送电子地图管理信息
     */
	List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageBatchView(String[] ids);
	
	/**
     * 根据salesDepartmentCode查询快递派送电子地图管理信息
     */
	ExpressDeliveryMapManageEntity queryExpressDeliveryMapManageByCode(String salesDepartmentCode);
	
	/**
     * 根据条件查询快递派送电子地图管理信息
     */
	List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageByCondition(ExpressDeliveryMapManageEntity entity, int start, int limit);
	
	/**
     * 统计根据条件查询的快递派送电子地图管理信息条数
     */
	Long queryExpressDeliveryMapManageCountByCondition(ExpressDeliveryMapManageEntity entity);
	
	/*
	 * 审核快递派送电子地图信息
	 */
	void verifyExpressDeliveryMapManage(String[] codes);
	
	/**
     * 生效快递派送电子地图管理信息
     */
	void activateExpressDeliveryMapManage(String[] codes);
	
	/**
     * 退回快递派送电子地图管理信息
     */
	void turnBackExpressDeliveryMapManage(String[] codes);
	
	/**
     * 根据实体查询快递派送电子地图管理信息用于导出(已作废)
     */
	List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageListForExport(ExpressDeliveryMapManageEntity entity);
	
	/*
	 * 更新保存GIS派送区域电子地图坐标
	 */
	void updateExpressDeliveryGISMap(ExpressDeliveryMapManageEntity entity);
	
	/*
	 * 根据编码作废单条营业部信息（已作废）
	 */
	void voidSalesDepartmentInfo(String[] codes);
	
	/**
	 * ---------------------------
	 */
	
	public ExpressDeliveryMapManageEntity addSalesDepartmentInfo(ExpressDeliveryMapManageEntity entity);
	
	/**
	 * 
	 * @param orgCode
	 * @return
	 */
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageEntityByCode(ExpressDeliveryMapManageEntity entity);
	
	
	public long deleteExpressDeliveryMapManageEntityById(ExpressDeliveryMapManageEntity entity);
	  
	
	
}
