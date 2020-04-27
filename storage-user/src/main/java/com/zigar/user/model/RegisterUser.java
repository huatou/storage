package com.zigar.user.model;

import com.zigar.api.entity.UserEntity;
import com.zigar.zigarcore.action.RequestInsertAction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author Zigar
 * @createTime 2020-04-27 20:16:35
 * @description 注册用户模型
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterUser extends UserEntity {

}
