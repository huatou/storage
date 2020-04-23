package com.zigar.user.service.impl;

import com.zigar.api.entity.RoleEntity;
import com.zigar.user.mapper.RoleMapper;
import com.zigar.user.service.RoleService;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Service
public class RoleServiceImpl extends ZServiceImpl<RoleMapper, RoleEntity> implements RoleService {

}
