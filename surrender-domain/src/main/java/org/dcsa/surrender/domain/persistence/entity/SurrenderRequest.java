package org.dcsa.surrender.domain.persistence.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dcsa.surrender.domain.persistence.entity.enums.SurrenderRequestCode;
import org.springframework.data.annotation.CreatedDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "surrender_request")
public class SurrenderRequest {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "surrender_request_reference", length = 100, nullable = false)
  private String surrenderRequestReference;

  @Column(name = "transport_document_reference", length = 20, nullable = false)
  private String transportDocumentReference;


  @Column(name = "comments", length = 255)
  private String comments;

  @Column(name = "surrender_request_code", nullable = false)
  @Enumerated(EnumType.STRING)
  private SurrenderRequestCode surrenderRequestCode;

  @CreatedDate
  @Column(name = "created_date_time", nullable = false, updatable = false, insertable = false)
  private ZonedDateTime createdDateTime;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "surrender_requested_by")
  private TransactionParty surrenderRequestedBy;

  @OrderColumn(name = "entry_order")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "surrenderRequest", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<EndorsementChainLink> endorsementChain;

  public void receiveSurrenderRequest(@NotNull String surrenderRequestReference,
                                      @NotNull SurrenderRequestCode surrenderRequestCode,
                                      String comments,
                                      @NotNull TransactionParty surrenderRequestedBy,
                                      @NotNull List<EndorsementChainLink> endorsementChain
                                      ) {
    if (isNotNullAndDifferent(this.surrenderRequestReference, surrenderRequestReference)) {
      throw new IllegalStateException("New surrender request reference when we were not expecting a new surrender"
        + " request for transport document reference " + this.transportDocumentReference);
    }
    if (isNotNullAndDifferent(this.surrenderRequestCode, surrenderRequestCode)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed surrender request code");
    }
    if (isNotNullAndDifferent(this.comments, comments)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed comments");
    }
    if (isNotNullAndDifferent(this.surrenderRequestedBy, surrenderRequestedBy)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed comments");
    }
    // If this entry is pulled out of the DB, then the endorsement chain is set to an empty "PeristentList" rather than null
    // Cope with that here.
    if (this.endorsementChain != null && !this.endorsementChain.isEmpty() && !this.endorsementChain.equals(endorsementChain)) {
      throw new IllegalStateException("Surrender request " + surrenderRequestReference
        + " changed the endorsement chain");
    }
    this.surrenderRequestReference = surrenderRequestReference;
    this.surrenderRequestCode = surrenderRequestCode;
    this.comments = comments;
    if (this.surrenderRequestedBy == null) {
      this.surrenderRequestedBy = surrenderRequestedBy;
    }
    if (this.endorsementChain == null) {
      this.endorsementChain = new ArrayList<>(endorsementChain.size());
    }
    if (this.endorsementChain.isEmpty()) {
      // We should not have to do this. However, I am too tired fighting with JPA.
      // Patches welcome to do it the proper way.
      for (var link : endorsementChain) {
        this.endorsementChain.add(link.toBuilder().surrenderRequest(this).build());
      }
    }
  }


  private <T> boolean isNotNullAndDifferent(T l, T r) {
    return l != null && !Objects.equals(l, r);
  }
}
