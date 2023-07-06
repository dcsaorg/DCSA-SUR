package org.dcsa.surrender.controller.unofficial;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.dcsa.surrender.service.SurrenderRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

record PendingSurrenderRequestTO(
  @NotNull
  @Size(max = 20)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String transportDocumentReference
  ) {
}

@Validated
@RestController
@RequiredArgsConstructor
public class UnofficialSurrenderRequestController {

  private final SurrenderRequestService surrenderRequestService;

  @PutMapping(path = "${spring.application.sur-context-path}" + "/unofficial/ensure-pending-surrender-request")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ensurePendingSurrenderRequest(
      @Valid @RequestBody PendingSurrenderRequestTO pendingSurrenderRequestTO
  ) {
    var tdRef = pendingSurrenderRequestTO.transportDocumentReference();
    surrenderRequestService.ensurePendingSurrenderRequest(tdRef);
  }
}
