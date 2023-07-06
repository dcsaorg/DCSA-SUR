package org.dcsa.surrender.domain.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.dcsa.surrender.domain.persistence.entity.SurrenderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurrenderRequestRepository extends JpaRepository<SurrenderRequest, UUID> {

  Optional<SurrenderRequest> findFirstByTransportDocumentReferenceOrderByCreatedDateTimeDesc(String transportDocumentReference);
}
