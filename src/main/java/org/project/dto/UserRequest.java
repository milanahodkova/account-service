package org.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.model.DocumentType;
import static org.project.util.Constant.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    private String name;

    @NotNull(message = DOCTYPE_NOT_NULL_MESSAGE)
    private DocumentType docType;

    @NotBlank(message = DOCNUMBER_NOT_BLANK_MESSAGE)
    @Pattern(regexp = DOCNUMBER_REGEX, message = DOCNUMBER_PATTERN_MESSAGE)
    private String docNumber;
}