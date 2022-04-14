package com.sapient.api.swagger;

import com.sapient.wfs.common.vo.WeatherVO;
import com.sapient.wfs.common.exception.WFError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the Weather forecast details.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WeatherVO.class))),
        @ApiResponse(responseCode = "400", description = "Malformed Syntax or Missing request Param(s).", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WFError.class))),
        @ApiResponse(responseCode = "401", description = "Request doesn't have sufficient credentials.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WFError.class))),
        @ApiResponse(responseCode = "403", description = "User is not authorised.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WFError.class))),
        @ApiResponse(responseCode = "404", description = "Result Not found.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WeatherVO.class))),
        @ApiResponse(responseCode = "405", description = "Invalid Method, Please select a valid HTTP method.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WFError.class))),
        @ApiResponse(responseCode = "500", description = "Internal System error.", content = @Content(mediaType = APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = WFError.class))),
})
public @interface WeatherForecastResponse {
}
