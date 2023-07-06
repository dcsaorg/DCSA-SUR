package org.dcsa.surrender.domain.persistence.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EndorsementActionCode {
  ETOF("Endorse the EBL \"To Order of endorsee\" - also called \"Closed Endorsed\""),
  ETOO("Endorse the EBL \"To Order of endorsee or Order\" - also called \"Open Endorsed\""),
  ETOB("Endorse the EBL \"To Order\" with blank endorsee"),
  AEND("Accept endorsement - eqv. of the stamping/signing the BL in the paper world"),
  ;

  @Getter
  private final String value;

}
