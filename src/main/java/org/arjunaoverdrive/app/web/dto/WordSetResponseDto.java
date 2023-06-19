package org.arjunaoverdrive.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.app.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordSetResponseDto {
    private int id;
    private String name;
    private int size;
    private User createdBy;

}
