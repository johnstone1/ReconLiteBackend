package com.emtechhouse.reconmasterLitebackend.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Colu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String columnName;
    private String columnPosition;
    private String fileTypeIdentifier;
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