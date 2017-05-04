package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;

/**
 * 
 * @ClassName: IDepotAddressService 
 * @Description: 进仓地址 Service
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午3:18:31 
 *
 */
public interface IDepotAddressService extends IService {

	/**
	 * 查询进仓地址 ，分页查询
	 * @Title: queryDepotAddress 
	 * @Description: 查询进仓地址 ，分页查询
	 * @param @return    设定文件 
	 * @return List<DepotAddressEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<DepotAddressEntity> queryDepotAddress(DepotAddressEntity entity, int start, int limit);
	
	/**
	 * 获得总数
	 * @Title: getCountByCondition 
	 * @Description: 获得总数
	 * @param @param entity
	 * @param @return    设定文件 
	 * @return long    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public long getCountByCondition(DepotAddressEntity entity);
	
	/**
	 * 新增进仓地址 
	 * @Title: addDepotAddress 
	 * @Description: TODO(新增进仓地址 ) 
	 * @param @param entity    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public void addDepotAddress(DepotAddressEntity entity);
	
	/**
	 * 修改进仓地址
	 * @Title: updateDepotAddress 
	 * @Description: TODO(修改进仓地址) 
	 * @param @param entity    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public void updateDepotAddress(DepotAddressEntity entity);
	
	/**
	 * 作废进仓地址
	 * @Title: deleteDepotAddress 
	 * @Description: TODO(作废进仓地址) 
	 * @param @param entity    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public void deleteDepotAddress(DepotAddressEntity entity);
	
	/**
	 * 通过营业部code获取进仓地址信息
	 * @Title: queryDepotAddressByDepCode 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param depCode
	 * @param @return    设定文件 
	 * @return List<DepotAddressEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<DepotAddressEntity>  queryDepotAddressByDepCode(String depCode);
	
	/**
	 * 进仓地址信息导出
	* @Title: exportDepotAddressList 
	* @Description: TODO(进仓地址信息导出) 
	* @param @return    设定文件 
	* @return ExportResource    返回类型 
	* @throws 
	* @user 310854-liuzhenhua
	 */
	public ExportResource exportDepotAddressList();
}
