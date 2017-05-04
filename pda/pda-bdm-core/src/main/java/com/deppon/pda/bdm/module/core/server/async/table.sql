create table async_job_lock(
       id varchar2(20),
       value number(1)
)

create table async_job_active(
       id varchar2(50),
       lastActiveTime Date
)