package com.example.springlocalgovernmentsupport.domains;

import com.example.springlocalgovernmentsupport.enums.UsageCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
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

    @OneToOne(mappedBy = "supportedItem", cascade = CascadeType.ALL)
    private LocalGovernment localGovernment;

    @Column(name = "target", nullable = false)
    @Setter
    private String target;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "usage", nullable = false)
    @Setter
    private UsageCode usage;

    @Column(name = "limitAmount", nullable = false)
    @Setter
    private long limitAmount = -1L;

    @Column(name = "fromRate", precision = 3, scale = 2, nullable = false)
    @Setter
    private float fromRate = -1.00f;

    @Column(name = "endRate", precision = 3, scale = 2, nullable = false)
    @Setter
    private float endRate = -1.00f;

    @Column(name = "institute", nullable = false)
    @Setter
    private String institute;

    @Column(name = "mgmt", nullable = false)
    @Setter
    private String mgmt;

    @Column(name = "reception", nullable = false)
    @Setter
    private String reception;

    @Builder
    public SupportedItem(LocalGovernment localGovernment, String target, UsageCode usage, long limitAmount,
                         float fromRate, float endRate, String institute, String mgmt, String reception) {
        this.setLocalGovernment(localGovernment);
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

    public void setLocalGovernment(LocalGovernment localGovernment) {
        if (localGovernment == null) {
            if (this.localGovernment != null) {
                this.localGovernment.setSupportedItem(null);
            }
        } else {
            if (localGovernment.getSupportedItem() != this) {
                this.localGovernment = localGovernment;
                localGovernment.setSupportedItem(this);
            }
        }
        this.localGovernment = localGovernment;
    }

    public void update(SupportedItem supportedItem) {
        this.target = supportedItem.getTarget();
        this.usage = supportedItem.getUsage();
        this.limitAmount = supportedItem.getLimitAmount();
        this.fromRate = supportedItem.getFromRate();
        this.endRate = supportedItem.getEndRate();
        this.institute = supportedItem.getInstitute();
        this.mgmt = supportedItem.getMgmt();
        this.reception = supportedItem.getReception();
    }
}
