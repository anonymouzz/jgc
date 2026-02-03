package hu.alphabox.jgc.github.app.install;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

record OrganizationSelectRequest(
    @NotNull
    @PositiveOrZero
    Long organizationId
) {

}
