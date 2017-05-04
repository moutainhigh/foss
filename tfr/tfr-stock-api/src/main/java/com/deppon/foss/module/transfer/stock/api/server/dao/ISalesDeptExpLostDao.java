package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostOaLogEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.SalesDeptExpLostWaybillSerialNosDto;

public interface ISalesDeptExpLostDao {

	/**
	 * @desc 清除库存快照
	 * @date 2014年10月24日 上午10:53:30
	 * @author Ouyang
	 */
	void truncateStock0amSnapshot();

	/**
	 * @desc 保存0点时的库存快照，目前只保留营业部的快递货
	 * @param date
	 *            统计日期
	 * @date 2014年10月24日 上午10:49:04
	 * @author Ouyang
	 */
	void saveStock0amSnapshot(Date date);

	/**
	 * @desc 获取0点库存快照的部门编码，按ora_hash(org_code,threadCount) = threadNo分组
	 * @param map
	 *            threadCount 线程数-1；如启动5个job，则threadCount为4
	 *            threadNo线程号；如启动5个job，则threadNo分别为0、1、2、3、4
	 * @return
	 * @date 2014年10月25日 下午4:41:37
	 * @author Ouyang
	 */
	List<String> queryStockOrgCodes(Map<String, Integer> map);

	/**
	 * @desc 将快递派送差异报告转移至历史表
	 * @param orgCode部门编码
	 * @date 2014年10月25日 下午4:33:15
	 * @author Ouyang
	 */
	void insertSalesDeptExpLost2His(String orgCode);

	/**
	 * @desc 删除快递派送差异报告转移至历史表
	 * @param orgCode部门编码
	 * @date 2014年10月25日 下午4:33:58
	 * @author Ouyang
	 */
	void deleteSalesDeptExpLost(String orgCode);

	/**
	 * @desc 插入24H差异报告
	 * @param map
	 * @return 插入的记录条数
	 * @date 2014年10月25日 下午8:20:57
	 * @author Ouyang
	 */
	int insertReport24H(Map<String, Object> map);

	/**
	 * @desc 插入48H或者72H差异报告
	 * @param map
	 * @return
	 * @date 2014年10月25日 下午8:42:00
	 * @author Ouyang
	 */
	int updateStatus48or72H(Map<String, Object> map);

	/**
	 * @desc 查询快递派送差异报告
	 * @param info
	 * @return
	 * @date 2014年10月25日 下午8:52:25
	 * @author Ouyang
	 */
	List<SalesDeptExpLostEntity> queryReport(SalesDeptExpLostEntity info);

	/**
	 * @desc 系统自动上报丢货后，修改差异报告丢货上报丢货状态
	 * @param info
	 * @date 2014年10月25日 下午9:05:36
	 * @author Ouyang
	 */
	void update72hReproted(SalesDeptExpLostEntity info);

	/**
	 * @desc 查询快递派送差异报告
	 * @param info
	 * @return
	 * @date 2014年10月25日 下午8:52:25
	 * @author Ouyang
	 */
	List<SalesDeptExpLostEntity> queryReportWaybillNo(
			SalesDeptExpLostEntity info);

	/**
	 * @desc 分页查询快递派送差异报告
	 * @param info
	 * @return
	 * @date 2014年10月25日 下午8:52:25
	 * @author Ouyang
	 */
	List<SalesDeptExpLostEntity> queryReportWaybillNoPaging(
			SalesDeptExpLostEntity info, RowBounds rowBounds);
	
	/**
	 * @desc 查询报告数量
	 * @param info
	 * @return
	 * @date 2014年11月19日 上午9:59:03
	 * @author Ouyang
	 */
	Long queryReportWaybillNoCnt(SalesDeptExpLostEntity info);

	/**
	 * @desc 查询对应运单的流水号
	 * @param info
	 * @return
	 * @date 2014年10月27日 上午10:37:43
	 * @author Ouyang
	 */
	List<SalesDeptExpLostWaybillSerialNosDto> queryReportSerialNo(
			SalesDeptExpLostEntity info);

	/**
	 * @desc 新增上报oa丢货日志
	 * @param oaLog
	 * @date 2014年10月29日 上午10:29:42
	 * @author Ouyang
	 */
	void insertReportOaLog(SalesDeptExpLostOaLogEntity oaLog);
}
