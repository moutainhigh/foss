package com.deppon.foss.module.transfer.load.server.action;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.ILFDrivingFileService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.LFDrivingFileVo;
import com.deppon.foss.util.CollectionUtils;
/**
 * 
* @ClassName: LFDrivingFileAction
* @Description: 长途车辆行驶档案 Action
* @author ZX_189284
* @date 2016-5-25 上午10:53:34
*
 */
public class LFDrivingFileAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private LFDrivingFileVo lfDrivingFileVo;
	private ILFDrivingFileService lfDrivingFileService;
	/**
	 * @return lfDrivingFileVo
	 */
	public LFDrivingFileVo getLfDrivingFileVo() {
		return lfDrivingFileVo;
	}
	/**
	 * @param lfDrivingFileVo 要设置的 lfDrivingFileVo
	 */
	public void setLfDrivingFileVo(LFDrivingFileVo lfDrivingFileVo) {
		this.lfDrivingFileVo = lfDrivingFileVo;
	}
	/**
	 * @param lfDrivingFileService 要设置的 lfDrivingFileService
	 */
	public void setLfDrivingFileService(ILFDrivingFileService lfDrivingFileService) {
		this.lfDrivingFileService = lfDrivingFileService;
	}
	@JSON
	public String querylfDrivingFileList(){
		try {
			LFDrivingFileEntity lfDrivingFileEntity =lfDrivingFileVo.getLfDrivingFile();
			
			//获取库存运单的条数，分页
			Long totalCount = lfDrivingFileService.selectLFDrivingFileCount(lfDrivingFileEntity);
			//获取库存运单的条数，分页
			this.setTotalCount(totalCount);
			List<LFDrivingFileEntity> lfDrivingFiles =lfDrivingFileService.selectLFDrivingFile(lfDrivingFileEntity, this.getStart(), this.getLimit());
			lfDrivingFileVo.setLfDrivingFiles(lfDrivingFiles);
		  return	returnSuccess();
		} catch (BusinessException e) {
		  return returnError(e.getMessage());
		}
	}
	@JSON
	public String saveLFDrivingFile(){
		try {
			if (null == lfDrivingFileVo) {
				throw new TfrBusinessException("长途车行驶档案查询条件为空！");
			}
			String drivingNo=lfDrivingFileService.saveLFDrivingFile
			(lfDrivingFileVo.getLfDrivingFile(), lfDrivingFileVo.getLfDrivingFileDetails());
		  return	returnSuccess(drivingNo);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: updateLFDrivingFile
	* @Description: 修改行驶 档案（包括配载信息）
	* @author ZX_189284
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String updateLFDrivingFile(){
		try{
			String drivingNo=lfDrivingFileService.updateLFDrivingFile(lfDrivingFileVo);
			 return	returnSuccess(drivingNo);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: qureyVehicleassembleInfo
	* @Description: 根据配载单号查询 配载信息
	* @author ZX_189284
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String qureyVehicleassembleInfo(){
		try {
			 List<LFDrivingFileDetailEntity> lfDrivingFileDetails=lfDrivingFileService.qureyOtherBillInfo(lfDrivingFileVo.getVehicleassembleNo());
			 if(CollectionUtils.isNotEmpty(lfDrivingFileDetails)&&lfDrivingFileDetails.size()>0){
				 for (LFDrivingFileDetailEntity lfDrivingFileDetailEntity : lfDrivingFileDetails) {
					if(StringUtils.equals(lfDrivingFileVo.getVehicleassembleNo(), lfDrivingFileDetailEntity.getVehicleassembleNo())){
						lfDrivingFileVo.setLfDrivingFileDetailEntity(lfDrivingFileDetailEntity);
					}
				}
				 lfDrivingFileVo.setLfDrivingFileDetails(lfDrivingFileDetails); 
			 }
			  return	returnSuccess();
			} catch (BusinessException e) {
			  return returnError(e.getMessage());
		    }
	}
	@JSON
	public String queryLatestDrivingNo(){
		try {
			String drivingNo=lfDrivingFileService.getDrivingNo(lfDrivingFileVo.getOrgIdCode());
			lfDrivingFileVo.setDrivingNo(drivingNo);
			 return	returnSuccess();
		} catch (BusinessException e) {
		  return returnError(e.getMessage());
	    }
		
	}
	/**
	 * 
	* @Title: getlfDrivingFileVehicle
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ZX_189284
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String lfDrivingFileVehicle(){
		try {
			VehicleAssociationDto vehicleAssociationDto=lfDrivingFileService.lfDrivingFileVehicle(lfDrivingFileVo.getVehicleNo());
			lfDrivingFileVo.setVehicleAssociationDto(vehicleAssociationDto);
			 return	returnSuccess();
		} catch (BusinessException e) {
		  return returnError(e.getMessage());
	    }
	}
	public String queryLfDrivingFile(){
		try {
			LFDrivingFileEntity  lfDrivingFileEntity=lfDrivingFileService.queryLfDrivingFile(lfDrivingFileVo.getDrivingNo());
			lfDrivingFileVo.setLfDrivingFile(lfDrivingFileEntity);
			return	returnSuccess();
		} catch (BusinessException e) {
			 return returnError(e.getMessage());
		}
	}
	/***
	 * 
	* @Title: queryOrgIdCode
	* @Description: 根据登陆人部门信息查询所属车队
	* @author ZX_189284
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@JSON
	public String queryOrgIdCode(){
		try {
			LFDrivingFileEntity  lfDrivingFileEntity=lfDrivingFileService.queryOrgIdCode(lfDrivingFileVo.getOrgIdCode());
			lfDrivingFileVo.setLfDrivingFile(lfDrivingFileEntity);
			return	returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
}
