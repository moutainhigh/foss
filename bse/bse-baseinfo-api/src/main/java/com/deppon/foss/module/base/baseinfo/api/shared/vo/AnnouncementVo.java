package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AnnouncementDto;
/**
 * 系统公告，用于前台数据交互的Vo
 * @author zengjunfan
 * @date	2013-4-18 下午1:46:30
 *
 */
public class AnnouncementVo implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	 
	/**
	 * 系统公告Dto（用于查询条件）
	 */
	private	AnnouncementDto announcementDto; 
	/**
	 * 公告信息集合
	 */
	private	List<AnnouncementEntity> announcementEntitys ;
	/**
	 * 公告id的集合
	 */
	private List<String> idlist;
	
	public List<String> getIdlist() {
		return idlist;
	}
	/**
	 * idList的set方法
	 * @param idlist
	 */
	public void setIdlist(List<String> idlist) {
		this.idlist = idlist;
	}
	/**
	 * 公告dto的get方法
	 * @return
	 */
	public AnnouncementDto getAnnouncementDto() {
		return announcementDto;
	}
	/**
	 *公告dto的set方法
	 * @param announcementDto
	 */
	public void setAnnouncementDto(AnnouncementDto announcementDto) {
		this.announcementDto = announcementDto;
	}
	/**
	 * 公告实体集合的get方法
	 * @return
	 */
	public List<AnnouncementEntity> getAnnouncementEntitys() {
		return announcementEntitys;
	}
	
	/**
	 * 公告实体集合的set方法
	 * @return
	 */
	public void setAnnouncementEntitys(List<AnnouncementEntity> announcementEntitys) {
		this.announcementEntitys = announcementEntitys;
	}
	
}
