/**
 * 
 */
package com.deppon.foss.module.init.client.sync.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.init.client.sync.dao.ISynSaleDepartmentDao;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:数据同步基线DAo</small></b> </br> <b
 * style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-5-11 linws 新增 </div>
 ******************************************** 
 */
public class SynSaleDepartmentDao implements ISynSaleDepartmentDao{
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.sync.saleDepartment.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
     * <p>
     * (按登录人查询收货部门)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	public List<SaleDepartmentEntity> querySaleDepartmentByBillGroup(
			String orgCode){
		SaleDepartmentEntity entity = new SaleDepartmentEntity();
		entity.setTransferCenter(orgCode);//这个值用来传参数
		entity.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE+ "querySaleDepartmentByBillGroup", entity);
	}
	
}
