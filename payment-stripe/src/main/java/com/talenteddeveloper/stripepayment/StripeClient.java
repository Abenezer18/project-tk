package com.talenteddeveloper.stripepayment;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

    @Autowired
    StripeClient() {
        Stripe.apiKey = "sk_test_51NCIBMLptA7oYcwhdOkcOFofbn9ViioiFcOHS2mZ9P4c5RPgqUnSVpJ6lCWdXXu2F0pMLfLnZo6OU1lM72Dy7xrl00bA4tbpIe";
    }

    public Charge chargeCreditCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100)); // stripe requires cents and not dollars and it should always be an int
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
