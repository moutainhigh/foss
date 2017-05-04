package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;

/**
 * 
 * @ClassName: IDepotAddressDao 
 * @Description: 进仓地址 DAO
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午3:18:11 
 *
 */
public interface IDepotAddressDao {

	/**
	 * 查询进仓地址 ，分页查询
	 * @Title: queryDepotAddress 
	 * @Description: TODO(查询进仓地址 ，分页查询) 
	 * @param @param entity
	 * @param @param rowBound
	 * @param @return    设定文件 
	 * @return List<DepotAddressEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<DepotAddressEntity> queryDepotAddress(DepotAddressEntity entity,RowBounds rowBound);
	
	/**
	 * 获取总数
	 * @Title: getCountByCondition 
	 * @Description: 获取总数
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
	 * @Description: 新增进仓地址 
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public DepotAddressEntity addDepotAddress(DepotAddressEntity entity);
	
	/**
	 * 	修改进仓地址
	 * @Title: updateDepotAddress 
	 * @Description: 修改进仓地址 
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public DepotAddressEntity updateDepotAddress(DepotAddressEntity entity);
	
	/**
	 * 作废进仓地址 
	 * @Title: deleteDepotAddress 
	 * @Description: 作废进仓地址 
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public DepotAddressEntity deleteDepotAddress(DepotAddressEntity entity);
	
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
}
