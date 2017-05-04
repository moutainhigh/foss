package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryMapManageVo;

/**
 * 快递派送电子地图管理Service接口
 * @author dujunhui-187862
 * @date 2014-12-19 上午8:47:31
 */
public interface IExpressDeliveryMapManageService extends IService{
	
	/**
     * 批量查看选中的快递派送电子地图管理信息
     */
	List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageBatchView(String[] ids);
	
	/**
     * 根据ID查询快递派送电子地图管理信息
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
	 * 审核快递派送区域电子地图
	 */
	void verifyExpressDeliveryMapManage(String[] codes);
	
	/**
     * 生效快递派送电子地图管理信息
     */
	void activateExpressDeliveryMapManage(String[] codes);
	
	/**
     * 退回快递派送电子地图管理信息
     */
	void turnBackExpressDeliveryMapManage(ExpressDeliveryMapManageVo expressDeliveryMapManageVo);
	
	/**
     * 根据实体查询快递派送电子地图管理信息用于导出(已作废)
     */
	List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageListForExport(ExpressDeliveryMapManageEntity entity);

	/*
	 * 更新保存GIS派送区域电子地图坐标
	 */
	void updateExpressDeliveryGISMap(ExpressDeliveryMapManageEntity entity);
	
	/*
	 * 根据编码作废营业部信息（已作废）
	 */
	void voidSalesDepartmentInfo(String[] codes);
	
	
	/**
	 * 
	 */
	public ExpressDeliveryMapManageEntity addSalesDepartmentInfo(ExpressDeliveryMapManageEntity entity);
	/**
	 * 
	 * @return
	 */
	public ExpressDeliveryMapManageEntity queryExpressDeliveryMapManageEntityByCode(String orgCode);
	
	/**
	 * 
	 * @param updateEntity
	 * @return
	 */
	public long deleteExpressDeliveryMapManageEntityById(ExpressDeliveryMapManageEntity updateEntity);
	
	
}
