package org.dcsa.surrender.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.dcsa.surrender.domain.persistence.entity.SurrenderRequest;
import org.dcsa.surrender.domain.persistence.repository.SurrenderRequestRepository;
import org.dcsa.surrender.service.mapping.EndorsementChainMapper;
import org.dcsa.surrender.service.mapping.SurrenderRequestCodeMapper;
import org.dcsa.surrender.service.mapping.TransactionPartyMapper;
import org.dcsa.surrender.transferobjects.SurrenderRequestTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SurrenderRequestService {

  private final SurrenderRequestCodeMapper surrenderRequestCodeMapper;
  private final SurrenderRequestRepository surrenderRequestRepository;
  private final TransactionPartyMapper transactionPartyMapper;
  private final EndorsementChainMapper endorsementChainMapper;

  @Transactional(Transactional.TxType.REQUIRED)
  public void processSurrenderRequest(SurrenderRequestTO surrenderRequestTO) {
    var pendingRequest = getPendingRequestFor(surrenderRequestTO.transportDocumentReference()).orElse(null);
    if (pendingRequest == null || pendingRequest.getSurrenderRequestReference() != null) {
      throw new IllegalStateException("Not expecting a surrender request for " + surrenderRequestTO.transportDocumentReference());
    }
    pendingRequest.receiveSurrenderRequest(
      surrenderRequestTO.surrenderRequestReference(),
      surrenderRequestCodeMapper.toDAO(surrenderRequestTO.surrenderRequestCode()),
      surrenderRequestTO.comments(),
      transactionPartyMapper.toDAO(surrenderRequestTO.surrenderRequestedBy()),
      surrenderRequestTO.endorsementChain().stream().map(endorsementChainMapper::toDAO).toList()
    );
    surrenderRequestRepository.save(pendingRequest);
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public void ensurePendingSurrenderRequest(String transportDocumentReference) {
    var existing = getPendingRequestFor(transportDocumentReference).orElse(null);
    if (existing != null && existing.getSurrenderRequestReference() == null) {
      return;
    }
    var request = SurrenderRequest.builder().transportDocumentReference(transportDocumentReference).build();
    surrenderRequestRepository.save(request);
  }

  public boolean hasPendingRequestFor(String transportDocumentReference) {
    return getPendingRequestFor(transportDocumentReference).isPresent();
  }

  public Optional<SurrenderRequest> getPendingRequestFor(String transportDocumentReference) {
    return surrenderRequestRepository.findFirstByTransportDocumentReferenceOrderByCreatedDateTimeDesc(
      transportDocumentReference
    );
  }
}
