package com.deppon.foss.module.settlement.agency.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.agency.api.server.dao.IBillPAPayableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;


/**
 * 查询_审核_作废快递代理其它应付
 * @author foss-guxinhua
 * @date 2012-11-05 上午11:32:56
 * @since
 * @version
 */
public class BillPAPayableAgencyDao extends iBatis3DaoImpl implements IBillPAPayableAgencyDao {

	private static final String NAMESPACE = "foss.stl.BillPayableEntityDao.";// 命名空间路径
	
	/**
	 *  根据传入获取一到多条应付单信息(分页)
	 * @author foss-guxinhua
	 * @date 2012-11-05 上午11:30:20
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryBillPayableEntityParams(
			BillPayableAgencyDto billPayableAgencyDto,int start,int limit) {
		if((billPayableAgencyDto != null) && (
				StringUtils.isNotEmpty(billPayableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billPayableAgencyDto.getIsRedBack()) 
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryPABillPayableEntityParams", billPayableAgencyDto,rowBounds);
		}
		return null;
	}
	
	/**
	 *  根据传入获取一到多条应付单信息
	 * @author foss-guxinhua
	 * @date 2012-11-05 上午11:30:20
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryBillPayableEntityParams(
			BillPayableAgencyDto billPayableAgencyDto) {
		if(billPayableAgencyDto != null && (
				StringUtils.isNotEmpty(billPayableAgencyDto.getActive()) 
				)) {
			return this.getSqlSession().selectList(NAMESPACE + "queryPABillPayableEntityParams", billPayableAgencyDto);
		}
		return null;
	}
	
	/**
	 *  根据传入获取一到多条应收单总数
	 * @author foss-guxinhua
	 * @date 2012-10-26 上午11:30:20
	 * @param billPayableDto
	 * @return
	 * @see
	 */
	public Long queryBillPayableEntityParamsCount(BillPayableAgencyDto billPayableDto){
		if(billPayableDto != null){
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryPABillPayableEntityParamsCount" , billPayableDto);
		}
		return null;
	}
	
	/**
	 * 根据传入的一到多个应收单号，获取一到多条应付单信息
	 * @author foss-guxinhua
	 * @date 2012-11-05 上午10:08:04
	 * @param billPayableAgencyDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IBillPayableAgencyDao#queryByPayableNOs(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryByPayableNOs(
			BillPayableAgencyDto billPayableAgencyDto,int start,int limit) {
		//满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (CollectionUtils.isNotEmpty(billPayableAgencyDto.getPayableNos()) ||
				StringUtils.isNotEmpty(billPayableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billPayableAgencyDto.getIsRedBack()
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryPAByPayableNOs", billPayableAgencyDto,rowBounds);
		}
		return null;
	}

	/**
     * 根据传入的一到多个应收单号，获取一到多条应收单总数
     * @author foss-guxinhua
     * @date @date 2012-10-26 下午4:13:09
     * @param BillPayableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    public Long queryByPayableNOsCount(
	    BillPayableAgencyDto billPayableDto){
    	if(billPayableDto != null){
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryPAByPayableNOsCount" , billPayableDto);
		}
		return null;
    }
    
	/**
	 * 根据传入的一到多个航空正单号，获取一到多条应付单信息(分页)      
	 * @author foss-guxinhua
	 * @date 2012-11-05 上午10:08:41
	 * @param billPayableAgencyDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IBillPayableAgencyDao#queryBySourceBillNOs(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBySourceBillNOs(
			BillPayableAgencyDto billPayableAgencyDto,int start,int limit) {
		if (billPayableAgencyDto != null && (
				CollectionUtils.isNotEmpty(billPayableAgencyDto.getSourceBillNos()) ||
				StringUtils.isNotEmpty(billPayableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billPayableAgencyDto.getIsRedBack()) 
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryPABySourceBillNOs", billPayableAgencyDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据传入的一到多个航空正单号，获取一到多条应付单信息
	 * @author foss-guxinhua
	 * @date 2013-1-7 上午10:35:08
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryBySourceBillNOs(
			BillPayableAgencyDto billPayableAgencyDto){
		return this.getSqlSession().selectList(NAMESPACE + "queryPABySourceBillNOs", billPayableAgencyDto);
	}

	/**
     * 根据传入的一到多个航空正单号，获取一到多条应收单总数   
     * * @author foss-guxinhua
     * @date 2012-10-26 下午4:14:51
     * @param billPayableDto
     * @return
     * @see
     */
    public Long queryBySourceBillNOsCount(BillPayableAgencyDto billPayableDto){
    	if(billPayableDto != null){
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryPABySourceBillNOsCount" , billPayableDto);
		}
		return null;
    }
}
