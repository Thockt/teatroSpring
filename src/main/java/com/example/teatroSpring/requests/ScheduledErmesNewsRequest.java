package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledErmesNewsRequest {

    private String title;
    private String body;
    private Date targetDate;

}
