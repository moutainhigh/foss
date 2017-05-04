/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/dao/impl/PrintLabelDao.java
 *  
 *  FILE NAME          :PrintLabelDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现打印标签信息的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:17:50
 */
public class PrintLabelDao extends iBatis3DaoImpl implements IPrintLabelDao {
	
	
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.exceptiongoods.printLabel.";
	
	
	/**
	 * 增加打印操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午5:17:51
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#addPrintLabel(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity)
	 */
	@Override
	public int addPrintLabel(PrintLabelEntity printLabelEntity) {
		
		printLabelEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert(NAME_SPACE + "insertPrintLabel", printLabelEntity);
		
		return FossConstants.SUCCESS;
	}

	/**
	* @description 查询货件打印标签信息(巴枪扫描打印的运单)
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午1:55:35
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintLabelDto> queryPrintLabelInfo(PrintLabelDto printLabelDto) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryPrintLabelInfo", printLabelDto);
	}
	
	
	/**
	* @description 查询货件打印标签信息(巴枪扫描打印的运单)
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午1:55:35
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintLabelDto> queryPrintLabelForExpress(
			PrintLabelDto printLabelDto, int start,int limit) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryPrintLabelInfoForExpress", printLabelDto, new RowBounds(start, limit));
	}
	


	/**
	* @description 查询货件打印标签信息(巴枪扫描打印的运单)
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午1:55:35
	*/
	@Override
	public Long queryPrintLabelForExpressCount(PrintLabelDto printLabelDto) {
		return (Long) this.getSqlSession().selectOne(NAME_SPACE + "queryPrintLabelInfoForExpressCount", printLabelDto);
	}


	/**
	 * 提供给综合查询：根据运单号查询货件标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 上午10:54:49
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryLabelPrintByWaybillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsLabelPrintDto> queryLabelPrintByWaybillNo(String waybillNo) {
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("isActive", FossConstants.YES);
		return this.getSqlSession().selectList(NAME_SPACE + "queryLabelPrintByWaybillNo", paramsMap);
	}

	/**
	* @param waybillNo
	* @return
	* @description  提供给综合查询：根据运单号查询货件标签打印信息 明细
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-6 上午10:17:50
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsLabelPrintDto> queryLabelPrintByWaybillNoDetail(
			String waybillNo) {
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("isActive", FossConstants.YES);
		return this.getSqlSession().selectList(NAME_SPACE + "queryLabelPrintByWaybillNoDetail", paramsMap);
	}

	
	/**
	* @description 根据部门code查询标签打印信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryPrintLabelForExpressByOrgCode(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午2:39:12
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override                  
	public List<SortingAndPringLabelDto> queryPrintLabelForExpressByOrgCode(
			PrintLabelDto printLabelDto, int start, int limit) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryPrintLabelForExpressByOrgCode", printLabelDto, new RowBounds(start, limit));
	}

	/**
	* @description 根据部门code查询标签打印count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryPrintLabelInfoExpressByOrgCodeCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午2:56:09
	* @version V1.0
	*/
	@Override
	public Long queryPrintLabelInfoExpressByOrgCodeCount(
			PrintLabelDto printLabelDto) {
		return (Long)this.getSqlSession().selectOne(NAME_SPACE + "queryPrintLabelInfoExpressByOrgCodeCount",printLabelDto);
	}
	
	/**
	* @description  根据部门code查询上分拣信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#querySortingInfoByOrgCode(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午5:37:39
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SortingAndPringLabelDto> querySortingInfoByOrgCode( 
			PrintLabelDto printLabelDto, int start, int limit) {
		return this.getSqlSession().selectList(NAME_SPACE + "querySortingInfoByOrgCode", printLabelDto, new RowBounds(start, limit));
	}

	
	/**
	* @description 根据部门code查询上分拣count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#querySortingInfoByOrgCodeCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午3:21:53
	* @version V1.0
	*/
	@Override
	public Long querySortingInfoByOrgCodeCount(PrintLabelDto printLabelDto) {
		return (Long)this.getSqlSession().selectOne(NAME_SPACE + "querySortingInfoByOrgCodeCount",printLabelDto);
	}

	
	/**
	* @description 根据ids查询需要导出的标签打印信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryExportStock(java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午3:03:22
	* @version V1.0
	*/ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SortingAndPringLabelDto> queryExportPringLabel(List<String> idsList) {
		return this.getSqlSession().selectList(NAME_SPACE+ "queryExportPringLabel", idsList);
	}

	
	/**
	* @description 根据ids查询需要导出的标签打印信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryExportSorting(java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午6:00:56
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SortingAndPringLabelDto> queryExportSorting(List<String> idsList) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryExportSorting" , idsList);
	}

	
	/**
	* @description 查询需要导出的标签打印信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryExportPringLabelAll(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月11日 上午9:21:27
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SortingAndPringLabelDto> queryExportPringLabelAll(
			SortingAndPringLabelDto sortingAndPringLabelDto) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryExportPringLabelAll" , sortingAndPringLabelDto);
	}

	
	/**
	* @description 查询需要导出的上分拣信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao#queryExportSortingAll(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月11日 上午9:21:39
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<SortingAndPringLabelDto> queryExportSortingAll(
			SortingAndPringLabelDto sortingAndPringLabelDto) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryExportSortingAll" , sortingAndPringLabelDto);
	}

	/**
	 * @author nly
	 * @date 2015年4月21日 下午5:10:06
	 * @function
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintLabelEntity> queryLabelPrintByNo(String waybillNo) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryLabelPrintByNo", waybillNo);
	}
}