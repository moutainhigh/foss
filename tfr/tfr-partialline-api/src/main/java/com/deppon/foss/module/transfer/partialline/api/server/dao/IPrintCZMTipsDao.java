/**
 * @author foss 257200
 * 2015-8-21
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsHisEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;

/**
 * @author 257220
 *
 */
public interface IPrintCZMTipsDao {

	/**
	 * <p>新增打印提示标签</p>
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午3:16:40
	 */
	void addCZMTips(PrintCZMTipsEntity printTipsEntity);

	/**
	 * <p>更新打印提示标签</p>
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午3:16:57
	 */
	void updateCZMTips(PrintCZMTipsEntity printTipsEntity);

	/**
	 * <p>新增打印提示标签历史</p>
	 * @param printTipsHisEntity 打印提示标签历史实体
	 * @author 257220
	 * @date 2015-8-21下午3:17:04
	 */
	void addCZMTipsHis(PrintCZMTipsHisEntity printTipsHisEntity);

	/**
	 * <p>查询一件货是否打印</p>
	 * @param wayBillNo 运单号
	 * @param serialNo 运单流水号
	 * @return
	 * @author 257220
	 * @date 2015-8-22上午10:52:03
	 */
	String queryCZMTipsIsPrint(String wayBillNo,String serialNo);

	/**
	 * <>查询打印提示标签</p>
	 * @param printTipsDto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-8-26下午3:32:43
	 */
	List<PrintCZMTipsDto> queryCZMTipsList(PrintCZMTipsDto printTipsDto,int start, int limit);
	/**
	 *<p>根据条件查询数量</p>
	 *@param printTipsDto
	 *@return
	 *@date  2015-11-14下午6:15:34
	 *@author 257220
	 */
	Long queryCZMTipsListCount(PrintCZMTipsDto printTipsDto);
}
