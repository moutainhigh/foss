package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
/**
 * DOP传送数据处理Dao
 * @author foss-zhuliangzhi
 * @date 2015-10-18 下午6:16:31
 * @since
 * @version
 */
public interface IDopSignDao {

	/**
	 * 
	 * 根据运单号查询DOP传来的信息（整车）
	 * @author foss-zhuliangzhi
	 * @date 2015-10-18 下午6:16:31
	 * @param dto 
	 * @return
	 */
	List<DopSignEntity> queryDopListByWaybillNo(SignDto dto);
	/**
	 * 
	 * 根据运单号查询签收日志表的信息（整车）
	 * @author foss-zhuliangzhi
	 * @date 2015-10-18 下午6:16:31
	 * @param dto 
	 * @return
	 */
	List<DopSignEntity> queryDopSignListByWaybillNo(SignDto dto);
	/**
	 * * 根据运单号查询实际承运表里的信息
	 * @author foss-zhuliangzhi
	 * @date 2015-10-18 下午6:16:31
	 * @param signdto 
	 * @return
	 *//*
	List<DopDto> queryActualFreightListByWaybillDOP(SignDto signDto);
*/
	/**
	 * 将DOP传来的运单信息暂存到本地T_SRV_DOPCACHE表中
	 * @param dto
	 * @date 2015-10-18 下午6:16:31
	 * @author 269871 foss-zhuliangzhi
	 */
	int insertDopCacheWaybillInfo(DopSignEntity dopSignEntity);

	/**
	 * 将DOP签收信息经过FOSS系统签收后保存到本地签收日志表T_SRV_DOPSIGN表中
	 * @param dto
	 * @date 2015-10-18 下午6:16:31
	 * @author 269871 foss-zhuliangzhi
	 */
	int insertDopSign(DopSignEntity dopSignEntity);

	/*
	 * dop家装订单签收后，
	 * 在暂存表（t_srv_dopcache）中根据运单号删除对应记录保存到签收日志记录表（t_srv_dopsign）
	 */
	int deleteDopCache(String waybillNo);
	/**
	 * 查询家装反签收
	 * @param waybillNo
	 * @return
	 */
	long queryDopSignRfc(String waybillNo);
	/**
	 * 记录回传给DOP的签收信息
	 * @param waybillNo
	 */
	int updateByWaybillNo(DopSignEntity dopSignEntity);
	/**
	 * 更新反签收标记
	 * @param waybillNo
	 */
	int updateRfc(String waybillNo);

}
