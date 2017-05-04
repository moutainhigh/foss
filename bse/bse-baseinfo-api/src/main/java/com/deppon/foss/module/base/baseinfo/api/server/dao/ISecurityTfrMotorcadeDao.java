package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;

/**
 * TODO(描述类的职责)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:45:46
 * @since
 * @version
 */
public interface ISecurityTfrMotorcadeDao {
	
	/**
     * 
     * 根据保安组编码查询信息
     */
	List<SecurityTfrMotorcadeEntity> querySecurityTfrMotorcadeListBySecurityCode(SecurityTfrMotorcadeEntity entity,int limit,int start);
	/**
     * 
     * 统计所有保安组信息分页查询
     */
	Long queryRecordCount(SecurityTfrMotorcadeEntity entity);
	
	/**
     * 
     * 统计同一保安组信息条数
     */
	Long querySecurityCodeCount(SecurityTfrMotorcadeEntity entity);
	
	/**
     * 
     * 增加保安组信息
     */
	int addSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity);
	
	/**
     * 
     * 删除选中的保安组信息
     */
	int deleteSecurityTfrMotorcade(String[] codeList);
	
	/**
     * 
     * 更新保安组信息
     */
	int updateSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity);

}
