package com.deppon.foss.module.settlement.agency.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.agency.api.server.dao.IBillReceivableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;


/**
 * 查询_审核_作废空运其它应收
 * @author foss-pengzhen
 * @date 2012-10-26 上午11:32:56
 * @since
 * @version
 */
public class BillReceivableAgencyDao extends iBatis3DaoImpl implements IBillReceivableAgencyDao {

	private static final String NAMESPACE = "foss.stl.BillReceivableEntityDao.";// 命名空间路径
	
	/**
	 *  根据传入多条件参数获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryBillReceivableEntityParams(
			BillReceivableAgencyDto billReceivableAgencyDto,int start,int limit) {
		if (billReceivableAgencyDto != null && (
				StringUtils.isNotEmpty(billReceivableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getIsRedBack()) 
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryBillReceivableEntityParams", billReceivableAgencyDto,rowBounds);
		}
		return null;
	}
	
	/**
	 *  根据传入多条件参数获取一到多条应收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryBillReceivableEntityParams(
			BillReceivableAgencyDto billReceivableAgencyDto) {
		if (billReceivableAgencyDto != null && (
				StringUtils.isNotEmpty(billReceivableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getIsRedBack()) 
				)) {
			return this.getSqlSession().selectList(NAMESPACE + "queryBillReceivableEntityParams", billReceivableAgencyDto);
		}
		return null;
	}
	
	/**
	 *  根据传入多条件参数获取一到多条应收单总数
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableDto
	 * @return
	 * @see
	 */
	public Long queryBillReceivableEntityParamsCount(BillReceivableAgencyDto billReceivableDto){
		if(billReceivableDto != null){
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryBillReceivableEntityParamsCount",billReceivableDto);
		}
		return null;
	}
	
	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午10:08:04
	 * @param billReceivableAgencyDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IBillReceivableAgencyDao#queryByReceivableNOs(com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(
			BillReceivableAgencyDto billReceivableAgencyDto,int start,int limit) {
		//满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (billReceivableAgencyDto != null && (
				CollectionUtils.isNotEmpty(billReceivableAgencyDto.getReceivableNos()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getIsRedBack()) 
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryByReceivableNOs", billReceivableAgencyDto,rowBounds);
		}
		return null;
	}

	/**
     * 根据传入的一到多个应收单号，获取一到多条应收单总数
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillReceivableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    public Long queryByReceivableNOsCount(
	    BillReceivableAgencyDto billReceivableDto){
    	if(billReceivableDto != null){
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryByReceivableNOsCount",billReceivableDto);
		}
		return null;
    }
    
	/**
	 * 根据传入的一到多个航空正单号，获取一到多条应收单信息  (分页)    
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午10:08:41
	 * @param billReceivableAgencyDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IBillReceivableAgencyDao#queryBySourceBillNOs(com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(
			BillReceivableAgencyDto billReceivableAgencyDto,int start,int limit) {
		if (billReceivableAgencyDto != null && (
				CollectionUtils.isNotEmpty(billReceivableAgencyDto.getSourceBillNos()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getActive()) ||
				StringUtils.isNotEmpty(billReceivableAgencyDto.getIsRedBack()) 
				)) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryBySourceBillNOs", billReceivableAgencyDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据传入的一到多个航空正单号，获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2013-1-7 上午10:31:54
	 * @param billReceivableAgencyDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IBillReceivableAgencyDao#queryBySourceBillNOs(com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(
			BillReceivableAgencyDto billReceivableAgencyDto){
		return this.getSqlSession().selectList(NAMESPACE + "queryBySourceBillNOs", billReceivableAgencyDto);
	}
	
	/**
     * 根据传入的一到多个航空正单号，获取一到多条应收单总数     
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billReceivableDto
     * @return
     * @see
     */
    public Long queryBySourceBillNOsCount(BillReceivableAgencyDto billReceivableDto){
    	if(billReceivableDto != null){
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryBySourceBillNOsCount",billReceivableDto);
		}
		return null;
    }

}
