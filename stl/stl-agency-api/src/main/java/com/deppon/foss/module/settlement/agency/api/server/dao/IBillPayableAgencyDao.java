package com.deppon.foss.module.settlement.agency.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 查询_审核_作废空运其它应收DAO
 * @author foss-pengzhen
 * @date 2012-10-26 下午4:16:50
 * @since
 * @version
 */
public interface IBillPayableAgencyDao{
	
	/**
	 *  根据传入获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billPayableDto
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryBillPayableEntityParams(BillPayableAgencyDto billPayableDto,int start,int limit);
	
	/**
	 *  根据传入获取一到多条应付单信息
	 * @author foss-pengzhen
	 * @date 2012-11-05 上午11:30:20
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryBillPayableEntityParams(
			BillPayableAgencyDto billPayableAgencyDto);
			
	/**
	 *  根据传入获取一到多条应收单总数
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billPayableDto
	 * @return
	 * @see
	 */
	Long queryBillPayableEntityParamsCount(BillPayableAgencyDto billPayableDto);
	
	/**
     * 根据传入的一到多个应收单号，获取一到多条应收单信息
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillPayableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    List<BillPayableEntity> queryByPayableNOs(
	    BillPayableAgencyDto billPayableDto,int start,int limit);
    
    /**
     * 根据传入的一到多个应收单号，获取一到多条应收单总数
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillPayableAgencyDto
     *            单号集合
     * @return
     * @see
     */
    Long queryByPayableNOsCount(
	    BillPayableAgencyDto billPayableDto);
    
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单信息   (分页)  
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billPayableDto
     * @return
     * @see
     */    
    List<BillPayableEntity> queryBySourceBillNOs(BillPayableAgencyDto billPayableDto,int start,int limit);
    
    /**
	 * 根据传入的一到多个航空正单号，获取一到多条应付单信息
	 * @author foss-pengzhen
	 * @date 2013-1-7 上午10:35:08
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryBySourceBillNOs(
			BillPayableAgencyDto billPayableAgencyDto);
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单总数   
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billPayableDto
     * @return
     * @see
     */
    Long queryBySourceBillNOsCount(BillPayableAgencyDto billPayableDto);	
    
    
}
