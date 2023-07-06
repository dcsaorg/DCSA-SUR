package org.dcsa.surrender.service.mapping;

import org.dcsa.surrender.domain.persistence.entity.EndorsementChainLink;
import org.dcsa.surrender.transferobjects.EndorsementChainLinkTO;
import org.mapstruct.Mapper;

@Mapper(
  componentModel = "spring",
  uses = {
    TransactionPartyMapper.class
  }
)
public interface EndorsementChainMapper {
  EndorsementChainLink toDAO(EndorsementChainLinkTO endorsementChainLinkTO);

}
