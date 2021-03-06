package safe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionUtil;

/**
 * 对Clob操作的例子 
 * 
 * @author yangwm in Feb 5, 2009 9:35:38 AM
 */
public class ClobSample {

    /**
     * 
     * 
     * create yangwm in Feb 5, 2009 9:35:38 AM
     * @param userName
     * @param resName
     * @param resIP
     * @param resType
     * @param showRun
     * @param exeCuteTime
     */
    public void add(String userName, String resName, String resIP, String resType, String showRun,String exeCuteTime) {
        Connection conn = null;
        ResultSet rs = null;
        String sql = null;
        PreparedStatement pstm = null;
        sql = " insert into RCM_CHECK_TABLE(user_name,res_name,res_ip,res_type,showrun,executeTime) "
            + " values(?,?,?,?,?,?) ";
        try {
            conn = ConnectionUtil.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, resName);
            pstm.setString(3, resIP);
            pstm.setString(4, resType);
            pstm.setClob(5, LobUtil.createTemporaryClob(conn, showRun));
            pstm.setString(6, exeCuteTime);
            pstm.execute(); 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(conn, pstm, rs);
        }
    }
    
    public static void main(String... args) {
        String userName = "yangwm";
        String resName = "yangwm's computer";
        String resIP = "127.0.0.1";
        String resType = "windows";
        StringBuilder showRun = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            //showRun.append("zhu zhu zhu ...");
            showRun.append("zhu zhu zhu ... 哈 哈 哈 。。。");
        }
        String exeCuteTime = "1:12";
        new ClobSample().add(userName, resName, resIP, resType, showRun.toString(), exeCuteTime);
    }

}

/*

CREATE TABLE RCM_CHECK_TABLE
(   id NUMBER,
    user_name VARCHAR2(64),
    res_name VARCHAR2(64), 
    res_ip VARCHAR2(64), 
    res_type VARCHAR2(64),
    showrun CLOB,
    executeTime NVARCHAR2(255),
    PRIMARY KEY(id)
) TABLESPACE AMS;

create sequence SEQ_RCM_CHECK;

create trigger TRI_RCM_CHECK before insert on RCM_CHECK_TABLE for each row
begin
    select SEQ_RCM_CHECK.nextval into :new.id from dual;
end;


CREATE TABLE RCM_CHECK_TABLE
(   ID NUMBER(9) NOT NULL, 
    attemper_bid VARCHAR2(64) NOT NULL,
    pertask_bid VARCHAR2(64) NOT NULL,
    res_name VARCHAR2(64), 
    res_ip VARCHAR2(64), 
    res_type VARCHAR2(64),
    echo_result CLOB, 
    parse_result CLOB, 
    executeTime NVARCHAR2(255),  
    PRIMARY KEY(ID)
) 
TABLESPACE AMS;

CREATE   sequence   SEQ_RCM_CHECK
   INCREMENT   BY   1
   START   WITH   1
   NOMAXVALUE
   NOCYCLE
   CACHE   10;


create trigger TRI_RCM_CHECK
before insert
on RCM_CHECK_TABLE
for each row
begin
select SEQ_RCM_CHECK.nextval into :new.id
from dual;
end;


*/
