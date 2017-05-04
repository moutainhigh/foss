package com.deppon.foss.module.transfer.management.api.server.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.BillListCheckVo;

/**
 * @author songjie
 *
 */
public interface IBillListCheckService extends IService {
	
	
	/**
	* @description 分页查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:13:50
	*/
	public List<BillListCheckDto> queryBillListCheck(BillListCheckVo billListCheckVo,int start,int limit);
	
	
	/**
	* @description 分页查询的总记录数
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:18:12
	*/
	public long queryBillListCheckCount(BillListCheckVo billListCheckVo);
	
	
	/**
	* @description  分页查询数据的统计
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:18:29
	*/
	public BillListCheckStaDto queryBillListCheckStatic(BillListCheckVo billListCheckVo);
	
	/**
	 * 导入Excle文件 
	 * @param uploadFile
	 * @param uploadFileName
	 * @return
	 * @throws BusinessException
	 */
	public boolean importFileComm(File uploadFile,String uploadFileName,BillListCheckVo vo) throws BusinessException;
	
	
	/**
	* @description 修改对账单
	* @param vo
	* @throws BusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 下午2:42:38
	*/
	public void updateBillListCheckDto(BillListCheckDto dto) throws BusinessException;
	
	
	/**
	* @description 添加对账单
	* @param dto
	* @throws BusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 下午2:49:08
	*/
	public void addBillListCheckDto(BillListCheckDto dto) throws BusinessException;
	
	
	/**
	* @description 封装当前部门的属性
	* @param orgAdministrativeInfoEntity
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:38:24
	*/
	public BillListCheckVo queryCurrentInfo(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,BillListCheckVo billListCheckVo);
	
	
	/**
	* @description 导出Excel
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月29日 下午3:37:28
	*/
	public InputStream exportExcelStream(BillListCheckVo billListCheckVo);
	
	/**
	 * 下载模板
	 * @param uploadFile
	 * @param uploadFileName
	 * @return
	 * @throws BusinessException
	 */
	public InputStream downFileModel() throws BusinessException;
	
}
