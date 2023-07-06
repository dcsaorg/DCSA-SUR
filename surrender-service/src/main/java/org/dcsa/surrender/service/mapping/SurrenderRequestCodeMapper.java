package org.dcsa.surrender.service.mapping;

import org.dcsa.surrender.domain.persistence.entity.enums.SurrenderRequestCode;
import org.dcsa.surrender.transferobjects.enums.SurrenderRequestCodeTO;
import org.mapstruct.Mapper;

@Mapper(
  componentModel = "spring"
)
public interface SurrenderRequestCodeMapper {
  SurrenderRequestCode toDAO(SurrenderRequestCodeTO surrenderRequestCodeTO);

}
