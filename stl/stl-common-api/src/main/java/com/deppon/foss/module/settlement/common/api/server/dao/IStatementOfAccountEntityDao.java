package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;

/**
 * 对账单公共DAO接口
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 上午11:41:48
 */
public interface IStatementOfAccountEntityDao {

	/**
	 * 保存对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:41:48
	 * @param entity
	 *            对账单
	 * @return
	 */
	int add(StatementOfAccountEntity entity);

	/**
	 * 根据ID查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午5:23:45
	 * @param id
	 * @return
	 */
	StatementOfAccountEntity queryByPrimaryKey(String id);
	
	/**
	 * 根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-8 下午2:23:39
	 * @param list
	 *            对账单号集合
	 * @param orgCode
	 *            当前操作网点编码
	 * @return
	 */
    List<StatementOfAccountEntity> queryCurrentStatementNos(
			List<String> list, String orgCode);

	/**
	 * 根据对账单号查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午9:12:01
	 * @param list
	 *            对账单号集合
	 * @param confirmStatus
	 *            确认状态
	 * @return
	 */
	List<StatementOfAccountEntity> queryByStatementNos(List<String> list,
			String confirmStatus);

	/**
	 * 根据对账单单号查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午10:26:47
	 * @param statementNo
	 *            对账单号
	 * @return
	 */
	StatementOfAccountEntity queryByStatementNo(String statementNo);

	/**
	 * 新增对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 * @param entity
	 *            对账单
	 * @return
	 */
	int updateStatementForAddDetail(StatementOfAccountEntity entity);

	/**
	 * 删除对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 * @param entity
	 *            对账单
	 * @return
	 */
	int updateStatementForDeleteDetail(StatementOfAccountEntity entity);

	/**
	 * 确认、反确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午5:21:47
	 * @param entity
	 *            对账单
	 * @return
	 */
	int confirmOrUnConfirmStatement(StatementOfAccountEntity entity);

	/**
	 * 当对账单明细在对账单之外参与核销、反核销、红冲操作时，更新对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午8:45:03
	 * @param entity
	 *            对账单
	 * @return
	 */
	int updateStatementForChangeDetail(StatementOfAccountEntity entity);

	/**
	 * 根据制作日期查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午5:14:33
	 * @param updateDto
	 *            账单明细信息DTO
	 * @return
	 */
	List<StatementOfAccountEntity> queryStatementByCreateDate(
			StatementOfAccountUpdateDto updateDto, int start, int limit);

	/**
	 * 对账单核销时修改对账单的本期未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-15 上午11:53:57
	 * @param entity
	 *            对账单
	 * @return
	 */
	int updateStatementForWriteoff(StatementOfAccountEntity entity);

	/**
	 * 
	 * 反核销时，为了释放对账单中的应收单，更新对账单金额
	 * 
	 * @author foss-wangxuemin
	 * @date Apr 1, 2013 4:55:55 PM
	 */
	int updateStatementForReverseWriteoffRelease(StatementOfAccountEntity entity);

	/**
	 * 根据对账单号和对账单版本号查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-6 下午7:05:19
	 * @param statementNo
	 *            对账单号
	 * @param versionNo
	 *            版本号
	 * @return
	 */
	StatementOfAccountEntity queryByStatementNoAndVersion(String statementNo,
			short versionNo);

	StatementOfAccountQueryResultDto countStatementByCreateDate(
			StatementOfAccountUpdateDto updateDto);
}
