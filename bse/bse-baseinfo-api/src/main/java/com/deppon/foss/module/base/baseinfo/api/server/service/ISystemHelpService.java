package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;



/**
 * 系统帮助service
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午4:13:29
 */
public interface ISystemHelpService extends IService {

	/**
	 * 分页查询
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:13:33
	 * @param
	 * @return 成功失败标记
	 * @exception
	 * @see
	 */
	List<SystemHelpEntity> querySystemHelpEntity(SystemHelpDto systemHelpDto,int limit,int start);
	
	/**
	 * 查询总记录条数
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:13:35
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	long queryRecordCount(SystemHelpDto systemHelpDto);
	
	/**
	 * 新增
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:13:38
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	int addSystemHelpEntity(SystemHelpEntity systemHelpEntity);
	
	/**
	 * 通过传过来的id 进行查询符合条件的信息
	 * @author zengjunfan
	 * @date	2013-4-19 下午5:59:52
	 * @param id 
	 * @return
	 */
	SystemHelpEntity querySystemHelpEntityById(String id);
	
	/**
	 *  通过传人的对象修改信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:02:55
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	int upadteSystemHelpEntity(SystemHelpEntity entity);
	
	/**
	 * 根据传过来的id集合进行废除
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:02:36
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	int deleteSystemHelpEntityById(List<String> idList,String modifyUser);
}
