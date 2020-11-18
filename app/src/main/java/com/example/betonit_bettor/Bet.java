package com.example.betonit_bettor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Bet")
public class Bet extends ParseObject {
    public static final String KEY_BET_NAME = "bet_Name";
    public static final String KEY_BET_AMOUNT = "bet_Amount";
    public static final String KEY_BET_STATUS = "bet_Status";
    public static final String KEY_BET_DESCRIPTION = "bet_Desc";
    public static final String KEY_BET_CHALLENGER = "bet_User_Challenger";
    public static final String KEY_BET_CHALLENGEE = "bet_User_Challengee";
//    public static final String KEY_BET_START = "bet_Start";
//    public static final String KEY_BET_RESC = "bet_Resc";
//    public static final String KEY_BET_END = "bet_End";
//    public static final String KEY_BET_TYPE = "bet_Type";

    public String getBetName() {
        return getString(KEY_BET_NAME);
    }

    public void setBetName(String bet_Name)
    {
        put(KEY_BET_NAME, bet_Name);
    }

    public int getBetAmount() {
        return getInt(KEY_BET_AMOUNT);
    }

    public void setBetAmount(int bet_Amount)
    {
        put(KEY_BET_AMOUNT, bet_Amount);
    }

    public String getBetStatus() {
        return getString(KEY_BET_STATUS);
    }

    public void setBetStatus(String bet_Status)
    {
        put(KEY_BET_STATUS, bet_Status);
    }

    public String getBetDescription() {
        return getString(KEY_BET_DESCRIPTION);
    }

    public void setBetDescription(String bet_Desc)
    {
        put(KEY_BET_DESCRIPTION, bet_Desc);
    }

    public ParseUser getBetChallenger()
    {
        return getParseUser(KEY_BET_CHALLENGER);
    }

    public void setBetChallenger(ParseUser challenger)
    {
        put(KEY_BET_CHALLENGER, challenger);
    }

    public ParseUser getBetChallengee()
    {
        return getParseUser(KEY_BET_CHALLENGEE);
    }

    public void setBetChallengee(ParseUser challengee)
    {
        put(KEY_BET_CHALLENGEE, challengee);
    }

//    public Date getBetStart() {
//        return  getDate(KEY_BET_START);
//    }
//
//    public void setBetStart(Date bet_Start) {
//        put(KEY_BET_START, bet_Start);
//    }
//
//    public Date getBetEnd() {
//        return  getDate(KEY_BET_END);
//    }
//
//    public void setBetEnd(Date bet_End) {
//        put(KEY_BET_START, bet_End);
//    }
//
//    public Date getBetResc() {
//        return  getDate(KEY_BET_RESC);
//    }
//
//    public void setBetResc(Date bet_Resc) {
//        put(KEY_BET_RESC, bet_Resc);
//    }
//
//    public String getBetType() {
//        return getString(KEY_BET_TYPE);
//    }
//
//    public void setBetType(String bet_Type)
//    {
//        put(KEY_BET_TYPE, bet_Type);
//    }
}
