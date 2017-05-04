/**
 * @author foss 257200
 * 2015-8-15
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintCZMTipsDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintCZMTipsService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsHisEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外发打印提示标签service类
 * @author 257220
 * @date 2015-08-15 上午10:25:36
 */
public class PrintCZMTipsService implements IPrintCZMTipsService {
	private static final Logger LOGGER=LoggerFactory.getLogger(PrintCZMTipsService.class);
	
	private IPrintCZMTipsDao printCZMTipsDao;
	
	/**
	 * <ul>保存打印记录 
	 *  <li>如果未曾打印，则做新增，同时新增打印历史</li>
	 *  <li>如果已经存在打印，则做更新，同时新增打印历史</li>
	 * </ul>
	 * @param printTipsEntity 打印提示标签实体
	 * @author 257220
	 * @date 2015-8-21下午2:49:36
	 */
	public void savePrintRecord(PrintCZMTipsEntity printTipsEntity){
		//无打印实体，返回
		if(printTipsEntity == null){
			return;
		}
		//先判断是否打印过
		String isPrint = printTipsEntity.getIsPrint();
		if(StringUtils.equals(isPrint, FossConstants.ACTIVE)){ //已经打印
			LOGGER.info("更新运单号："+printTipsEntity.getWaybillNo()+"，流水号"+printTipsEntity.getSerialNo()+"的打印记录");
			printCZMTipsDao.updateCZMTips(printTipsEntity);//已打印做更新操作
		}else{
			String wayBillNo = printTipsEntity.getWaybillNo();
			String serialNo = printTipsEntity.getSerialNo();
			String queryCZMTipsIsPrint = printCZMTipsDao.queryCZMTipsIsPrint(wayBillNo,serialNo);
			if(!StringUtils.equals(queryCZMTipsIsPrint, FossConstants.ACTIVE)){ //未曾打印新增操作
				printTipsEntity.setId(UUIDUtils.getUUID());
				printTipsEntity.setIsPrint(FossConstants.ACTIVE);
				printTipsEntity.setInputUserCode(printTipsEntity.getPrintUserCode());
				printTipsEntity.setInputOrgcode(printTipsEntity.getPrintOrgCode());
				LOGGER.info("新增运单号："+printTipsEntity.getWaybillNo()+"，流水号"+printTipsEntity.getSerialNo()+"的打印记录");
				printCZMTipsDao.addCZMTips(printTipsEntity);
			}else{ //做更新
				LOGGER.info("更新运单号："+printTipsEntity.getWaybillNo()+"，流水号"+printTipsEntity.getSerialNo()+"的打印记录");
				printCZMTipsDao.updateCZMTips(printTipsEntity);//已打印做更新操作
			}
		}
		//插入历史记录
		printCZMTipsDao.addCZMTipsHis(this.buildTipsHisEntityByTips(printTipsEntity));
	}
	
	/**
	 * <p>通过打印提示实体构建打印提示历史</p>
	 * @param printTipsEntity
	 * @author 257220
	 * @date 2015-8-22上午11:27:20
	 */
	private PrintCZMTipsHisEntity  buildTipsHisEntityByTips(PrintCZMTipsEntity printTipsEntity){
		PrintCZMTipsHisEntity printTipsHisEntity = new PrintCZMTipsHisEntity();
		printTipsHisEntity.setId(UUIDUtils.getUUID());
		printTipsHisEntity.setPrintTipsId(printTipsEntity.getId());
		printTipsHisEntity.setWaybillNo(printTipsEntity.getWaybillNo());
		printTipsHisEntity.setSerialNo(printTipsEntity.getSerialNo());
		printTipsHisEntity.setAgentWaybillNo(printTipsEntity.getAgentWaybillNo());
		printTipsHisEntity.setParentWaybillNo(printTipsEntity.getParentWaybillNo());
		printTipsHisEntity.setGoodsName(printTipsEntity.getGoodsName());
		printTipsHisEntity.setIsPrint(printTipsEntity.getIsPrint());
		printTipsHisEntity.setPrintTime(printTipsEntity.getPrintTime());
		printTipsHisEntity.setPrintUserCode(printTipsEntity.getPrintUserCode());
		printTipsHisEntity.setPrintUserName(printTipsEntity.getPrintUserName());
		printTipsHisEntity.setPrintOrgCode(printTipsEntity.getPrintOrgCode());
		printTipsHisEntity.setPrintOrgName(printTipsEntity.getPrintOrgName());
		return printTipsHisEntity;
	}

	/**
	 * <p>根据页面传来的查询条件查询打印提示标签</p>
	 * @param printTipsDto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-8-26下午3:29:45
	 */
	public List<PrintCZMTipsDto> queryCZMTipsList(PrintCZMTipsDto printTipsDto, int start, int limit) {
		List<PrintCZMTipsDto> list = printCZMTipsDao.queryCZMTipsList(printTipsDto,start,limit);
		return list;
	}
	/**
	 *@return
	 *@date  2015-11-14下午6:13:25
	 *@author 257220
	 */
	public Long queryCZMTipsListCount(PrintCZMTipsDto printTipsDto){
		return printCZMTipsDao.queryCZMTipsListCount(printTipsDto);
	}
	public void setPrintCZMTipsDao(IPrintCZMTipsDao printCZMTipsDao) {
		this.printCZMTipsDao = printCZMTipsDao;
	}
}
