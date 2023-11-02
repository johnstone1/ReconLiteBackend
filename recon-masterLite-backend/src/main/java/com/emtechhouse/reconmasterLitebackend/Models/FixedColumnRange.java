package com.emtechhouse.reconmasterLitebackend.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FixedColumnRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String columnName;
    private String columnDataType;

    private String columnPosition;
    //private String fileTypeIdentifier;
    private long startIndex;
    private long endIndex;
    private String dateFormat;
    private boolean hasAllowance;
    private String allowanceAmount;
    private String columnTypeIdentifier;//variable type
    private int priority;
    private boolean isDelimited=false;
    private String delimiter;
    private int delimiterPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuration_id",referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private ConfigurationTable configurationTable;

}
