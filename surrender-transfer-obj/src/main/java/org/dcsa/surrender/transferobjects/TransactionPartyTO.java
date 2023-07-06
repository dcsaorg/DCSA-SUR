package org.dcsa.surrender.transferobjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public record TransactionPartyTO(

  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+@\\S+$")
  String eblPlatformIdentifier,

  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String legalName,

  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String registrationNumber,

  @Size(min = 2, max = 2)
  @Pattern(regexp = "^[A-Z]{2}$")
  String locationOfRegistration,
  @Size(max = 100)
  @Pattern(regexp = "^\\S+$")
  String taxReference,

  List<@Valid SupportingPartyCodeTO> supportingPartyCodes
) {
  @Builder(toBuilder = true)
  public TransactionPartyTO {}
}
