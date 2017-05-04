package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAnnouncementValueService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AnnouncementDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AnnouncementVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 公告的action
 * 
 * @author zengjunfan
 * @date 2013-4-18下午1:48:08
 * 
 */
public class AnnouncementAction extends AbstractAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnnouncementAction.class);
	/**
	 * 用于前后台的vo
	 */
	public AnnouncementVo vo;
	/**
	 * 公告信息的service 接口
	 */
	public IAnnouncementValueService announcementValueService;

	public AnnouncementVo getVo() {
		return vo;
	}

	public void setVo(AnnouncementVo vo) {
		this.vo = vo;
	}

	/**
	 * 根据条件进行查询符合的所有数据
	 * 
	 * @author zengjunfan
	 * @date 2013-4-18 下午4:18:33
	 * @return
	 */
	@JSON
	public String queryAnnouncement() {
		try {
			// 获取总的公告信息
			List<AnnouncementEntity> entitys = announcementValueService
					.queryAnnouncementEntity(vo.getAnnouncementDto(),
							this.limit, this.start);
			// 设置返回前台的信息
			vo.setAnnouncementEntitys(entitys);
			// 获取总的记录数
			long totalCount = announcementValueService.queryRecordCount(vo
					.getAnnouncementDto());
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 添加新的公告信息
	 * 
	 * @author zengjunfan
	 * @date 2013-4-18 下午4:40:56
	 * @return
	 */
	@JSON
	public String addAnnouncement() {
		try {
			AnnouncementEntity entity = vo.getAnnouncementDto().getAnnouncementEntity();
			// 获取当前的用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户
			entity.setCreateUser(currentInfo.getEmpCode());
			entity.setModifyUser(currentInfo.getEmpCode());

			// 添加公告信息
			announcementValueService.addAnnouncement(entity);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			returnError(e);
		}
		return returnSuccess(MessageType.SAVE_SUCCESS);
	}

	/**
	 * 查询符合条件的公告通过id
	 * 
	 * @author zengjunfan
	 * @date 2013-4-19 下午5:50:45
	 * @return
	 */
	@JSON
	public String queryAnnouncementById() {
		try {
			AnnouncementEntity entity = announcementValueService
					.queryAnnouncementEntityById(vo.getAnnouncementDto().getAnnouncementEntity()
							.getId());
			//新建dto 
			AnnouncementDto dto =new AnnouncementDto();
			//设置 entity
			dto.setAnnouncementEntity(entity);
			//设置dto
			vo.setAnnouncementDto(dto);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 修改公告信息
	 * 
	 * @author zengjunfan
	 * @date 2013-4-18 下午4:44:35
	 * @return
	 */
	@JSON
	public String updateAnnouncement() {
		try {
			// 获取传过来的数据
			AnnouncementEntity entity = vo.getAnnouncementDto().getAnnouncementEntity();
			// 获取当前用户
			CurrentInfo user = FossUserContext.getCurrentInfo();
			// 设置修改的公告信息用户
			entity.setModifyUser(user.getEmpCode());

			announcementValueService.upadteAnnouncement(entity);

			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 作废信息
	 * 
	 * @author zengjunfan
	 * @date 2013-4-18 下午5:02:41
	 * @return
	 */
	@JSON
	public String deleteAnnouncement() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 废除
			announcementValueService.deleteAnnouncementById(vo.getIdlist(),
					currentInfo.getEmpCode());

			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * this is set method
	 * 
	 * @author zengjunfan
	 * @date 2013-4-19 下午4:18:53
	 * @param announcementValueService
	 */
	public void setAnnouncementValueService(
			IAnnouncementValueService announcementValueService) {
		this.announcementValueService = announcementValueService;
	}

}
