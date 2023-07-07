package org.dcsa.surrender.controller.unofficial;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.dcsa.surrender.service.SurrenderRequestAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

record PendingSurrenderRequestAnswerTO(
  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String surrenderRequestReference
  ) {
}

@Validated
@RestController
@RequiredArgsConstructor
public class UnofficialSurrenderRequestAnswerController {

  private final SurrenderRequestAnswerService surrenderRequestAnswerService;

  @PutMapping(path = "${spring.application.sur-rsp-context-path}" + "/unofficial/ensure-pending-surrender-request-answer")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ensurePendingSurrenderRequest(
      @Valid @RequestBody PendingSurrenderRequestAnswerTO pendingSurrenderRequestTO
  ) {
    var ref = pendingSurrenderRequestTO.surrenderRequestReference();
    surrenderRequestAnswerService.ensurePendingSurrenderRequest(ref);
  }
}
