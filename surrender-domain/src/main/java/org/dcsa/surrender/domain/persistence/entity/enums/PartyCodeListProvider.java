package org.dcsa.surrender.domain.persistence.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyCodeListProvider {
  LEI("Legal Entity Identifier (LEI)"),
  DID("Decentralized Identifier"),
  ;

  private final String value;
}
