package com.billennium.vaccinationproject.utilities;

public enum VaccineDose {
    SINGLE_DOSE(1),
    DOUBLE_DOSE(2);

    private final Integer doseValue;

    VaccineDose(Integer doseValue) {
        this.doseValue = doseValue;
    }

    public Integer getDoseValue() {
        return doseValue;
    }
}


