package com.emtechhouse.reconmasterLitebackend.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigurationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileType;
    private String reconciliationType;
    private String location;
    private String username;
    private String password;
    private String ipAddress;
    private int port;
    private String isDelimited;
    private String  path;
    private int headers;
    private String dateFormat;
    private String fileName;
    private String fullFileName;
    private long runningNumberStartIndex;
    private long runningNumberEndIndex;
    private String fileExtension;
    private String rowSeparator;
    private String delimiter;
    private Character deletedFlag='N';
    @OneToMany(mappedBy = "configurationTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<FixedColumnRange> fixedSizeRange;

    @OneToMany(mappedBy = "configurationTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Colu> columns;


}
