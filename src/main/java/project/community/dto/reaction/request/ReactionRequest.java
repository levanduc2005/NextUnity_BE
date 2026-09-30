package project.community.dto.reaction.request;

import jakarta.validation.constraints.NotNull;
import project.community.entity.enums.ReactionType;

public record ReactionRequest(
        @NotNull(message = "Loại cảm xúc không được để trống")
        ReactionType reactionType
) {}
