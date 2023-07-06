package org.dcsa.surrender.transferobjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.dcsa.surrender.transferobjects.enums.EndorsementActionCodeTO;

import java.time.ZonedDateTime;

public record EndorsementChainLinkTO(
  @NotNull
  EndorsementActionCodeTO action,
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
