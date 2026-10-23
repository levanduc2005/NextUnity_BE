package project.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.payment.dto.response.AdminPaymentResponse;
import project.payment.dto.response.CreatePaymentResponse;
import project.payment.dto.response.PaymentResponse;
import project.payment.dto.response.SubscriptionPlanResponse;
import project.payment.entity.Payment;
import project.payment.entity.enums.SubscriptionPlan;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);

    CreatePaymentResponse toCreateResponse(Payment payment);

    SubscriptionPlanResponse toPlanResponse(SubscriptionPlan plan);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "userAvatarUrl", source = "user.avatarUrl")
    @Mapping(target = "planName", source = "plan.name")
    AdminPaymentResponse toAdminResponse(Payment payment);
}
