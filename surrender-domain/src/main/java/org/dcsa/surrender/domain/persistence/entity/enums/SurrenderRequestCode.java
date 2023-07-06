package org.dcsa.surrender.domain.persistence.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SurrenderRequestCode {
  SREQ("Surrender for delivery"),
  AREQ("Surrender for amendment"),
  ;

  @Getter
  private final String value;

}
