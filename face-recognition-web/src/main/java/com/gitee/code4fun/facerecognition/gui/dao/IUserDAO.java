package com.gitee.code4fun.facerecognition.gui.dao;

import com.gitee.code4fun.facerecognition.gui.vo.UserVO;

/**
 * @author yujingze
 * @data 18/4/8
 */
public interface IUserDAO {

    String getUserIdByMapping(String categoryId) throws Exception;

    UserVO getUserById(String userId) throws Exception;

}
