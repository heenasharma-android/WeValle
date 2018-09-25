package com.albaniancircle;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Sumit on 27/08/2015.
 */
public class AlbanianConstants {

//    public static final String SENDER_ID = "671994544220";
    public static final String SENDER_ID = "22840128177";

    public static final String TAB_1_TAG = "Local_tab";
    public static final String TAB_2_TAG = "Activity_tab";
    public static final String TAB_3_TAG = "Messages_tab";


    // Splash Screen Timeout
    public static final int SplashTimeout = 3000;
    public static final String TAG = "sumit";



//    public static  final String base_url="http://albaniancircle.com/acappadmin/apicallV2Android.php";
//    public static final String base_url = "http://culturalsinglesapps.com/apicallboth.php";
//    public static final String base_url = "http://culturalsinglesapps.com/CSAPICalls.php";
    public static final String base_url = "http://culturalsinglesapps.com/WeValleAPICalls.php";


    public static final String EXTRA_USERMODEL = "usermodel";
    public static final String EXTRA_USERID = "userid";
    public static final String EXTRA_PROFILEVISITEDID = "ProfileVisitedId";
    public static final String EXTRA_EMAILOPTIN = "EmailOptIn";
    public static final String EXTRA_PROFILEVISITEDNAME = "ProfileVisitedName";
    public static final String EXTRA_PROFILEVISITEDIMAGE = "ProfileVisitedImage";
    public static final String EXTRA_PROFILEVISITEDAGE = "ProfileVisitedage";
    public static final String EXTRA_PROFILEVISITEDQUOTE = "ProfileVisitedquote";
    public static final String EXTRA_PROFILEVISITEDINTEREST = "ProfileVisitedinterest";
    public static final String EXTRA_MATCHMAKINGLIST = "matchmakinglist";
    public static final String EXTRA_CURRENTTAB_TAG = "currenttabtag";
    public static final String EXTRA_IMAGESLIST = "userimageslist";
    public static final String EXTRA_LOCALPAGENAME = "localpagename";
    public static final String EXTRA_LASTIMAGE = "extralastimage";
//    public static final String EXTRA_FAQ = "FAQ";
    public static final String EXTRA_TOS = "TOS";
    public static final String EXTRA_WEVALLE = "WEVALLE";
    public static final String EXTRA_PRIVACY = "PRIVACY";
    public static final String EXTRA_PAYMENTPRICE = "paymentprice";

    public static ArrayList<String> matchmaking_string_arraylist;


    public static final String EXTRA_HEIGHT = "userheight";
    public static final String EXTRA_RELIGION = "userreligion";
    public static final String EXTRA_OTHERLANGUAGES = "userotherlanguages";
    public static final String EXTRA_PETS = "userpets";
    public static final String EXTRA_DRINKS = "userdrinks";
    public static final String EXTRA_SMOKES = "usersmokes";
    public static final String EXTRA_INTEREST = "userinterest";
    public static final String EXTRA_FAVQUOTE = "userfavquote";
    public static final String EXTRA_DESC = "userdesc";


//        public static  final String AppName="Habibi";
//    public static final String AppDomain = "@culturalsinglesapps.com";


    public static final String AppName = "Albanian";
    public static final String AppDomain = "@wevalle.com";

    public static final String EXTRA_NOTIFICATIONTYPE = "notificationtype";


    /*
     * Regx to Validate Email Address
	 */

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");


    public static String EXTRA_ChatMessage_alert = "ChatMessage_alert";
    public static String EXTRA_Chat_MessageTab_alert = "Chat_MessageTab_alert";
    public static String EXTRA_Chat_NonMessageTab_alert = "Chat_NonMessageTab_alert";


    public static ArrayList<String> getMatchList() {
        matchmaking_string_arraylist = new ArrayList<>();

        //    matchmaking_string_arraylist.clear();
        matchmaking_string_arraylist.add("Close to family--Far from family");
        matchmaking_string_arraylist.add("Love--Lottery");
        matchmaking_string_arraylist.add("I'll pay on the first date--To each their own");
        matchmaking_string_arraylist.add("Morning person--Night owel");
        matchmaking_string_arraylist.add("Independent--Dependent");
        matchmaking_string_arraylist.add("Goal oriented--Leave it to destiny");
        matchmaking_string_arraylist.add("With my heart--With my brain");
        matchmaking_string_arraylist.add("Tolerant--Intolerant");
        matchmaking_string_arraylist.add("Charitable--Greedy");
        matchmaking_string_arraylist.add("Conditional--Unconditional");
        matchmaking_string_arraylist.add("Go out on dates--Netflix and Chill");
        matchmaking_string_arraylist.add("Friends--Family");
        matchmaking_string_arraylist.add("Messy--Organized");
        matchmaking_string_arraylist.add("I'm a communicator--I'm reserved");
        matchmaking_string_arraylist.add("Change it up--Stick to routine");
        matchmaking_string_arraylist.add("I do a lot in my spare time--I relax in my spare time");
        matchmaking_string_arraylist.add("Forgiving--No second chance");
        matchmaking_string_arraylist.add("Traditional--Modern");
        matchmaking_string_arraylist.add("City--Suburbs");
        matchmaking_string_arraylist.add("Order out--Home cooked meal");

        return matchmaking_string_arraylist;

    }
}