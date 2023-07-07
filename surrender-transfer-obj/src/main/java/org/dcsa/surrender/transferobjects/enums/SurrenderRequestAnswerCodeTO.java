package org.dcsa.surrender.transferobjects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SurrenderRequestAnswerCodeTO {
  SURR("Surrendered"),
  SREJ("Surrender rejected"),
  ;

  @Getter
  private final String value;

}
