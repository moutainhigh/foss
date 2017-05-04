CREATE OR REPLACE PACKAGE PKG_STL_VCH_COMMON IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : ƾ֤ͨ�÷���
  -- ==============================================================

  RATIO_PRECISION NUMBER := 4; -- ����

  ---========================== ƾ֤�����ֵ� ==================================
  /**
  * VOUCHER_BATCH_STATUS ƾ֤����״̬
  */
  VOUCHER_BATCH_STATUS_PROCESS VARCHAR2(20) := 'PROCESS'; -- ������
  VOUCHER_BATCH_STATUS_SUCCESS VARCHAR2(20) := 'SUCCESS'; -- �ɹ�
  VOUCHER_BATCH_STATUS_FAILURE VARCHAR2(20) := 'FAILURE'; -- ʧ��

  /**
  * VOUCHER_ORG_TYPE ��������
  */
  VOUCHER_ORG_TYPE_ORIG VARCHAR2(20) := 'O'; -- ʼ��
  VOUCHER_ORG_TYPE_DEST VARCHAR2(20) := 'D'; -- ����
  VOUCHER_ORG_TYPE_AIR  VARCHAR2(20) := 'A'; --����
  VOUCHER_ORG_TYPE_PL   VARCHAR2(20) := 'P'; --ƫ��
  VOUCHER_ORG_TYPE_PKG  VARCHAR2(20) := 'G'; --�����

  /**
  * VOUCHER_BUSINESS_TYPE ƾ֤ҵ������
  */
  VOUCHER_BUSINESS_TYPE_DE      VARCHAR2(20) := 'DE'; -- ����
  VOUCHER_BUSINESS_TYPE_UR_ORIG VARCHAR2(20) := 'UR_ORIG'; -- ǩ�ջ���
  VOUCHER_BUSINESS_TYPE_UR_DEST VARCHAR2(20) := 'UR_DEST'; -- ǩ�յ���
  VOUCHER_BUSINESS_TYPE_COD     VARCHAR2(20) := 'COD'; -- ���ջ���
  VOUCHER_BUSINESS_TYPE_OR      VARCHAR2(20) := 'OR'; -- СƱ
  VOUCHER_BUSINESS_TYPE_CLAIM   VARCHAR2(20) := 'CLAIM'; -- ����
  VOUCHER_BUSINESS_TYPE_BA      VARCHAR2(20) := 'BA'; -- ���˳�Ӧ��
  VOUCHER_BUSINESS_TYPE_AC      VARCHAR2(20) := 'AC'; -- ��������
  VOUCHER_BUSINESS_TYPE_RD      VARCHAR2(20) := 'RD'; -- ���˷�
  VOUCHER_BUSINESS_TYPE_CN      VARCHAR2(20) := 'CN'; -- ���񲹾�
  VOUCHER_BUSINESS_TYPE_PL      VARCHAR2(20) := 'PL'; -- ƫ�߳ɱ�
  VOUCHER_BUSINESS_TYPE_AIR     VARCHAR2(20) := 'AIR'; -- ���˳ɱ�
  VOUCHER_BUSINESS_TYPE_DR      VARCHAR2(20) := 'DR'; -- Ԥ��
  VOUCHER_BUSINESS_TYPE_ADP     VARCHAR2(20) := 'ADP'; -- ����Ԥ��
  VOUCHER_BUSINESS_TYPE_OTH     VARCHAR2(20) := 'OT'; -- ����Ӧ��Ӧ��

  VOUCHER_BUSINESS_TYPE_AFI  VARCHAR2(20) := 'AFI'; -- ��������
  VOUCHER_BUSINESS_TYPE_AFR  VARCHAR2(20) := 'AFR'; -- �����±�
  VOUCHER_BUSINESS_TYPE_PLI  VARCHAR2(20) := 'PLI'; -- ƫ������
  VOUCHER_BUSINESS_TYPE_PLR  VARCHAR2(20) := 'PLR'; -- ƫ���±�
  VOUCHER_BUSINESS_TYPE_RFD  VARCHAR2(20) := 'RFD'; -- ר�ߵ���
  VOUCHER_BUSINESS_TYPE_RFI  VARCHAR2(20) := 'RFI'; -- ʼ����������
  VOUCHER_BUSINESS_TYPE_RFO  VARCHAR2(20) := 'RFO'; -- ʼ���±�
  VOUCHER_BUSINESS_TYPE_RCOD VARCHAR2(20) := 'RCOD'; -- �˴��ջ���

  ---========================== ƾ֤ҵ�񳡾� ==================================
  DE_CH                          VARCHAR2(30) := 'DE_CH'; --�����˵�_�����ֽ�
  DE_CD                          VARCHAR2(30) := 'DE_CD'; --�����˵�_�������п�
  UR_ORIG_CH_NPOD                VARCHAR2(30) := 'UR_ORIG_CH_NPOD'; --�����˵����˷ѣ��½���ʱǷ������֧����_�����ֽ�δǩ��
  UR_ORIG_CD_NPOD                VARCHAR2(30) := 'UR_ORIG_CD_NPOD'; --�����˵����˷ѣ��½���ʱǷ������֧����_��������δǩ��
  UR_ORIG_CH_POD                 VARCHAR2(30) := 'UR_ORIG_CH_POD'; --�����˵����˷ѣ��½���ʱǷ������֧����_�����ֽ���ǩ��
  UR_ORIG_CD_POD                 VARCHAR2(30) := 'UR_ORIG_CD_POD'; --�����˵����˷ѣ��½���ʱǷ������֧����_����������ǩ��
  UR_DEST_CH_NPOD                VARCHAR2(30) := 'UR_DEST_CH_NPOD'; --�����˵����˷ѣ�������_�����ֽ�δǩ��
  UR_DEST_CD_NPOD                VARCHAR2(30) := 'UR_DEST_CD_NPOD'; --�����˵����˷ѣ�������_��������δǩ��
  UR_DEST_CH_POD                 VARCHAR2(30) := 'UR_DEST_CH_POD'; --�����˵����˷ѣ�������_�����ֽ���ǩ��
  UR_DEST_CD_POD                 VARCHAR2(30) := 'UR_DEST_CD_POD'; --�����˵����˷ѣ�������_����������ǩ��
  POD_CASH_COLLECTED             VARCHAR2(30) := 'POD_CASH_COLLECTED'; --ǩ���˵�_ǩ��ʱ�ָ����տ���
  POD_ORIG_RCV_WO                VARCHAR2(30) := 'POD_ORIG_RCV_WO'; --ǩ���˵�_ǩ��ʱʼ��Ӧ���Ѻ������
  POD_ORIG_RCV_NWO               VARCHAR2(30) := 'POD_ORIG_RCV_NWO'; --ǩ���˵�_ǩ��ʱʼ��Ӧ��δ�������
  POD_DEST_RCV_WO                VARCHAR2(30) := 'POD_DEST_RCV_WO'; --ǩ���˵�_ǩ��ʱ����Ӧ���Ѻ������
  POD_DEST_RCV_NWO               VARCHAR2(30) := 'POD_DEST_RCV_NWO'; --ǩ���˵�_ǩ��ʱ����Ӧ��δ�������
  UPD_CASH_COLLECTED             VARCHAR2(30) := 'UPD_CASH_COLLECTED'; --��ǩ���˵�_��ǩ��ʱ�ָ����տ���
  UPD_ORIG_RCV_WO                VARCHAR2(30) := 'UPD_ORIG_RCV_WO'; --��ǩ���˵�_��ǩ��ʱʼ��Ӧ���Ѻ������
  UPD_ORIG_RCV_NWO               VARCHAR2(30) := 'UPD_ORIG_RCV_NWO'; --��ǩ���˵�_��ǩ��ʱʼ��Ӧ��δ�������
  UPD_DEST_RCV_WO                VARCHAR2(30) := 'UPD_DEST_RCV_WO'; --��ǩ���˵�_��ǩ��ʱ����Ӧ���Ѻ������
  UPD_DEST_RCV_NWO               VARCHAR2(30) := 'UPD_DEST_RCV_NWO'; --��ǩ���˵�_��ǩ��ʱ����Ӧ��δ�������
  CLAIM_ORIG_WO_INCOME           VARCHAR2(30) := 'CLAIM_ORIG_WO_INCOME'; --����_������������_���������
  CLAIM_ORIG_COST                VARCHAR2(30) := 'CLAIM_ORIG_COST'; --����_������������_������ɱ�
  CLAIM_WO_ORIG_RCV_POD          VARCHAR2(30) := 'CLAIM_WO_ORIG_RCV_POD'; --����_������������_�����ʼ��Ӧ����ǩ��
  CLAIM_WO_ORIG_RCV_NPOD         VARCHAR2(30) := 'CLAIM_WO_ORIG_RCV_NPOD'; --����_������������_�����ʼ��Ӧ��δǩ��
  CLAIM_ORIG_PAY_APPLY           VARCHAR2(30) := 'CLAIM_ORIG_PAY_APPLY'; --����_������������_���⸶������
  CLAIM_DEST_WO_INCOME           VARCHAR2(30) := 'CLAIM_DEST_WO_INCOME'; --����_���ﲿ������_���������
  CLAIM_DEST_COST                VARCHAR2(30) := 'CLAIM_DEST_COST'; --����_���ﲿ������_������ɱ�
  CLAIM_DEST_PAY_APPLY           VARCHAR2(30) := 'CLAIM_DEST_PAY_APPLY'; --����_���ﲿ������_���⸶������
  CLAIM_WO_DEST_RCV_POD          VARCHAR2(30) := 'CLAIM_WO_DEST_RCV_POD'; --����_���ﲿ������_����嵽��Ӧ����ǩ��
  CLAIM_WO_DEST_RCV_NPOD         VARCHAR2(30) := 'CLAIM_WO_DEST_RCV_NPOD'; --����_���ﲿ������_����嵽��Ӧ��δǩ��
  SF_PAY_APPLY                   VARCHAR2(30) := 'SF_PAY_APPLY'; --װж��_װж�Ѹ�������
  COD_DEST_RCV_POD               VARCHAR2(30) := 'COD_DEST_RCV_POD'; --���ջ���_Ӧ�����ջ����Ӧ�յ����˷���ǩ��
  COD_DEST_RCV_NPOD              VARCHAR2(30) := 'COD_DEST_RCV_NPOD'; --���ջ���_Ӧ�����ջ����Ӧ�յ����˷�δǩ��
  COD_ORIG_RCV_POD               VARCHAR2(30) := 'COD_ORIG_RCV_POD'; --���ջ���_Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
  COD_ORIG_RCV_NPOD              VARCHAR2(30) := 'COD_ORIG_RCV_NPOD'; --���ջ���_Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
  COD_UR_CH_NPOD                 VARCHAR2(30) := 'COD_UR_CH_NPOD'; --���ջ���_������ջ����ֽ�δǩ��
  COD_UR_CD_NPOD                 VARCHAR2(30) := 'COD_UR_CD_NPOD'; --���ջ���_������ջ�������δǩ��
  COD_WO_OR_RCV                  VARCHAR2(30) := 'COD_WO_OR_RCV'; --���ջ���_Ӧ�����ջ����СƱӦ��
  CUST_DR_CH                     VARCHAR2(30) := 'CUST_DR_CH'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ��ֽ�
  CUST_DR_CD                     VARCHAR2(30) := 'CUST_DR_CD'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ�����
  CUST_DR_DEST_RCV_NPOD          VARCHAR2(30) := 'CUST_DR_DEST_RCV_NPOD'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ���Ӧ�յ����˷�δǩ��
  CUST_DR_DEST_RCV_POD           VARCHAR2(30) := 'CUST_DR_DEST_RCV_POD'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ���Ӧ�յ����˷���ǩ��
  CUST_DR_ORIG_RCV_NPOD          VARCHAR2(30) := 'CUST_DR_ORIG_RCV_NPOD'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ���Ӧ��ʼ���˷�δǩ��
  CUST_DR_ORIG_RCV_POD           VARCHAR2(30) := 'CUST_DR_ORIG_RCV_POD'; --Ӫҵ��Ԥ�տͻ�_Ԥ�տͻ���Ӧ��ʼ���˷���ǩ��
  CUST_DR_ORIG_PAY_APPLY         VARCHAR2(30) := 'CUST_DR_ORIG_PAY_APPLY'; --Ӫҵ��Ԥ�տͻ�_ʼ����Ԥ�ո�������
  EX_ORIG_RCV_POD                VARCHAR2(30) := 'EX_ORIG_RCV_POD'; --�쳣������_Ӧ��ʼ���˷���ǩ��
  EX_DEST_RCV_POD                VARCHAR2(30) := 'EX_DEST_RCV_POD'; --�쳣������_Ӧ�յ����˷���ǩ��
  BD_ORIG_RCV_POD                VARCHAR2(30) := 'BD_ORIG_RCV_POD'; --������ʧ_���˳�Ӧ��ʼ���˷���ǩ��
  BD_DEST_RCV_POD                VARCHAR2(30) := 'BD_DEST_RCV_POD'; --������ʧ_���˳�Ӧ�յ����˷���ǩ��
  OR_CH_AC                       VARCHAR2(30) := 'OR_CH_AC'; --СƱ_СƱ¼���ֽ�_СƱ�ֽ�֮�¹����
  OR_CH_SI                       VARCHAR2(30) := 'OR_CH_SI'; --СƱ_СƱ¼���ֽ�_СƱ�ֽ�֮������Ʒ����
  OR_CH_OPAY                     VARCHAR2(30) := 'OR_CH_OPAY'; --СƱ_СƱ¼���ֽ�_СƱ�ֽ�֮�ͻ��ึ�˷ѻ��̵㳤����
  OR_CH_OTHER                    VARCHAR2(30) := 'OR_CH_OTHER'; --СƱ_СƱ¼���ֽ�_СƱ�ֽ�֮����
  OR_CH_MBI                      VARCHAR2(30) := 'OR_CH_MBI'; --СƱ_СƱ¼���ֽ�_СƱ�ֽ���Ӫҵ������
  OR_CD_AC                       VARCHAR2(30) := 'OR_CD_AC'; --СƱ_СƱ¼������_СƱ����֮�¹����
  OR_CD_BANK_IT                  VARCHAR2(30) := 'OR_CD_BANK_IT'; --СƱ_СƱ¼������_СƱ����֮����Ա����Ϣ
  OR_CD_OPAY                     VARCHAR2(30) := 'OR_CD_OPAY'; --СƱ_СƱ¼������_СƱ����֮�ͻ��ึ�˷ѻ��̵㳤����
  OR_CD_OTHER                    VARCHAR2(30) := 'OR_CD_OTHER'; --СƱ_СƱ¼������_СƱ����֮����
  OR_CD_MBI                      VARCHAR2(30) := 'OR_CD_MBI'; --СƱ_СƱ¼������_СƱ������Ӫҵ������
  OR_RCV_MBI                     VARCHAR2(30) := 'OR_RCV_MBI'; --СƱ_СƱ¼��Ӧ��_СƱӦ����Ӫҵ������
  OR_RCV_WO_UR_CH                VARCHAR2(30) := 'OR_RCV_WO_UR_CH'; --СƱ_СƱӦ�պ���_�����ֽ��СƱӦ��
  OR_RCV_WO_UR_CD                VARCHAR2(30) := 'OR_RCV_WO_UR_CD'; --СƱ_СƱӦ�պ���_�������г�СƱӦ��
  OR_RCV_WO_COD_PAY              VARCHAR2(30) := 'OR_RCV_WO_COD_PAY'; --СƱ_СƱӦ�պ���_Ӧ�����ջ����СƱӦ��
  OR_RCV_WO_CLAIM_PAY            VARCHAR2(30) := 'OR_RCV_WO_CLAIM_PAY'; --СƱ_СƱӦ�պ���_Ӧ�������СƱӦ��
  OR_RCV_WO_CUST_DR              VARCHAR2(30) := 'OR_RCV_WO_CUST_DR'; --СƱ_СƱӦ�պ���_Ԥ�տͻ���СƱӦ��
  OR_RCV_WO_BD_DEBT              VARCHAR2(30) := 'OR_RCV_WO_BD_DEBT'; --СƱ_СƱӦ�պ���_����֮���������СƱӦ��
  OR_RCV_WO_BD_INCOME            VARCHAR2(30) := 'OR_RCV_WO_BD_INCOME'; --СƱ_СƱӦ�պ���_����֮������ʧ��СƱӦ��
  AC_CTDTOL_NWO                  VARCHAR2(30) := 'AC_CTDTOL_NWO'; --������Υ��Ʒ��ȫƱ����_����Ϊ�½���ʱǷ������֧��δ����
  AC_CTDTOL_WO                   VARCHAR2(30) := 'AC_CTDTOL_WO'; --������Υ��Ʒ��ȫƱ����_����Ϊ�½���ʱǷ������֧���Ѻ���
  AC_CHCD                        VARCHAR2(30) := 'AC_CHCD'; --������Υ��Ʒ��ȫƱ����_����Ϊ�ֽ����п�
  RD_ORIG_WO_INCOME              VARCHAR2(30) := 'RD_ORIG_WO_INCOME'; --���˷�_������������_���˷ѳ�����
  RD_ORIG_COST                   VARCHAR2(30) := 'RD_ORIG_COST'; --���˷�_������������_���˷���ɱ�
  RD_ORIG_PAY_APPLY              VARCHAR2(30) := 'RD_ORIG_PAY_APPLY'; --���˷�_������������_���˷Ѹ�������
  RD_DEST_WO_INCOME              VARCHAR2(30) := 'RD_DEST_WO_INCOME'; --���˷�_���ﲿ������_���˷ѳ�����
  RD_DEST_COST                   VARCHAR2(30) := 'RD_DEST_COST'; --���˷�_���ﲿ������_���˷���ɱ�
  RD_DEST_PAY_APPLY              VARCHAR2(30) := 'RD_DEST_PAY_APPLY'; --���˷�_���ﲿ������_���˷Ѹ�������
  CN_ORIG_PAY_APPLY              VARCHAR2(30) := 'CN_ORIG_PAY_APPLY'; --���񲹾�_ʼ�����񲹾ȸ�������
  CN_DEST_PAY_APPLY              VARCHAR2(30) := 'CN_DEST_PAY_APPLY'; --���񲹾�_������񲹾ȸ�������
  PL_COST_WO_DEST_RCV_POD        VARCHAR2(30) := 'PL_COST_WO_DEST_RCV_POD'; --ƫ�ߴ���ɱ�_Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��
  PL_COST_WO_DEST_RCV_NPOD       VARCHAR2(30) := 'PL_COST_WO_DEST_RCV_NPOD'; --ƫ�ߴ���ɱ�_Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��
  PL_COST_VECH                   VARCHAR2(30) := 'PL_COST_VECH'; --ƫ�ߴ���ɱ�_�ⷢ��¼��
  PL_COST_CONFIRM                VARCHAR2(30) := 'PL_COST_CONFIRM'; --ƫ�ߴ���ɱ�_ƫ�ߴ���ɱ�ȷ��
  PL_COST_NOT_CONFIRM            VARCHAR2(30) := 'PL_COST_NOT_CONFIRM'; --ƫ�ߴ���ɱ�_ƫ�ߴ���ɱ���ȷ��
  PL_COST_PAY_APPLY              VARCHAR2(30) := 'PL_COST_PAY_APPLY'; --ƫ�ߴ���ɱ�_ƫ�ߴ���ɱ���������
  PL_DR_WO_DEST_RCV_POD          VARCHAR2(30) := 'PL_DR_WO_DEST_RCV_POD'; --Ԥ��ƫ�ߴ���_Ԥ��ƫ�ߴ����Ӧ�յ����˷���ǩ��
  PL_DR_WO_DEST_RCV_NPOD         VARCHAR2(30) := 'PL_DR_WO_DEST_RCV_NPOD'; --Ԥ��ƫ�ߴ���_Ԥ��ƫ�ߴ����Ӧ�յ����˷�δǩ��
  PL_DR_CH                       VARCHAR2(30) := 'PL_DR_CH'; --Ԥ��ƫ�ߴ���_Ԥ��ƫ�ߴ����ֽ�
  PL_DR_CD                       VARCHAR2(30) := 'PL_DR_CD'; --Ԥ��ƫ�ߴ���_Ԥ��ƫ�ߴ�������
  PL_DR_PAY_APPLY                VARCHAR2(30) := 'PL_DR_PAY_APPLY'; --Ԥ��ƫ�ߴ���_ƫ����Ԥ�ո�������
  AIR_COST_COM_CONFIRM           VARCHAR2(30) := 'AIR_COST_COM_CONFIRM'; --���˳ɱ�_���˺��չ�˾�˷�ȷ��
  AIR_COST_ORIG_AGENCY_CFM       VARCHAR2(30) := 'AIR_COST_ORIG_AGENCY_CFM'; --���˳ɱ�_���˳�������Ӧ��ȷ��
  AIR_COST_DEST_AGENCY_GEN       VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_GEN'; --���˳ɱ�_���˵������Ӧ������
  AIR_COST_DEST_AGENCY_CFM       VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_CFM'; --���˳ɱ�_���˵������Ӧ���ɱ�ȷ��
  AIR_COST_DEST_AGENCY_NCFM      VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_NCFM'; --���˳ɱ�_���˵������Ӧ���ɱ���ȷ��
  AIR_COST_OTHER_CONFIRM         VARCHAR2(30) := 'AIR_COST_OTHER_CONFIRM'; --���˳ɱ�_����Ӧ���ɱ�ȷ��
  AIR_COST_PAY_APPLY             VARCHAR2(30) := 'AIR_COST_PAY_APPLY'; --���˳ɱ�_���˳ɱ���������
  OTH_ENTRY                      VARCHAR2(30) := 'OTH_ENTRY'; --��������Ӧ��_��������Ӧ��¼��
  OTH_RCV_CH                     VARCHAR2(30) := 'OTH_RCV_CH'; --��������Ӧ��_�����������Ӧ���ֽ�
  OTH_RCV_CD                     VARCHAR2(30) := 'OTH_RCV_CD'; --��������Ӧ��_�����������Ӧ������
  AIR_DR_DEST_RCV_POD            VARCHAR2(30) := 'AIR_DR_DEST_RCV_POD'; --Ԥ�տ��˴���_Ԥ�տ��˴����Ӧ�յ����˷���ǩ��
  AIR_DR_DEST_RCV_NPOD           VARCHAR2(30) := 'AIR_DR_DEST_RCV_NPOD'; --Ԥ�տ��˴���_Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
  AIR_DR_CH                      VARCHAR2(30) := 'AIR_DR_CH'; --Ԥ�տ��˴���_Ԥ�տ��˴����ֽ�
  AIR_DR_CD                      VARCHAR2(30) := 'AIR_DR_CD'; --Ԥ�տ��˴���_Ԥ�տ��˴�������
  AIR_DR_WO_OTHER_RCV            VARCHAR2(30) := 'AIR_DR_WO_OTHER_RCV'; --Ԥ�տ��˴���_Ԥ�տ��˴��������Ӧ��
  AIR_DR_WO_COD_RCV_POD          VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_POD'; --Ԥ�տ��˴���_Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
  AIR_DR_WO_COD_RCV_NPOD         VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_NPOD'; --Ԥ�տ��˴���_Ԥ�տ��˴����Ӧ�մ��ջ���δǩ��
  AIR_DR_PAY_APPLY               VARCHAR2(30) := 'AIR_DR_PAY_APPLY'; --Ԥ�տ��˴���_������Ԥ�ո�������
  AIR_PR_AGENCY_WO_DEST_RCV_POD  VARCHAR2(30) := 'AIR_PR_AGENCY_WO_DEST_RCV_POD'; --����Ӧ����Ӧ��_Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
  AIR_PR_AGENCY_WO_DEST_RCV_NPOD VARCHAR2(30) := 'AIR_PR_AGENCY_WO_DEST_RCV_NPOD'; --����Ӧ����Ӧ��_Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
  AIR_PR_OT_WO_DEST_RCV_POD      VARCHAR2(30) := 'AIR_PR_OT_WO_DEST_RCV_POD'; --����Ӧ����Ӧ��_����Ӧ����Ӧ�յ����˷���ǩ��
  AIR_PR_OTH_WO_DEST_RCV_NPOD    VARCHAR2(30) := 'AIR_PR_OTH_WO_DEST_RCV_NPOD'; --����Ӧ����Ӧ��_����Ӧ����Ӧ�յ����˷�δǩ��
  AIR_PR_OTH_WO_OTH_RCV          VARCHAR2(30) := 'AIR_PR_OTH_WO_OTH_RCV'; --����Ӧ����Ӧ��_����Ӧ��������Ӧ��
  AIR_COD_CH_POD                 VARCHAR2(30) := 'AIR_COD_CH_POD'; --���˴��ջ���_���˻�����ջ����ֽ���ǩ��
  AIR_COD_CD_POD                 VARCHAR2(30) := 'AIR_COD_CD_POD'; --���˴��ջ���_���˻�����ջ���������ǩ��
  AIR_COD_POD_WO_COD             VARCHAR2(30) := 'AIR_COD_POD_WO_COD'; --���˴��ջ���_����ǩ��ʱ�Ѻ������ջ���
  AIR_COD_NPOD_WO_COD            VARCHAR2(30) := 'AIR_COD_NPOD_WO_COD'; --���˴��ջ���_���˷�ǩ��ʱ�Ѻ������ջ���
  AIR_COD_POD_NWO_COD            VARCHAR2(30) := 'AIR_COD_POD_NWO_COD'; --���˴��ջ���_����ǩ��ʱδ�������ջ���
  AIR_COD_NPOD_NWO_COD           VARCHAR2(30) := 'AIR_COD_NPOD_NWO_COD'; --���˴��ջ���_���˷�ǩ��ʱδ�������ջ���
  AIR_COD_CH_NPOD                VARCHAR2(30) := 'AIR_COD_CH_NPOD'; --���˴��ջ���_���˻�����ջ����ֽ�δǩ��
  AIR_COD_CD_NPOD                VARCHAR2(30) := 'AIR_COD_CD_NPOD'; --���˴��ջ���_���˻�����ջ�������δǩ��
  AIR_COD_WO_AGENCY_PAY_POD      VARCHAR2(30) := 'AIR_COD_WO_AGENCY_PAY_POD'; --���˴��ջ���_���˵������Ӧ����Ӧ�մ��ջ�����ǩ��
  AIR_COD_WO_OTH_PAY_COD         VARCHAR2(30) := 'AIR_COD_WO_OTH_PAY_COD'; --���˴��ջ���_��������Ӧ����Ӧ�մ��ջ�����ǩ��
  AIR_COD_WO_AGENCY_PAY_NPOD     VARCHAR2(30) := 'AIR_COD_WO_AGENCY_PAY_NPOD'; --���˴��ջ���_���˵������Ӧ����Ӧ�մ��ջ���δǩ��
  AIR_COD_WO_OTH_NPOD            VARCHAR2(30) := 'AIR_COD_WO_OTH_NPOD'; --���˴��ջ���_��������Ӧ����Ӧ�մ��ջ���δǩ��
  APT_COM                        VARCHAR2(30) := 'APT_COM'; --Ԥ��_Ԥ�����չ�˾
  APT_WO_COM_PAY                 VARCHAR2(30) := 'APT_WO_COM_PAY'; --Ԥ��_Ԥ����Ӧ�����չ�˾
  APT_WO_OTH_PAY                 VARCHAR2(30) := 'APT_WO_OTH_PAY'; --Ԥ��_Ԥ��������Ӧ��
  BDR_WO_OTH_RCV                 VARCHAR2(30) := 'BDR_WO_OTH_RCV'; --���˳�����Ӧ��_���˳�����Ӧ��
  RD_WO_ORIG_RCV_POD             VARCHAR2(30) := 'RD_WO_ORIG_RCV_POD'; --���˷ѳ�ʼ��Ӧ����ǩ��
  RD_WO_ORIG_RCV_NPOD            VARCHAR2(30) := 'RD_WO_ORIG_RCV_NPOD'; --���˷ѳ�ʼ��Ӧ��δǩ��
  RD_WO_DEST_RCV_POD             VARCHAR2(30) := 'RD_WO_DEST_RCV_POD'; --���˷ѳ嵽��Ӧ����ǩ��
  RD_WO_DEST_RCV_NPOD            VARCHAR2(30) := 'RD_WO_DEST_RCV_NPOD'; --���˷ѳ嵽��Ӧ��δǩ��
  ---------------------------------------------------------------
  --��������ֶ�
  ---------------------------------------------------------------
  LAND_COST_AGENCY_GEN           VARCHAR2(30) := 'LAND_COST_AGENCY_GEN'; --��������Ӧ������
  LAND_COST_AGENCY_CFM           VARCHAR2(30) := 'LAND_COST_AGENCY_CFM'; --����乫˾�˷�ȷ��
  LAND_COST_AGENCY_NCFM          VARCHAR2(30) := 'LAND_COST_AGENCY_NCFM'; --��������Ӧ���ɱ���ȷ��
  LAND_COST_OTHER_CONFIRM        VARCHAR2(30) := 'LAND_COST_OTHER_CONFIRM'; --���������Ӧ���ɱ�ȷ��
  LAND_COST_PAY_APPLY            VARCHAR2(30) := 'LAND_COST_PAY_APPLY'; --�����ɱ���������
  LAND_OTH_ENTRY                 VARCHAR2(30) := 'LAND_OTH_ENTRY'; --���������Ӧ��¼��
  LAND_OTH_RCV_CH                VARCHAR2(30) := 'LAND_OTH_RCV_CH'; --�������������Ӧ���ֽ�
  LAND_OTH_RCV_CD                VARCHAR2(30) := 'LAND_OTH_RCV_CD'; --�������������Ӧ������
  LAND_COD_CH_NPOD               VARCHAR2(30) := 'LAND_COD_CH_NPOD'; --����仹����ջ����ֽ�δǩ��
  LAND_COD_CD_NPOD               VARCHAR2(30) := 'LAND_COD_CD_NPOD'; --����仹����ջ�������δǩ��
  LAND_COD_WO_AGENCY_PAY_NPOD    VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_NPOD'; --����䵽�����Ӧ����Ӧ�մ��ջ���δǩ��
  LAND_PR_OTH_WO_OTH_RCV         VARCHAR2(30) := 'LAND_PR_OTH_WO_OTH_RCV'; --���������Ӧ��������Ӧ��
  LAND_DR_CH                     VARCHAR2(30) := 'LAND_DR_CH'; --Ԥ�����������ֽ�
  LAND_DR_CD                     VARCHAR2(30) := 'LAND_DR_CD'; --Ԥ��������������
  LAND_DR_WO_OTHER_RCV           VARCHAR2(30) := 'LAND_DR_WO_OTHER_RCV'; --�����Ԥ���������������Ӧ��
  LAND_DR_WO_COD_RCV_NPOD        VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_NPOD'; --�����Ԥ�����������Ӧ�մ��ջ���δǩ��
  LAND_DR_PAY_APPLY              VARCHAR2(30) := 'LAND_DR_PAY_APPLY'; --�������Ԥ�ո�������
  LAND_COD_POD_NWO_COD           VARCHAR2(30) := 'LAND_COD_POD_NWO_COD'; --�����ǩ��ʱδ�������ջ���
  LAND_COD_CH_POD                VARCHAR2(30) := 'LAND_COD_CH_POD'; --����仹����ջ����ֽ���ǩ��
  LAND_COD_CD_POD                VARCHAR2(30) := 'LAND_COD_CD_POD'; --����仹����ջ���������ǩ��
  LAND_COD_NPOD_NWO_COD          VARCHAR2(30) := 'LAND_COD_NPOD_NWO_COD'; --����䷴ǩ��ʱδ�������ջ���
  LAND_COD_POD_WO_COD            VARCHAR2(30) := 'LAND_COD_POD_WO_COD'; --�����ǩ��ʱ�Ѻ������ջ���
  LAND_COD_NPOD_WO_COD           VARCHAR2(30) := 'LAND_COD_NPOD_WO_COD'; --����䷴ǩ��ʱ�Ѻ������ջ���
  LAND_COD_WO_AGENCY_PAY_POD     VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_POD'; --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
  LAND_COD_WO_OTH_PAY_COD        VARCHAR2(30) := 'LAND_COD_WO_OTH_PAY_COD'; --���������Ӧ����Ӧ�մ��ջ�����ǩ��
  LAND_PR_AGENCY_WO_DEST_RCV_POD VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_POD'; --�����Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
  LAND_PR_AGENCY_WO_DEST_RCV_NP  VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_NP'; --�����Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
  LAND_PR_OT_WO_DEST_RCV_POD     VARCHAR2(30) := 'LAND_PR_OT_WO_DEST_RCV_POD'; --���������Ӧ����Ӧ�յ����˷���ǩ��
  LAND_PR_OTH_WO_DEST_RCV_NPOD   VARCHAR2(30) := 'LAND_PR_OTH_WO_DEST_RCV_NPOD'; --���������Ӧ����Ӧ�յ����˷�δǩ��
  LAND_DR_DEST_RCV_POD           VARCHAR2(30) := 'LAND_DR_DEST_RCV_POD'; --�����Ԥ�����������Ӧ�յ����˷���ǩ��
  LAND_DR_DEST_RCV_NPOD          VARCHAR2(30) := 'LAND_DR_DEST_RCV_NPOD'; --�����Ԥ�����������Ӧ�յ����˷�δǩ��
  LAND_DR_WO_COD_RCV_POD         VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_POD'; --�����Ԥ�����������Ӧ�մ��ջ�����ǩ��
  LAND_UR_DEST_CH_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CH_NPOD'; --����仹���ֽ�δǩ��
  LAND_UR_DEST_CD_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CD_NPOD'; --����仹������δǩ��
  LAND_UR_DEST_CH_POD            VARCHAR2(30) := 'LAND_UR_DEST_CH_POD'; --����仹���ֽ���ǩ��
  LAND_UR_DEST_CD_POD            VARCHAR2(30) := 'LAND_UR_DEST_CD_POD'; --����仹��������ǩ��
  LAND_BDR_WO_OTH_RCV            VARCHAR2(30) := 'LAND_BDR_WO_OTH_RCV'; --����仵�˳�����Ӧ��_���˳�����Ӧ��
  LAND_APT_COM                   VARCHAR2(30) := 'LAND_APT_COM'; --�����Ԥ��_Ԥ�����չ�˾
  LAND_APT_WO_COM_PAY            VARCHAR2(30) := 'LAND_APT_WO_COM_PAY'; --�����Ԥ��_Ԥ����Ӧ�����չ�˾
  LAND_APT_WO_OTH_PAY            VARCHAR2(30) := 'LAND_APT_WO_OTH_PAY'; --�����Ԥ��_Ԥ��������Ӧ��
  LAND_COD_WO_OTH_NPOD           VARCHAR2(30) := 'LAND_COD_WO_OTH_NPOD'; --���������Ӧ����Ӧ�մ��ջ���δǩ��

  CUST_DR_ORIG_LAND_RCV_NPOD   VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_NPOD'; --Ԥ�տͻ�����Ӧ��ʼ���˷�δǩ��
  CUST_DR_ORIG_LAND_RCV_POD    VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_POD'; --Ԥ�տͻ�����Ӧ��ʼ���˷���ǩ��
  CLAIM_WO_ORIG_LAND_RCV_POD   VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_POD'; --�������ʼ��Ӧ����ǩ��
  CLAIM_WO_ORIG_LAND_RCV_NPOD  VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_NPOD'; --�������ʼ��Ӧ��δǩ��
  OR_LAND_RCV_WO_CLAIM_PAY     VARCHAR2(30) := 'OR_LAND_RCV_WO_CLAIM_PAY'; --Ӧ���������СƱӦ��
  OR_LAND_RCV_WO_CUST_DR       VARCHAR2(30) := 'OR_LAND_RCV_WO_CUST_DR'; --Ԥ�տͻ�����СƱӦ��
  REFUND_WO_ORIG_LAND_RCV_POD  VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_POD'; --���˷ѳ���ʼ��Ӧ����ǩ��
  REFUND_WO_ORIG_LAND_RCV_NPOD VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_NPOD'; --���˷ѳ���ʼ��Ӧ��δǩ��
  LAND_CLAIM_WO_ORIG_RCV_POD   VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_POD'; --��������ʼ��Ӧ����ǩ��
  LAND_CLAIM_WO_ORIG_RCV_NPOD  VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_NPOD'; --��������ʼ��Ӧ��δǩ��
  OR_RCV_WO_LAND_CLAIM_PAY     VARCHAR2(30) := 'OR_RCV_WO_LAND_CLAIM_PAY'; --���Ӧ�������СƱӦ��
  LAND_REFUND_WO_ORIG_RCV_POD  VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_POD'; --������˷ѳ�ʼ��Ӧ����ǩ��
  LAND_REFUND_WO_ORIG_RCV_NPOD VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_NPOD'; --������˷ѳ�ʼ��Ӧ��δǩ��

  -----------------------------------------------------------------
  -- ƾ֤��ʼ����
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
                                 P_BUSINESS_TYPE VARCHAR2 -- ����
                                 ); -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤�ɹ�
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
                                 P_BUSINESS_TYPE VARCHAR2 -- ����
                                 ); -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤ʧ��
  -----------------------------------------------------------------
  PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
                                 P_BUSINESS_TYPE VARCHAR2 -- ����
                                 ); -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤��ͳ�ƿ�ʼ����
  -----------------------------------------------------------------
  PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
                             ); -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤��ͳ�Ƴɹ�����
  -----------------------------------------------------------------
  PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
                             ); -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_COMMON IS

	-----------------------------------------------------------------
	-- ƾ֤��ʼ��¼
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'ƾ֤��ʼ��¼' || '״̬�쳣');
			RAISE_APPLICATION_ERROR(SQLCODE, '��¼ƾ֤��־����');
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'ƾ֤��ʼ��¼' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤�ɹ�
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- ��ѯ��¼
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_SUCCESS',
																				'ƾ֤�ɹ�' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤ʧ��
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- ��ѯ��¼
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_FAILURE',
																				'ƾ֤ʧ��' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�ƿ�ʼ����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_PROCESS',
																				'ƾ֤��ͳ�ƿ�ʼ����' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�Ƴɹ�����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_SUCCESS',
																				'ƾ֤��ͳ�Ƴɹ�����' || '״̬�쳣');
	END;

END PKG_STL_VCH_COMMON;
/
