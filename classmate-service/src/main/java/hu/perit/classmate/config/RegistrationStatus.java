package hu.perit.classmate.config;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Generated // To disable counting in unit test coverage
public enum RegistrationStatus
{
    CONFIRMED(1, "Visszaigazolt"),
    WAITLISTED(2, "Várólistán");

    private final long value;
    private final String label;

    public static Optional<RegistrationStatus> fromValue(Long input)
    {
        return Arrays.stream(RegistrationStatus.values()).filter(i -> i.value == input).findFirst();
    }
}
