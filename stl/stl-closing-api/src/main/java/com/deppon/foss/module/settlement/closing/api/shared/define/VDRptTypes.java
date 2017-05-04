package com.deppon.foss.module.settlement.closing.api.shared.define;

/**
 * 凭证报表类型
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-4-1 下午6:14:20
 */
public abstract class VDRptTypes {

    /**
     * 始发月报
     */
    public static final String TYPE_RFO = "RFO";

    /**
     * 专线到达
     */
    public static final String TYPE_RFD = "RFD";

    /**
     * 始发到达往来
     */
    public static final String TYPE_RFI = "RFI";

    /**
     * 偏线月报
     */
    public static final String TYPE_PLR = "PLR";

    /**
     * 始发偏线往来
     */
    public static final String TYPE_PLI = "PLI";

    /**
     * 空运月报
     */
    public static final String TYPE_AFR = "AFR";

    /**
     * 始发空运往来
     */
    public static final String TYPE_AFI = "AFI";

    /**
     * 快递代理月报表
     */
    public static final String TYPE_LDD = "LDD";

    /**
     * 快递代理往来
     */
    public static final String TYPE_LDI = "LDI";

    /**
     * 营业部核销月报表
     */
    public static final String TYPE_LWO = "LWO";


    /**
     * 01普通业务始发月报表明细
     */
    public static final String NRFONO = "NRFONO";

    /**
     * 01特殊业务始发月报表明细
     */
    public static final String NRFOSO = "NRFOSO";


    /**
     * 02普通业务始发月报表明细
     */
    public static final String NRFONT = "NRFONT";

    /**
     * 02特殊业务始发月报表明细
     */
    public static final String NRFOST = "NRFOST";
    /**
     * 01到达月报表明细
     */
    public static final String NRFDO = "NRFDO";
    /**
     * 02到达月报表明细
     */
    public static final String NRFDT = "NRFDT";

    /**
     * 02到达月报表明细（特殊）
     */
    public static final String SNRFDT = "SNRFDT";
    /**
     * 偏线月报表明细
     */
    public static final String NPLR = "NPLR";
    /**
     * 空运月报表明细
     */
    public static final String NAFR = "NAFR";
    /**
     * 始发专线到达往来月报表明细
     */
    public static final String NRFI = "NRFI";
    /**
     * 始发专线到达往来月报表(特殊)明细
     */
    public static final String SNRFI = "SNRFI";
    /**
     * 始发偏线往来月报表明细
     */
    public static final String NPLI = "NPLI";
    /**
     * 始发空运往来月报表明细
     */
    public static final String NAFI = "NAFI";
    /**
     * 始发外请车月报表明细
     */
    public static final String ORCC = "ORCC";
    /**
     * 始发往来月报表明细
     */
    public static final String ORCCI = "ORCCI";

    /**
     * 代打木架
     */
    public static final String WOODEN = "WOODEN";

    /**
     * 统一结算
     */
    public static final String NUSI = "NUSI";
    /**
     * 快递始发折扣
     */
    public static final String DCO = "DCO";
    /**
     * 快递到达折扣
     */
    public static final String DCD = "DCD";
    /**
     * 快递往来折扣
     */
    public static final String DCI = "DCI";
    /**
     * 家装
     */
    public static final String HI = "HI";
    /**
     * 合伙人德启代付日明细
     */
    public static final String PTP_DQPA = "PTP_DQPA";
    /**
     * 合伙人股份中转日明细
     */
    public static final String PTP_ST = "PTP_ST";
    /**
     * 合伙人子公司日明细
     */
    public static final String PTP_PSC = "PTP_PSC";
    /**
     * 合伙人奖罚月日明细
     */
    public static final String PTP_RP = "PTP_RP";
    /**
     * 合伙人奖罚特殊日明细
     */
    public static final String PTP_RPS = "PTP_RPS";
}