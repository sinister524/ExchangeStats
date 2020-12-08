package com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"name", "value"})
public class Currency implements Comparable<Currency> {


    private String charCode;

    private String name;

    private Double value;

    @JsonCreator
    public Currency(@JsonProperty("CharCode") String charCode, @JsonProperty("Nominal") Integer nominal,
                    @JsonProperty("Name") String name, @JsonProperty("Value") Double value) {
        this.charCode = charCode;
        this.name = name;
        this.value = value/nominal;
    }

    @Override
    public int compareTo(Currency o) {
        return this.charCode.compareTo(o.charCode);
    }

    @Override
    public String toString() {
        return  charCode + " - " + name;
    }
}

