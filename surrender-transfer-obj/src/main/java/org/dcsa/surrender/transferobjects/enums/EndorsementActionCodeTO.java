package org.dcsa.surrender.transferobjects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EndorsementActionCodeTO {
  ETOF("Endorse the EBL \"To Order of endorsee\" - also called \"Closed Endorsed\"", true),
  ETOO("Endorse the EBL \"To Order of endorsee or Order\" - also called \"Open Endorsed\"", true),
  ETOB("Endorse the EBL \"To Order\" with blank endorsee", false),
  AEND("Accept endorsement - eqv. of the stamping/signing the BL in the paper world", false),
  ;

  @Getter
  private final String value;

  private final boolean hasRecipient;

  public boolean hasRecipient() {
    return this.hasRecipient;
  }

}
