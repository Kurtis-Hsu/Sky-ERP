package com.vireosci.sky.common.jpa;

import jakarta.persistence.PreRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class LogicDeleteListener
{
    private static final Logger log = LoggerFactory.getLogger(LogicDeleteListener.class);

    @PreRemove
    public void preRemove(LogicDeletable entity)
    {
        entity.setDeletedTime(LocalDateTime.now());
        log.debug("已设置逻辑删除: {}", entity);
    }
}
