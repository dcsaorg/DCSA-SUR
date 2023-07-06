package org.dcsa.surrender.transferobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.dcsa.surrender.transferobjects.enums.SurrenderRequestCodeTO;

public record IssuanceRequestResponseTO(
  @NotNull
  @Size(max = 20)
  String transportDocumentReference,
  @NotNull
  SurrenderRequestCodeTO issuanceResponseCode,
  @Size(max = 255)
  String reason

) {
  @Builder(toBuilder = true)
  public IssuanceRequestResponseTO {}
}
