package com.zigar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zigar.api.entity.ActionEntity;
import com.zigar.user.mapper.ActionMapper;
import com.zigar.user.service.ActionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作项表 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-28
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, ActionEntity> implements ActionService {

}
