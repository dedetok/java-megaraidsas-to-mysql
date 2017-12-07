package igam;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class JIgmMegaRaidHelper {

	/*
	 * line 0 contains RAID Controller
	 * brandHDTag
	 * raidTag
	 */
	final String[] raidControllerTag = { 
			"bios:",
			"fw:",
			"encl:",
			"rbld:",
			"batt:"
		};
	final String[] brandHDTag = { 
			"FUJITSU",
			"SEAGATE"
		};
	final String[] raidTag = {
			"RAID",
			"row",
			"unconfigured"
		};
	
	ArrayList<String> listController;
	ArrayList<String> listHD;
	ArrayList<String> listRaid;
	
	JIgmMegaRaidHelper(ArrayList<String>listStream, ArrayList<String>listError) throws NoSuchElementException{
		listController = new ArrayList<String>();
		listHD = new ArrayList<String>();
		listRaid = new ArrayList<String>();
		
		if (!(listStream.size()>0 || listError.size()>0)) {
			throw new NoSuchElementException();			
		}; 
		if (listStream.size()>0) {
			populate(listStream);
		} 
		if (listError.size()>0) {
			populate(listError);
		} 
		
	}
	
	void populate(ArrayList<String> mList) {

		if (mList.size()>0) {
			for (int i=0;i<mList.size();i++) {
				String tmp = mList.get(i);
				int containBrandHDTag = cString2Strings(tmp, brandHDTag);
				int containRaidTag = cString2Strings(tmp, raidTag);
				if (containRaidTag>=0) {
					// RAID config
					StringTokenizer mST = new StringTokenizer(tmp);
					String mS = "";
					boolean isFirst = true;
					while (mST.hasMoreElements()) {
						String mNT = mST.nextToken();
						int mInt = cString2Strings(mNT, raidTag);
						if (!(mST.hasMoreElements())) {
							mS = mS + " "+ mNT;
							listRaid.add(mS);							
						} else if (mInt>=0) {
							if (!isFirst) {
								listRaid.add(mS);
							} 
							mS = mNT;

						} else {
							if (isFirst) {
								mS = mNT;
							} else {
								mS = mS + " "+ mNT;
							}
						}
						isFirst = false;

					}
				} else if (containBrandHDTag>=0) {
					// HD 
					StringTokenizer mST = new StringTokenizer(tmp);
					String mS = "";
					boolean isFirst = true;
					while (mST.hasMoreElements()) {
						String mNT = mST.nextToken();
						int mInt = cString2Strings(mNT, brandHDTag);
						if (!(mST.hasMoreElements())) {
							mS = mS + " "+ mNT;
							listHD.add(mS);							
						} else if (mInt>=0) {
							if (!isFirst) {
								listHD.add(mS);
							} 
							mS = mNT;

						} else {
							if (isFirst) {
								mS = mNT;
							} else {
								mS = mS + " "+ mNT;
							}
						}
						isFirst = false;

					}
				} else {
					// Raid controller
					// e.q: a0       PERC H710 Mini           bios:5.31.01_4.12.05. fw:3.130.05-1796 encl:1 ldrv:1  rbld:30% batt:good/3920mV/39C
					StringTokenizer mST = new StringTokenizer(tmp);
					String mS = "";
					boolean isFirst = true;
					while (mST.hasMoreElements()) {
						String mNT = mST.nextToken();
						int mInt = cString2Strings(mNT, raidControllerTag);
						
						if (isFirst) {
							mS=mNT;
						} else if (mInt<0) {
							mS = mS + " "+ mNT;
						} else if (mInt>=0) {
							// found a new tag
							listController.add(mS);
							mS=mNT;							
						}
						if (!mST.hasMoreElements()) {
							// last word
							listController.add(mS);
						}
						
						isFirst = false;
						

					}
				}
			}
		}
	}
	
	int cString2Strings(String mStr, String[] mStrs) {
		for (int i=0;i<mStrs.length;i++) {
			if (mStr.contains(mStrs[i])) {
				return i;
			}
		}
		return -1;
	}
	
	void dumpResult(String mTitle, ArrayList<String> dStrings) {
		// dump for debug
		System.out.println("------- start dump: "+mTitle);
		for (int i=0;i<dStrings.size();i++ ) {
			System.out.println(i+": "+dStrings.get(i));
		}
		System.out.println("------- end dump: "+mTitle);
	}
}
