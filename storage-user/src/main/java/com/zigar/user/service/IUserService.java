package com.zigar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.api.entity.UserEntity;
import com.zigar.zigarcore.model.Results;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 更新或新增用户
     *
     * @param userEntity
     */
    void saveOrUpdateUser(UserEntity userEntity);


    /**
     * 用户登录，成功返回token
     *
     * @param userEntity
     * @return
     */
    Results<String> userLogin(UserEntity userEntity);

}
