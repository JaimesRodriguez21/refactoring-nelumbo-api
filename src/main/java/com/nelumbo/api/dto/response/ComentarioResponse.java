package com.nelumbo.api.dto.response;

import com.nelumbo.api.dto.ComentarioDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComentarioResponse {
    ComentarioDTO comentarioPadre;
    List<ComentarioDTO> comentariosHijos;

}
