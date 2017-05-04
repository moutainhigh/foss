package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;

/**
 * @className: IComplementService
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码service接口
 * @date: 2013-7-23 下午1:49:54
 * 
 */
public interface IComplementService extends IService {

	/**
	 * 查询补码
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:53:53
	 */
	List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto,
			int start, int limit);
	
	/**
	 * 查询补码退回信息
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:53:53
	 */
	List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto, int start, int limit);
	/**
	 * 查询补码总记录数
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:54:53
	 */
	Long queryComplementCount(ComplementQueryDto queryDto);
	
	/**
	 * 查询补码退回总记录数
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:54:53
	 */
	Long queryComplementBackCount(ComplementQueryDto queryDto);
	
	/**
	 * 补码
	 * @author 045923-foss-shiwei
	 * @date 2013-7-24 下午4:05:21
	 */
	int complementPkpOrg (String waybillNo, String pkpOrgCode, String pkpOrgName,String beAutoComplement, String matchType)throws Exception;

	/**
	 * 根据用户获取所属转运场
	 * @param empCode
	 * @return
	 */
	String queryTransferCenterCode();

	
	/**
	 * 查询补码日志
	 * @param complementLogDto
	 * @param start
	 * @param limit
	 * @return
	 * @date 2013-11-4 上午9:11:57
	 * @author Ouyang
	 */
	List<ComplementLogEntity> queryComplementLogList(
			ComplementLogDto complementLogDto, int start, int limit);
	
	/**
	 * 查询补码日志记录条数
	 * @param complementLogDto
	 * @return
	 * @date 2013-11-4 上午9:15:37
	 * @author Ouyang
	 */
	Long queryComplementLogCount(ComplementLogDto complementLogDto);

	/**
	 * 补码标签打印
	 * @param waybillNo
	 * @date 2013-11-26 上午11:28:41
	 * @author Ouyang
	 * @return 
	 */
	Map<String, String> printComplementLabel(String waybillNo);
	/**
	 * @author nly
	 * @date 2015年4月21日 下午4:49:34
	 * @function 根据运单号查询最晚补码日志
	 * @param waybillNo
	 * @return
	 */
	ComplementLogEntity queryLastComplementLog(String waybillNo);
	
	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年12月3日 下午3:33:33
	 */
	OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode);
	
	/**
	 * @desc 手动补码
	 * @param waybillNo
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @date 2015年11月30日 下午6:38:16
	 */
	void complementByHand(String waybillNo, String pkpOrgCode, String pkpOrgName);
	
	/**
	 * @desc 异步补码
	 * @date 2015年12月4日 下午5:20:29
	 */
	void complementAsync(AsyncComplementEntity info);
	
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
	 * @desc 对异步补码失败的运单进行补码
	 * @param waybillNo
	 * @param pkpOrgCode
	 * @param pkpOrgName
	 * @date 2015年12月1日 上午11:22:13
	 */
	void complement4Failed(String waybillNo, String pkpOrgCode, String pkpOrgName);

	List<AsyncComplementEntity> findAsyncComplement();

	void createComplementLog(AsyncComplementEntity info);

	int deleteAsyncComplement(AsyncComplementEntity info);

	void updateAsyncFailed(AsyncComplementEntity info);

	/**
	 * 查询补码列表前设置查询条件 335284
	 * @since 2016年9月9日 15:07:43
	 * @param queryDto
	 * @return 条件dto
	 */
	ComplementQueryDto configComplementQueryInfo(ComplementQueryDto queryDto);
	/**
	 * 查询补码退回列表前设置查询条件 335284
	 * @since 2016年9月9日 15:07:43
	 * @param queryDto
	 * @return 条件dto
	 */
	ComplementQueryDto configComplementBackQueryInfo(ComplementQueryDto queryDto);
	
}
