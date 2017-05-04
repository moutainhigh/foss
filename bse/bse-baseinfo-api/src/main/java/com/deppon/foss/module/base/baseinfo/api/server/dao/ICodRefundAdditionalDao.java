package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity;

/**
 * (附件CodRefundAdditional的Dao层接口)
 * @author 187862-dujunhui
 * @date 2014-7-23 下午6:16:14
 * @since
 * @version v1.0
 */
public interface ICodRefundAdditionalDao {

	/**
	 * <p>(增加单客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:16:35
	 * @param entity
	 * @return
	 * @see
	 */
	int addCodRefundAdditional(CodRefundAdditionalEntity entity);

	/**
	 * <p>(删除单客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:16:42
	 * @param entity
	 * @return
	 * @see
	 */
	int deleteCodRefundAdditional(CodRefundAdditionalEntity entity);

	/**
	 * <p>(删除多客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:16:49
	 * @param ids
	 * @param modifyUser
	 * @return
	 * @see
	 */
	int deleteCodRefundAdditionals(String[] ids, String modifyUser);

	/**
	 * <p>(修改单客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:16:55
	 * @param entity
	 * @return
	 * @see
	 */
	int updateCodRefundAdditional(CodRefundAdditionalEntity entity);

	/**
	 * <p>(根据ID查询单客户附件)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:17:00
	 * @param id
	 * @return
	 * @see
	 */
	CodRefundAdditionalEntity queryCodRefundAdditionalById(String id);

	/**
	 * <p>(根据客户编码查询附件List)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:17:08
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<CodRefundAdditionalEntity> queryCodRefundAdditionalByCustomerCode(CodRefundAdditionalEntity entity,
			int start, int limit);

	/**
	 * <p>(根据客户编码查询附件个数)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-23 下午6:17:15
	 * @param entity
	 * @return
	 * @see
	 */
	Long queryCodRefundAdditionalCountByCustomerCode(CodRefundAdditionalEntity entity);

}
