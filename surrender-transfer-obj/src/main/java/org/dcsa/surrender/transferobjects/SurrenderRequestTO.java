package org.dcsa.surrender.transferobjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import org.dcsa.surrender.transferobjects.enums.SurrenderRequestCodeTO;

public record SurrenderRequestTO(

  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String surrenderRequestReference,

  @NotNull
  @Size(max = 20)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String transportDocumentReference,

  @NotNull
  SurrenderRequestCodeTO surrenderRequestCode,

  @Size(max = 255)
  String comments,

  @Valid
  @NotNull
  TransactionPartyTO surrenderRequestedBy,

  @NotNull
  List<@Valid EndorsementChainLinkTO> endorsementChain
) {
  @Builder(toBuilder = true)
  public SurrenderRequestTO {}
}
