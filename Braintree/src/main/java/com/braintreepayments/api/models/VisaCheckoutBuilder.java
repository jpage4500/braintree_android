package com.braintreepayments.api.models;

import com.visa.checkout.VisaPaymentSummary;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Builder used to construct a Visa Checkout tokenization request.
 */
public class VisaCheckoutBuilder extends PaymentMethodBuilder<VisaCheckoutBuilder> {

    private static final String VISA_CHECKOUT_KEY = "visaCheckoutCard";
    private static final String CALL_ID = "callId";
    private static final String ENCRYPTED_KEY = "encryptedKey";
    private static final String ENCRYPTED_PAYMENT_DATA = "encryptedPaymentData";

    private String mCallId;
    private String mEncryptedKey;
    private String mEncryptedPaymentData;

    /**
     * @param visaPaymentSummary returned from Visa Checkout after a successful payment.
     */
    public VisaCheckoutBuilder(VisaPaymentSummary visaPaymentSummary) {
        if (visaPaymentSummary == null) {
            return;
        }

        mCallId = visaPaymentSummary.getCallId();
        mEncryptedKey = visaPaymentSummary.getEncKey();
        mEncryptedPaymentData = visaPaymentSummary.getEncPaymentData();
    }

    @Override
    protected void build(JSONObject base, JSONObject paymentMethodNonceJson) throws JSONException {
        paymentMethodNonceJson.put(CALL_ID, mCallId);
        paymentMethodNonceJson.put(ENCRYPTED_KEY, mEncryptedKey);
        paymentMethodNonceJson.put(ENCRYPTED_PAYMENT_DATA, mEncryptedPaymentData);
        base.put(VISA_CHECKOUT_KEY, paymentMethodNonceJson);
    }

    @Override
    public String getApiPath() {
        return "visa_checkout_cards";
    }

    @Override
    public String getResponsePaymentMethodType() {
        return VisaCheckoutNonce.TYPE;
    }
}
