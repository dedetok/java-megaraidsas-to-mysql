# java-megaraidsas-to-mysql

This application requires (depend):
1. megacli
2. megactl
3. megaraid-status

from https://hwraid.le-vert.net/ 

List supported hardware can be found at http://hwraid.le-vert.net/wiki/LSIMegaRAIDSAS

Installation for Debian family can be found at http://hwraid.le-vert.net/wiki/DebianPackages

This java application requires MySQL Connector/J. You can download it from https://dev.mysql.com/downloads/connector/j/. 

Note: JIgmMegaRaid.jar already contains MySQL Connector/J.

Create mysql user and mysql database for jimegaraidsas. You need to change database name, user name and password at JIgmMegaRaid.java:

			MySQLHelper mSQL = new MySQLHelper("jimegaraidsas"); // change to your database schema
			
			mSQL.connect("jimegaraidsas", "jimegaraidsas"); // change to your database user and password


To create database (for example with user jimegaraidsas

$ mysql -u jimegaraidsas -p < jigmmegaraid.sql

Enter password:


To run
# java -jar JIgmMegaRaid.jar
Oct 16, 2017 4:35:22 PM igam.JIgmMegaRaid main
INFO: JIgmMegaRaid start
Oct 16, 2017 4:35:22 PM igam.RunLinuxCmd execute
INFO: Execute: /usr/sbin/megasasctl -v
Oct 16, 2017 4:35:22 PM igam.RunLinuxCmd execute
INFO: Exit Value is 0
------- start dump: Controller
0: a0 PERC H710 Mini
1: bios:5.31.01_4.12.05.
2: fw:3.130.05-1796
3: encl:1 ldrv:1
4: rbld:30%
5: batt:FAULT, low voltage/3935mV/40C
------- end dump: Controller
------- start dump: Config Raid
0: a0d0 931GiB
1: RAID 1 1x2 optimal
2: row 0: a0e32s0 a0e32s1
3: unconfigured: a0e32s2
------- end dump: Config Raid
------- start dump: HD
0: a0e32s0
1: SEAGATE ST1000NM0001 931GiB a0d0 online
2: a0e32s1
3: SEAGATE ST1000NM0023 931GiB a0d0 online
4: a0e32s2
5: SEAGATE ST1000NM0001 931GiB ready
------- end dump: HD
Mon Oct 16 16:35:23 WIB 2017 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: connecting to mysql ok
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawcontroller ok: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawhd 1 ok: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawhd 3 ok: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawhd 5 ok: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawconfig 0: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawconfig 1: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawconfig 2: 1
Oct 16, 2017 4:35:24 PM igam.JIgmMegaRaid main
INFO: insert rawconfig 3: 1


Create root cron to execute this java every sunday at 0:00

0 0 * * sun /usr/bin/java -jar /root/java/JIgmMegaRaid.jar


Log file can be found at /root/java/jigmmegaraid.log


Todo: create PHP appliciation to show Mega Raid Status
