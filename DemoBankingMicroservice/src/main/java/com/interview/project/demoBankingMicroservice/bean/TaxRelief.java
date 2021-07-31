package com.interview.project.demoBankingMicroservice.bean;

import lombok.Data;

@Data
public class TaxRelief {

	private String taxReliefId;
    private boolean isCondoUpgrade;
    private String creditorFiscalCode;
    private String beneficiaryType;
	private NaturalPersonBeneficiary naturalPersonBeneficiary;
	private LegalPersonBeneficiary legalPersonBeneficiary;
	
}
