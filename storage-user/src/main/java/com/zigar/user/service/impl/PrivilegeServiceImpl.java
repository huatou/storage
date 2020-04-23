package com.zigar.user.service.impl;

import com.zigar.api.entity.PrivilegeEntity;
import com.zigar.user.mapper.PrivilegeMapper;
import com.zigar.user.service.PrivilegeService;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Service
public class PrivilegeServiceImpl extends ZServiceImpl<PrivilegeMapper, PrivilegeEntity> implements PrivilegeService {

}
