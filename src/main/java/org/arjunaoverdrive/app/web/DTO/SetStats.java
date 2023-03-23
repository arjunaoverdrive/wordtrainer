package org.arjunaoverdrive.app.web.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetStats {
    private int setId;
    private String name;
    private double srcLangProgress;
    private int srcTimesPracticed;
    private double targetLangProgress;
    private int targetTimesPracticed;

}
