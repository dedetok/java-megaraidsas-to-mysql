package igam;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunLinuxCmd {
	Runtime r = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	Process p =null;

	Logger myLog;
	
	ResultLinuxCmd mResultCmd = new ResultLinuxCmd();

    public RunLinuxCmd(Logger myLog) {
    	this.myLog = myLog;
		r = Runtime.getRuntime();
    }
    
    String readStatusMegaRaid( ) {
    	return "";
    }


    void execute(String mCmd) {
    	myLog.log(Level.INFO, "Execute: "+mCmd);
		try {
			p = r.exec(mCmd);
		} catch (IOException e) {
			myLog.log(Level.INFO, e.toString());
		}
		isr = new InputStreamReader(p.getInputStream());
		br = new BufferedReader(isr);
		String tmp="";
		try {
			while ((tmp = br.readLine()) != null) {
				mResultCmd.mCmdStream.add(tmp);
				//myLog.log(Level.INFO, tmp);
			}
		} catch (IOException e) {
			myLog.log(Level.INFO, e.toString());
		}
		isr = new InputStreamReader(p.getErrorStream());
		br = new BufferedReader(isr);
		tmp="";
		try {
			while ((tmp = br.readLine()) != null) {
				mResultCmd.mCmdError.add(tmp);
				//myLog.log(Level.INFO, tmp);
			}
		} catch (IOException e) {
			myLog.log(Level.INFO, e.toString());
		}
		// waitFor() method is used to wait till the process returns the exit value
		try {
			int exitValue = p.waitFor();
			myLog.log(Level.INFO, "Exit Value is " + exitValue);
		} catch (InterruptedException ex) {
			myLog.log(Level.INFO, "InterruptedException: "+ex.getMessage());
		}

    }
    

}
