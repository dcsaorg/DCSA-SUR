package org.dcsa.surrender.domain.persistence.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SurrenderRequestAnswerCode {
  SURR("Surrendered"),
  SREJ("Surrender rejected"),
  ;

  @Getter
  private final String value;

}
