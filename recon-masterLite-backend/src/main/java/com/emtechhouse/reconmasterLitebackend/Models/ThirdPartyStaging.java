package com.emtechhouse.reconmasterLitebackend.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThirdPartyStaging {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rrn;
    private String columnName;
    private String startIndex;
    private String endIndex;
    private String columnTypeIdentifier;//finacle or third party
    private int priority;
    private boolean isDelimited=false;
    private String delimiter;
    private int delimiterPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_id",referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private PriorityTable priorityTable;
}
