package com.emtechhouse.reconmasterLitebackend.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class PriorityTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int priority;

    private String status="";
    private String priorityName;
    private Character deletedFlag='N';

    @OneToMany(mappedBy = "priorityTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Columns> columns;

}
