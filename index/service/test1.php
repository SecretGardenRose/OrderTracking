<?php

	function getOrders($fulfillment) {

		echo is_null($fulfillment);
		
		$date = new DateTime();
		$max_date = $date->format('c');
		$min_date = $date->sub(new DateInterval('P20D'))->format('c');
		
        	$ch = curl_init();
		
		// set url
		$cmd = "https://1fe22e83bbe716b1e82b22d465b4c363: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@love-florist.myshopify.com/admin/api/2021-04/orders.json"; 

		$option = "?limit=250&created_at_min=" . $min_date . "&created_at_max=" . $max_date;
		if ($fulfillment != '') {
			$option .= "&fulfillment_status=" . $fulfillment;
		} 
	       	else {
			$option .= "&status=any";
		}	
	
		$cmd .= $option;


		curl_setopt($ch, CURLOPT_URL, $cmd);
		//return the transfer as a string
        	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);

        	// $output contains the output string
        	$output = curl_exec($ch);

        	// close curl resource to free up system resources
		curl_close($ch);      

		return $output;
	}

	$date = $_POST['date'];
	$check = $_POST['check'];
	$fulfillment = $_POST['fulfillment'];
	$output = getOrders($fulfillment);


	$match = "";
	if ($check == "check") {
		$match = "check";
	}
	else if ($date == "today") {
		$d = new DateTime();
		$match = $d->format("n/j");
	}
	else if ($date == "yesterday") {
		$d = new DateTime();
		$match = $d->sub(new DateInterval('P1D'))->format('n/j');
	}
	else if ($date == "tomorrow") {
		$d = new DateTime();
 	        $match = $d->add(new DateInterval('P1D'))->format('n/j');
	}
	echo $match;

       	echo '<table class="table1" style="border: 1px solid black; table-layout: fixed;">';
	$output = json_decode($output);

	foreach ($output->orders as $o) {
		echo '<tr>';

		$cont = true;			
		$checktagstr = "";
		$tagstr = "";
		if (!$o->tags) {
			$tagstr = '<td bgcolor="black" style="border: 1px solid black"><font color="white"><pre>TAGS!!!</pre></font></td>';
		} 
		else {
			$tags = explode(",", $o->tags); 
			foreach ($tags as $t) {
				$trimt = strtolower(trim($t));
				if ($trimt == "check") {
					$checktagstr = '<td bgcolor="red" style="border: 1px solid black"><font color="white"><pre>' . trim($t) . "</pre></font></td>";	
				}
				else {
					$tagstr .= '<td bgcolor="pink" style="border: 1px solid black"><font color="black"><pre>' . trim($t) . "</pre></font></td>";
				}

				//echo $trimt . " " . $match . "<br>";	
				if ($trimt == $match) {
					$cont = false;
				}	
			}
		}

		if ($match != "" && $cont) {
			continue;
		}
		
		echo '<td';
		if ($o->fulfillment_status == "fulfilled") {
			$namecolor = ' bgcolor="gray" ';	
		}			
		else {
			$namecolor = ' bgcolor="green" ';
		}		
		echo $namecolor;
		echo 'style="border: 1px solid black"><pre>' . $o->name . "</pre></td>";
		echo $checktagstr;
		echo $tagstr;	
		//echo '<td style="border: 1px solid black"><pre>' . $o->id . "</pre></td>";
		echo '<td style="border: 1px solid black"><pre>';
		echo $o->customer->default_address->address1 . "\n";
		echo $o->customer->default_address->address2 . "\n";
		echo $o->customer->default_address->city . "\n";
		echo $o->customer->default_address->province . "\n";
		echo $o->customer->default_address->zip;
		echo '</pre></td>';




		echo '<td style="border: 1px solid black"><pre>' . $o->note . "</pre></td>";
		foreach ($o->note_attributes as $oa) {
			echo '<td style="border: 1px solid black"><pre>' . $oa->value . "</pre></td>";
		}
		
		echo '</tr>';
	}
	echo '</table>';
?>
