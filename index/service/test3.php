
<?php

$response = file_get_contents('https://1fe22e83bbe716b1e82b22d465b4c363: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@love-florist.myshopify.com/admin/api/2021-04/orders.json');
$response = json_decode($response);
$response = json_encode($response, JSON_PRETTY_PRINT);
echo $response;
//echo $response->order->id;

?>
