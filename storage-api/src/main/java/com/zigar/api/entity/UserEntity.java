package com.zigar.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.myabtisplus.Unique;
import com.zigar.zigarcore.utils.DateUtils;
import com.zigar.zigarcore.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_user")
@ApiModel(value = "UserEntity对象", description = "用户")
@NoArgsConstructor
public class UserEntity implements Serializable, UserDetails {

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.pwdResetTime = DateUtils.now();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId("user_id_")
    private String userId;

    @NotBlank(groups = RequestInsertAction.class, message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @TableField("username_")
    @Unique
    private String username;

    @NotBlank(groups = RequestInsertAction.class, message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    @TableField("password_")
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField("display_name_")
    private String displayName;

    @ApiModelProperty(value = "性别")
    @TableField("sex_")
    private String sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone_")
    @Unique
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time_")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonIgnore
    @TableField(value = "pwd_reset_time_")
    private LocalDateTime pwdResetTime;

    @JsonIgnore
    @TableField(value = "roles_")
    private String roles;

    @JsonIgnore
    @TableField(value = "is_account_non_expired_")
    private Boolean isAccountNonExpired;

    @JsonIgnore
    @TableField(value = "is_account_non_locked_")
    private Boolean isAccountNonLocked;

    @JsonIgnore
    @TableField(value = "is_credentials_non_expired_")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled_", fill = FieldFill.INSERT)
    private Boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (!StringUtils.isEmpty(roles)) {
            String[] roleStrArr = StringUtils.split(roles, ",");
            if (!ArrayUtils.isEmpty(roleStrArr)) {
                for (String roleStr : roleStrArr) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleStr);
                    grantedAuthorityList.add(simpleGrantedAuthority);
                }
            }
        }
        return grantedAuthorityList;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }


}
