package com.gitee.code4fun.facerecognition.gui.dao.impl;

import com.gitee.code4fun.facerecognition.gui.dao.IUserDAO;
import com.gitee.code4fun.facerecognition.gui.vo.UserVO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yujingze
 * @data 18/4/8
 */
@Repository
public class UserDAOImpl implements IUserDAO{

    @Autowired
    QueryRunner queryRunner;

    public String getUserIdByMapping(String categoryId) throws Exception {

        String userId = queryRunner.query("SELECT USER_ID FROM T_USER_MAPPING WHERE CATEGORY_ID = ?", new ResultSetHandler<String>() {
            public String handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                    return resultSet.getString("USER_ID");
                }else{
                    return null;
                }
            }
        },categoryId);



        return userId;
    }

    public UserVO getUserById(String userId) throws Exception {

        UserVO userVO = queryRunner.query("SELECT * FROM T_USER_INFO WHERE USER_ID = ?", new ResultSetHandler<UserVO>() {
            public UserVO handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                    UserVO vo = new UserVO();
                    vo.setUserId(resultSet.getString("USER_ID"));
                    vo.setUserName(resultSet.getString("USER_NAME"));
                    vo.setUserPosition(resultSet.getString("USER_POSITION"));
                    vo.setUserDepartment(resultSet.getString("USER_DEPARTMENT"));
                    return vo;
                }else{
                    return null;
                }
            }
        }, userId);

        return userVO;
    }
}
