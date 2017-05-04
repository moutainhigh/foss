package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;

/**
 * @className: IComplementOptimizedDao
 * @author: 335284
 * @description: 补码界面和退回界面DAO
 * @since 2016年9月7日 13:58:16
 * @description 用于优化补码查询的效率，新建的服务套件
 */
public interface IComplementOptimizedDao {
	// 1 定义dao接口
	// 2 定义dao实现类
	// 3 注入spring.xml
	// 4 ComplementService中增加属性
	// 5 dao对接mybatis
	// 6 定义mapper文件
	// 7 dto中增加字段属性

	/**
	 * 查询补码信息 - 分页
	 * @param queryDto 查询条件
	 * @param start 分页
	 * @param limit 分页
	 * @return 满足条件的补码列表
	 */
	List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto, int start, int limit);

	/**
	 * 根据运单号查询补码信息
	 * @param queryDto 查询条件
	 * @return 满足条件的补码实体
	 */
	List<ComplementQueryDto> queryComplementByWaybillno(ComplementQueryDto queryDto);

	/**
	 * 查询补码地址相关的信息
	 * @param info
	 * @return
	 */
	ComplementQueryDto queryComplementAddressInfo(ComplementQueryDto info);

	/**
	 * 查询补码总条数
	 * @param queryDto
	 * @return
	 */
	Long queryComplementCount(ComplementQueryDto queryDto);
	/**
	 * 根据运单号查询补码总条数
	 * @param queryDto
	 * @return
	 */
	Long queryComplementCountByWaybillno(ComplementQueryDto queryDto);

	List<ComplementQueryDto> queryComplementBackByWaybillno(ComplementQueryDto queryDto);

	List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto, int start, int limit);

	Long queryComplementBackCount(ComplementQueryDto queryDto);

	Long queryComplementBackCountByWaybillno(ComplementQueryDto queryDto);

}
