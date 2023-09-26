package org.dcsa.surrender.domain.persistence.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "endorsement_chain_link")
public class EndorsementChainLink {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "surrender_request")
  private SurrenderRequest surrenderRequest;

  @Column(name = "action_date_time", nullable = false)
  private ZonedDateTime actionDateTime;

  // Surrender Request assumes the actor is covered by equals
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "actor")
  private TransactionParty actor;

  // Surrender Request assumes the recipient is covered by equals
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "recipient")
  private TransactionParty recipient;

}
