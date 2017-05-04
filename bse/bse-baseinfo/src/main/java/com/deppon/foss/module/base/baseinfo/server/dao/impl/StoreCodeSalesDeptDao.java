package com.deppon.foss.module.base.baseinfo.server.dao.impl;
import java.util.HashMap;
import java.util.Map;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStoreCodeSalesDeptDao;

/**
 * GXG项目提供给接送货根据客户门店编号查询德邦网点code DAO接口实现类
 * @author 151211 
 * @date 2015-9-11 下午4:01:52
 * @since
 * @version
 */
public class StoreCodeSalesDeptDao extends SqlSessionDaoSupport implements IStoreCodeSalesDeptDao{
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".storeCodeSalesDept.";
	/** 
	 * <p>根据客户门店编号查询德邦网点code</p> 
	 * @author 151211 
	 * @date 2015-9-11 下午4:02:16
	 * @param storeCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStoreCodeSalesDeptDao#querySalesDeptByStoreCode(java.lang.String)
	 */
	@Override
	public String querySalesDeptByStoreCode(String storeCode) {
		if(StringUtil.isBlank(storeCode)){
			return null;
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("storeCode", storeCode);
		return (String)getSqlSession().selectOne(NAMESPACE + "querySalesDeptByStoreCode", map);
	}
}
