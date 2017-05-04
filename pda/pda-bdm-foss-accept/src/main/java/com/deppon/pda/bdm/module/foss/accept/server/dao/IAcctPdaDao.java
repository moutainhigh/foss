package com.deppon.pda.bdm.module.foss.accept.server.dao;

import java.util.List;

import com.deppon.pda.bdm.module.foss.accept.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AllTimeNode;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdBusinessAreasEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdInsuredEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdOneManyLimitEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdPartSalesDeptEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.kdPartSalesAreaEntitys;

/** 
  * @ClassName IAcctPdaDao 
  * @Description TODO 
  * @author cbb 
  * @date 2014-6-3 上午8:38:47 
*/ 
public interface IAcctPdaDao {

	/** 
	* @Description: 获取快递点部营业部信息（即快递收入部门信息）
	* @param userCode 
	* @return List<KdPartSalesDeptEntity>
	* @author 078829
	* @date 2014-5-22 上午17:06:12
	*/ 
	public List<KdPartSalesDeptEntity> queryKdPartSalesDeptEntitys(String userCode) ;

	/**   
	 * @Title: queryDeptEntityIsOneMany  
	 * @Description: 快递员出发部门是否可以发一票多件
	 * @param @param userCode
	 * @param @return    设定文件   
	 * @return String    返回类型  
	 * @throws  
	 */
	public String queryDeptEntityIsOneMany(String userCode);
	
	/**   
	 * @Title: queryExpLimitWeight  
	 * @Description:  获取快递运输性质限定重量临界值
	 * @param @return    设定文件   
	 * @return double    返回类型  
	 * @throws  
	 */
	public String queryExpLimitWeight();
	
	/**
	 * 保存工时节点
	 * @param allTimeNode
	 */
	public void saveTimeNode(AllTimeNode allTimeNode);
	
	/**
	 * 
	 * <p>TODO(快递外发保价申明价值 获取 //author:245960 Date:2015-08-22 comment:骆敏霞需求获取  快递保价申明价值上限  1104   快递外发保价申明价值 1105)</p> 
	 * @author 245960
	 * @date 2015-8-22 下午4:52:21
	 * @return
	 * @see
	 */
	public List<KdInsuredEntity> queryKdInsuredEntity();
	
	/**
	 * 一票多件重量、体积、件数限制
	 * @author 268974
	 * @date 2015-11-30 下午
	 */
	public List<KdOneManyLimitEntity> queryOneManyLimit();
	/**
	 * 快递员当前城市的所有营业区
	 * @author 268974
	 * @date 2015-12-17 下午
	 */
	public List<kdPartSalesAreaEntitys> queryCourierAllSalesArea(String empCode);
	
	/**
	 * 营业区对应的所有营业部
	 * @author 268974
	 * @date 2015-12-17 下午
	 */
	public List<KdPartSalesDeptEntity> queryAllSalesDept(String areaCode);
	/**
	 * 快递员默认营业区
	 * @author 268974
	 * @date 2015-12-18 下午
	 */
	public KdBusinessAreasEntity queryDefaultAreaCode(String empCode);
	
	public void saveNCIPaymentCard(AccountStatementEntitys accoutAccountStatement);
}
