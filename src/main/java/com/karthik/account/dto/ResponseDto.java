package com.karthik.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successfull repsone information"
)
public class ResponseDto
{

    @Schema(
            description = "Status code in response",
            example = "200"

    )
    private String statusCode;

    @Schema(
            description = "Status message in response",
            example = "Request processed successfully"

    )
    private String statusMsg;
}
