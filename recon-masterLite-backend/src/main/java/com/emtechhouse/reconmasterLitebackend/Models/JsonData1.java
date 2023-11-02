//package com.emtechhouse.reconmasterLitebackend.Models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import java.util.List;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class JsonData1 {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private int priority;
//    @OneToMany(mappedBy = "priorityTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private List<Columns> columns;
//}
