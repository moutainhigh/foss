package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITitleBaseInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;

/**
 * 仓库预警短信接收岗位基础资料Service层实现
 * @author dujunhui-187862
 * @date 2014-08-08 下午4:10:23
 * 
*/
public class TitleBaseInfoService implements ITitleBaseInfoService {
	
    /**
     * 仓库预警短信接收岗位基础资料DAO层接口
     */
    private ITitleBaseInfoDao titleBaseInfoDao;
    
    /**
	 * @param titleBaseInfoDao the titleBaseInfoDao to set
	 */
	public void setTitleBaseInfoDao(ITitleBaseInfoDao titleBaseInfoDao) {
		this.titleBaseInfoDao = titleBaseInfoDao;
	}

	/** 
	 * <p>TODO(新增)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:47
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#addTitleBaseInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
	 */
	@Override
	public int addTitleBaseInfo(TitleBaseInfoEntity entity) {
		// TODO Auto-generated method stub
		int result = titleBaseInfoDao.addTitleBaseInfo(entity);
		return result;
	}
	/** 
	 * <p>TODO(删除)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:49
	 * @param codeList
	 * @param modifyUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#deleteTitleBaseInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteTitleBaseInfo(String[] codeList, String modifyUser) {
		// TODO Auto-generated method stub
		int result= titleBaseInfoDao.deleteTitleBaseInfo(codeList, modifyUser);
		return result;
	}
	/** 
	 * <p>TODO(修改)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:49
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#updateTitleBaseInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
	 */
	@Override
	public int updateTitleBaseInfo(TitleBaseInfoEntity entity) {
		// TODO Auto-generated method stub
		int result= titleBaseInfoDao.updateTitleBaseInfo(entity);
		return result;
	}
	/** 
	 * <p>TODO(根据条件查询列表)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:49
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#queryTitleBaseInfoEntityByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity, int, int)
	 */
	@Override
	public List<TitleBaseInfoEntity> queryTitleBaseInfoEntityByCondition(
			TitleBaseInfoEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		if(null==entity){
			entity =new TitleBaseInfoEntity();
		}
		List<TitleBaseInfoEntity> list= titleBaseInfoDao.queryTitleBaseInfoByCondition(entity, limit, start);
		return list;
	}
	/** 
	 * <p>TODO(查询记录数)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:49
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity)
	 */
	@Override
	public Long queryRecordCount(TitleBaseInfoEntity entity) {
		// TODO Auto-generated method stub
		return titleBaseInfoDao.queryRecordCount(entity);
	}
	/** 
	 * <p>TODO(根据ID查询信息详情)</p> 
	 * @author 187862
	 * @date 2014-8-8 下午4:07:49
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#queryTitleBaseInfoEntityByID(java.lang.String)
	 */
	@Override
	public TitleBaseInfoEntity queryTitleBaseInfoEntityByID(String id) {
		// TODO Auto-generated method stub
		return titleBaseInfoDao.queryTitleBaseInfoEntityByID(id);
	}
	/** 
	 * <p>TODO(根据部门编码查询该部门下所有员工的电话号码)</p> 
	 * @author 187862
	 * @date 2014-8-11下午2:47:14
	 * @param orgCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService#queryPhoneInfoByOrgCode
	 */
	@Override
	public List<String> queryPhoneInfoByOrgCode(String orgCode) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(orgCode)){
			return null;
		}
		return titleBaseInfoDao.queryPhoneInfoByOrgCode(orgCode);
	}

}
