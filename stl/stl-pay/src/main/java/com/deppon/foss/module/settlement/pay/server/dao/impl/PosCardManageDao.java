package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPosCardManageDao;

/**
 * POS刷卡管理Dao
 * 
 * @ClassName: PosCardManageDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-14 下午1:33:54
 */
public class PosCardManageDao extends iBatis3DaoImpl implements
		IPosCardManageDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.stl.PosCardManageDao.";

	/**
	 * 查询POS实体集合
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-14 下午1:33:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardEntity> queryPosCardEntitys(PosCardManageDto dto,
			int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<PosCardEntity> posCardEntitys = this.getSqlSession().selectList(
				NAMESPACE + "queryPosCardEntitys", dto, rb);
		return posCardEntitys;
	}

	/**
	 * 根据日期去查询总行数
	 * 
	 * @Title: queryPosCardEntitys
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int getCountByDate(PosCardManageDto dto) {
		Integer result = (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "getCountByDate", dto);
		return result;
	}

	/**
	 * 根据交易流水号去查询明细
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-14 下午1:33:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardDetailEntity> queryPosCardDetail(PosCardManageDto dto) {
		List<PosCardDetailEntity> posCardDetailEntitys = this.getSqlSession()
				.selectList(NAMESPACE + "queryPosCardDetail", dto);
		return posCardDetailEntitys;
	}

	/**
	 * 根据交易流水号去查询POS实体集合
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-14 下午1:33:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardEntity> queryPosCardBySerialNos(List<String> list) {
		List<PosCardEntity> posCardEntitys = this.getSqlSession().selectList(
				NAMESPACE + "queryPosCardBySerialNos", list);
		return posCardEntitys;
	}

	/**
	 * 更具交易流水号导出数据
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-14 下午1:33:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardEntity> queryExportData(List<String> list) {
		List<PosCardEntity> posCardEntitys = this.getSqlSession().selectList(
				NAMESPACE + "queryExportData", list);
		return posCardEntitys;
	}

	/**
	 * 更具单据号去查询POS刷卡实体集合
	 * 
	 * @Title: queryPosCardByNumbers
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PosCardEntity> queryPosCardByNumbers(List<String> list,String empCode) {
		Map map = new HashMap();
		map.put("empCode", empCode);
		map.put("list", list);
		List<PosCardEntity> posCardEntitys = this.getSqlSession().selectList(
				NAMESPACE + "queryPosCardByNumbers", map);
		return posCardEntitys;
	}
	
	
	/**
	 * POS机刷卡管理--按流水号,员工工号查询流水信息
	 *
	 * @Title: queryPosCardBySerialNos
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-7 下午12:40:37
	 */
	@SuppressWarnings("unchecked")
	public List<PosCardEntity> queryPosCardBySerialNosAndEmpCode(List<String> list,
			String empCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("empCode", empCode);
		map.put("list", list);
		List<PosCardEntity> PosCardEntitys = this.getSqlSession().selectList(
				NAMESPACE + "queryPosCardBySerialNosAndEmpCode", map);
		return PosCardEntitys;
	}
}
