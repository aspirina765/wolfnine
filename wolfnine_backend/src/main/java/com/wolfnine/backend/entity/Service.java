package com.wolfnine.backend.entity;

import com.wolfnine.backend.entity.entityEnum.DurationType;
import com.wolfnine.backend.entity.entityEnum.ServiceStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int duration;
    private DurationType durationType;
    private String description;
    private double price;
    private ServiceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
