package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
/**
 * 用来操作交互“信息部”的数据库对应数据访问Service接口实现类：SUC-222
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:130346-foss-lifanghong,date:2014-02-18 下午3:44:25
 * </p>
 * 
 * @author 130346-foss-lifanghong
 * @date 2014-02-18 下午3:44:25
 * @since
 * @version
 */
public class CommonInfoDeptService implements ICommonInfoDeptService {

	private IInfoDeptDao infoDeptDao;
	/**
     * 根据传入对象查询符合条件所有落地公司信息 
     * 
     * @author lifanghong
     * @date 2014-02-18 
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
	@Override
	public List<InfoDeptEntity> queryInfoDeptEntityByCondtion(
			InfoDeptEntity entity, int start, int limit) {
		List<InfoDeptEntity> infoDeptEntitys = infoDeptDao.queryInfoDeptListBySelectiveCondition(entity, start, limit);
		return infoDeptEntitys;
	}
	 /**
     * 统计总记录数 
     * @author lifanghong
     * @date  2014-02-18
     * @return 符合条件的总记录数
     */
	@Override
	public Long countRecordByCondition(InfoDeptEntity entity) {
		// TODO Auto-generated method stub
		return infoDeptDao.queryInfoDeptRecordCountBySelectiveCondition(entity);
	}

	public void setInfoDeptDao(IInfoDeptDao infoDeptDao) {
		this.infoDeptDao = infoDeptDao;
	}

}
