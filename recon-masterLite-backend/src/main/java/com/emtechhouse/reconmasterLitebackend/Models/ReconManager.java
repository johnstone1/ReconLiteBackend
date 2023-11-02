package com.emtechhouse.reconmasterLitebackend.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReconManager {
   @Id
   private String rrn;
   private String secRef;
   private String transParticular;
   private LocalDateTime transactionDate;
   private BigDecimal amount;
   private  BigDecimal debit;
   @Column(length = 15)
   private int phoneNumber;
   private String transactionId;
   private String transactionDescription;
   private String exceptionReason;
   private String currencyCode;
   private String status;
   private String accountNumber;
   private  String forAcid;
   private BigDecimal runningBalance;
   private Date valueDate;
   private String TransactionRemarks;


}
