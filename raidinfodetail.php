<?php 
$lastphpupdate = "last update 2017-10-17 09:40 \n"; // info last update php  
// Open Source
// Write by: IGAM Muliarsa 2017-10-17
// For Education Purpose 
// Use it with your own risk
// No Support

/*
`jimegaraidsas`.`jilog` (
  `jiid` INT NOT NULL AUTO_INCREMENT,
  `jidate` DATETIME NULL,

`jimegaraidsas`.`rawcontroller` (
  `jilog_jiid` INT NOT NULL,
  `t1` VARCHAR(45) NULL,
  `t2` VARCHAR(45) NULL,
  `t3` VARCHAR(45) NULL,
  `t4` VARCHAR(45) NULL,
  `t5` VARCHAR(45) NULL,
  `t6` VARCHAR(45) NULL,

`jimegaraidsas`.`rawhd` (
  `jilog_jiid` INT NOT NULL,
  `t1` VARCHAR(45) NULL,
  `t2` VARCHAR(45) NULL,
  
`jimegaraidsas`.`rawconfig` (
  `jilog_jiid` INT NOT NULL,
  `t1` VARCHAR(50) NULL,
*/

// change user and password with your
// host, user, password, database, [port]
$mysqli = new mysqli("localhost", "jimegaraidsas", "jimegaraidsas", "jimegaraidsas");
if ($mysqli->connect_errno) {
	die('Internal Server Error maybe database');
}

$id = -1;
// prevent mysql inject
// sanitize input, we don't belive any input :) 
if(isset($_GET["id"])){
	$tmpID = $_GET["id"];
	if (!ctype_digit($tmpID)) {
		die('Illegal input');		
	}
	$id = intval($tmpID);
}
if(isset($_POST["id"])){
	$tmpID = $_POST["id"];
	if (!ctype_digit($tmpID)) {
		die('Illegal input');		
	}
	$id = intval($tmpID);
}

if ($id<0) {
	die('No data to request');
}

/*
`jimegaraidsas`.`jilog` (
  `jiid` INT NOT NULL AUTO_INCREMENT,
  `jidate` DATETIME NULL,

  `t1` VARCHAR(45) NULL,
  `t2` VARCHAR(45) NULL,
  `t3` VARCHAR(45) NULL,
  `t4` VARCHAR(45) NULL,
  `t5` VARCHAR(45) NULL,
  `t6` VARCHAR(45) NULL,
*/
$sql = "SELECT * FROM jilog jl, rawcontroller rc where jl.jiid = ".$id." and jl.jiid = rc.jilog_jiid";
$res = $mysqli->query($sql);
$rows = $res->num_rows; 
if ($rows<=0) {
	die('No data or raid controller data found');
}
$date; $t1; $t2; $t3; $t4; $t5; $t6; 
while ($row = $res->fetch_assoc()) {
		$id =  $row['jiid'];
		$date = date('Y-m-d', strtotime($row['jidate']));
		$t1 = $row['t1'];
		$t2 = $row['t2'];
		$t3 = $row['t3'];
		$t4 = $row['t4'];
		$t5 = $row['t5'];
		$t6 = $row['t6'];
}

/*
`jimegaraidsas`.`rawhd` (
  `jilog_jiid` INT NOT NULL,
  `t1` VARCHAR(45) NULL,
  `t2` VARCHAR(45) NULL,
*/
$sql = "SELECT * FROM rawhd rh where rh.jilog_jiid = ".$id;
$res = $mysqli->query($sql);
$rows = $res->num_rows; 
if ($rows<=0) {
	die('No harddrive data found');
}
$hd = array();
while ($row = $res->fetch_assoc()) {
	$hd[$row['t1']] = $row['t2'];
}

/*
`jimegaraidsas`.`rawconfig` (
  `jilog_jiid` INT NOT NULL,
  `t1` VARCHAR(50) NULL,
*/  
$sql = "SELECT * FROM rawconfig rc where rc.jilog_jiid = ".$id;
$res = $mysqli->query($sql);
$rows = $res->num_rows; 
if ($rows<=0) {
	die('No raid config data found');
}

$rcfg = array();
for ($i=0;$i<$rows;$i++) {
	$row = $res->fetch_assoc(); 
	$rcfg[$i] = $row['t1'];
}

?>

<html>
<head>
</head>
<body>
<p>Version: 1.0</p>
<p><?php echo $lastphpupdate; // print last update php  ?></p>
<p>Server Farm Info</p>

<!-- Raid Controller -->
<table border="1">
<thead>
<tr>
	<th>Date</th>
	<th>Raid Controller</th>
	<th>Bios</th>
	<th>Firmware</th>
	<th>Config</th>
	<th>Rebuild</th>
	<th>Battery</th>
</tr>
</thead>
<tbody>
<tr>
	<td><?php echo $date; ?></td>
	<td><?php echo $t1; ?></td>
	<td><?php echo $t2; ?></td>
	<td><?php echo $t3; ?></td>
	<td><?php echo $t4; ?></td>
	<td><?php echo $t5; ?></td>
	<td><?php echo $t6; ?></td>
</tr>
</tbody>
</table>

<!-- HD Config -->
<table border="1">
<thead>
<tr>
	<th>No</th>
	<th>HD ID</th>
	<th>Info</th>
</tr>
</thead>
<tbody>
<?php
$no=1;
foreach($hd as $hdid => $hddata) {
?>
<tr>
	<td><?php echo $no; ?></td>
	<td><?php echo $hdid; ?></td>
	<td><?php echo $hddata; ?></td>
</tr>
<?php 
	$no++;
}
?>
</tbody>
</table>

<!-- Raid Config -->
<table border="1">
<thead>
<tr>
	<th>No</th>
	<th>Raid Config</th>
</tr>
</thead>
<tbody>
<?php
$no=1;
foreach($rcfg as $cfgdata) {
?>
<tr>
	<td><?php echo $no; ?></td>
	<td><?php echo $cfgdata; ?></td>
</tr>
<?php 
	$no++;
}
?>
</tbody>
</table>

<p>Hosted on <a href="http://www.aryfanet.com/">Aryfanet Dot Com</a>, your partner to trust.</p>


</body>
</html> 
