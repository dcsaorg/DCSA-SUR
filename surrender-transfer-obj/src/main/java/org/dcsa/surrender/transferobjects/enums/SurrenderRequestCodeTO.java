package org.dcsa.surrender.transferobjects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SurrenderRequestCodeTO {
  SREQ("Surrender for delivery"),
  AREQ("Surrender for amendment"),
  ;

  @Getter
  private final String value;

}
