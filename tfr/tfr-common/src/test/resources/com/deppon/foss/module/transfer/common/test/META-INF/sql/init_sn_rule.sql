create table T_OPT_SN_RULE
(
  code         VARCHAR2(50),
  name         VARCHAR2(100),
  current_time DATE,
  current_num  NUMBER(9)
)

-- Create/Recreate indexes 
create unique index IDX_OPT_SN_RULE on T_OPT_SN_RULE (CODE)
  tablespace SYSTEM
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

--drop sequence
drop sequence TFR.SEQ_SN_DC;
drop sequence TFR.SEQ_SN_ZDJJD;
drop sequence TFR.SEQ_SN_ZZ;
drop sequence TFR.SEQ_SN_HP;
drop sequence TFR.SEQ_SN_CLRW;
drop sequence TFR.SEQ_SN_FX;
drop sequence TFR.SEQ_SN_WBQ;
drop sequence TFR.SEQ_SN_QC;
drop sequence TFR.SEQ_SN_YC;
drop sequence TFR.SEQ_SN_JC;
drop sequence TFR.SEQ_SN_WQCYC;
drop sequence TFR.SEQ_SN_JJD_PDA;
drop sequence TFR.SEQ_SN_JJD;

-- Create sequence 
create sequence TFR.SEQ_SN_DC
minvalue 1
maxvalue 999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_ZDJJD
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_ZZ
minvalue 1
maxvalue 9999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_HP
minvalue 1
maxvalue 9999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_CLRW
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_FX
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_WBQ
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_QC
minvalue 1
maxvalue 999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_YC
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_JC
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_WQCYC
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_JJD_PDA
minvalue 1
maxvalue 9999999
start with 1
increment by 1
cache 20;

create sequence TFR.SEQ_SN_JJD
minvalue 1
maxvalue 9999999
start with 1
increment by 1
cache 20;