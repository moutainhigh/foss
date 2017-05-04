package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptDEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;


/**
 * 缴款报表分录dao
 * @author 045738-foss-maojianqiang
 * @date 2012-12-20 上午9:12:20
 */
public class DriverCollectionRptDEntityDao extends iBatis3DaoImpl implements
		IDriverCollectionRptDEntityDao {
	/**
	 * 声明mybatis工作域
	 */
	private static final String NAMESPACES = "foss.stl.DriverCollectionRptDEntityDao.";
	
	/** 
	 * 新增缴款报表分录
	 * @author 045738-foss-maojianqiang
	 * @param 缴款明细实体
	 * @date 2012-12-20 上午9:12:20
	 * @return 
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptDEntityDao#add(com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity)
	 */
	@Override
	public int insert(DriverCollectionRptDEntity entity) {
		//插入创建时间
		return this.getSqlSession().insert(NAMESPACES+"insert",entity);
	}

	/** 
	 * 根据id查询明细
	 * @author 045738-foss-maojianqiang
	 * @param 明细id
	 * @date 2012-12-20 上午9:12:20
	 * @return 
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptDEntityDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public DriverCollectionRptDEntity queryByPrimaryKey(String id) {
		return (DriverCollectionRptDEntity) this.getSqlSession().selectOne(NAMESPACES+"selectByPrimaryKey",id);
	}

	/** 
	 * 根据报表编号查询报表明细
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午9:17:53
	 * @param reportNo
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptDEntityDao#queryByReportNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverCollectionRptDEntity> queryByReportNo(String reportNo,Date createTime) {
		//如果报表编号为空，则直接返回
		if(StringUtils.isNotBlank(reportNo)){
			//声明传入查询参数map
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("reportNo", reportNo);
			map.put("createTime", createTime);
			return this.getSqlSession().selectList(NAMESPACES+"queryByReportNo",map);
		}else{
			return null;
		}
	}

}
