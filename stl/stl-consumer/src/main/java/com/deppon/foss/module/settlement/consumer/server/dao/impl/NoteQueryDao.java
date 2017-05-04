package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;

/**
 * 小票单据QueryDao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午9:17:26
 */
public class NoteQueryDao extends iBatis3DaoImpl implements INoteQueryDao {

	private static final String NAMESPACE = "foss.stl.noteQueryDao.";// 小票单据明细命名空间
	
	/**
	 * 查询票据详细信息列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoteQueryDto> queryNoteDetailsById(
			NoteQueryDto noteQueryDto, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String address = NAMESPACE+"queryNoteById";
		return getSqlSession().selectList(address, noteQueryDto, rowBounds);
	}
	
	/**
	 * 判断小票单号是否存在
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NoteQueryDto queryNoteApplyInfoByDetailNo(String detailNo,String applyOrgCode,String status) {
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtil.isNotBlank(detailNo)){
			map.put("noteDetailsNo", detailNo);
		}
		if(StringUtil.isNotBlank(applyOrgCode)){
			map.put("applyOrgCode", applyOrgCode);
		}
		if(StringUtil.isNotBlank(status)){
			map.put("status",status);
		}
		String address = NAMESPACE+"queryNoteApplyInfo";
		List<NoteQueryDto> noteQueryDtoLst=getSqlSession().selectList(address, map);
		if(CollectionUtils.isNotEmpty(noteQueryDtoLst)){
			return noteQueryDtoLst.get(0);
		}
		return null;
	}
	
	 /**
     * 校验上一部门输入的结束编号对应最早一个未使用的小票单号
     * @author 101911-foss-zhouChunlai
     * @date 2012-12-3 下午2:18:13
     */
	@Override
	public NoteQueryDto queryMixNoUseDetailsNo(String endNo,String applyOrgCode) {
		String address = NAMESPACE+"queryMixNoUseDetailsNo";
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtil.isNotBlank(endNo)){
			map.put("endNo", endNo);
		}
		if(StringUtil.isNotBlank(applyOrgCode)){
			map.put("applyOrgCode", applyOrgCode);
		}
		map.put("writeoffStatus", SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF);
		map.put("status", SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN);
		return (NoteQueryDto) getSqlSession().selectOne(address, map);
	}
	
	/**
	 * 查询票据详细信息总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	@Override
	public Long countQueryNoteDetailsById(NoteQueryDto noteQueryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"countNoteQueryById", noteQueryDto);
	}
	 
}
