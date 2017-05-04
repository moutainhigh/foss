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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/IPrintLabelDao.java
 * 
 *  FILE NAME          :IPrintLabelDao.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
/**
 * 定义了打印标签信息的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:04:09
 */
public interface IPrintLabelDao {
	
	/**
	 * 增加打印操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午5:16:17
	 */
	int addPrintLabel(PrintLabelEntity printLabelEntity);
	
	/**
	 * 查询货物标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:06:14
	 */
	List<PrintLabelDto> queryPrintLabelInfo(PrintLabelDto printLabelDto); 
	
	
	

	/**
	 * 提供给综合查询：根据运单号查询货件标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 上午10:48:41
	 */
	List<GoodsLabelPrintDto> queryLabelPrintByWaybillNo(String waybillNo);
	

	/**
	* @param waybillNo
	* @return
	* @description  提供给综合查询：根据运单号查询货件标签打印信息 明细
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-6 上午10:17:50
	*/
	List<GoodsLabelPrintDto> queryLabelPrintByWaybillNoDetail(String waybillNo);
	
	/**
	* @description 查询货件打印标签信息(巴枪扫描打印的运单)
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午3:13:43
	*/
	Long queryPrintLabelForExpressCount(PrintLabelDto printLabelDto);
	
	/**
	* @description 查询货件打印标签信息(巴枪扫描打印的运单)
	* @param printLabelDto
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午3:10:35
	*/
	List<PrintLabelDto> queryPrintLabelForExpress(PrintLabelDto printLabelDto,int start,int limit);
	/**
	 * @author nly
	 * @date 2015年4月21日 下午5:07:44
	 * @function 查询最晚标签打印记录
	 * @param waybillNo
	 * @return
	 */
	List<PrintLabelEntity> queryLabelPrintByNo(String waybillNo); 
	
	/**
	* @description 根据部门code查询标签打印信息
	* @param printLabelDto
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午2:38:30
	*/
	List<SortingAndPringLabelDto> queryPrintLabelForExpressByOrgCode(PrintLabelDto printLabelDto,int start,int limit);
	
	
	/**
	* @description 根据部门code查询标签打印count
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午2:55:49
	*/
	Long queryPrintLabelInfoExpressByOrgCodeCount(PrintLabelDto printLabelDto);
	/**
	* @description 根据部门code查询上分拣信息
	* @param printLabelDto
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午5:36:58
	*/
	List<SortingAndPringLabelDto> querySortingInfoByOrgCode(PrintLabelDto printLabelDto,int start,int limit);
	
	
	/**
	* @description 根据部门code查询上分拣count
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午3:21:31
	*/
	Long querySortingInfoByOrgCodeCount(PrintLabelDto printLabelDto);
	
	
	/**
	* @description 根据ids查询需要导出的标签打印信息
	* @param idsList
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午3:03:29
	*/
	List<SortingAndPringLabelDto> queryExportPringLabel(List<String> idsList);
	
	
	
	/**
	* @description 根据ids查询需要导出的上分拣信息
	* @param idsList
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午6:00:28
	*/
	List<SortingAndPringLabelDto> queryExportSorting(List<String> idsList);
	
	
	/**
	* @description 查询需要导出的标签打印信息
	* @param sortingAndPringLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月11日 上午9:15:51
	*/
	List<SortingAndPringLabelDto> queryExportPringLabelAll(SortingAndPringLabelDto sortingAndPringLabelDto);
	
	
	/**
	* @description 查询需要导出的上分拣信息
	* @param sortingAndPringLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月11日 上午9:16:42
	*/
	List<SortingAndPringLabelDto> queryExportSortingAll(SortingAndPringLabelDto sortingAndPringLabelDto);
}