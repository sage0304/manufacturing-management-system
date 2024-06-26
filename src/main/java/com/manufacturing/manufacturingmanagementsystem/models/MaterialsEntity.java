package com.manufacturing.manufacturingmanagementsystem.models;

import com.manufacturing.manufacturingmanagementsystem.models.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "materials")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaterialsEntity extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit",columnDefinition = "varchar(255) check (unit in ('g', 'kg', 'amount','meter','liter'))")
    private String unit;

    @Column(name = "price")
    private Double price;

    @Column(name = "volume")
    private Double volume;

}
