package org.sopt.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record WithdrawRequest(
        String withdrawReason,

        @NotNull
        @Schema(
                description = "탈퇴 동의 여부",
                example = "true"
        )
        boolean isAgree
) {
}