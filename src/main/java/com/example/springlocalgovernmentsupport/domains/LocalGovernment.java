package com.example.springlocalgovernmentsupport.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// TODO: UNIQUE KEY Custom Name으로 생성되지 않는 현상 Fix.
@Entity
@Table(name = "local_government",
        uniqueConstraints = @UniqueConstraint(name = "uk_local_government_name", columnNames = "name"))
@NoArgsConstructor
@Getter
public class LocalGovernment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "supported_item_id", foreignKey = @ForeignKey(name = "fk_supported_item_id"))
    private SupportedItem supportedItem;

    public LocalGovernment(String name) {
        this.name = name;
    }

    public LocalGovernment(String name, SupportedItem supportedItem) {
        this.name = name;
        this.supportedItem = supportedItem;
    }
}
