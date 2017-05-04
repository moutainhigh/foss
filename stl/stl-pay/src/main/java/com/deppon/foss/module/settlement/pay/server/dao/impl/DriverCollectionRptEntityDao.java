package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;


/**
 * 司机缴款DAO
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午7:37:53
 */
public class DriverCollectionRptEntityDao extends iBatis3DaoImpl implements
		IDriverCollectionRptEntityDao {
	/**
	 * 声明命名空间
	 */
	public static final String NAMESPACES = "foss.stl.DriverCollectionRptEntityDao.";
	/** 
	 * 查询收款报表信息
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-18 下午7:37:53
	 * @return 
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IReceiveReportDAO#queryReceiveReportBill()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverCollectionRptEntity> queryReceiveReportBill(DriverCollectionRptDto dto, int start, int limit) {
		//添加分页参数
		RowBounds rb = new RowBounds(start,limit);
		/**
		 * 查询收款报表信息
		 */
		return this.getSqlSession().selectList(NAMESPACES+"queryReceiveReportBill",dto,rb);
	}

	/** 
	 * 保存司机收款报表
	 * @author 045738-foss-maojianqiang
	 * @param  缴款报表实体
	 * @date 2012-12-18 下午7:37:53
	 * @return 缴款报表实体、当前登录人信息
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IReceiveReportDAO#addReceiveReportBill()
	 */
	@Override
	public void addReceiveReportBill(DriverCollectionRptEntity entity,CurrentInfo currentInfo) {
		/**
		 * 判断保存的司机缴款报表实体
		 */
		if(entity==null){
			throw new SettlementException("传入参数为空，不能进行保存操作！");
		}
		//设置创建人
		entity.setCreateUserCode(currentInfo.getEmpCode());//
		//创建人名称	
		entity.setCreateUserName(currentInfo.getEmpName());//
		//创建部门
		entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());//
		//创建部门
		entity.setCreateOrgName(currentInfo.getCurrentDeptName());//
		//创建时间
		entity.setCreateTime(new Date());
		//修改用户编号
		entity.setModifyUserCode(currentInfo.getEmpCode());
		//修改用户名称
		entity.setModifyUserName(currentInfo.getEmpName());
		//修改时间 
		entity.setModifyTime(new Date());
		//执行insert
		this.getSqlSession().insert(NAMESPACES+"insert", entity);
	}

	/** 
	 * 获取查询日期内，该司机该车辆最近一次缴款报表日期
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-18 下午7:37:53
	 * @return 
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IReceiveReportDAO#getMaxReportDate(com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto)
	 */
	@Override
	public Date selectMaxReportDate(DriverCollectionRptDto dto) {
		//查询司机最近一次缴款日期
		return (Date) this.getSqlSession().selectOne(NAMESPACES+"selectMaxReportDate",dto);
	}

	/** 
	 * 查询总计
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件参数
	 * @date 2012-12-19 下午2:15:19
	 * @return  结果条数
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptEntityDao#queryReceiveReportBillCount(com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto)
	 */
	@Override
	public int queryReceiveReportBillCount(DriverCollectionRptDto dto) {
		//查询报表条数
		return (Integer) this.getSqlSession().selectOne(NAMESPACES+"queryReceiveReportBillCount",dto);
	}
	
	/**
	 * 查询生成报表时，是否已经有报表生成
	 * @author 045738-foss-maojianqiang
	 * @param 报表头实体
	 * @date 2012-12-21 下午3:30:56
	 * @return 符合条件条数
	 */
	@Override
	public int isExistReport(DriverCollectionRptEntity entity){
		//生成报表时先查询，是否已经有报表生成
		return (Integer) this.getSqlSession().selectOne(NAMESPACES+"isExistReport",entity);
	}

	/** 
	 * 根据报表号查询报表
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午8:27:34
	 * @param reportNo
	 * @return 报表头
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptEntityDao#queryReceiveReportByReportNo(java.lang.String)
	 */
	@Override
	public DriverCollectionRptEntity queryReceiveReportByReportNo(String reportNo) {
		//根据报表编号查询报表
		return (DriverCollectionRptEntity) this.getSqlSession().selectOne(NAMESPACES+"queryReceiveReportByReportNo",reportNo);
	}
	
	/** 
	 * 获取查询日期内，该司机该车辆最近一次缴款报表日期
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-18 下午7:37:53
	 * @return 
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IReceiveReportDAO#getMaxReportDate(com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto)
	 */
	@Override
	public Date selectMaxReportDateByDate(DriverCollectionRptDto dto) {
		//查询司机最近一次缴款日期
		return (Date) this.getSqlSession().selectOne(NAMESPACES+"selectMaxReportDateByDate",dto);
	}
	
}
