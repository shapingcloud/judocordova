// JudoPaymentPlugin
function JudoPaymentPlugin() {
}

// Make one off payment
JudoPaymentPlugin.prototype.makeOneOffSimpleTransaction = function(
        paymentAmount,
        currency,
        paymentRef,
        consumerRef,
        cardToken,
        consumerToken,
        onSuccess,
        onFail) {

    cordova.exec(onSuccess, onFail, 'JudoPaymentPlugin', 'makeSimpleTransaction',
            [
                parseFloat(paymentAmount),
                currency,
                paymentRef,
                consumerRef,
                cardToken,
                consumerToken
            ]
            );
}

cordova.addConstructor(function() {
    if (!window.plugins) {
        window.plugins = {};
    }

    if (!window.Cordova) {
        window.Cordova = cordova;
    }

    window.plugins.JudoPaymentPlugin = new JudoPaymentPlugin();
});











