package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;

/** 
 * @className: IComplementLogDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码日志dao接口
 * @date: 2013-7-19 下午2:30:26
 * 
 */
public interface IComplementDao {
	
	/**
	 * 新增一条补码日志实体
	 * @author 045923-foss-shiwei
	 * @date 2013-7-19 下午2:32:42
	 */
	int addComplementLog(ComplementLogEntity logEntity);
	
	/**
	 * 根据运单号查询补码记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-24 下午4:37:42
	 */
	List<ComplementLogEntity> queryComplementLogListByWaybillNo(String waybillNo);
	
	/**
	 * 查询补码
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:53:53
	 */
	List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto,int start,int limit);
	
	/**
	 * 查询补码总记录数
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:54:53
	 */
	Long queryComplementCount(ComplementQueryDto queryDto);
	/**
	 * 查询补码退回信息
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:53:53
	 */
	List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto,int start,int limit);
	
	/**
	 * 查询补码退回总记录数
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:54:53
	 */
	Long queryComplementBackCount(ComplementQueryDto queryDto);

	/**
	 * 查询补码日志
	 * @param complementLogDto
	 * @param start
	 * @param limit
	 * @return
	 * @date 2013-11-4 上午9:25:54
	 * @author Ouyang
	 */
	List<ComplementLogEntity> queryComplementLogList(
			ComplementLogDto complementLogDto, int start, int limit);
	
	/**
	* @description 根据运单号查询对应的目的地址
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月22日 下午4:12:29
	*/
	String queryComplementLogAddress(String waybillNo);
	
	/**
	* @description 根据运单号查询补码部门
	* @param waybillNo
	* @return
	* @author duhao
	* @update 2015年8月21日 下午2:14:21
	*/
	List<ComplementLogEntity> queryOperationOrg(String waybillNo);

	/**
	 * 查询补码日志记录数
	 * @param complementLogDto
	 * @return
	 * @date 2013-11-4 上午9:25:56
	 * @author Ouyang
	 */
	Long queryComplementLogCount(ComplementLogDto complementLogDto);


//    /**
//	 * @description 通过参数运单号查询出 所有子母件单号 
//	 * @param waybillNo
//	 * @return
//	 * @date 2015-09-10 上午9:13:56
//	 * @author hongwy
//	 */
//	List<String> queryWaybillNos(String waybillNo);

	//通过单号 查询出子母件验证  hongwy
	String queryWaybillNoCZM(String waybillNo);


	/**
	 * @desc 插入待异步补码信息
	 * @param info
	 * @date 2015年11月30日 下午7:23:24
	 */
	void insertAsyncComplement(AsyncComplementEntity info);
	
	/**
	 * @desc 查询待异步补码的运单
	 * @return
	 * @date 2015年11月30日 下午8:42:24
	 */
	List<AsyncComplementEntity> findAsyncComplement();
	
	/**
	 * @desc 从待异步补码表中删除已经补码完成的运单
	 * @param info
	 * @date 2015年11月30日 下午7:24:29
	 */
	int deleteAsyncComplement(AsyncComplementEntity info);
	
	/**
	 * @desc 修改异步补码失败的运单失败标识
	 * @param info
	 * @date 2015年11月30日 下午7:26:52
	 */
	void updateAsyncFailed(AsyncComplementEntity info);
	
	/**
	 * @desc 查询异步补码失败的运单
	 * @return
	 * @date 2015年11月30日 下午8:42:24
	 */
	List<AsyncComplementEntity> findAsyncComplementFailed(AsyncComplementFailedQcDto info, int start, int limit);
	
	/**
	 * @desc 查询异步补码失败运单数量
	 * @param info
	 * @return
	 * @date 2015年11月30日 下午9:09:03
	 */
	Long cntAsyncComplementFailed(AsyncComplementFailedQcDto info);
	
	/**
	 * @desc 删除异步补码失败又重新补码成功的运单
	 * @param info
	 * @date 2015年12月3日 上午10:50:38
	 */
	void deleteAsyncComplementFailed(AsyncComplementEntity info);
	
}
