package org.dcsa.surrender.transferobjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.Builder;

public record EndorsementChainLinkTO(
  @NotNull
  ZonedDateTime actionDateTime,

  @Valid
  @NotNull
  TransactionPartyTO actor,

  @Valid
  TransactionPartyTO recipient
) {
  @Builder(toBuilder = true)
  public EndorsementChainLinkTO {}
}
