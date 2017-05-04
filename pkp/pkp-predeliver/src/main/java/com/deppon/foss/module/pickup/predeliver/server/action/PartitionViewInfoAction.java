package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionViewInfoService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionViewInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo;

/** 
 * @ClassName: PartitionViewInfoAction 
 * @Description: 分区查看Action 
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-19 上午10:41:46 
 *  
 */
public class PartitionViewInfoAction  extends AbstractAction{

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分区查看Service
	 */
	private IPartitionViewInfoService partitionViewInfoService;
	
	/**
	 * 分区查看信息集合
	 */
	private List<PartitionViewInfoDto> partitionViewInfos;
	
	/**
	 * 分区查看Vo
	 */
	private PartitionViewInfoVo partitionViewInfoVo;
	
	/**
	 * @Title: queryPartitionViewInfoByBigZone
	 * @Description: 根据大区分区
	 * @return
	 */
	public String queryPartitionViewInfoByBigZone() {
		
		try {
			totalCount = partitionViewInfoService.selectPartitionByBigZoneCount(partitionViewInfoVo);
			
			if (totalCount != null && totalCount > 0L) {
				partitionViewInfos = partitionViewInfoService.selectPartitionByBigZone(partitionViewInfoVo,super.getStart(), super.getLimit());
			} else {
				partitionViewInfos = null;
			}
			
			return returnSuccess();
		} catch (BusinessException e) {
			// 返回error
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * @Title: queryPartitionViewInfoBySmallZone
	 * @Description: 根据小区分区
	 * @return
	 */
	public String queryPartitionViewInfoBySmallZone() {
		try {
			partitionViewInfos = partitionViewInfoService.selectPartitionBySmallZone(partitionViewInfoVo);
			return returnSuccess();
		} catch (Exception e) {
			// 返回error
			return returnError(e.getMessage());
		}
	}
	

	/**
	 * 设置partitionViewInfoService
	 * @param partitionViewInfoService 要设置的partitionViewInfoService
	 */
	@Resource
	public void setPartitionViewInfoService(IPartitionViewInfoService partitionViewInfoService) {
		this.partitionViewInfoService = partitionViewInfoService;
	}

	/**
	 * 获取partitionViewInfos
	 * @return the partitionViewInfos
	 */
	public List<PartitionViewInfoDto> getPartitionViewInfos() {
		return partitionViewInfos;
	}

	/**
	 * 设置partitionViewInfos
	 * @param partitionViewInfos 要设置的partitionViewInfos
	 */
	public void setPartitionViewInfos(List<PartitionViewInfoDto> partitionViewInfos) {
		this.partitionViewInfos = partitionViewInfos;
	}

	/**
	 * 获取partitionViewInfoVo
	 * @return the partitionViewInfoVo
	 */
	public PartitionViewInfoVo getPartitionViewInfoVo() {
		return partitionViewInfoVo;
	}

	/**
	 * 设置partitionViewInfoVo
	 * @param partitionViewInfoVo 要设置的partitionViewInfoVo
	 */
	public void setPartitionViewInfoVo(PartitionViewInfoVo partitionViewInfoVo) {
		this.partitionViewInfoVo = partitionViewInfoVo;
	}
	
	
	

}
