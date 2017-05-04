/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 下午8:11:07
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IWSCManageDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;

import java.util.List;
import java.util.Map;

/**
 * 
* @description: 待刷卡运单管理数据层接口实现
* @className: WSCWayBillManageDao
* 
* @authorCode 309613
* @date 2016年2月18日 下午8:11:15 
*
 */
public class WSCManageDao extends iBatis3DaoImpl implements IWSCManageDao {

	/**
	 * SQL Mapper namespace
	 */
	public static final String NAMESPACES = "foss.common.WSCManageDao.";


//-------------------add  by  309603 yangqiang ---
    //使用方法--
    @Override
    public int insertWSCWayBill(WSCEntity params) {
        return this.getSqlSession().insert(NAMESPACES.concat("insertWSCWayBill"), params);
    }

    @Override
    public int updateWSCWayBillByID(Map params) {
        return this.getSqlSession().update(NAMESPACES.concat("updateWSCWayBillByID"), params);
    }

    @Override
    public int updateWSCWayBillByWayBillNo(Map params) {
        return this.getSqlSession().update(NAMESPACES.concat("updateWSCWayBillByWayBillNo"), params);
    }

    @Override
    public List<WSCEntity> queryWSCWayBill(WSCEntity params) {
        return this.getSqlSession().selectList(NAMESPACES.concat("queryWSCWayBill"), params);
    }

    @Override
    public int insertPosCardDetail(PosCardDetailEntity params) {
        return this.getSqlSession().insert(NAMESPACES.concat("insertPosCardDetail"), params);
    }

    @Override
    public int updatePosCardDetailByID(Map params) {
        return this.getSqlSession().update(NAMESPACES.concat("updatePosCardDetailByID"), params);
    }

    @Override
    public int updatePosCardDetailByWayBillNo(Map params) {
        return this.getSqlSession().update(NAMESPACES.concat("updatePosCardDetailByWayBillNo"), params);
    }

    @Override
    public List<PosCardDetailEntity> queryPosCardDetail(PosCardDetailEntity params) {
        return this.getSqlSession().selectList(NAMESPACES.concat("queryPosCardDetail"), params);
    }

    @Override
    public int updatePosCardByID(Map params) {
        return this.getSqlSession().update(NAMESPACES.concat("updatePosCardByID"), params);
    }

    @Override
    public List<PosCardEntity> queryPosCard(PosCardEntity params) {
        return this.getSqlSession().selectList(NAMESPACES.concat("queryPosCard"), params);
    }

    @Override
    public int insertPosCard(PosCardEntity params) {
        return this.getSqlSession().insert(NAMESPACES.concat("insertPosCard"), params);
    }
    /**
     * 查询刷卡长期未占用数据
     * @author 231438
     * 2014-12-10
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<PosCardEntity> queryPosCardToFrozen(PosCardEntity params){
    	return this.getSqlSession().selectList(NAMESPACES.concat("queryPosCardToFrozen"), params);
    }
	/**
	 * 批量更新长期未使用的刷卡数据未冻结
     * @author 231438
     * 2014-12-10
	 * @param posCardList
	 */
    public int batchUpdatePosCardToFrozen(List<PosCardEntity> posCardList){
    	return this.getSqlSession().update(NAMESPACES.concat("batchUpdatePosCardToFrozen"), posCardList);
    }
}
