/**
 * 
 */
package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto;

/**
 * @author 045738
 * @预提Dao
 */
public class WithholdingEntityDao extends iBatis3DaoImpl implements
		IWithholdingEntityDao {
	//命名预提空间
	private static final String NAMESPACE = "foss.stl.WithholdingEntityDao.";
	
	/**
	 * 功能：新增预提记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-23
	 * @return
	 */
	public int insert(WithholdingEntity entity){
		return this.getSqlSession().insert(NAMESPACE+"insert",entity); 
	}
	
	/**
	 * 功能：根据传入的单号，类型，和预提状态来查询。
	 * 		单号、类型为必填项，预提状态为非必填
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WithholdingEntity> selectByNosAndType(List<String> billNos,
			String billType, List<String> status,String active) {
		//只有单据编号和单据类型不为空，才会查询
		if(!CollectionUtils.isEmpty(billNos) && StringUtils.isNotBlank(billType)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("billNos", billNos);
			map.put("billType", billType);
			map.put("status", status);
			map.put("active", active);
			return this.getSqlSession().selectList(NAMESPACE+"selectByNosAndType",map);
		}
		return null;
	}

	/**
	 * <p>预提接口实现</p> 
	 * @author 105762
	 * @date 2014-7-10 下午4:31:07
	 * @see com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto
	 */
	@Override
	public int wipePayableWorkflowNo(WithholdingDto dto) {
		return this.getSqlSession().update(NAMESPACE + "wipePayableWorkflowNo", dto);
	}

	/** 
	 * <p>更新预提单预提状态</p> 
	 * @author 105762
	 * @date 2014-7-11 下午4:39:50
	 * @param dto
	 * @return 更新条数
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingDao#updateWithholdingState(com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto)
	 */
	@Override
	public int updateWithholdingSuccessByWorkflowNo(WithholdingEntity en) {
		return this.getSqlSession().update(NAMESPACE + "updateWithholdingSuccessByWorkflowNo", en);
	}
	/** 
	 * <p>更新预提单预提状态</p> 
	 * @author 105762
	 * @date 2014-7-11 下午4:39:50
	 * @param dto
	 * @return 更新条数
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingDao#updateWithholdingState(com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto)
	 */
	@Override
	public int updateWithholdingFailedByWorkflowNo(WithholdingEntity en) {
		return this.getSqlSession().update(NAMESPACE + "updateWithholdingFailedByWorkflowNo", en);
	}
}
