package org.dcsa.surrender.controller;

import jakarta.validation.Valid;
import java.util.EnumSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.dcsa.surrender.service.SurrenderRequestService;
import org.dcsa.surrender.transferobjects.SurrenderRequestTO;
import org.dcsa.surrender.transferobjects.TransactionPartyTO;
import org.dcsa.surrender.transferobjects.enums.PartyCodeListProviderTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class SurrenderRequestController {

  private final SurrenderRequestService surrenderRequestService;

  @PutMapping(path = "${spring.application.sur-context-path}" + "/surrender-requests")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newSurrenderRequest(@Valid @RequestBody SurrenderRequestTO surrenderRequestTO) {
    validateTransactionParty(surrenderRequestTO.surrenderRequestedBy());
    for (var endorsementChainLink : surrenderRequestTO.endorsementChain()) {
      validateTransactionParty(endorsementChainLink.actor());
      if (endorsementChainLink.recipient() != null) {
        validateTransactionParty(endorsementChainLink.recipient());
      }
    }
    try {
      surrenderRequestService.processSurrenderRequest(surrenderRequestTO);
    } catch (IllegalArgumentException|IllegalStateException e) {
      if (!surrenderRequestService.hasPendingRequestFor(surrenderRequestTO.transportDocumentReference())) {
        throw ConcreteRequestErrorMessageException.invalidInput("Did not expect a surrender request for"
          + " transport document reference: "
          + surrenderRequestTO.transportDocumentReference());
      }
      throw e;
    }
  }

  private void validateTransactionParty(TransactionPartyTO transactionPartyTO) {
    var supportingPartyCodeTOs = transactionPartyTO.supportingPartyCodes();
    if (supportingPartyCodeTOs == null) {
      return;
    }
    Set<PartyCodeListProviderTO> codeListProviderSet = EnumSet.noneOf(PartyCodeListProviderTO.class);
    for (var supportingPartyCode : supportingPartyCodeTOs) {
      if (!codeListProviderSet.add(supportingPartyCode.partyCodeListProvider())) {
        throw ConcreteRequestErrorMessageException.invalidInput("The \"issueTo\" party had two supporting party codes"
          + " with code list provider \"" + supportingPartyCode.partyCodeListProvider() + "\". That code list provider"
          + " must be used at most once.");
      }
    }
  }
}
