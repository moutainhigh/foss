package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;

/**
 * 系统帮助DAO
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午3:55:20
 */
public interface ISystemHelpDao {
	
	/**
	 * 添加
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午3:55:37
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	int addSystemHelpEntity(SystemHelpEntity systemHelpEntity);
	  /**
     * 根据传入对象查询符合条件所有信息
     * 
     * @author zengjunfan
     * @date 2013-4-18 
     * @param dto
     *            查询实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	List<SystemHelpEntity> querySystemHelpEntity(SystemHelpDto dto,int limit,int start);
	
	/**
	 * 根据传入的对象查询记录总数
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午3:57:08
	 * @param
	 * @return 成功失败标记
	 * @exception
	 * @see
	 */
	long queryRecordCount(SystemHelpDto dto);
	
	/**
	 * 通过传人的对象修改信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午3:57:21
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	int upadteSystemHelpEntity(SystemHelpEntity entity);
	
	/**
	 * 根据传过来的id集合进行废除
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午3:57:38
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	int deleteSystemHelpEntityById(List<String> idList,String modifyUser);
	
	/**
	 * 根据传过来的id 进行查询实体
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午3:57:48
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	SystemHelpEntity querySystemHelpEntityById(String id);
	
	
}
