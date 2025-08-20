package com.example.Producer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiderLocation {
    private String riderId;
    private double latitude;
    private double longitude;
}
