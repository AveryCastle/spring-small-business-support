package com.example.springlocalgovernmentsupport.domains;

import com.example.springlocalgovernmentsupport.enums.UsageCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "supported_item")
@Getter
@NoArgsConstructor
public class SupportedItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "supportedItem")
    private LocalGovernment localGovernment;

    @Column(name = "target", nullable = false)
    private String target;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "usage", nullable = false)
    private UsageCode usage;

    @Column(name = "limitAmount", nullable = false)
    private long limitAmount = 0L;

    @Column(name = "fromRate", precision = 3, scale = 2, nullable = false)
    private float fromRate = 0.00f;

    @Column(name = "endRate", precision = 3, scale = 2, nullable = false)
    private float endRate = 0.00f;

    @Column(name = "institute", nullable = false)
    private String institute;

    @Column(name = "mgmt", nullable = false)
    private String mgmt;

    @Column(name = "reception", nullable = false)
    private String reception;

    @Builder
    public SupportedItem(LocalGovernment localGovernment, String target, UsageCode usage, long limitAmount,
                         float fromRate, float endRate, String institute, String mgmt, String reception) {
        this.localGovernment = localGovernment;
        this.target = target;
        this.usage = usage;
        this.limitAmount = limitAmount;
        this.fromRate = fromRate;
        this.endRate = endRate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    @PrePersist
    public void setEndRate() {
        if (endRate == 0.0f) {
            endRate = fromRate;
        }
    }
}
