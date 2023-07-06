CREATE TABLE transaction_party (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  ebl_platform_identifier varchar(100) NOT NULL,
  legal_name varchar(100) NOT NULL,
  registration_number varchar(100) NULL,
  location_of_registration varchar(2) NULL,
  tax_reference varchar(100) NULL
);

CREATE TABLE transaction_party_supporting_code (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  transaction_party_id uuid NOT NULL REFERENCES transaction_party(id),
  party_code varchar(100) NOT NULL,
  party_code_list_provider varchar(4) NOT NULL CHECK (party_code_list_provider IN ('LEI', 'DID'))
);

CREATE TABLE surrender_request (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  surrender_request_reference varchar(100) NULL,
  transport_document_reference varchar(20) NOT NULL,
  surrender_request_code varchar(4) NULL CHECK ( (surrender_request_code IS NULL AND surrender_request_reference IS NULL) OR (surrender_request_reference IS NOT NULL AND surrender_request_code IN ('SREQ', 'AREQ'))),
  comments varchar(255) NULL,
  surrender_requested_by uuid NULL REFERENCES transaction_party(id) CHECK (surrender_requested_by IS NOT NULL OR surrender_request_reference IS NULL),
  created_date_time timestamp with time zone NOT NULL default now()
);



CREATE TABLE endorsement_chain_link (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  -- entry_order should probably be NOT NULL without defaults and UNIQUE w. surrender_request
  -- but I am not willing to fight with JPA/hibernate to make it work.
  surrender_request uuid NOT NULL REFERENCES surrender_request(id),
  entry_order int NULL,
  action varchar(4) NOT NULL CHECK (action IN ('ETOF', 'ETOO', 'ETOB', 'AEND')),
  action_date_time timestamp with time zone NOT NULL,
  actor uuid NOT NULL references transaction_party(id),
  recipient uuid NULL references transaction_party(id)
);

