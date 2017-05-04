/**
 * 
 */
package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingEntityDetailDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;

/**
 * @author 045738
 *
 */
public class WithholdingEntityDetailDao extends iBatis3DaoImpl implements
		IWithholdingEntityDetailDao {
	//声明命名空间
	private static final String NAMESPACES = "foss.stl.WithholdingEntityDetailDao.";
	
	/**
	 * 功能：插入预提明细
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public int insert(WithholdingEntityDetail entity){
		//校验传入参数
		if(entity==null){
			throw new SettlementException("传入参数不能为空！");
		}
		return this.getSqlSession().insert(NAMESPACES+"insert",entity);
	}

	/**
	 * 功能：根据工作流号查询预提明细
	 * @date 2014-8-15
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectRentCarNoByWorkFlowNo(String workFlowNo){
		if(!StringUtils.isBlank(workFlowNo)){
			return this.getSqlSession().selectList(NAMESPACES+"selectRentCarNoByWorkFlowNo",workFlowNo);
		}
		return null;
	}
}
