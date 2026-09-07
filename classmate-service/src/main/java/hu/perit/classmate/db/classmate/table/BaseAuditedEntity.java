/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.classmate.db.classmate.table;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;


/**
 * @author nagy_peter
 */
@Getter
@Setter
@MappedSuperclass
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public abstract class BaseAuditedEntity<T extends Serializable> extends BaseEntity<T>
{
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_UPDATED_BY = "updated_by";

    @CreatedBy
    @Size(max = 150)
    @Column(name = COL_CREATED_BY, nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = COL_CREATED_AT, nullable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Size(max = 150)
    @Column(name = COL_UPDATED_BY)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = COL_UPDATED_AT)
    private Instant updatedAt;


    public OffsetDateTime getCreatedAt()
    {
        return createdAt == null ? null : OffsetDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }


    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt == null ? null : OffsetDateTime.ofInstant(updatedAt, ZoneId.systemDefault());
    }
}
