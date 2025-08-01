package com.instantpayment.fraud_service.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "FraudCheckResultXml")
@XmlAccessorType(XmlAccessType.FIELD)
public class FraudCheckResultXml {

    @XmlElement
    private String transactionId;

    @XmlElement
    private String status;

    @XmlElement
    private String message;

}
