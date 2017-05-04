/**   
* @Title: UploadFileDao.java 
* @Package com.deppon.pda.bdm.module.ocb.server.dao 
* @Description: 
* @author 183272
* @date 2014年10月11日 下午4:41:26 
* @version V1.0   
*/
package com.deppon.pda.bdm.module.ocb.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.ocb.shared.domain.UploadImageSnippetBean;

/** 
 * @ClassName: UploadFileDao 
 * @Description: 
 * @author 183272
 * @date 2014年10月11日 下午4:41:26 
 *  
 */
public interface IUploadImageDao {
	/**
	 * 
	* @Title: saveUploadFile 
	* @Description: 保存碎片文件
	* @author 183272
	* @date 2014年10月27日 下午2:57:56 
	* @param @param uploadImageSnippet    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveUploadFile(UploadImageSnippetBean uploadImageSnippet) ;
	/**
	 * 
	* @Title: getUploadFileByUUID 
	* @Description: 通过UUID查询碎片文件
	* @author 183272
	* @date 2014年10月27日 下午2:58:00 
	* @param @param uuid
	* @param @return    设定文件 
	* @return UploadImageSnippetBean    返回类型 
	* @throws
	 */
	public UploadImageSnippetBean getUploadFileByUUID(String uuid) ;
	
	public WaybillPicturePdaDto getWaybillPicturePdaByID(String id);
	/**
	 * 
	* @Title: saveWaybillPicture 
	* @Description: 保存运单图片
	* @author 183272
	* @date 2014年10月27日 下午4:20:26 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveWaybillPicture(WaybillPicturePdaDto waybillPicturePdaDto);
	
	public void saveWaybillPictureByOld(WaybillPicturePdaDto waybillPicturePdaDto);
	
	public String getUploadFileByWblcode(String wblcode);
	
	/**
	 * 
	 * <p>TODO(撤销运单的时候删除碎片表：pda.t_app_uploadfilesnippet中数据)</p> 
	 * @author 245960
	 * @date 2015-8-6 下午7:17:04
	 * @param wblcode
	 * @see
	 */
	public void deleteWaybillDateInTable(String wblcode);
	
	/**
	 * 保存撤销运单添加时间日志
	 * @author 245955
	 * @date 2016-7-7
	 */
	public void saveWaybillPictureTimeLog(WaybillPictureLogEntity entity);
}
