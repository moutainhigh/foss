/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description:
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 下午8:07:37
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;

import java.util.List;
import java.util.Map;

/**
 * 
* @description: 待刷卡运单管理数据层接口
* @className: IWSCManageDao
* 
* @authorCode 309603
* @date 2016年2月18日 下午8:08:07 
*
 */
public interface IWSCManageDao {

    /**
     *add by 309603 yangqiang
     *对待刷卡单据的一些基础操作
     */
    /**
     *  WSCWayBillParamEntity 实体内所有字段都会插入表中
     */
    public int insertWSCWayBill(WSCEntity params);

    /**
     *更新待刷卡单据
     * @param params 必须包含 key  "new" 对应要更新的 WSCWayBillParamEntity
     *               其他 key 对应WSCWayBillParamEntity里面的字段，即为查询条件
     * @return
     */
    public int updateWSCWayBillByID(Map params);
    public int updateWSCWayBillByWayBillNo(Map params);

    /**
     * 查询待刷卡单据
     * @param params 属性 fields 为所需要查询的字段 对应数据库中字段多个用，分割
     *               eg  fields =  "ID,WAYBILL_NO,SEND_CUSTOMER_CODE,WAYBILL_SOURCE"
     *               fields为null时默认全查
     *               其余属性为查询条件
     * @return
     */
    public List<WSCEntity> queryWSCWayBill(WSCEntity params);
    /**
     *add by 309603 yangqiang
     *对T+0明细的一些常用操作
     */
    /**
     *更新PosCardDetail待刷卡单据
     * @param params 必须包含 key  "new" 对应要更新的 PosCardDetailEntity
     *               其他 key 对应PosCardDetailEntity里面的字段，即为查询条件
     * @return
     */
    public int insertPosCardDetail(PosCardDetailEntity params);
    public int updatePosCardDetailByID(Map params);
    public int updatePosCardDetailByWayBillNo(Map params);

    /**
     * 查询PosCardDetail单据
     * @param params 属性 fields 为所需要查询的字段 对应数据库中字段多个用，分割
     *               eg  fields =  "ID, TRADE_SERIAL_NO, INVOICE_TYPE, INVOICE_NO"
     *               fields为null时默认全查
     *               其余属性为查询条件
     * @return
     */
    public List<PosCardDetailEntity> queryPosCardDetail(PosCardDetailEntity params);



    public int insertPosCard(PosCardEntity params);
    public int updatePosCardByID(Map params);
    public List<PosCardEntity> queryPosCard(PosCardEntity params);
    /**
     * 查询刷卡长期未占用数据
     * @author 231438
     * 2014-12-10
     */
	public List<PosCardEntity> queryPosCardToFrozen(PosCardEntity params);
	/**
	 * 批量更新长期未使用的刷卡数据未冻结
     * @author 231438
     * 2014-12-10
	 * @param posCardList
	 */
	public int batchUpdatePosCardToFrozen(List<PosCardEntity> posCardList);
}
