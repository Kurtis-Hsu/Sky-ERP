package com.vireosci.sky.common.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/// 实体基类
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements LogicDeletable, Serializable
{
    /// 备注
    @Comment("备注")
    @Column(columnDefinition = "TEXT")
    private String remark;

    /// 数据创建时间
    @Comment("数据创建时间")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    /// 数据上次修改时间
    @Comment("数据上次修改时间")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateTime;

    /// 数据删除时间，不为 null 表示未删除
    @Comment("数据删除时间，不为 null 表示未删除")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedTime;

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public LocalDateTime getCreatedTime() { return createdTime; }

    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }

    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public LocalDateTime getDeletedTime() { return deletedTime; }

    public void setDeletedTime(LocalDateTime deletedTime) { this.deletedTime = deletedTime; }

    private static final ObjectMapper debugObjectMapper = new ObjectMapper()

            .registerModule(
                    new JavaTimeModule().addSerializer(
                            LocalDateTime.class,
                            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    )
            );

    /// 使用 Jackson 打印实体类用于 debug
    @Override
    public String toString()
    {
        try
        {
            return debugObjectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
