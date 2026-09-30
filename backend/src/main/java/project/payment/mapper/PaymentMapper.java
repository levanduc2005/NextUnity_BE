package project.payment.mapper;

import org.mapstruct.Mapper;
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
}
