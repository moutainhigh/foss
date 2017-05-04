package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSaveConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;

/** 
 * @className: IExpressPackageService
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包Service接口
 * @date: 2013-7-17 下午5:18:57
 * 
 */
public interface IExpressPackageService extends IService {
	
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
	 * @date 2013-7-23 上午9:15:27
	 */
	int updateExpressPackageState(String packageNo,String targetState);
	
	/**
	 * PC界面，批量撤销包
	 * @author 045923-foss-shiwei
	 * @date 2013-7-25 下午2:07:00
	 */
	int cancelExpressPackage(String packageNo);
	
	/**
	 * 根据包号获取包信息
	 * @param packageNo
	 * @return
	 */
	ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo);
	
	/**
	 * 修改包目的站
	 * @author 105869
	 * @2014年7月7日 11:40:10
	 * @param packageNo
	 * @param destOrgName
	 * @param destOrgCode 
	 * @return int
	 */
	int modifyPackageInfo(String packageNo, String destOrgName, String destOrgCode);
	
	/**
	 * 包新增界面，快速添加通过运单号获取库存运单信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-21
	 * @param queyWaybillForExpressPackageDto
	 * @return
	 */
	Map<String,Object> queryWaybillStockByWaybillNo(QueryWaybillForExpressPackageConditionDto queyWaybillForExpressPackageDto);
	
	/**
	 *  传入运单号、库存部门，获取流水号list，用于查询包交接运单界面
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-21
	 * @param querySerialNoListForWaybillConditionDto
	 * @return
	 */
	List<ExpressPackageSerialNoStockDto> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto);
	
	/**
	 * 根据出发部门和到达部门查询是否存在线路
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	boolean queryExpressLineIsExist(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto);
	
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
	 * 新增快递包（包括新增明细）
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param expressPackageSaveConditionDto
	 * @return
	 */
	boolean saveExpressPackage(ExpressPackageSaveConditionDto expressPackageSaveConditionDto);
	
	/**
	 * 查询新增包号是否存在
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param packageNo
	 * @return
	 */
	boolean queryIsExistPackageNo(String packageNo);
	
	/**
	 * 根据传入的库存运单，查询哪些运单中的流水号已经建包，并设置流水号中isCreatedPackage字段为Ｙ。用于快速添加快递运单和添加运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-23
	 * @param waybillStockList
	 * @return
	 */
	 void checkPackageWaybillIsCreatedPackage(List<ExpressPackageWayBillDetailDto> waybillStockList);

}
