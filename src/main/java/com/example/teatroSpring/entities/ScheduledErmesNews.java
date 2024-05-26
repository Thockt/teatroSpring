package com.example.teatroSpring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduledErmesNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    private Date publishTime;

}
