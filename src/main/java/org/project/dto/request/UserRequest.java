package org.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.model.enums.DocumentType;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private static final String DOCNUMBER_REGEX = "^[A-Za-z0-9]+$";

    @NotBlank(message = "${name.notBlankMessage}")
    private String name;

    @NotNull(message = "${docType.notNullMessage}")
    private DocumentType docType;

    @NotBlank(message = "${docNumber.notBlankMessage}")
    @Pattern(regexp = DOCNUMBER_REGEX, message = "${docNumber.patternMessage}")
    private String docNumber;
}