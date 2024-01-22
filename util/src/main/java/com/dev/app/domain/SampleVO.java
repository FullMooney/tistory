package com.dev.app.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleVO {
    
    @Schema(example = "Ligue1")
    private String league;
    @Schema(example = "PSG")
    private String team;
    @Schema(example = "Lee Gang-in")
    private String name;
    private int number;
    private int wage;
}
