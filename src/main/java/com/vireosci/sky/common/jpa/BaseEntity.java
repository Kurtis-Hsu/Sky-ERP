package com.vireosci.sky.common.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/// 实体基类
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable
{
    /// 备注
    @Comment("备注")
    @Column(columnDefinition = "TEXT")
    private String remark;

    /// 数据创建人
    @Comment("数据创建人")
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    /// 数据创建时间
    @Comment("数据创建时间")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /// 数据上次修改人
    @Comment("数据上次修改人")
    @LastModifiedBy
    private String modifiedBy;

    /// 数据上次修改时间
    @Comment("数据上次修改时间")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    /// 数据删除时间，不为 null 表示未删除
    @Comment("数据删除时间，不为 null 表示未删除")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getModifiedBy() { return modifiedBy; }

    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }

    public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

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
