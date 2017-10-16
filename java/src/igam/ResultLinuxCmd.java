package igam;

import java.util.ArrayList;

public class ResultLinuxCmd {

	ArrayList<String> mCmdStream;
	ArrayList<String> mCmdError;
	
	int mMinCap = 8;
	
	public ResultLinuxCmd() {
		mCmdStream = new ArrayList<String>();
		mCmdStream.ensureCapacity(mMinCap);
		mCmdError = new ArrayList<String>();
		mCmdError.ensureCapacity(mMinCap);
	}
	
	
}
