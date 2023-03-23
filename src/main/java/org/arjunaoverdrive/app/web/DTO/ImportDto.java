package org.arjunaoverdrive.app.web.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportDto {
    private String name;
    private String words;
    private String delimiter;
}
