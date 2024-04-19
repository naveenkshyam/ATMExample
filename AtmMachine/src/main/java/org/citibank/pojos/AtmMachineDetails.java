package org.citibank.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmMachineDetails {
    private String location;
    private String bankName;
    private String branch;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
