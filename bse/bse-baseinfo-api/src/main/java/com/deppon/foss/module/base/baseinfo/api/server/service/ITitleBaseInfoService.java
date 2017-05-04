package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;

/**
 * 仓库预警短信接收岗位基础资料Service层接口
 * @author dujunhui-187862
 * @date 2014-08-08 下午3:24:18
 */

public interface ITitleBaseInfoService {
	
	/**
	 * 新增实体
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:25:25
	 */
	int addTitleBaseInfo(TitleBaseInfoEntity entity);

	/**
	 * 根据ID批量作废实体信息
	 * @param codeList
	 * @param modifyUser
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:26:33
	 */
	int deleteTitleBaseInfo(String[] codeList, String modifyUser);

	/**
	 * 修改实体信息
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:28:21
	 */
	int updateTitleBaseInfo(TitleBaseInfoEntity entity);

	/**
	 * 根据传入对象查询符合条件的实体信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:30:18
	 */
	List<TitleBaseInfoEntity> queryTitleBaseInfoEntityByCondition(TitleBaseInfoEntity entity,
			int limit, int start);

	/**
	 * 统计记录数
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:31:38
	 */
	Long queryRecordCount(TitleBaseInfoEntity entity);

	/**
	 * 根据ID查询实体详细信息
	 * @param id
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:32:26
	 */
	TitleBaseInfoEntity queryTitleBaseInfoEntityByID(String id);

	/**
	 * <p>TODO(根据部门编码查询该部门下所有员工的电话号码)</p> 
	 * @author 187862
	 * @date 2014-8-11 下午2:49:38
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<String> queryPhoneInfoByOrgCode(String orgCode);

}
