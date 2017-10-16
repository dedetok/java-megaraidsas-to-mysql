package igam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JIgmMegaRaid {

	protected static final Logger myLog = Logger.getLogger(JIgmMegaRaid.class.getName());
	private static FileHandler fh;
	private static String fn = "/root/java/jigmmegaraid.log"; // Fix file name
	private static java.sql.Date mDate;
	private static String mQuery="";
	private static int mNumUpdate= 0;
	private static int mKey=0;

	public static void main(String[] args) {
		mDate = new java.sql.Date((new java.util.Date()).getTime());
		
    	try {
    		// Handling Logger
    		//todayDate = new java.util.GregorianCalendar ();	
			fh = new FileHandler(fn, true);
	    	// true to append exisitng file
			java.util.logging.Formatter formatter = new BriefFormatter(); 
			fh.setFormatter(formatter);
			myLog.addHandler(fh);
	    	
			myLog.log(Level.INFO, "JIgmMegaRaid start");
			
			RunLinuxCmd rCmd = new RunLinuxCmd(myLog);
			
			rCmd.execute("/usr/sbin/megasasctl -v");
			
			JIgmMegaRaidHelper mHelper = new JIgmMegaRaidHelper(rCmd.mResultCmd.mCmdStream, rCmd.mResultCmd.mCmdError);
			mHelper.dumpResult("Controller", mHelper.listController);
			mHelper.dumpResult("Config Raid", mHelper.listRaid);
			mHelper.dumpResult("HD", mHelper.listHD);
			
			MySQLHelper mSQL = new MySQLHelper("jimegaraidsas");		
			mSQL.connect("jimegaraidsas", "jimegaraidsas");
			
			myLog.log(Level.INFO, "connecting to mysql ok");
						
			/*
			 * OK insert and get key into table jilog
			 *   `jiid` INT NOT NULL AUTO_INCREMENT,
			 *   `jidate` DATETIME NULL
			 */
			mQuery = "INSERT INTO jilog (jidate) values ('"+mDate+"');";
			//myLog.log(Level.INFO, "SQL: "+mQuery); // debug
			mKey = mSQL.executeAutoIncrementSQL(mQuery);			
			myLog.log(Level.INFO, "key is: "+Integer.toString(mKey)); // debug			
			
			/*
			 * Insert Raid controller log into table rawcontroller
			 * `jilog_jiid` INT NOT NULL,
			 * `t1` VARCHAR(45) NULL,
			 * `t2` VARCHAR(45) NULL,
			 * `t3` VARCHAR(45) NULL,
			 * `t4` VARCHAR(45) NULL,
			 * `t5` VARCHAR(45) NULL,
			 * `t6` VARCHAR(45) NULL,
			 */
			mQuery = "INSERT INTO rawcontroller(jilog_jiid, t1, t2, t3, t4, t5, t6) values (?,?,?,?,?,?,?);";
			mNumUpdate = mSQL.executeUpdate(mQuery, mKey, mHelper.listController);
			myLog.log(Level.INFO, "insert rawcontroller ok: "+Integer.toString(mNumUpdate)); // debug
			

			/*
			 * Insert hd log into table rawhd
			 * `jilog_jiid` INT NOT NULL,
			 * `t1` VARCHAR(45) NULL,
			 * `t2` VARCHAR(45) NULL
			 */
			mQuery = "INSERT INTO rawhd(jilog_jiid, t1, t2) values (?,?,?);";
			for (int i=0;i<mHelper.listHD.size();i++) {
				ArrayList<String> tmpList = new ArrayList<String>();
				tmpList.add(mHelper.listHD.get(i)); // id hd -> t1
				i++; // content
				tmpList.add(mHelper.listHD.get(i)); // content -> t2
				mNumUpdate = mSQL.executeUpdate(mQuery, mKey, tmpList);
				myLog.log(Level.INFO, "insert rawhd "+Integer.toString(i)+" ok: "+Integer.toString(mNumUpdate)); // debug
			}

			/*
			 * Insert raid config log into table rawconfig
			 * `jilog_jiid` INT NOT NULL,
			 * `t1` VARCHAR(45) NULL
			 */
			mQuery = "INSERT INTO rawconfig(jilog_jiid, t1) values (?,?);";
			for (int i=0;i<mHelper.listRaid.size();i++) {
				ArrayList<String> tmpList = new ArrayList<String>();
				tmpList.add(mHelper.listRaid.get(i)); // -> t1
				mNumUpdate = mSQL.executeUpdate(mQuery, mKey, tmpList);
				myLog.log(Level.INFO, "insert rawconfig "+Integer.toString(i)+": "+Integer.toString(mNumUpdate)); // debug
			}

    	} catch (SecurityException e) {
			myLog.log(Level.INFO, e.toString());
		} catch (IOException e) {
			myLog.log(Level.INFO, e.toString());
		} catch (NoSuchElementException e) {
			myLog.log(Level.INFO, e.toString());
		} catch (ClassNotFoundException e) {
			myLog.log(Level.INFO, e.toString());
		} catch (SQLException e) {
			myLog.log(Level.INFO, e.toString());
		}
	}
	
}
