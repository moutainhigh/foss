package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;

/** 
 * @className: IExpressPackageService
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包dao接口
 * @date: 2013-7-17 下午4:50:44
 * 
 */
public interface IExpressPackageDao {

	/**
	 * 包查询
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午5:16:48
	 */
	List<ExpressPackageEntity> queryExpressPackageList(ExpressPackageQueryDto entity,int start,int limit);
	
	/**
	 * 查询包总数量，用于分页
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午5:16:58
	 */
	Long queryExpressPackageCount(ExpressPackageQueryDto entity);
	
	/**
	 * 更新包状态
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 上午8:47:05
	 */
	int updateExpressPackageState(String packageNo,String targetState);
	
	/**
	 * 根据包号获取包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午2:47:55
	 */
	ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo);
	
	
	/**
	 * 根据包号跟新目的站
	 * @author 105869
	 * @2014年7月7日 18:13:26
	 * */
	int updatePackageInfo(ExpressPackageEntity updateEntity);
	
	/**
	 * 新增包界面  根据运单号快速添加运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-20
	 * @param queyWaybillForExpressPackageDto
	 * @return
	 */
	List<ExpressPackageWayBillDetailDto> queryWaybillStockByWaybillNo(QueryWaybillForExpressPackageConditionDto queyWaybillForExpressPackageDto);
	
	/**
	 * 根据运单号获取流水号库存信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-21
	 * @param querySerialNoListForWaybillConditionDto
	 * @return
	 */
	List<ExpressPackageSerialNoStockDto> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 根据出发部门和到达部门查询线路条数
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	long queryExpressSourceLineByOrgCount(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto);
	
	/**
	 * 分页查询库存运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressPackageWayBillDetailDto> queryWaybillStockListForPackage(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto,int start,int limit);
	
	/**
	 * 查询库存运单总数
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	Long queryWaybillStockListForPackageCount(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto);
	
	/**
	 * 新增快递包明细
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param goods
	 */
	void insertExpressPackageDetail(ExpressPackageDetailEntity goods);
	
	/**
	 * 新增快递包
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param expressPackage
	 */
	void insertExpressPackage(ExpressPackageEntity expressPackage);
	
	/**
	 * 根据运单号列表查询包明细
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-23
	 * @param waybillNoList
	 * @return
	 */
	List<ExpressPackageDetailEntity> queryPackageDetailByWaybills(List<String> waybillNoList);
	
	
}
