package com.nelumbo.api.dto.response;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.VehiculoDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehiculoParqueaderoResponse {

    private String parqueadero;
    private List<String> vehiculo;

}
