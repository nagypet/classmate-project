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

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;


/**
 * @author nagy_peter
 */
@Getter
@Setter
@MappedSuperclass
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public abstract class BaseEntity<T extends Serializable>
{
    public static final String COL_REC_VERSION = "rec_version";


    @JsonIgnore
    @Version
    @Column(name = COL_REC_VERSION, nullable = false)
    private Long recVersion = 0L;


    public abstract T getId();


    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof BaseEntity<?> that))
        {
            return false;
        }

        return new EqualsBuilder().append(getId(), that.getId()).isEquals();
    }


    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }
}
