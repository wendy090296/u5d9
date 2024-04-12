package wendydeluca.u5d9.payloads;

import java.time.LocalDateTime;

public record ErrorResponseDTO (String message, LocalDateTime timestamp){
}
