create or replace procedure refresh_mv(mvname in varchar2)
is
begin
      dbms_refresh.refresh(mvname);
      commit;
exception
      when others then
      -- dbms_output.put_line(sqlcode||' '|| SQLERRM);
      commit;
end;