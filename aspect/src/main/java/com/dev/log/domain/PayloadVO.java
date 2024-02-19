package com.dev.log.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadVO {
    @Schema(example = "Ligue1")
    private String league;
    @Schema(example = "PSG")
    private String team;
    @Schema(example = "Lee Gang-in")
    private String name;
    private int number;
    private int wage;

}
