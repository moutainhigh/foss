CREATE OR REPLACE PACKAGE PKG_STL_VCH_COMMON IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 凭证通用方法
  -- ==============================================================

  RATIO_PRECISION NUMBER := 4; -- 精度

  ---========================== 凭证数据字典 ==================================
  /**
  * VOUCHER_BATCH_STATUS 凭证处理状态
  */
  VOUCHER_BATCH_STATUS_PROCESS VARCHAR2(20) := 'PROCESS'; -- 处理中
  VOUCHER_BATCH_STATUS_SUCCESS VARCHAR2(20) := 'SUCCESS'; -- 成功
  VOUCHER_BATCH_STATUS_FAILURE VARCHAR2(20) := 'FAILURE'; -- 失败

  /**
  * VOUCHER_ORG_TYPE 部门类型
  */
  VOUCHER_ORG_TYPE_ORIG VARCHAR2(20) := 'O'; -- 始发
  VOUCHER_ORG_TYPE_DEST VARCHAR2(20) := 'D'; -- 到达
  VOUCHER_ORG_TYPE_AIR  VARCHAR2(20) := 'A'; --空运
  VOUCHER_ORG_TYPE_PL   VARCHAR2(20) := 'P'; --偏线
  VOUCHER_ORG_TYPE_PKG  VARCHAR2(20) := 'G'; --落地配

  /**
  * VOUCHER_BUSINESS_TYPE 凭证业务类型
  */
  VOUCHER_BUSINESS_TYPE_DE      VARCHAR2(20) := 'DE'; -- 开单
  VOUCHER_BUSINESS_TYPE_UR_ORIG VARCHAR2(20) := 'UR_ORIG'; -- 签收还款
  VOUCHER_BUSINESS_TYPE_UR_DEST VARCHAR2(20) := 'UR_DEST'; -- 签收到付
  VOUCHER_BUSINESS_TYPE_COD     VARCHAR2(20) := 'COD'; -- 代收货款
  VOUCHER_BUSINESS_TYPE_OR      VARCHAR2(20) := 'OR'; -- 小票
  VOUCHER_BUSINESS_TYPE_CLAIM   VARCHAR2(20) := 'CLAIM'; -- 理赔
  VOUCHER_BUSINESS_TYPE_BA      VARCHAR2(20) := 'BA'; -- 坏账冲应收
  VOUCHER_BUSINESS_TYPE_AC      VARCHAR2(20) := 'AC'; -- 弃货丢货
  VOUCHER_BUSINESS_TYPE_RD      VARCHAR2(20) := 'RD'; -- 退运费
  VOUCHER_BUSINESS_TYPE_CN      VARCHAR2(20) := 'CN'; -- 服务补救
  VOUCHER_BUSINESS_TYPE_PL      VARCHAR2(20) := 'PL'; -- 偏线成本
  VOUCHER_BUSINESS_TYPE_AIR     VARCHAR2(20) := 'AIR'; -- 空运成本
  VOUCHER_BUSINESS_TYPE_DR      VARCHAR2(20) := 'DR'; -- 预收
  VOUCHER_BUSINESS_TYPE_ADP     VARCHAR2(20) := 'ADP'; -- 空运预付
  VOUCHER_BUSINESS_TYPE_OTH     VARCHAR2(20) := 'OT'; -- 其它应收应付

  VOUCHER_BUSINESS_TYPE_AFI  VARCHAR2(20) := 'AFI'; -- 空运往来
  VOUCHER_BUSINESS_TYPE_AFR  VARCHAR2(20) := 'AFR'; -- 空运月报
  VOUCHER_BUSINESS_TYPE_PLI  VARCHAR2(20) := 'PLI'; -- 偏线往来
  VOUCHER_BUSINESS_TYPE_PLR  VARCHAR2(20) := 'PLR'; -- 偏线月报
  VOUCHER_BUSINESS_TYPE_RFD  VARCHAR2(20) := 'RFD'; -- 专线到达
  VOUCHER_BUSINESS_TYPE_RFI  VARCHAR2(20) := 'RFI'; -- 始发到达往来
  VOUCHER_BUSINESS_TYPE_RFO  VARCHAR2(20) := 'RFO'; -- 始发月报
  VOUCHER_BUSINESS_TYPE_RCOD VARCHAR2(20) := 'RCOD'; -- 退代收货款

  ---========================== 凭证业务场景 ==================================
  DE_CH                          VARCHAR2(30) := 'DE_CH'; --开单运单_开单现金
  DE_CD                          VARCHAR2(30) := 'DE_CD'; --开单运单_开单银行卡
  UR_ORIG_CH_NPOD                VARCHAR2(30) := 'UR_ORIG_CH_NPOD'; --还款运单总运费（月结临时欠款网上支付）_还款现金未签收
  UR_ORIG_CD_NPOD                VARCHAR2(30) := 'UR_ORIG_CD_NPOD'; --还款运单总运费（月结临时欠款网上支付）_还款银行未签收
  UR_ORIG_CH_POD                 VARCHAR2(30) := 'UR_ORIG_CH_POD'; --还款运单总运费（月结临时欠款网上支付）_还款现金已签收
  UR_ORIG_CD_POD                 VARCHAR2(30) := 'UR_ORIG_CD_POD'; --还款运单总运费（月结临时欠款网上支付）_还款银行已签收
  UR_DEST_CH_NPOD                VARCHAR2(30) := 'UR_DEST_CH_NPOD'; --还款运单总运费（到付）_还款现金未签收
  UR_DEST_CD_NPOD                VARCHAR2(30) := 'UR_DEST_CD_NPOD'; --还款运单总运费（到付）_还款银行未签收
  UR_DEST_CH_POD                 VARCHAR2(30) := 'UR_DEST_CH_POD'; --还款运单总运费（到付）_还款现金已签收
  UR_DEST_CD_POD                 VARCHAR2(30) := 'UR_DEST_CD_POD'; --还款运单总运费（到付）_还款银行已签收
  POD_CASH_COLLECTED             VARCHAR2(30) := 'POD_CASH_COLLECTED'; --签收运单_签收时现付已收款金额
  POD_ORIG_RCV_WO                VARCHAR2(30) := 'POD_ORIG_RCV_WO'; --签收运单_签收时始发应收已核销金额
  POD_ORIG_RCV_NWO               VARCHAR2(30) := 'POD_ORIG_RCV_NWO'; --签收运单_签收时始发应收未核销金额
  POD_DEST_RCV_WO                VARCHAR2(30) := 'POD_DEST_RCV_WO'; --签收运单_签收时到达应收已核销金额
  POD_DEST_RCV_NWO               VARCHAR2(30) := 'POD_DEST_RCV_NWO'; --签收运单_签收时到达应收未核销金额
  UPD_CASH_COLLECTED             VARCHAR2(30) := 'UPD_CASH_COLLECTED'; --反签收运单_反签收时现付已收款金额
  UPD_ORIG_RCV_WO                VARCHAR2(30) := 'UPD_ORIG_RCV_WO'; --反签收运单_反签收时始发应收已核销金额
  UPD_ORIG_RCV_NWO               VARCHAR2(30) := 'UPD_ORIG_RCV_NWO'; --反签收运单_反签收时始发应收未核销金额
  UPD_DEST_RCV_WO                VARCHAR2(30) := 'UPD_DEST_RCV_WO'; --反签收运单_反签收时到达应收已核销金额
  UPD_DEST_RCV_NWO               VARCHAR2(30) := 'UPD_DEST_RCV_NWO'; --反签收运单_反签收时到达应收未核销金额
  CLAIM_ORIG_WO_INCOME           VARCHAR2(30) := 'CLAIM_ORIG_WO_INCOME'; --理赔_出发部门申请_理赔冲收入
  CLAIM_ORIG_COST                VARCHAR2(30) := 'CLAIM_ORIG_COST'; --理赔_出发部门申请_理赔入成本
  CLAIM_WO_ORIG_RCV_POD          VARCHAR2(30) := 'CLAIM_WO_ORIG_RCV_POD'; --理赔_出发部门申请_理赔冲始发应收已签收
  CLAIM_WO_ORIG_RCV_NPOD         VARCHAR2(30) := 'CLAIM_WO_ORIG_RCV_NPOD'; --理赔_出发部门申请_理赔冲始发应收未签收
  CLAIM_ORIG_PAY_APPLY           VARCHAR2(30) := 'CLAIM_ORIG_PAY_APPLY'; --理赔_出发部门申请_理赔付款申请
  CLAIM_DEST_WO_INCOME           VARCHAR2(30) := 'CLAIM_DEST_WO_INCOME'; --理赔_到达部门申请_理赔冲收入
  CLAIM_DEST_COST                VARCHAR2(30) := 'CLAIM_DEST_COST'; --理赔_到达部门申请_理赔入成本
  CLAIM_DEST_PAY_APPLY           VARCHAR2(30) := 'CLAIM_DEST_PAY_APPLY'; --理赔_到达部门申请_理赔付款申请
  CLAIM_WO_DEST_RCV_POD          VARCHAR2(30) := 'CLAIM_WO_DEST_RCV_POD'; --理赔_到达部门申请_理赔冲到达应收已签收
  CLAIM_WO_DEST_RCV_NPOD         VARCHAR2(30) := 'CLAIM_WO_DEST_RCV_NPOD'; --理赔_到达部门申请_理赔冲到达应收未签收
  SF_PAY_APPLY                   VARCHAR2(30) := 'SF_PAY_APPLY'; --装卸费_装卸费付款申请
  COD_DEST_RCV_POD               VARCHAR2(30) := 'COD_DEST_RCV_POD'; --代收货款_应付代收货款冲应收到付运费已签收
  COD_DEST_RCV_NPOD              VARCHAR2(30) := 'COD_DEST_RCV_NPOD'; --代收货款_应付代收货款冲应收到付运费未签收
  COD_ORIG_RCV_POD               VARCHAR2(30) := 'COD_ORIG_RCV_POD'; --代收货款_应付代收货款冲应收始发运费已签收
  COD_ORIG_RCV_NPOD              VARCHAR2(30) := 'COD_ORIG_RCV_NPOD'; --代收货款_应付代收货款冲应收始发运费未签收
  COD_UR_CH_NPOD                 VARCHAR2(30) := 'COD_UR_CH_NPOD'; --代收货款_还款代收货款现金未签收
  COD_UR_CD_NPOD                 VARCHAR2(30) := 'COD_UR_CD_NPOD'; --代收货款_还款代收货款银行未签收
  COD_WO_OR_RCV                  VARCHAR2(30) := 'COD_WO_OR_RCV'; --代收货款_应付代收货款冲小票应收
  CUST_DR_CH                     VARCHAR2(30) := 'CUST_DR_CH'; --营业部预收客户_预收客户现金
  CUST_DR_CD                     VARCHAR2(30) := 'CUST_DR_CD'; --营业部预收客户_预收客户银行
  CUST_DR_DEST_RCV_NPOD          VARCHAR2(30) := 'CUST_DR_DEST_RCV_NPOD'; --营业部预收客户_预收客户冲应收到付运费未签收
  CUST_DR_DEST_RCV_POD           VARCHAR2(30) := 'CUST_DR_DEST_RCV_POD'; --营业部预收客户_预收客户冲应收到付运费已签收
  CUST_DR_ORIG_RCV_NPOD          VARCHAR2(30) := 'CUST_DR_ORIG_RCV_NPOD'; --营业部预收客户_预收客户冲应收始发运费未签收
  CUST_DR_ORIG_RCV_POD           VARCHAR2(30) := 'CUST_DR_ORIG_RCV_POD'; --营业部预收客户_预收客户冲应收始发运费已签收
  CUST_DR_ORIG_PAY_APPLY         VARCHAR2(30) := 'CUST_DR_ORIG_PAY_APPLY'; --营业部预收客户_始发退预收付款申请
  EX_ORIG_RCV_POD                VARCHAR2(30) := 'EX_ORIG_RCV_POD'; --异常冲收入_应收始发运费已签收
  EX_DEST_RCV_POD                VARCHAR2(30) := 'EX_DEST_RCV_POD'; --异常冲收入_应收到付运费已签收
  BD_ORIG_RCV_POD                VARCHAR2(30) := 'BD_ORIG_RCV_POD'; --坏账损失_坏账冲应收始发运费已签收
  BD_DEST_RCV_POD                VARCHAR2(30) := 'BD_DEST_RCV_POD'; --坏账损失_坏账冲应收到付运费已签收
  OR_CH_AC                       VARCHAR2(30) := 'OR_CH_AC'; --小票_小票录入现金_小票现金之事故赔款
  OR_CH_SI                       VARCHAR2(30) := 'OR_CH_SI'; --小票_小票录入现金_小票现金之变卖废品收入
  OR_CH_OPAY                     VARCHAR2(30) := 'OR_CH_OPAY'; --小票_小票录入现金_小票现金之客户多付运费或盘点长款金额
  OR_CH_OTHER                    VARCHAR2(30) := 'OR_CH_OTHER'; --小票_小票录入现金_小票现金之其他
  OR_CH_MBI                      VARCHAR2(30) := 'OR_CH_MBI'; --小票_小票录入现金_小票现金主营业务收入
  OR_CD_AC                       VARCHAR2(30) := 'OR_CD_AC'; --小票_小票录入银行_小票银行之事故赔款
  OR_CD_BANK_IT                  VARCHAR2(30) := 'OR_CD_BANK_IT'; --小票_小票录入银行_小票银行之收银员卡利息
  OR_CD_OPAY                     VARCHAR2(30) := 'OR_CD_OPAY'; --小票_小票录入银行_小票银行之客户多付运费或盘点长款金额
  OR_CD_OTHER                    VARCHAR2(30) := 'OR_CD_OTHER'; --小票_小票录入银行_小票银行之其他
  OR_CD_MBI                      VARCHAR2(30) := 'OR_CD_MBI'; --小票_小票录入银行_小票银行主营业务收入
  OR_RCV_MBI                     VARCHAR2(30) := 'OR_RCV_MBI'; --小票_小票录入应收_小票应收主营业务收入
  OR_RCV_WO_UR_CH                VARCHAR2(30) := 'OR_RCV_WO_UR_CH'; --小票_小票应收核销_还款现金冲小票应收
  OR_RCV_WO_UR_CD                VARCHAR2(30) := 'OR_RCV_WO_UR_CD'; --小票_小票应收核销_还款银行冲小票应收
  OR_RCV_WO_COD_PAY              VARCHAR2(30) := 'OR_RCV_WO_COD_PAY'; --小票_小票应收核销_应付代收货款冲小票应收
  OR_RCV_WO_CLAIM_PAY            VARCHAR2(30) := 'OR_RCV_WO_CLAIM_PAY'; --小票_小票应收核销_应付理赔冲小票应收
  OR_RCV_WO_CUST_DR              VARCHAR2(30) := 'OR_RCV_WO_CUST_DR'; --小票_小票应收核销_预收客户冲小票应收
  OR_RCV_WO_BD_DEBT              VARCHAR2(30) := 'OR_RCV_WO_BD_DEBT'; --小票_小票应收核销_坏账之保险理赔冲小票应收
  OR_RCV_WO_BD_INCOME            VARCHAR2(30) := 'OR_RCV_WO_BD_INCOME'; --小票_小票应收核销_坏账之坏账损失冲小票应收
  AC_CTDTOL_NWO                  VARCHAR2(30) := 'AC_CTDTOL_NWO'; --弃货、违禁品、全票丢货_开单为月结临时欠款网上支付未核销
  AC_CTDTOL_WO                   VARCHAR2(30) := 'AC_CTDTOL_WO'; --弃货、违禁品、全票丢货_开单为月结临时欠款网上支付已核销
  AC_CHCD                        VARCHAR2(30) := 'AC_CHCD'; --弃货、违禁品、全票丢货_开单为现金银行卡
  RD_ORIG_WO_INCOME              VARCHAR2(30) := 'RD_ORIG_WO_INCOME'; --退运费_出发部门申请_退运费冲收入
  RD_ORIG_COST                   VARCHAR2(30) := 'RD_ORIG_COST'; --退运费_出发部门申请_退运费入成本
  RD_ORIG_PAY_APPLY              VARCHAR2(30) := 'RD_ORIG_PAY_APPLY'; --退运费_出发部门申请_退运费付款申请
  RD_DEST_WO_INCOME              VARCHAR2(30) := 'RD_DEST_WO_INCOME'; --退运费_到达部门申请_退运费冲收入
  RD_DEST_COST                   VARCHAR2(30) := 'RD_DEST_COST'; --退运费_到达部门申请_退运费入成本
  RD_DEST_PAY_APPLY              VARCHAR2(30) := 'RD_DEST_PAY_APPLY'; --退运费_到达部门申请_退运费付款申请
  CN_ORIG_PAY_APPLY              VARCHAR2(30) := 'CN_ORIG_PAY_APPLY'; --服务补救_始发服务补救付款申请
  CN_DEST_PAY_APPLY              VARCHAR2(30) := 'CN_DEST_PAY_APPLY'; --服务补救_到达服务补救付款申请
  PL_COST_WO_DEST_RCV_POD        VARCHAR2(30) := 'PL_COST_WO_DEST_RCV_POD'; --偏线代理成本_应付偏线代理成本冲应收到付运费已签收
  PL_COST_WO_DEST_RCV_NPOD       VARCHAR2(30) := 'PL_COST_WO_DEST_RCV_NPOD'; --偏线代理成本_应付偏线代理成本冲应收到付运费未签收
  PL_COST_VECH                   VARCHAR2(30) := 'PL_COST_VECH'; --偏线代理成本_外发单录入
  PL_COST_CONFIRM                VARCHAR2(30) := 'PL_COST_CONFIRM'; --偏线代理成本_偏线代理成本确认
  PL_COST_NOT_CONFIRM            VARCHAR2(30) := 'PL_COST_NOT_CONFIRM'; --偏线代理成本_偏线代理成本反确认
  PL_COST_PAY_APPLY              VARCHAR2(30) := 'PL_COST_PAY_APPLY'; --偏线代理成本_偏线代理成本付款申请
  PL_DR_WO_DEST_RCV_POD          VARCHAR2(30) := 'PL_DR_WO_DEST_RCV_POD'; --预收偏线代理_预收偏线代理冲应收到付运费已签收
  PL_DR_WO_DEST_RCV_NPOD         VARCHAR2(30) := 'PL_DR_WO_DEST_RCV_NPOD'; --预收偏线代理_预收偏线代理冲应收到付运费未签收
  PL_DR_CH                       VARCHAR2(30) := 'PL_DR_CH'; --预收偏线代理_预收偏线代理现金
  PL_DR_CD                       VARCHAR2(30) := 'PL_DR_CD'; --预收偏线代理_预收偏线代理银行
  PL_DR_PAY_APPLY                VARCHAR2(30) := 'PL_DR_PAY_APPLY'; --预收偏线代理_偏线退预收付款申请
  AIR_COST_COM_CONFIRM           VARCHAR2(30) := 'AIR_COST_COM_CONFIRM'; --空运成本_空运航空公司运费确认
  AIR_COST_ORIG_AGENCY_CFM       VARCHAR2(30) := 'AIR_COST_ORIG_AGENCY_CFM'; --空运成本_空运出发代理应付确认
  AIR_COST_DEST_AGENCY_GEN       VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_GEN'; --空运成本_空运到达代理应付生成
  AIR_COST_DEST_AGENCY_CFM       VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_CFM'; --空运成本_空运到达代理应付成本确认
  AIR_COST_DEST_AGENCY_NCFM      VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_NCFM'; --空运成本_空运到达代理应付成本反确认
  AIR_COST_OTHER_CONFIRM         VARCHAR2(30) := 'AIR_COST_OTHER_CONFIRM'; --空运成本_其它应付成本确认
  AIR_COST_PAY_APPLY             VARCHAR2(30) := 'AIR_COST_PAY_APPLY'; --空运成本_空运成本付款申请
  OTH_ENTRY                      VARCHAR2(30) := 'OTH_ENTRY'; --空运其他应收_空运其他应收录入
  OTH_RCV_CH                     VARCHAR2(30) := 'OTH_RCV_CH'; --空运其他应收_还款空运其他应收现金
  OTH_RCV_CD                     VARCHAR2(30) := 'OTH_RCV_CD'; --空运其他应收_还款空运其他应收银行
  AIR_DR_DEST_RCV_POD            VARCHAR2(30) := 'AIR_DR_DEST_RCV_POD'; --预收空运代理_预收空运代理冲应收到付运费已签收
  AIR_DR_DEST_RCV_NPOD           VARCHAR2(30) := 'AIR_DR_DEST_RCV_NPOD'; --预收空运代理_预收空运代理冲应收到付运费未签收
  AIR_DR_CH                      VARCHAR2(30) := 'AIR_DR_CH'; --预收空运代理_预收空运代理现金
  AIR_DR_CD                      VARCHAR2(30) := 'AIR_DR_CD'; --预收空运代理_预收空运代理银行
  AIR_DR_WO_OTHER_RCV            VARCHAR2(30) := 'AIR_DR_WO_OTHER_RCV'; --预收空运代理_预收空运代理冲其他应收
  AIR_DR_WO_COD_RCV_POD          VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_POD'; --预收空运代理_预收空运代理冲应收代收货款已签收
  AIR_DR_WO_COD_RCV_NPOD         VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_NPOD'; --预收空运代理_预收空运代理冲应收代收货款未签收
  AIR_DR_PAY_APPLY               VARCHAR2(30) := 'AIR_DR_PAY_APPLY'; --预收空运代理_空运退预收付款申请
  AIR_PR_AGENCY_WO_DEST_RCV_POD  VARCHAR2(30) := 'AIR_PR_AGENCY_WO_DEST_RCV_POD'; --空运应付冲应收_应付到达代理成本冲应收到付运费已签收
  AIR_PR_AGENCY_WO_DEST_RCV_NPOD VARCHAR2(30) := 'AIR_PR_AGENCY_WO_DEST_RCV_NPOD'; --空运应付冲应收_应付到达代理成本冲应收到付运费未签收
  AIR_PR_OT_WO_DEST_RCV_POD      VARCHAR2(30) := 'AIR_PR_OT_WO_DEST_RCV_POD'; --空运应付冲应收_其他应付冲应收到付运费已签收
  AIR_PR_OTH_WO_DEST_RCV_NPOD    VARCHAR2(30) := 'AIR_PR_OTH_WO_DEST_RCV_NPOD'; --空运应付冲应收_其他应付冲应收到付运费未签收
  AIR_PR_OTH_WO_OTH_RCV          VARCHAR2(30) := 'AIR_PR_OTH_WO_OTH_RCV'; --空运应付冲应收_其他应付冲其他应收
  AIR_COD_CH_POD                 VARCHAR2(30) := 'AIR_COD_CH_POD'; --空运代收货款_空运还款代收货款现金已签收
  AIR_COD_CD_POD                 VARCHAR2(30) := 'AIR_COD_CD_POD'; --空运代收货款_空运还款代收货款银行已签收
  AIR_COD_POD_WO_COD             VARCHAR2(30) := 'AIR_COD_POD_WO_COD'; --空运代收货款_空运签收时已核销代收货款
  AIR_COD_NPOD_WO_COD            VARCHAR2(30) := 'AIR_COD_NPOD_WO_COD'; --空运代收货款_空运反签收时已核销代收货款
  AIR_COD_POD_NWO_COD            VARCHAR2(30) := 'AIR_COD_POD_NWO_COD'; --空运代收货款_空运签收时未核销代收货款
  AIR_COD_NPOD_NWO_COD           VARCHAR2(30) := 'AIR_COD_NPOD_NWO_COD'; --空运代收货款_空运反签收时未核销代收货款
  AIR_COD_CH_NPOD                VARCHAR2(30) := 'AIR_COD_CH_NPOD'; --空运代收货款_空运还款代收货款现金未签收
  AIR_COD_CD_NPOD                VARCHAR2(30) := 'AIR_COD_CD_NPOD'; --空运代收货款_空运还款代收货款银行未签收
  AIR_COD_WO_AGENCY_PAY_POD      VARCHAR2(30) := 'AIR_COD_WO_AGENCY_PAY_POD'; --空运代收货款_空运到达代理应付冲应收代收货款已签收
  AIR_COD_WO_OTH_PAY_COD         VARCHAR2(30) := 'AIR_COD_WO_OTH_PAY_COD'; --空运代收货款_空运其他应付冲应收代收货款已签收
  AIR_COD_WO_AGENCY_PAY_NPOD     VARCHAR2(30) := 'AIR_COD_WO_AGENCY_PAY_NPOD'; --空运代收货款_空运到达代理应付冲应收代收货款未签收
  AIR_COD_WO_OTH_NPOD            VARCHAR2(30) := 'AIR_COD_WO_OTH_NPOD'; --空运代收货款_空运其他应付冲应收代收货款未签收
  APT_COM                        VARCHAR2(30) := 'APT_COM'; --预付_预付航空公司
  APT_WO_COM_PAY                 VARCHAR2(30) := 'APT_WO_COM_PAY'; --预付_预付冲应付航空公司
  APT_WO_OTH_PAY                 VARCHAR2(30) := 'APT_WO_OTH_PAY'; --预付_预付冲其他应付
  BDR_WO_OTH_RCV                 VARCHAR2(30) := 'BDR_WO_OTH_RCV'; --坏账冲其他应收_坏账冲其他应收
  RD_WO_ORIG_RCV_POD             VARCHAR2(30) := 'RD_WO_ORIG_RCV_POD'; --退运费冲始发应收已签收
  RD_WO_ORIG_RCV_NPOD            VARCHAR2(30) := 'RD_WO_ORIG_RCV_NPOD'; --退运费冲始发应收未签收
  RD_WO_DEST_RCV_POD             VARCHAR2(30) := 'RD_WO_DEST_RCV_POD'; --退运费冲到达应收已签收
  RD_WO_DEST_RCV_NPOD            VARCHAR2(30) := 'RD_WO_DEST_RCV_NPOD'; --退运费冲到达应收未签收
  ---------------------------------------------------------------
  --快递新增字段
  ---------------------------------------------------------------
  LAND_COST_AGENCY_GEN           VARCHAR2(30) := 'LAND_COST_AGENCY_GEN'; --落地配代理应付生成
  LAND_COST_AGENCY_CFM           VARCHAR2(30) := 'LAND_COST_AGENCY_CFM'; --落地配公司运费确认
  LAND_COST_AGENCY_NCFM          VARCHAR2(30) := 'LAND_COST_AGENCY_NCFM'; --落地配代理应付成本反确认
  LAND_COST_OTHER_CONFIRM        VARCHAR2(30) := 'LAND_COST_OTHER_CONFIRM'; --落地配其它应付成本确认
  LAND_COST_PAY_APPLY            VARCHAR2(30) := 'LAND_COST_PAY_APPLY'; --落地配成本付款申请
  LAND_OTH_ENTRY                 VARCHAR2(30) := 'LAND_OTH_ENTRY'; --落地配其他应收录入
  LAND_OTH_RCV_CH                VARCHAR2(30) := 'LAND_OTH_RCV_CH'; --还款落地配其他应收现金
  LAND_OTH_RCV_CD                VARCHAR2(30) := 'LAND_OTH_RCV_CD'; --还款落地配其他应收银行
  LAND_COD_CH_NPOD               VARCHAR2(30) := 'LAND_COD_CH_NPOD'; --落地配还款代收货款现金未签收
  LAND_COD_CD_NPOD               VARCHAR2(30) := 'LAND_COD_CD_NPOD'; --落地配还款代收货款银行未签收
  LAND_COD_WO_AGENCY_PAY_NPOD    VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_NPOD'; --落地配到达代理应付冲应收代收货款未签收
  LAND_PR_OTH_WO_OTH_RCV         VARCHAR2(30) := 'LAND_PR_OTH_WO_OTH_RCV'; --落地配其他应付冲其他应收
  LAND_DR_CH                     VARCHAR2(30) := 'LAND_DR_CH'; --预收落地配代理现金
  LAND_DR_CD                     VARCHAR2(30) := 'LAND_DR_CD'; --预收落地配代理银行
  LAND_DR_WO_OTHER_RCV           VARCHAR2(30) := 'LAND_DR_WO_OTHER_RCV'; --落地配预收落地配代理冲其他应收
  LAND_DR_WO_COD_RCV_NPOD        VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_NPOD'; --落地配预收落地配代理冲应收代收货款未签收
  LAND_DR_PAY_APPLY              VARCHAR2(30) := 'LAND_DR_PAY_APPLY'; --落地配退预收付款申请
  LAND_COD_POD_NWO_COD           VARCHAR2(30) := 'LAND_COD_POD_NWO_COD'; --落地配签收时未核销代收货款
  LAND_COD_CH_POD                VARCHAR2(30) := 'LAND_COD_CH_POD'; --落地配还款代收货款现金已签收
  LAND_COD_CD_POD                VARCHAR2(30) := 'LAND_COD_CD_POD'; --落地配还款代收货款银行已签收
  LAND_COD_NPOD_NWO_COD          VARCHAR2(30) := 'LAND_COD_NPOD_NWO_COD'; --落地配反签收时未核销代收货款
  LAND_COD_POD_WO_COD            VARCHAR2(30) := 'LAND_COD_POD_WO_COD'; --落地配签收时已核销代收货款
  LAND_COD_NPOD_WO_COD           VARCHAR2(30) := 'LAND_COD_NPOD_WO_COD'; --落地配反签收时已核销代收货款
  LAND_COD_WO_AGENCY_PAY_POD     VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_POD'; --落地配到达代理应付冲应收代收货款已签收
  LAND_COD_WO_OTH_PAY_COD        VARCHAR2(30) := 'LAND_COD_WO_OTH_PAY_COD'; --落地配其他应付冲应收代收货款已签收
  LAND_PR_AGENCY_WO_DEST_RCV_POD VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_POD'; --落地配应付到达代理成本冲应收到付运费已签收
  LAND_PR_AGENCY_WO_DEST_RCV_NP  VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_NP'; --落地配应付到达代理成本冲应收到付运费未签收
  LAND_PR_OT_WO_DEST_RCV_POD     VARCHAR2(30) := 'LAND_PR_OT_WO_DEST_RCV_POD'; --落地配其他应付冲应收到付运费已签收
  LAND_PR_OTH_WO_DEST_RCV_NPOD   VARCHAR2(30) := 'LAND_PR_OTH_WO_DEST_RCV_NPOD'; --落地配其他应付冲应收到付运费未签收
  LAND_DR_DEST_RCV_POD           VARCHAR2(30) := 'LAND_DR_DEST_RCV_POD'; --落地配预收落地配代理冲应收到付运费已签收
  LAND_DR_DEST_RCV_NPOD          VARCHAR2(30) := 'LAND_DR_DEST_RCV_NPOD'; --落地配预收落地配代理冲应收到付运费未签收
  LAND_DR_WO_COD_RCV_POD         VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_POD'; --落地配预收落地配代理冲应收代收货款已签收
  LAND_UR_DEST_CH_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CH_NPOD'; --落地配还款现金未签收
  LAND_UR_DEST_CD_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CD_NPOD'; --落地配还款银行未签收
  LAND_UR_DEST_CH_POD            VARCHAR2(30) := 'LAND_UR_DEST_CH_POD'; --落地配还款现金已签收
  LAND_UR_DEST_CD_POD            VARCHAR2(30) := 'LAND_UR_DEST_CD_POD'; --落地配还款银行已签收
  LAND_BDR_WO_OTH_RCV            VARCHAR2(30) := 'LAND_BDR_WO_OTH_RCV'; --落地配坏账冲其他应收_坏账冲其他应收
  LAND_APT_COM                   VARCHAR2(30) := 'LAND_APT_COM'; --落地配预付_预付航空公司
  LAND_APT_WO_COM_PAY            VARCHAR2(30) := 'LAND_APT_WO_COM_PAY'; --落地配预付_预付冲应付航空公司
  LAND_APT_WO_OTH_PAY            VARCHAR2(30) := 'LAND_APT_WO_OTH_PAY'; --落地配预付_预付冲其他应付
  LAND_COD_WO_OTH_NPOD           VARCHAR2(30) := 'LAND_COD_WO_OTH_NPOD'; --落地配其他应付冲应收代收货款未签收

  CUST_DR_ORIG_LAND_RCV_NPOD   VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_NPOD'; --预收客户冲快递应收始发运费未签收
  CUST_DR_ORIG_LAND_RCV_POD    VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_POD'; --预收客户冲快递应收始发运费已签收
  CLAIM_WO_ORIG_LAND_RCV_POD   VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_POD'; --理赔冲快递始发应收已签收
  CLAIM_WO_ORIG_LAND_RCV_NPOD  VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_NPOD'; --理赔冲快递始发应收未签收
  OR_LAND_RCV_WO_CLAIM_PAY     VARCHAR2(30) := 'OR_LAND_RCV_WO_CLAIM_PAY'; --应付理赔冲快递小票应收
  OR_LAND_RCV_WO_CUST_DR       VARCHAR2(30) := 'OR_LAND_RCV_WO_CUST_DR'; --预收客户冲快递小票应收
  REFUND_WO_ORIG_LAND_RCV_POD  VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_POD'; --退运费冲快递始发应收已签收
  REFUND_WO_ORIG_LAND_RCV_NPOD VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_NPOD'; --退运费冲快递始发应收未签收
  LAND_CLAIM_WO_ORIG_RCV_POD   VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_POD'; --快递理赔冲始发应收已签收
  LAND_CLAIM_WO_ORIG_RCV_NPOD  VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_NPOD'; --快递理赔冲始发应收未签收
  OR_RCV_WO_LAND_CLAIM_PAY     VARCHAR2(30) := 'OR_RCV_WO_LAND_CLAIM_PAY'; --快递应付理赔冲小票应收
  LAND_REFUND_WO_ORIG_RCV_POD  VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_POD'; --快递退运费冲始发应收已签收
  LAND_REFUND_WO_ORIG_RCV_NPOD VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_NPOD'; --快递退运费冲始发应收未签收

  -----------------------------------------------------------------
  -- 凭证开始处理
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
                                 P_BUSINESS_TYPE VARCHAR2 -- 类型
                                 ); -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证成功
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
                                 P_BUSINESS_TYPE VARCHAR2 -- 类型
                                 ); -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证失败
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
                                 P_BUSINESS_TYPE VARCHAR2 -- 类型
                                 ); -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证月统计开始处理
  -----------------------------------------------------------------
  PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
                             ); -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证月统计成功处理
  -----------------------------------------------------------------
  PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
                             ); -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_COMMON IS

	-----------------------------------------------------------------
	-- 凭证开始记录
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
																 P_BUSINESS_TYPE VARCHAR2 -- 类型
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		CURSOR V_CUR IS
			SELECT *
				FROM T_STL_VOUCHER_BATCH T
			 WHERE T.PERIOD = P_PERIOD
						 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE
			 ORDER BY T.ID;
		V_REC V_CUR%ROWTYPE;
		VOUCHER_BATCH_ERROR EXCEPTION;
	BEGIN
	
		OPEN V_CUR;
		FETCH V_CUR
			INTO V_REC;
		IF V_CUR%NOTFOUND THEN
			INSERT INTO T_STL_VOUCHER_BATCH
				(ID, PERIOD, STAT_BEGIN_DATE, STATUS, BUSINESS_TYPE)
			VALUES
				(SYS_GUID(),
				 P_PERIOD,
				 SYSDATE,
				 VOUCHER_BATCH_STATUS_PROCESS,
				 P_BUSINESS_TYPE);
		ELSIF V_REC.STATUS IN
					(VOUCHER_BATCH_STATUS_SUCCESS, VOUCHER_BATCH_STATUS_FAILURE) THEN
			UPDATE T_STL_VOUCHER_BATCH T
				 SET T.STAT_BEGIN_DATE = SYSDATE,
						 T.STATUS          = VOUCHER_BATCH_STATUS_PROCESS
			 WHERE T.ID = V_REC.ID;
		ELSE
			CLOSE V_CUR;
			RAISE VOUCHER_BATCH_ERROR;
		END IF;
	
		CLOSE V_CUR;
		COMMIT;
	
	EXCEPTION
		WHEN VOUCHER_BATCH_ERROR THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'凭证开始记录' || '状态异常');
			RAISE_APPLICATION_ERROR(SQLCODE, '记录凭证日志错误');
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'凭证开始记录' || '状态异常');
	END;

	-----------------------------------------------------------------
	-- 凭证成功
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
																 P_BUSINESS_TYPE VARCHAR2 -- 类型
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- 查询记录
		SELECT ROWID, T.STATUS
			INTO V_ROWID, V_STATUS
			FROM T_STL_VOUCHER_BATCH T
		 WHERE T.PERIOD = P_PERIOD
					 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE;
	
		UPDATE T_STL_VOUCHER_BATCH T
			 SET T.STAT_END_DATE = SYSDATE,
					 T.STATUS        = VOUCHER_BATCH_STATUS_SUCCESS
		 WHERE T.ROWID = V_ROWID;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_SUCCESS',
																				'凭证成功' || '状态异常');
	END;

	-----------------------------------------------------------------
	-- 凭证失败
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- 账期yyyymm，例子201212
																 P_BUSINESS_TYPE VARCHAR2 -- 类型
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- 查询记录
		SELECT ROWID, T.STATUS
			INTO V_ROWID, V_STATUS
			FROM T_STL_VOUCHER_BATCH T
		 WHERE T.PERIOD = P_PERIOD
					 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE;
	
		UPDATE T_STL_VOUCHER_BATCH T
			 SET T.STAT_END_DATE = SYSDATE,
					 T.STATUS        = VOUCHER_BATCH_STATUS_FAILURE
		 WHERE T.ROWID = V_ROWID;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_FAILURE',
																				'凭证失败' || '状态异常');
	END;

	-----------------------------------------------------------------
	-- 凭证月统计开始处理
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		V_COUNT NUMBER;
		RPT_BATCH_ERROR EXCEPTION;
	BEGIN
		SELECT COUNT(T.PERIOD)
			INTO V_COUNT
			FROM STV.T_STL_MVR_BATCH T
		 WHERE T.PERIOD = P_PERIOD;
	
		IF V_COUNT <= 0 THEN
			INSERT INTO STV.T_STL_MVR_BATCH
				(ID, PERIOD, STATISTICS_BEGIN_DATE, ACTIVE)
			VALUES
				(SYS_GUID(), P_PERIOD, SYSDATE, PKG_STL_COMMON.NO);
		ELSIF V_COUNT = 1 THEN
			UPDATE STV.T_STL_MVR_BATCH T
				 SET T.STATISTICS_BEGIN_DATE = SYSDATE,
						 T.STATISTICS_END_DATE   = NULL,
						 T.RFO                   = NULL,
						 T.RFD                   = NULL,
						 T.RFI                   = NULL,
						 T.PLC                   = NULL,
						 T.PLI                   = NULL,
						 T.AFC                   = NULL,
						 T.AFI                   = NULL,
						 T.STATUS                = NULL,
						 T.ACTIVE                = PKG_STL_COMMON.NO,
						 T.RCOD                  = NULL
			 WHERE T.PERIOD = P_PERIOD;
		ELSE
			RAISE RPT_BATCH_ERROR;
		END IF;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_PROCESS',
																				'凭证月统计开始处理' || '状态异常');
	END;

	-----------------------------------------------------------------
	-- 凭证月统计成功处理
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		V_COUNT NUMBER;
		RPT_BATCH_ERROR EXCEPTION;
	BEGIN
		SELECT COUNT(T.PERIOD)
			INTO V_COUNT
			FROM STV.T_STL_MVR_BATCH T
		 WHERE T.PERIOD = P_PERIOD;
	
		IF V_COUNT = 1 THEN
			UPDATE STV.T_STL_MVR_BATCH T
				 SET T.STATISTICS_END_DATE = SYSDATE,
						 T.RFO                 = PKG_STL_COMMON.YES,
						 T.RFD                 = PKG_STL_COMMON.YES,
						 T.RFI                 = PKG_STL_COMMON.YES,
						 T.PLC                 = PKG_STL_COMMON.YES,
						 T.PLI                 = PKG_STL_COMMON.YES,
						 T.AFC                 = PKG_STL_COMMON.YES,
						 T.AFI                 = PKG_STL_COMMON.YES,
						 T.ACTIVE              = PKG_STL_COMMON.YES,
						 T.RCOD                = PKG_STL_COMMON.YES
			 WHERE T.PERIOD = P_PERIOD;
		ELSE
			RAISE RPT_BATCH_ERROR;
		END IF;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_SUCCESS',
																				'凭证月统计成功处理' || '状态异常');
	END;

END PKG_STL_VCH_COMMON;
/
