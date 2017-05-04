/**  
 * Project Name:tfr-load  
 * File Name:AutoAddCodeByHandDao.java  
 * Package Name:com.deppon.foss.module.transfer.load.server.dao.impl  
 * Date:2015年6月16日上午1:47:13  
 *  
 */
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeByHandDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: AutoAddCodeByHandDao <br/>  
 * Function: 待人工补码记录dao. <br/>  
 * date: 2015年6月16日 上午1:47:13 <br/>  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class AutoAddCodeByHandDao extends iBatis3DaoImpl implements
		IAutoAddCodeByHandDao {
	
	private final static String NAMESPACE = "com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity.";

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeByHandDao#insertAddCodeByHand(com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity)  
	 */
	@Override
	public int insertAddCodeByHand(AutoAddCodeByHandEntity entity) {
		this.getSqlSession().insert(NAMESPACE + "insertAddCodeByHand", entity);
		return FossConstants.SUCCESS;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeByHandDao#deleteAddCodeByHand(java.lang.String)  
	 */
	@Override
	public int deleteAddCodeByHand(String waybillNo) {
		this.getSqlSession().delete(NAMESPACE + "deleteAddCodeByHand", waybillNo);
		return FossConstants.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AutoAddCodeByHandEntity queryAddCodeByHand(String waybillNo,
			String reason) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("reason", reason);
		List<AutoAddCodeByHandEntity> backList = this.getSqlSession().selectList(NAMESPACE + "queryAddCodeByHand", paramsMap);
		if(backList!=null &&backList.size()>0){
			return backList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String searchAddCodeByHandByWaybillNo(String waybillNo) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "searchAddCodeByHandByWaybillNo", waybillNo);
	}

	@Override
	public int updateAddCodeByHandByWaybillNo(AutoAddCodeByHandEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateAddCodeByHandByWaybillNo", entity);
	}
	
	
}
