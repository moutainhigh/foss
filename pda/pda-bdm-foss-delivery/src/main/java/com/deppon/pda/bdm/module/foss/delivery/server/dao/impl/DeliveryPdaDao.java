package com.deppon.pda.bdm.module.foss.delivery.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeryExcpScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DriverSerDeptResultEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpExcpSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpNormSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.MotherSonSignList;

/**  
 * 作者：肖龙雾
 * 描述：TODO 
 * 包名：com.deppon.pda.bdm.module.foss.delivery.server.dao.impl
 * 时间：2014-11-13 上午8:22:17
 */
public class DeliveryPdaDao extends SqlSessionDaoSupport implements IDeliveryPdaDao{

	 /**
     * @Title: saveExpAbnormalSignTimeNode
    * @Description: 【快递异常按件签收】时间节点表
    * @param expExcpSign
    * @return void  返回类型 
    * @throws
     */
	@Override
	public void saveExpAbnormalSignTimeNode(ExpExcpSignEntity expExcpSign) {
		getSqlSession().insert(DeliveryPdaDao.class.getName() + ".saveExpAbnormalSignTimeNode",expExcpSign);
		
	}

	 /**
     * @Title: saveExpNormalSignTimeNode
    * @Description: 【快递正常按件签收】时间节点表
    * @param expNormSign
    * @return void  返回类型 
    * @throws
     */
	@Override
	public void saveExpNormalSignTimeNode(ExpNormSignEntity expNormSign) {
		getSqlSession().insert(DeliveryPdaDao.class.getName() + ".saveExpNormalSignTimeNode",expNormSign);
		
	}

	/**
	 * @作者：xiaolongwu
	 * @描述：【快递派送拉回】时间节点表
	 * @参数：deryExcpScan 派送异常 
	 * @返回值：void
	 * @时间：2014-11-27 下午2:40:23
	 */
	@Override
	public void saveDeryExcepTimeNode(DeryExcpScanEntity deryExcpScan) {
		getSqlSession().insert(DeliveryPdaDao.class.getName() + ".saveDeryExcepTimeNode",deryExcpScan);
	}

	/**
	 * @作者：245955
	 * @描述：【子母件签收】时间节点表
	 * @参数：正常签收
	 * @返回值：void
	 * @时间：2015-09-17 
	 */
	@Override
	public void saveExpParentSignNode(MotherSonSignList motherSonSignList) {
		 getSqlSession().insert(DeliveryPdaDao.class.getName()+".saveExpParentNode",motherSonSignList);
	}
	/*
	 * 
	 * @Description: TODO(查询司机的部门编码)  
	 * @return String    返回类型
	 * @param empNo
	 * @author： 268974  wangzhili
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverSerDeptResultEntity> queryDriverDeptCode(String deptName) {
		return  getSqlSession().selectList(DeliveryPdaDao.class.getName()+".queryDriverDeptCode",deptName);
	}

	/**
	 * 保存刷卡失败的数据
	 */
	@Override
	public void saveNCIPaymentCard(
			AccountStatementEntitys accountStatementEntitys) {
		getSqlSession().insert(DeliveryPdaDao.class.getName()+".saveNCIPaymentCard",accountStatementEntitys);
	}

	@Override
	public void saveNCIPaymentCardByBush(GetBushCardEntitys getBushCardEntitys) {
		getSqlSession().insert(DeliveryPdaDao.class.getName()+".saveNCIPaymentCardByBush",getBushCardEntitys);
		
	}

	@Override
	public void saveNCIPrepaymentCard(
			AccountStatementEntitys accountStatementEntitys) {
		
		getSqlSession().insert(DeliveryPdaDao.class.getName()+".saveNCIPrepaymentCard",accountStatementEntitys);
	}

	/**
     * *@Title: saveNCIPrepaymentCardSuccess 
     * @Description: TODO(保存结清货款刷卡数据)  
     * @return void    返回类型 
     * @param getPaymentSuccessEntitys
     * @author： 245955
     */
	@Override
	public void saveNCIPaymentCardSuccess(
			GetPaymentSuccessEntitys getPaymentSuccessEntitys) {
		getSqlSession().insert(DeliveryPdaDao.class.getName()+".saveNCIPaymentCardSuccess", getPaymentSuccessEntitys);
	}
}
