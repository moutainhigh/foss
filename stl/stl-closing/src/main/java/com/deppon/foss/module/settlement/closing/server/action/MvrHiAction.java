package com.deppon.foss.module.settlement.closing.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrHiService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrHiDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.List;


/**
 * 家装月报表Action
 * @author 302307
 * @date 2015年12月16日9:44:04
 */
public class MvrHiAction extends AbstractAction {

	/** 序列号 */
	private static final long serialVersionUID = 1L;

	/** 日志 */
    private static final Logger LOG = LogManager.getLogger(MvrHiAction.class);

    /** Dto */
    private MvrHiDto mvrHiDto ;

    /** 家装月报表查询服务 */
    private IMvrHiService mvrHiService;

    public MvrHiDto getMvrHiDto() {
        return mvrHiDto;
    }

    public void setMvrHiDto(MvrHiDto mvrHiDto) {
        this.mvrHiDto = mvrHiDto;
    }

	public void setMvrHiService(IMvrHiService mvrHiService) {
		this.mvrHiService = mvrHiService;
	}

	/**
     * 查询方法
     *
     * @author 302307
     * @date 2015-12-17 下午2:07:53
     * @return
     * @throws Exception
     * @see org.apache.struts2.dispatcher.DefaultActionSupport#execute()
     */
    @Override
    @JSON
    public String execute() throws Exception {

        try {

            // 查询参数不能为空
            if (mvrHiDto == null) {
                return returnError("查询参数为空");
            }

            CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
            // 设置用户数据查询权限
            mvrHiDto.setUserCode(currentInfo.getEmpCode());
            // 查询汇总
            MvrHiDto dto = mvrHiService.queryMvrHiTotal(mvrHiDto);
            dto.setPeriod("合计");
            if (dto.getCount() > 0) {
                // 查询列表
                List<MvrHiEntity> queryList = mvrHiService.queryMvrHi(
                        mvrHiDto, getStart(), getLimit());

                // 附加统计信息
                MvrHiEntity total = new MvrHiEntity();
                BeanUtils.copyProperties(dto, total);
                queryList.add(total);

                // 设置查询结果列表
                dto.setQueryList(queryList);
            }

            // 将查询结果返回给前台
            this.setMvrHiDto(dto);

        } catch (BusinessException e) {

            // 记录日志并返回失败
            LOG.error(e);
            return returnError("查询家装月报表异常:" + e.getMessage());

        }

        // 返回成功
        return returnSuccess();
    }

}
