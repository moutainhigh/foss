/**
 * @author foss 257200
 * 2015-8-15
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;

/**
 * @author 257220
 *
 */
public interface IPrintCZMTipsService {
	void savePrintRecord(PrintCZMTipsEntity printTipsEntity);
	
	public List<PrintCZMTipsDto> queryCZMTipsList(PrintCZMTipsDto printTipsDto, int start, int limit) ;

	public Long queryCZMTipsListCount(PrintCZMTipsDto printTipsDto);
}
