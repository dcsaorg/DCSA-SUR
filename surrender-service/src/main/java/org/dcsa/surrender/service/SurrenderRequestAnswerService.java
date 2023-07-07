package org.dcsa.surrender.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.dcsa.surrender.domain.persistence.entity.SurrenderRequest;
import org.dcsa.surrender.domain.persistence.entity.SurrenderRequestAnswer;
import org.dcsa.surrender.domain.persistence.repository.SurrenderRequestAnswerRepository;
import org.dcsa.surrender.service.mapping.SurrenderRequestAnswerCodeMapper;
import org.dcsa.surrender.transferobjects.SurrenderRequestAnswerTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SurrenderRequestAnswerService {

  private final SurrenderRequestAnswerCodeMapper surrenderRequestCodeAnswerMapper;
  private final SurrenderRequestAnswerRepository surrenderRequestAnswerRepository;

  @Transactional(Transactional.TxType.REQUIRED)
  public void processSurrenderRequest(SurrenderRequestAnswerTO surrenderRequestAnswerTO) {
    var pendingRequest = getPendingRequestFor(surrenderRequestAnswerTO.surrenderRequestReference()).orElse(null);
    if (pendingRequest == null || pendingRequest.getAction() != null) {
      throw new IllegalStateException("Not expecting a surrender request for " + surrenderRequestAnswerTO.surrenderRequestReference());
    }
    pendingRequest.receiveSurrenderRequestAnswer(
      surrenderRequestCodeAnswerMapper.toDAO(surrenderRequestAnswerTO.action()),
      surrenderRequestAnswerTO.comments()
    );
    surrenderRequestAnswerRepository.save(pendingRequest);
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public void ensurePendingSurrenderRequest(String surrenderRequestReference) {
    var existing = getPendingRequestFor(surrenderRequestReference).orElse(null);
    if (existing != null && existing.getSurrenderRequestReference() == null) {
      return;
    }
    var request = SurrenderRequestAnswer.builder().surrenderRequestReference(surrenderRequestReference).build();
    surrenderRequestAnswerRepository.save(request);
  }

  public boolean hasPendingRequestFor(String surrenderRequestReference) {
    return getPendingRequestFor(surrenderRequestReference).isPresent();
  }

  public Optional<SurrenderRequestAnswer> getPendingRequestFor(String surrenderRequestReference) {
    return surrenderRequestAnswerRepository.findFirstBySurrenderRequestReferenceOrderByCreatedDateTimeDesc(
      surrenderRequestReference
    );
  }
}
