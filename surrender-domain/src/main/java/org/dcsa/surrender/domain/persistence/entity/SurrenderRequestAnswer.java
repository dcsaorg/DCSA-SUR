package org.dcsa.surrender.domain.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.*;
import lombok.*;
import org.dcsa.surrender.domain.persistence.entity.enums.SurrenderRequestAnswerCode;
import org.springframework.data.annotation.CreatedDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "surrender_request_answer")
public class SurrenderRequestAnswer {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "surrender_request_reference", length = 100, nullable = false)
  private String surrenderRequestReference;

  @Column(name = "comments", length = 255)
  private String comments;

  @Column(name = "action")
  @Enumerated(EnumType.STRING)
  private SurrenderRequestAnswerCode action;

  @CreatedDate
  @Column(name = "created_date_time", nullable = false, updatable = false, insertable = false)
  private ZonedDateTime createdDateTime;

  public void receiveSurrenderRequestAnswer(
    @NotNull SurrenderRequestAnswerCode action,
    String comments
  ) {
    if (isNotNullAndDifferent(this.action, action)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed surrender request code");
    }
    if (isNotNullAndDifferent(this.comments, comments)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed comments");
    }
    this.surrenderRequestReference = surrenderRequestReference;
    this.action = action;
  }


  private <T> boolean isNotNullAndDifferent(T l, T r) {
    return l != null && !Objects.equals(l, r);
  }
}
