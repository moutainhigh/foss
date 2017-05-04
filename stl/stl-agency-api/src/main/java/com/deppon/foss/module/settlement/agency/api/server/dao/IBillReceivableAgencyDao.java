package com.deppon.foss.module.settlement.agency.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 查询_审核_作废空运其它应收DAO
 * @author foss-pengzhen
 * @date 2012-10-26 下午4:16:50
 * @since
 * @version
 */
public interface IBillReceivableAgencyDao{
	
	/**
	 *  根据传入获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableDto
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryBillReceivableEntityParams(BillReceivableAgencyDto billReceivableDto,int start,int limit);
	
	/**
	 *  根据传入多条件参数获取一到多条应收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryBillReceivableEntityParams(
			BillReceivableAgencyDto billReceivableAgencyDto);
	
	/**
	 *  根据传入获取一到多条应收单总数
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableDto
	 * @return
	 * @see
	 */
	Long queryBillReceivableEntityParamsCount(BillReceivableAgencyDto billReceivableDto);
	
	/**
     * 根据传入的一到多个应收单号，获取一到多条应收单信息
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillReceivableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    List<BillReceivableEntity> queryByReceivableNOs(
	    BillReceivableAgencyDto billReceivableDto,int start,int limit);
    
    /**
     * 根据传入的一到多个应收单号，获取一到多条应收单总数
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillReceivableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    Long queryByReceivableNOsCount(
	    BillReceivableAgencyDto billReceivableDto);
    
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单信息 (分页)    
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billReceivableDto
     * @return
     * @see
     */
    List<BillReceivableEntity> queryBySourceBillNOs(BillReceivableAgencyDto billReceivableDto,int start,int limit);	
    
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单信息 
     * @author foss-pengzhen
     * @date 2013-1-7 上午10:28:18
     * @param billReceivableAgencyDto
     * @return
     * @see
     */
    List<BillReceivableEntity> queryBySourceBillNOs(
			BillReceivableAgencyDto billReceivableAgencyDto);
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单总数     
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billReceivableDto
     * @return
     * @see
     */
    Long queryBySourceBillNOsCount(BillReceivableAgencyDto billReceivableDto);	
}
