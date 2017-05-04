package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.DopBillEntity;

/**
 * 家装应付单应收单Dao
 * 
 * @ClassName: IDopPayableEntityDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-30 上午10:35:50
 * 
 */
public interface IDopBillPayableReceivableEntityDao {

	// -------------------- write methods --------------------

	/**
	 * 保存应收单
	 * 
	 * @Title: add
	 * @param @param entity
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int insertDopPayableEntity(DopBillEntity dopBillEntity);

	/**
	 * 保存应收单
	 * 
	 * @Title: insertDopReceiveEntity
	 * @param @param dopReceivableEntity
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int insertDopReceiveEntity(DopBillEntity dopBillEntity);

	/**
	 * 查询应收单应付单
	 * 
	 * @Title: queryDopPayAndRec
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<DopBillEntity> queryDopPayAndRec(List<DopBillEntity> list);

	/**
	 * 更新应付单
	 * 
	 * @Title: updateDopPayable
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int updateDopPayable(List<DopBillEntity> list);

	/**
	 * 更新应收单
	 * 
	 * @Title: updateDopPayable
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int updateDopReceive(List<DopBillEntity> list);

	/**
	 * 插入应收红单
	 * 
	 * @Title: insetDopRedPayable
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int insetDopRedPayable(List<DopBillEntity> list);

	/**
	 * 插入应收红单
	 * 
	 * @Title: insetDopRedReceive
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int insetDopRedReceive(List<DopBillEntity> list);

	/**
	 * 获取所属子公司名称和编码
	 * 
	 * @Title: insetDopRedReceive
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<DopBillEntity> getSubCompanyNameAndCode(List<DopBillEntity> list);

	/**
	 * 判断是否生成应收单
	 * @Title: judgeReceive
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<DopBillEntity> judgeReceive(DopBillEntity entity);
	
	/**
	 * 判断是否生成应付单
	 * 
	 * @Title: judgePayable
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public List<DopBillEntity> judgePayable(DopBillEntity entity);

}
