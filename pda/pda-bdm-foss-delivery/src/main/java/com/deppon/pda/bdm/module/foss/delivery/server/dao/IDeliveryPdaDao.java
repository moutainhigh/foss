package com.deppon.pda.bdm.module.foss.delivery.server.dao;

import java.util.List;

import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeryExcpScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DriverSerDeptResultEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpExcpSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpNormSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.MotherSonSignList;


/** 
  * @ClassName IAcctPdaDao 
  * @Description TODO 
  * @author cbb 
  * @date 2014-6-3 上午8:38:47 
*/ 
public interface IDeliveryPdaDao {
	 /**
    * @Title: saveExpAbnormalSignTimeNode
    * @Description: 【快递异常按件签收】时间节点表
    * @param picture
    * @return void  返回类型 
    * @throws
     */
	void saveExpAbnormalSignTimeNode(ExpExcpSignEntity expExcpSign);

	/**
	 * @Title: saveExpAbnormalSignTimeNode
    * @Description: 【快递正常常按件签收】时间节点表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
	 */
	void saveExpNormalSignTimeNode(ExpNormSignEntity expNormSign);

	/**
	 * @作者：xiaolongwu
	 * @描述：【快递派送拉回】时间节点表
	 * @参数：deryExcpScan 派送异常 
	 * @返回值：void
	 * @时间：2014-11-27 下午2:40:23
	 */
	void saveDeryExcepTimeNode(DeryExcpScanEntity deryExcpScan);
    
	/**
	 * @作者：245955
	 * @描述：【子母件签收】时间节点表
	 * @参数：正常签收
	 * @返回值：void
	 * @时间：2015-09-17 
	 */
	void saveExpParentSignNode(MotherSonSignList motherSonSignList);
    /**
     * 
     * @Title: queryDriverDeptCode 
     * @Description: TODO(查询司机部门编码)  
     * @return String    返回类型 
     * @param empNo
     * @author： 268974  wangzhili
     */
    public List<DriverSerDeptResultEntity> queryDriverDeptCode(String deptName);
    /**
     * @Title: saveNCIPaymentCard 
     * @Description: TODO(保存刷卡数据)  
     * @return String    返回类型 
     * @param empNo
     * @author： 268974  wangzhili 
     */
    void saveNCIPaymentCard(AccountStatementEntitys accountStatementEntitys );
    /**
     * 
     * @Title: saveNCIPaymentCardByBush 
     * @Description: TODO(保存待刷卡数据)  
     * @return void    返回类型 
     * @param getBushCardEntitys
     * @author： 268974  wangzhili
     */
    void saveNCIPaymentCardByBush(GetBushCardEntitys getBushCardEntitys);
    /**
     * 
     * @Title: saveNCIPrepaymentCard 
     * @Description: TODO(保存预存款刷卡数据)  
     * @return void    返回类型 
     * @param accountStatementEntitys
     * @author： 268974  wangzhili
     */
    void saveNCIPrepaymentCard(AccountStatementEntitys accountStatementEntitys);
    /**
     * *@Title: saveNCIPrepaymentCardSuccess 
     * @Description: TODO(保存结清货款刷卡数据)  
     * @return void    返回类型 
     * @param getPaymentSuccessEntitys
     * @author： 245955
     */
    void saveNCIPaymentCardSuccess(GetPaymentSuccessEntitys getPaymentSuccessEntitys);
}
