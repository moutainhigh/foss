/**   
* @Title: UploadFileDao.java 
* @Package com.deppon.pda.bdm.module.ocb.server.dao.impl 
* @Description: 
* @author 183272
* @date 2014年10月13日 下午5:20:46 
* @version V1.0   
*/
package com.deppon.pda.bdm.module.ocb.server.dao.impl;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.ocb.server.dao.IUploadImageDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.UploadImageSnippetBean;

/** 
 * @ClassName: UploadFileDao 
 * @Description: 
 * @author 183272
 * @date 2014年10月13日 下午5:20:46 
 *  
 */
public class UploadImageDao extends iBatis3DaoImpl implements IUploadImageDao {

	/** 
	* @Title: saveUploadFile 
	* @Description: 新增碎片文件
	* @author 183272
	* @date 2014年10月13日 下午8:17:26 
	* @param @param uploadFile    设定文件 
	* @throws 
	*/
	@Override
	public void saveUploadFile(UploadImageSnippetBean uploadImageSnippet) {
		UploadImageSnippetBean u = getUploadFileByUUID(uploadImageSnippet.getUuid());
		if(u!=null){
			System.out.println("UploadImageSnippetBean对象为:"+u);
		}else{
			getSqlSession().insert(getClass().getName()+".saveUploadImageSnippet", uploadImageSnippet);
		}
		
	}

	/** 
	* @Title: getUploadFileByUUID 
	* @Description: 通过UUID获取上传文件
	* @author 183272
	* @date 2014年10月24日 上午11:14:10 
	* @param @param uuid    设定文件 
	* @throws 
	*/
	@Override
	public UploadImageSnippetBean getUploadFileByUUID(String uuid) {
		return (UploadImageSnippetBean) getSqlSession().selectOne(getClass().getName()+".getUploadFileByUUID", uuid);
	}

	/** 
	* @Title: saveWaybillPicture 
	* @Description: 
	* @author 183272
	* @date 2014年10月27日 下午4:20:42 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void saveWaybillPicture(WaybillPicturePdaDto waybillPicturePdaDto) {
		WaybillPicturePdaDto u = getWaybillPicturePdaByID(waybillPicturePdaDto.getId());
		if(u!=null){
			System.out.println("WaybillPicturePdaDto对象为:"+u);
		}else{
			getSqlSession().insert(getClass().getName()+".saveWaybillPicture", waybillPicturePdaDto);
		}
	}

	/** 
	* @Title: getWaybillPicturePdaByID 
	* @Description: 
	* @author 183272
	* @date 2014年11月14日 下午4:09:29 
	* @param @param id
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public WaybillPicturePdaDto getWaybillPicturePdaByID(String id) {
		// TODO Auto-generated method stub
		return (WaybillPicturePdaDto) getSqlSession().selectOne(getClass().getName()+".getWaybillPicturePdaByID", id);
	}

	@Override
	public String getUploadFileByWblcode(String wblcode) {
		return  (String) getSqlSession().selectOne(getClass().getName()+".getUploadFileByWblcode", wblcode);
		
	}

	/**
	 * 
	 * <p>TODO(撤销运单的时候删除碎片表：pda.t_app_uploadfilesnippet中数据)</p> 
	 * @author 245960
	 * @date 2015-8-6 下午7:17:04
	 * @param wblcode
	 * @see
	 */
	@Override
	public void deleteWaybillDateInTable(String wblcode) {
		getSqlSession().delete(getClass().getName()+".deleteWaybillDateInTable", wblcode);
	}

	/**
	 * 保存撤销运单添加时间日志
	 * @author 245955
	 * @date 2016-7-7
	 */
	@Override
	public void saveWaybillPictureTimeLog(WaybillPictureLogEntity entity) {
		getSqlSession().insert(getClass().getName()+".saveWaybillPictureTimeLog", entity);
	}
	
	/** 
	* @Title: saveWaybillPictureByOld
	* @Description: 
	* @author 218371
	* @date 2016年9月30日 下午4:20:42 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void saveWaybillPictureByOld(WaybillPicturePdaDto waybillPicturePdaDto) {
		WaybillPicturePdaDto u = getWaybillPicturePdaByID(waybillPicturePdaDto.getId());
		if(u!=null){
			System.out.println("WaybillPicturePdaDto对象为:"+u);
		}else{
			getSqlSession().insert(getClass().getName()+".saveWaybillPictureByOld", waybillPicturePdaDto);
		}
	}
}
