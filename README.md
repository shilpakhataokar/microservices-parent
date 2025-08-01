1. # Payment Processing System (PPS)

- Receives payments in JSON.
- Validates ISO country/currency codes.
- Calls Broker System for fraud check.
- Processes payment based on fraud check.

**Run:**  
`mvn spring-boot:run`

**Endpoint:**  
POST `/api/payments` (JSON)

**Config:**  
Change `bs.fraud-check.url` in `application.properties` if BS is not running on localhost:8082.

2. # Broker System (BS)

- Receives fraud check req (JSON) from PPS.
- Converts JSON to XML, calls FCS.
- Converts FCS XML result to JSON and returns to PPS.

**Run:**  
`mvn spring-boot:run`

**Endpoint:**  
POST `/api/broker/fraud-check` (JSON)

**Config:**  
Change `fcs.fraud-check.url` in `application.properties` if FCS is not running on localhost:8080.

3. # Fraud  Check Service (FCS)
   
- Receives XML payment requests via REST endpoint.
- Checks payer and payee details (name, country, bank) and payment instruction against configurable blacklists.
- Validates the structure and required fields of the incoming request.
- Approves or rejects payments, sending results back as XML.
- Returns clear messages:
-"Suspicious payment" if any check fails.
-"Nothing found, all okay" if all checks pass.

**Run:**  
`mvn spring-boot:run`

**Endpoint:**  
POST `/api/fraud-check` (JSON)

### Sample Request

```xml
<PaymentRequest>
    <TransactionID>123e4567-e89b-12d3-a456-426614174000</TransactionID>
    <PayerName>Munster Muller</PayerName>
    <PayerBank>Bank of America</PayerBank>
    <PayerCountryCode>USA</PayerCountryCode>
    <PayerAccount>12345678</PayerAccount>
    <PayeeName>Jane Smith</PayeeName>
    <PayeeBank>BNP Paribas</PayeeBank>
    <PayeeCountryCode>FRA</PayeeCountryCode>
    <PayeeAccount>87654321</PayeeAccount>
    <PaymentInstruction>Loan Repayment</PaymentInstruction>
    <ExecutionDate>2025-07-30</ExecutionDate>
    <Amount>100.50</Amount>
    <Currency>USD</Currency>
    <CreationTimestamp>2025-07-30T10:00:00Z</CreationTimestamp>
</PaymentRequest>
```

### Sample Response

```xml
<PaymentResponse>
    <TransactionID>123e4567-e89b-12d3-a456-426614174000</TransactionID>
    <Status>APPROVED</Status>
    <Message>Nothing found, all okay</Message>
</PaymentResponse>
```
