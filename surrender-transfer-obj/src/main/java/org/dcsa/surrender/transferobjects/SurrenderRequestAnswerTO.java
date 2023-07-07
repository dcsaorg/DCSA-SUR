package org.dcsa.surrender.transferobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.dcsa.surrender.transferobjects.enums.SurrenderRequestAnswerCodeTO;

public record SurrenderRequestAnswerTO(

  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String surrenderRequestReference,

  @NotNull
  SurrenderRequestAnswerCodeTO action,

  @Size(max = 255)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String comments
) {
  @Builder(toBuilder = true)
  public SurrenderRequestAnswerTO {}
}
