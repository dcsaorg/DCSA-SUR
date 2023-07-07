package org.dcsa.surrender.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.dcsa.surrender.service.SurrenderRequestAnswerService;
import org.dcsa.surrender.transferobjects.SurrenderRequestAnswerTO;
import org.dcsa.surrender.transferobjects.enums.SurrenderRequestAnswerCodeTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class SurrenderRequestAnswerController {

  private final SurrenderRequestAnswerService surrenderRequestAnswerService;

  @PutMapping(path = "${spring.application.sur-rsp-context-path}" + "/surrender-request-responses")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newSurrenderRequest(@Valid @RequestBody SurrenderRequestAnswerTO surrenderRequestAnswerTO) {

    if (surrenderRequestAnswerTO.action() == SurrenderRequestAnswerCodeTO.SREJ && isNullOrEmpty(surrenderRequestAnswerTO.comments())) {
      throw ConcreteRequestErrorMessageException.invalidInput("The comments field must be non-empty when rejecting a surrender request");
    }
    try {
      surrenderRequestAnswerService.processSurrenderRequest(surrenderRequestAnswerTO);
    } catch (IllegalArgumentException|IllegalStateException e) {
      if (!surrenderRequestAnswerService.hasPendingRequestFor(surrenderRequestAnswerTO.surrenderRequestReference())) {
        throw ConcreteRequestErrorMessageException.invalidInput("Did not expect an answer for"
          + " surrender request reference: "
          + surrenderRequestAnswerTO.surrenderRequestReference());
      }
      throw e;
    }
  }


  private boolean isNullOrEmpty(String s) {
    return s == null || s.trim().equals("");
  }
}
