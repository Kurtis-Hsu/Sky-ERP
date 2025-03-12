package com.vireosci.sky.common.jpa;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/// 可逻辑删除对象
@MappedSuperclass
@SQLRestriction("delete_time IS NULL")
@EntityListeners(LogicDeleteListener.class)
public interface LogicDeletable
{
    void setDeletedTime(LocalDateTime deletedTime);
}
