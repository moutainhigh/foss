/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: ISortingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;

/**
 * 分拣接口
 * @author dp-duyi
 * @update zwd 200968 2015-07-15
 * @date 2013-7-26 上午11:11:08
 */
public interface IPDASortingService {
	
	
	/**分拣扫描*/
	public List<SortingScanDto> scan(SortingScanDto record);
	/**分拣包扫描*/
	public List<SortingScanDto> scanPackage(SortingScanDto record);
}
