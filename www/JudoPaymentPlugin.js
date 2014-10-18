// JudoPaymentPlugin
function JudoPaymentPlugin() {
}

// Make one off payment
JudoPaymentPlugin.prototype.makeOneOffSimpleTransaction = function(
        paymentAmount,
        paymentId,
        paymentToken,
        paymentRef,
        consumerRef,
        restaurantName,
        restaurantColor,
        restaurantSecColor,
        onSuccess,
        onFail) {

    cordova.exec(onSuccess, onFail, 'JudoPaymentPlugin', 'makeSimpleTransaction',
            [
                parseFloat(paymentAmount),
                paymentId,
                paymentToken,
                paymentRef,
                consumerRef,
                restaurantName,
                restaurantColor,
                restaurantSecColor,
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











