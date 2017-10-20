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

$page = 1;
if(isset($_GET["page"])){
	$page = intval($_GET["page"]);
}
if(isset($_POST["page"])){
	$page = intval($_POST["page"]);
}
//This is the number of results displayed per page 
$page_rows = 15; 
$start = ($page_rows * $page) - $page_rows;

$sql = "select Count(*) AS total FROM jilog";
$res = $mysqli->query($sql);
$rows = $res->num_rows; 
$total=0;
if($rows) {
	$row = $res->fetch_assoc();
	$total = $row['total'];
}
$sql = "SELECT * FROM jilog jl, rawcontroller rc where jl.jiid = rc.jilog_jiid ORDER BY jidate DESC LIMIT $start, $page_rows ";
$res = $mysqli->query($sql);
$rows = $res->num_rows; 
//This tells us the page number of our last page 
$last = ceil($total/$page_rows);
$line_no=$start;
?>

<html>
<head>
</head>
<body>
<p>Version: 1.0</p>
<p><?php echo $lastphpupdate; // print last update php  ?></p>
<p>Server Farm Info</p>

<?php
if ($res) {
	echo "<p>Total: $total</p>";
	if ($rows>0) {
?>

<table border="1">
<thead>
<tr >
	<th>No</th>
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
<?php 
		while ($row = $res->fetch_assoc()) {
			$line_no++; // numbering
			$date = date('Y-m-d', strtotime($row['jidate']));
?>
<tr  >
	<td valign="top"><?php echo $line_no; ?></td>
	<td valign="top"><?php echo $date; ?></td>
	<td valign="top"><a href="./raidinfodetail.php?id=<?php echo $row['jiid']; ?>"><?php echo $row['t1']; ?></a>
	</td>
	<td valign="top"><?php echo $row['t2']; ?></td>
	<td valign="top"><?php echo $row['t3']; ?></td>
	<td valign="top"><?php echo $row['t4']; ?></td>
	<td valign="top"><?php echo $row['t5']; ?></td>
	<td valign="top"><?php
            $t6 = $row['t6'];
            if (stripos($t6,'FAULT')===false) {
                echo $t6; 
            } else {
                echo "<strong>".$t6."</strong>";
            }?>
	</td>
</tr>

<?php
		}
	} else {
		echo "<p>No Data</p>";
	}
?>
</tbody>
</table>
<?php 
// simple navigation
	if ($last>1) {
		echo "<P>";
		if ($page>1) {
			echo "<a href=\"".htmlspecialchars($_SERVER["PHP_SELF"], ENT_QUOTES, "utf-8")."?page=1\">First</a> ";
		}
		if ($page>2 && $page<=$last) {
			echo "... ";
		}
		if ($page>1) {
			echo "<a href=\"".htmlspecialchars($_SERVER["PHP_SELF"], ENT_QUOTES, "utf-8")."?page=".($page-1)."\">".($page-1)."</a> ";
		}
		echo "$page ";
		if ($page+1<=$last) {
			echo "<a href=\"".htmlspecialchars($_SERVER["PHP_SELF"], ENT_QUOTES, "utf-8")."?page=".($page+1)."\">".($page+1)."</a> ";
		}
		if ($page+1<$last) {
			echo "... ";
		}
		if ($page<$last) {
			echo "<a href=\"".htmlspecialchars($_SERVER["PHP_SELF"], ENT_QUOTES, "utf-8")."?page=$last\">Last</a> ";
		}
		echo "| Number of pages: $last </p>";
	}
} else {
	echo "<p>Query Fail</p>";
}
?>
<p>Hosted on <a href="http://www.aryfanet.com/">Aryfanet Dot Com</a>, your partner to trust.</p>


</body>
</html> 
