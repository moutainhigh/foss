/**
 * @author foss 257200
 * 2015-8-21
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsHisEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;

/**
 * @author 257220
 *
 */
public class PrintCZMTipsDao extends iBatis3DaoImpl implements IPrintCZMTipsDao{
	/**
	 * 打印提示标签Mapper前缀  foss.partialline.printCZMTipsMapper.
	 */
	private static String prefix="foss.partialline.printCZMTipsMapper.";

	/**
	 * 新增打印记录
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午3:28:03
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao#addCZMTips(com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity)
	 */
	public void addCZMTips(PrintCZMTipsEntity printTipsEntity) {
		this.getSqlSession().insert(prefix + "addCZMTips", printTipsEntity);
	}

	/**
	 * 更新打印记录
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午3:30:31
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao#updateCZMTips(com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity)
	 */
	public void updateCZMTips(PrintCZMTipsEntity printTipsEntity) {
		this.getSqlSession().update(prefix + "updateCZMTips", printTipsEntity);
	}

	/**
	 * 跟据打印实体新增打印历史记录
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午3:31:31
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao#addCZMTipsHis(com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity)
	 */
	public void addCZMTipsHis(PrintCZMTipsHisEntity printTipsHisEntity) {
		this.getSqlSession().insert(prefix + "addCZMTipsHis", printTipsHisEntity);
	}

	/**
	 * @param wayBillNo
	 * @param serialNo
	 * @return
	 * @author 257220
	 * @date 2015-8-26下午3:34:15
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao#queryCZMTipsIsPrint(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String queryCZMTipsIsPrint(String waybillNo, String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		List<String> list = this.getSqlSession().selectList(prefix + "queryCZMTipsIsPrint",map);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @param printTipsDto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-8-26下午3:34:15
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao#queryCZMTipsList(com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto, int, int)
	 */
	@Override
	public List<PrintCZMTipsDto> queryCZMTipsList(
			PrintCZMTipsDto printTipsDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryCZMTipsList", printTipsDto, rowBounds);
	}
	/**
	 *根据条件查询数量
	 *@param printTipsDto
	 *@return
	 *@date  2015-11-14下午6:19:54
	 *@author 257220
	 */
	public Long queryCZMTipsListCount(PrintCZMTipsDto printTipsDto){
		return (Long)this.getSqlSession().selectOne(prefix + "queryCZMTipsListCount", printTipsDto);
	}
}
