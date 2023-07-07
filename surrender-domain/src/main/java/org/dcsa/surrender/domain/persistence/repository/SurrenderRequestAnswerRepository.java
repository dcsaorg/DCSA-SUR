package org.dcsa.surrender.domain.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.dcsa.surrender.domain.persistence.entity.SurrenderRequestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurrenderRequestAnswerRepository extends JpaRepository<SurrenderRequestAnswer, UUID> {

  Optional<SurrenderRequestAnswer> findFirstBySurrenderRequestReferenceOrderByCreatedDateTimeDesc(String surrenderRequestReference);
}
