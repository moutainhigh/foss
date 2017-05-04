package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDownLoadExcelFileResourcesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.DownloadInfoEntityVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * Excel文件下载 action
 * 
 * @author WangPeng
 * @date 2013-06-21 8:41AM
 * @since
 * 
 */
public class DownLoadExcelFileResourcesAction extends AbstractAction {

	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文件下载的服务
	 */
	private IDownLoadExcelFileResourcesService downLoadExcelFileResourcesService;

	public void setDownLoadExcelFileResourcesService(
			IDownLoadExcelFileResourcesService downLoadExcelFileResourcesService) {
		this.downLoadExcelFileResourcesService = downLoadExcelFileResourcesService;
	}

	private DownloadInfoEntityVo downloadInfoEntityVo;

	public DownloadInfoEntityVo getDownloadInfoEntityVo() {
		return downloadInfoEntityVo;
	}

	public void setDownloadInfoEntityVo(
			DownloadInfoEntityVo downloadInfoEntityVo) {
		this.downloadInfoEntityVo = downloadInfoEntityVo;
	}

	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DownLoadExcelFileResourcesAction.class.getName());

	/**
	 * 根据条件查询需要下载的文件资源
	 * 
	 * @author:  WangPeng
	 * @date:    2013-6-25 上午9:52:43
	 * @return:  String
	 */
	@JSON
	public String queryNeedDownLoadExcelFileInfo() {
		if(null == downloadInfoEntityVo){
			return returnError("员工工号和文件名称为空");
		}
		DownloadInfoEntity downLoadInfo = downloadInfoEntityVo
				.getDownloadInfoEntity();
     /* 
		if(null == downloadInfoEntityVo){
			return returnError("员工工号和文件名称为空");
		}
		*/
		// 校验前台传过来的员工工号和文件名称是否为空
		if (null == downloadInfoEntityVo.getDownloadInfoEntity().getEmpCode()
				|| StringUtils.isBlank(downloadInfoEntityVo
						.getDownloadInfoEntity().getEmpCode())){
			if (null == downloadInfoEntityVo.getDownloadInfoEntity()
					.getFileName()
					|| StringUtils.isBlank(downloadInfoEntityVo
							.getDownloadInfoEntity().getFileName())) {
				return returnError("员工工号和文件名称为空");
			} else {
				// 按文件名称查询时，员工工号要默认为当前登录人的
				downLoadInfo.setEmpCode(FossUserContext.getCurrentInfo()
						.getEmpCode().trim());
				downLoadInfo.setFileName(downloadInfoEntityVo
						.getDownloadInfoEntity().getFileName().trim());
				try {
					downloadInfoEntityVo
							.setDownloadInfoEntityList(downLoadExcelFileResourcesService
									.queryDownLoadableResource(downLoadInfo));
					// 获取返回的行数
					totalCount = (long) downLoadExcelFileResourcesService
							.queryDownLoadableResource(downLoadInfo).size();
				} catch (BusinessException e) {
					logger.error("查询文件下载信息失败", e);
					return returnError("查询文件下载信息失败");
				}
				return returnSuccess();
			}
		} else {
			// 按员工工号查询时，文件名称可以为空
			downLoadInfo.setEmpCode(downloadInfoEntityVo
					.getDownloadInfoEntity().getEmpCode().trim());
			downLoadInfo.setFileName(downloadInfoEntityVo
					.getDownloadInfoEntity().getFileName().trim());
			try {
				downloadInfoEntityVo
						.setDownloadInfoEntityList(downLoadExcelFileResourcesService
								.queryDownLoadableResource(downLoadInfo));
			} catch (BusinessException e) {
				logger.error("查询文件下载信息失败", e);
				return returnError("查询文件下载信息失败");
			}
			return returnSuccess();
		}
	}

	/**
	 * 删除选中的记录
	 * 
	 * @author:  WangPeng
	 * @date:    2013-6-25 上午9:55:06
	 * @return:  String
	 */
	@JSON
	public String deleteSomeRecords() {
		// 校验VO实体是否为空
		if (null == downloadInfoEntityVo) {
			return returnError("参数为空，不能执行删除操作！");
		}

		// 根据上下文获得前台传递的参数：id
		List<DownloadInfoEntity> downLoadList = downloadInfoEntityVo
				.getDownloadInfoEntityList();
		// 获取list的大小
		int size = downLoadList.size();
		if (size <= 0) {
			return returnError("参数为空，不能执行删除操作！");
		}
		int i = 0;
		// 定义一个存在删除记录id的数组
		String[] info = new String[size];
		for (DownloadInfoEntity downloadInfo : downLoadList) {
			info[i] = downloadInfo.getId().trim();
			i++;
			if(i > size-1){
				break;
			}
		}
		// 根据上下文获得前台传递的参数：id
		try {
			downLoadExcelFileResourcesService.deleteRecordById(info);
		} catch (BusinessException e) {
			logger.error("删除失败", e);
			return returnError("删除失败！");
		}
		return returnSuccess("删除成功");
	}

	/**
	 * 文件下载（此方法已经作废）
	 * @author:  WangPeng
	 * @date:    2013-6-25 上午9:56:31
	 * @return:  String
	 */
	@JSON
	public String downLoadFileInfo() {
		// 校验VO实体是否为空
		if (null == downloadInfoEntityVo) {
			return returnError("参数为空，不能执行下载操作！");
		}

		// 根据上下文获得前台传递的参数：id
		List<DownloadInfoEntity> downLoadList = downloadInfoEntityVo
				.getDownloadInfoEntityList();
		// 获取list的大小
		int size = downLoadList.size();
		if (size <= 0) {
			return returnError("参数为空，不能执行删除操作！");
		}
		int i = 0;
		// 定义一个存在删除记录id的数组
		String[] info = new String[size];
		for (DownloadInfoEntity downloadInfo : downLoadList) {
			info[i] = downloadInfo.getFileLoadPath().trim();
			i++;
			if(i > size-1){
				break;
			}
		}
		// 文件存在在本地的路径
		String filePath = "d:\\";
		try {
			downLoadExcelFileResourcesService.downLoadExcelFile(info, filePath);
		} catch (BusinessException e) {
			logger.error("下载失败", e);
			return returnError("下载失败！");
		}
		return returnSuccess();
	}
}
