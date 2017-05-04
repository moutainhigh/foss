package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;

/**
 * 仓库预警短信接收岗位基础资料DAO层接口
 * @author dujunhui-187862
 * @date 2014-08-08 下午3:09:37
 */
public interface ITitleBaseInfoDao {

	/**
	 * 新增实体
	 * @param entity
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:12:52
	 */
    int addTitleBaseInfo(TitleBaseInfoEntity entity);

    /**
     * 根据ID批量作废实体信息
     * @param codeList
     * @param modifyUser
     * @return
     * @author dujunhui-187862
     * @date 2014-08-08 下午3:14:32
     */
    int deleteTitleBaseInfo(String[] codeList, String modifyUser);

    /**
     * 修改实体信息
     * @param entity
     * @return
     * @author dujunhui-187862
     * @date 2014-08-08 下午3:14:32
     */
    int updateTitleBaseInfo(TitleBaseInfoEntity entity);
    
	/**
	 * 根据传入对象查询符合条件的实体信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @author dujunhui-187862
	 * @date 2014-08-08 下午3:18:11
	 */
    List<TitleBaseInfoEntity> queryTitleBaseInfoByCondition(TitleBaseInfoEntity entity,
	    int limit, int start);

    /**
     * 统计记录数
     * @param entity
     * @return
     * @author dujunhui-187862
     * @date 2014-08-08 下午3:19:48
     */
    Long queryRecordCount(TitleBaseInfoEntity entity);

    /**
     * 根据ID查询实体详细信息
     * @param id
     * @return
     * @author dujunhui-187862
     * @date 2014-08-08 下午3:20:35
     */
    TitleBaseInfoEntity queryTitleBaseInfoEntityByID(String id);

	/**
	 * <p>TODO(根据部门编码查询该部门下所有员工的电话号码)</p> 
	 * @author 187862
	 * @date 2014-8-11 下午2:50:22
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<String> queryPhoneInfoByOrgCode(String orgCode);

}
