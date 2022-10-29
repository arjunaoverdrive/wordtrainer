package org.arjunaoverdrive.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetStats {
    private int setId;
    private String name;
    private float srcLangAccuracy;
    private int srcTimesPracticed;
    private float targetLangAccuracy;
    private int targetTimesPracticed;

}
