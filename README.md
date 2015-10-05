
Judopay Cordova Plugin
================

To use do
```
cordova plugin add https://github.com/shapingcloud/judocordova.git --variable API_TOKEN="xxx" --variable API_SECRET="xxx" --variable JUDO_ID="xxx" --variable JUDO_ENV="live"
```
or for sandbox mode
```
cordova plugin add https://github.com/shapingcloud/judocordova.git --variable API_TOKEN="xxx" --variable API_SECRET="xxx" --variable JUDO_ID="xxx" --variable JUDO_ENV="staging"
```


then use


```
window.plugins.JudoPaymentPlugin.makeOneOffSimpleTransaction(
	100, //paymentAmount,
	'GBP',
	'123', //paymentRef,
	'456', //consumerRef
	userReceipt !== "" ? JSON.parse(userReceipt) : userReceipt, // optional card details
	function(receipt) { //onSuccess,
		localStorage.setItem("JudoReceipt", JSON.stringify(receipt));
		// security issue around stroing details
		var data = (typeof receipt === 'object' ? receipt : JSON.parse(receipt));
		callback(data);
	},
	function(error) { //onFail)
		failcallback(error);
	}
);
```
