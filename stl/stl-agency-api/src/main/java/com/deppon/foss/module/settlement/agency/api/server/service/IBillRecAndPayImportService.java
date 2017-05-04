package com.deppon.foss.module.settlement.agency.api.server.service;

import java.io.File;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;

/**
 * 导入其他应收、应付
 * @author foss-pengzhen
 * @date 2012-11-13 下午7:33:24
 * @since
 * @version
 */
public interface IBillRecAndPayImportService extends IService {

	/**
	 * 导入Excel信息
	 * @author foss-pengzhen
	 * @date 2012-11-13 下午2:31:52
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception 
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IBillRecAndPayImportService#importRecAndPaylist(java.io.File, java.lang.String)
	 */
	BillRecAndPayImportDto importRecAndPaylist(File file, String fileName) throws Exception;
	
	/**
	 * 保存应收、应付信息
	 * @author foss-pengzhen
	 * @date 2012-11-13 下午3:06:33
	 * @return
	 * @see
	 */
	void saveAirImportPayAndRec(BillRecAndPayImportDto billRecAndPayImportDto,
			CurrentInfo currentInfo);
}
