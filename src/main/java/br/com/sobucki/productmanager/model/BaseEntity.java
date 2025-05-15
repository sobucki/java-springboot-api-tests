package br.com.sobucki.productmanager.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(name = "created_date", nullable = false, updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  @Column(name = "last_updated_date", nullable = false)
  private Instant lastUpdatedDate;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;

  public Instant getCreatedDate() {
    return createdDate;
  }

  public Instant getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  protected void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  protected void setLastUpdatedDate(Instant lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }

  protected void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  protected void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
